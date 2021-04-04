package com.jeetprksh.file.download;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.sql.Date;
import java.util.Queue;
import java.util.logging.Logger;

class DownloadLogWriter {

  private final Logger logger = Logger.getLogger(DownloadLogWriter.class.getName());

  private final File downloadDirectory;

  public DownloadLogWriter(File downloadDir) {
    this.downloadDirectory = downloadDir;
  }

  public void writeDownloadLogFile(Queue<String> logs) throws IOException {
    Date date = new Date(System.currentTimeMillis());
    File logFile = new File(downloadDirectory.toPath() + "/downloadLog-" + date.toString() + ".txt");
    logger.info("Appending download logs to " + logFile.getAbsolutePath());

    try(FileWriter logFileWriter = new FileWriter(logFile, true)) {
      for (String log : logs) {
        logFileWriter.write(log + "\n");
      }
    }
  }

  public static DownloadLogWriter create(String appName) {
    File downloadDir = new File(System.getProperty("user.home") + "/" + appName);
    downloadDir.mkdirs();
    return new DownloadLogWriter(downloadDir);
  }

}
