package com.jeetprksh.file.download;

import java.util.List;
import java.util.concurrent.Callable;
import java.util.concurrent.ConcurrentLinkedQueue;

class DownloadTask implements Callable<Void> {

  private final FileStore fileStore;
  private final List<String> fileUrls;
  private final ConcurrentLinkedQueue<String> downloadLog;

  public DownloadTask(FileStore fileStore, List<String> fileUrls, ConcurrentLinkedQueue<String> downloadLog) {
    this.fileStore = fileStore;
    this.fileUrls = fileUrls;
    this.downloadLog = downloadLog;
  }

  @Override
  public Void call() throws Exception {
    try {
      for (String fileUrl : fileUrls) {
        DownloadFile downloadFile = new DownloadFile(fileUrl);
        DownloadedFile downloadedFile = downloadFile.execute();
        fileStore.createFile(downloadedFile);
        downloadLog.add(fileUrl);
      }
    } catch (Exception ex) {
      ex.printStackTrace();
    }
    return Void.TYPE.newInstance();
  }

}
