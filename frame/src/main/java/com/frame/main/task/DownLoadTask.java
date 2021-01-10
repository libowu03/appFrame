package com.frame.main.task;

import android.os.AsyncTask;
import android.os.Environment;
import android.util.Log;


import com.frame.main.callback.DownloadListener;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.RandomAccessFile;

import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

public class DownLoadTask extends AsyncTask<String,Integer,Integer> {
    public static final int TYPE_SUCCESS = 0;
    public static final int TYPE_FAILED = 1;
    public static final int TYPE_PAUSED = 2;
    public static final int TYPE_CANCELED = 3;


    private DownloadListener listener;
    private boolean isCanceled = false;
    private boolean isPaused = false;
    private int lastProgress;
    private String fileName;
    private String downloadPath;

    public DownLoadTask(DownloadListener listener){
        this.listener = listener;
    }

    @Override
    protected Integer doInBackground(String... strings) {
        InputStream is = null;
        RandomAccessFile saveFile = null;
        File file = null;
        try {
            long downloadedLength = 0;
            String downloadUrl = strings[0];
            String fileName = downloadUrl.substring(downloadUrl.lastIndexOf("/"));
            this.fileName = fileName;
            String directory = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOWNLOADS).getPath();
            downloadPath = directory + fileName;
            file = new File(directory + fileName);
            if (file.exists()){
                downloadedLength = file.length();
            }
            long contentLength = getContentLength(downloadUrl);
            if (contentLength == 0){
                return TYPE_FAILED;
            }else if (contentLength == downloadedLength){
                return TYPE_SUCCESS;
            }

            OkHttpClient okHttpClient = new OkHttpClient();
            Request request = new Request.Builder()
                    .addHeader("RANGE","bytes="+downloadedLength+"-")
                    .url(downloadUrl)
                    .build();
            Response response = okHttpClient.newCall(request).execute();
            if (request != null){
                is = response.body().byteStream();
                saveFile = new RandomAccessFile(file,"rw");
                byte[] b = new byte[1024];
                int total = 0;
                int len;
                while ((len = is.read(b)) != -1){
                    if (isCanceled){
                        return TYPE_CANCELED;
                    }else if (isPaused){
                        return TYPE_PAUSED;
                    }else {
                        total += len;
                        saveFile.write(b,0,len);
                        int progress = (int) ((total + downloadedLength) * 100 /contentLength);
                        publishProgress(progress);
                    }
                }
                response.body().close();
                return TYPE_SUCCESS;
            }

        }catch (Exception e){
            Log.e("错误日志","发生位置：DownLoadTask.java,原因："+e.getMessage());
        }finally {
            try {
                if (is != null){
                    is.close();
                }
                if (saveFile != null){
                    saveFile.close();
                }
                if (isCanceled && file != null){
                    file.delete();
                }
            }catch (Exception e){
                Log.e("错误日志","发生位置：DownLoadTask.java,原因："+e.getMessage());
            }
        }
        return null;
    }

    @Override
    protected void onProgressUpdate(Integer... values) {
        int progress = values[0];
        if (progress > lastProgress){
            listener.onProgress(progress);
            lastProgress = progress;
        }
    }

    @Override
    protected void onPostExecute(Integer integer) {
        switch (integer){
            case TYPE_SUCCESS:
                listener.onSuccess(fileName,downloadPath);
                break;
            case TYPE_CANCELED:
                listener.onCanceled(fileName,downloadPath);
                break;
            case TYPE_FAILED:
                listener.onFailed(fileName,downloadPath);
                break;
            case TYPE_PAUSED:
                listener.onPaused(fileName,downloadPath);
                break;
             default:
                 break;
        }
    }

    public void pauseDownLoad(){
        isPaused = true;
    }

    public void cancelDownLad(){
        isCanceled = true;
    }

    private long getContentLength(String downloadUrl) throws IOException{
        OkHttpClient okHttpClient = new OkHttpClient();
        Request request = new Request.Builder()
                .url(downloadUrl)
                .build();
        Response response = okHttpClient.newCall(request).execute();
        if (response != null && response.isSuccessful()){
            long contentLength = response.body().contentLength();
            response.body().close();
            return contentLength;
        }
        return 0;
    }
}
