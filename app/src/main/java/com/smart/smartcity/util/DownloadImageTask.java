package com.smart.smartcity.util;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.util.Log;

import com.smart.smartcity.context.global.IDownloadImageContext;

import java.io.InputStream;
import java.net.URL;

public class DownloadImageTask extends AsyncTask<String, Void, Bitmap> {
    public IDownloadImageContext context;
    private int id;

    public DownloadImageTask(IDownloadImageContext context, int id) {
        this.context = context;
        this.id = id;
    }

    @Override
    protected Bitmap doInBackground(String... urls) {
        String url = urls[0];
        Bitmap image = null;

        try {
            InputStream stream = new URL(url).openStream();
            image = BitmapFactory.decodeStream(stream);
        } catch (Exception e) {
            Log.e("Error", e.getMessage());
            e.printStackTrace();
        }

        return image;
    }

    @Override
    protected void onPostExecute(Bitmap result) {
        context.onImageDownloaded(result, id);
    }
}
