package com.jeetprksh.file.download;

import com.google.gson.Gson;
import org.apache.commons.io.IOUtils;

import java.io.InputStream;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.List;
import java.util.concurrent.ConcurrentLinkedQueue;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.stream.Collectors;

public class App {

  public static String APP_NAME = "Batch File Downloader";

  public static void main( String[] args ) throws Exception {

    AllDownloads allDownloads = readDownloadConfig();
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

    DownloadLogWriter logWriter = DownloadLogWriter.create(APP_NAME);
    logWriter.writeDownloadLogFile(downloadLog);
  }

  private static AllDownloads readDownloadConfig() throws Exception {
    InputStream stream = ClassLoader.getSystemResourceAsStream("downloads.json");
    return new Gson().fromJson(IOUtils.toString(stream, StandardCharsets.UTF_8), AllDownloads.class);
  }
}
