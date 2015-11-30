package com.hexad.snackmate.Utils.Cache;

/**
 * Created by yilunliu on 11/14/15.
 */
import java.util.Collections;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Map.Entry;
import android.graphics.Bitmap;
import android.util.Log;
import android.util.LruCache;

public class MemoryCache {

    public static  MemoryCache defaultCache = new MemoryCache();

    private static final String TAG = "MemoryCache";

    // Last argument true for LRU ordering
    private LruCache<String,Bitmap> cache;

    // Current allocated size
    private long size = 0;

    // Max memory in bytes
    private int limit = 1000000;

    private MemoryCache() {
        // Use 25% of available heap size
        setLimit((int)Runtime.getRuntime().maxMemory() / 1024);
        cache = new LruCache<String,Bitmap>(limit){
            @Override
            protected int sizeOf(String key, Bitmap value) {
                return value.getByteCount()/1024;
            }
        };
    }

    public void setLimit(int new_limit) {
        limit = new_limit;
        Log.i(TAG, "MemoryCache will use up to " + limit / 1024.  + "MB");
    }

    public Bitmap get(String id){
        return cache.get(id);
    }


    public void put(String id, Bitmap bitmap){
        if(get(id) == null){
            cache.put(id, bitmap);
        }
    }

//    private void checkSize() {
//        Log.i(TAG, "cache size=" + size + " length=" + cache.size());
//        if (size > limit) {
//            // Least recently accessed item will be the first one iterated
//            Iterator<Entry<String, Bitmap>> iter = cache.entrySet().iterator();
//            while (iter.hasNext()) {
//                Entry<String, Bitmap> entry = iter.next();
//                size -= getSizeInBytes(entry.getValue());
//                iter.remove();
//                if (size <= limit)
//                    break;
//            }
//            Log.i(TAG, "Clean cache. New size " + cache.size());
//        }
//    }

    public void clear() {
        try {
            cache.evictAll();
            size = 0;
        } catch (NullPointerException ex) {
            ex.printStackTrace();
        }
    }

    long getSizeInBytes(Bitmap bitmap) {
        if (bitmap == null)
            return 0;
        return bitmap.getRowBytes() * bitmap.getHeight();
    }
}
