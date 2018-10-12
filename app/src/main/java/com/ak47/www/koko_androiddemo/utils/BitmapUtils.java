package com.ak47.www.koko_androiddemo.utils;

import android.content.Context;


import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

public class BitmapUtils {
    /**
     * Creates the temporary image file in the cache directory.
     * @param context
     * @return The temporary image file.
     * @throws  IOException Thrown if there is an error creating the file
     */
    public static File createTempImageFile(Context context) throws IOException {
        String timeStamp = new SimpleDateFormat("yyyyMMdd_HHmmss",
                Locale.getDefault()).format(new Date());
        String imageFileName = "JPEG_" + timeStamp + "_";
        File storageDir = context.getExternalCacheDir();
        return File.createTempFile(
                imageFileName,  /* prefix前缀 */
                ".jpg",         /* suffix后缀 */
                storageDir      /* directory目标文件夹 */
        );
    }
}
