package com.frame.main.callback;

public interface DownloadListener {
    void onProgress(int progress);
    void onSuccess(String fileName,String url);
    void onFailed(String fileName,String url);
    void onPaused(String fileName,String url);
    void onCanceled(String fileName,String url);
}
