package com.jeetprksh.file.download;

import org.apache.commons.io.FileUtils;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.util.logging.Logger;

class FileStore {

  private final Logger logger = Logger.getLogger(FileStore.class.getName());

  private final File downloadDirectory;

  private FileStore(File downloadDirectory) {
    this.downloadDirectory = downloadDirectory;
  }

  public void createFile(DownloadedFile file) throws IOException {
    String fileNameExt = file.getFileName() + "." + file.getExtension();
    File imgFile = new File(downloadDirectory.toPath() + "/" + sanitizeName(fileNameExt));
    InputStream inputStream = file.getFileContent();
    FileUtils.copyInputStreamToFile(inputStream, imgFile);
    logger.info("File saved at " + imgFile.getAbsolutePath());
  }

  public static FileStore create(String appName, String folderName) {
    File downloadDir = new File(System.getProperty("user.home") + "/" + appName + "/" + sanitizeName(folderName));
    downloadDir.mkdirs();
    return new FileStore(downloadDir);
  }

  private static String sanitizeName(String fileName) {
    return fileName.replaceAll("[\\/:*?<>|\"]", "_");
  }
}
