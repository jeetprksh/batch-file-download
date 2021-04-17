package com.jeetprksh.file.download;

import com.jeetprksh.file.download.config.AllDownloads;
import com.jeetprksh.file.download.config.DownloadConfig;
import com.jeetprksh.file.download.config.DownloadSet;
import com.jeetprksh.file.download.config.File;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.List;
import java.util.concurrent.ConcurrentLinkedQueue;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.stream.Collectors;

public class BatchFileDownloader {

  private final static int DOWNLOAD_CHUNK_SIZE = 5;
  private final static int THREAD_POOL_SIZE = 6;

  private final AllDownloads allDownloads;

  public BatchFileDownloader(AllDownloads allDownloads) {
    this.allDownloads = allDownloads;
  }

  public static void main(String[] args) throws Exception {
    AllDownloads allDownloads = DownloadConfig.createDefault();
    BatchFileDownloader batchFileDownloader = new BatchFileDownloader(allDownloads);
    batchFileDownloader.start();
  }

  public void start() throws InterruptedException, IOException {
    ExecutorService executorService = Executors.newFixedThreadPool(THREAD_POOL_SIZE);
    ConcurrentLinkedQueue<String> downloadLog = new ConcurrentLinkedQueue<>();
    List<DownloadTask> downloadTasks = new ArrayList<>();
    String appName = allDownloads.getAppName();

    for (DownloadSet downloadSet : allDownloads.getDownloadSets()) {
      List<File> files = downloadSet.getFiles();
      String folderName = downloadSet.getFolderName();

      Collections.shuffle(files);

      final AtomicInteger counter = new AtomicInteger(0);
      Collection<List<File>> partitionedUrls = files.stream()
              .collect(Collectors.groupingBy(it -> counter.getAndIncrement() / DOWNLOAD_CHUNK_SIZE))
              .values();

      FileStore fileStore = FileStore.create(appName, folderName);

      for (List<File> fileList : partitionedUrls) {
        DownloadTask task = new DownloadTask(fileStore, fileList, downloadLog);
        downloadTasks.add(task);
      }
    }

    executorService.invokeAll(downloadTasks);
    executorService.shutdown();

    if (allDownloads.getCreateLogs()) {
      DownloadLogWriter logWriter = DownloadLogWriter.create(allDownloads.getAppName());
      logWriter.writeDownloadLogFile(downloadLog);
    }
  }

}
