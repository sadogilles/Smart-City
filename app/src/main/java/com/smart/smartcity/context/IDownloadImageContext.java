package com.smart.smartcity.context;

import android.graphics.Bitmap;

public interface IDownloadImageContext {
    void onImageDownloaded(Bitmap bitmap, int id);
}
