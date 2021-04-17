package com.jeetprksh.file.download;

import com.jeetprksh.file.download.config.File;

import java.util.List;
import java.util.Objects;
import java.util.concurrent.Callable;
import java.util.concurrent.ConcurrentLinkedQueue;

class DownloadTask implements Callable<Void> {

  private final FileStore fileStore;
  private final List<File> files;
  private final ConcurrentLinkedQueue<String> downloadLog;

  public DownloadTask(FileStore fileStore, List<File> files, ConcurrentLinkedQueue<String> downloadLog) {
    this.fileStore = fileStore;
    this.files = files;
    this.downloadLog = downloadLog;
  }

  @Override
  public Void call() throws Exception {
    try {
      for (File file : files) {
        DownloadFile downloadFile = new DownloadFile(file.getUrl());
        DownloadedFile downloadedFile = downloadFile.execute();

        String fileName = Objects.isNull(file.getFileName()) ?
                file.getUrl().substring(file.getUrl().lastIndexOf("/") + 1) : file.getFileName();
        downloadedFile.setFileName(fileName);

        fileStore.createFile(downloadedFile);
        downloadLog.add(file.getUrl());
      }
    } catch (Exception ex) {
      ex.printStackTrace();
    }
    return Void.TYPE.newInstance();
  }

}
