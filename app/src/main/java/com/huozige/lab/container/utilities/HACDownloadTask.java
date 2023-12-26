package com.huozige.lab.container.utilities;

import java.io.File;

public class HACDownloadTask {

    public interface IHACDownloadHandler{
        void onSuccess(File targetFile);
        void onError(String fileName, String url);
    }

    public long taskId;
    public String url;
    public String fileName;
    public File targetFile;
    public int statusCode;
    public IHACDownloadHandler handler;

    public void registryHandler(IHACDownloadHandler handler) {
        this.handler = handler;
    }

}
