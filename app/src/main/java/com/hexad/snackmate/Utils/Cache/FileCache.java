package com.hexad.snackmate.Utils.Cache;

/**
 * Created by yilunliu on 11/14/15.
 */
import java.io.File;
import android.content.Context;
import android.os.Environment;
import android.util.Log;

public class FileCache {

    private File cacheDir;

    public FileCache(Context context) {
        // Find the dir to save cached images
        String state = android.os.Environment.getExternalStorageState();

        cacheDir = context.getCacheDir();

        if (!cacheDir.exists()){
            boolean isSuccess = cacheDir.mkdirs();
            Log.d("Directory", "Created :" + isSuccess);

        }
        Log.d("", "" + cacheDir.exists()+ cacheDir.isDirectory());
    }

    public File getFile(String url) {
        String filename = String.valueOf(url.hashCode());
        // String filename = URLEncoder.encode(url);
        File f = new File(cacheDir, filename);
        return f;

    }

    public void clear() {
        File[] files = cacheDir.listFiles();
        if (files == null)
            return;
        for (File f : files)
            f.delete();
    }

}
