package com.huozige.lab.container.utilities;

import android.net.Uri;

public class HACDownloadTask {

    public interface IHACDownloadHandler{
        void onSuccess(Uri localFileUri);
        void onError(String fileName, String url);
    }

    public long taskId;
    public String url;
    public String fileName;

    public int statusCode;
    public IHACDownloadHandler handler;

    public void registryHandler(IHACDownloadHandler handler) {
        this.handler = handler;
    }

}
