package com.jeetprksh.file.download;

import com.jeetprksh.file.download.config.AllDownloads;
import com.jeetprksh.file.download.config.DownloadConfig;
import com.jeetprksh.file.download.config.DownloadSet;

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

public class Downloader {

  public static String APP_NAME = "Batch File Downloader";

  private final AllDownloads allDownloads;

  public Downloader(AllDownloads allDownloads) {
    this.allDownloads = allDownloads;
  }

  public static void main(String[] args) throws Exception {
    AllDownloads allDownloads = DownloadConfig.createDefault();
    Downloader downloader = new Downloader(allDownloads);
    downloader.start();
  }

  private void start() throws InterruptedException, IOException {
    ExecutorService executorService = Executors.newFixedThreadPool(6);
    ConcurrentLinkedQueue<String> downloadLog = new ConcurrentLinkedQueue<>();
    List<DownloadTask> downloadTasks = new ArrayList<>();

    for (DownloadSet downloadSet : allDownloads.getDownloadSets()) {
      List<String> urls = downloadSet.getUrls();
      String folderName = downloadSet.getFolderName();

      Collections.shuffle(urls);

      final AtomicInteger counter = new AtomicInteger(0);
      Collection<List<String>> partitionedUrls = urls.stream()
              .collect(Collectors.groupingBy(it -> counter.getAndIncrement() / 5))
              .values();

      FileStore fileStore = FileStore.create(APP_NAME, folderName);

      for (List<String> urlList : partitionedUrls) {
        DownloadTask task = new DownloadTask(fileStore, urlList, downloadLog);
        downloadTasks.add(task);
      }
    }

    executorService.invokeAll(downloadTasks);
    executorService.shutdown();

    if (allDownloads.getCreateLogs()) {
      DownloadLogWriter logWriter = DownloadLogWriter.create(APP_NAME);
      logWriter.writeDownloadLogFile(downloadLog);
    }
  }

}
