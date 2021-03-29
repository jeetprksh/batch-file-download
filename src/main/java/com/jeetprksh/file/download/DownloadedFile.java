package com.jeetprksh.file.download;

import java.io.InputStream;

public class DownloadedFile {

  private final String fileName;
  private final InputStream fileContent;

  public DownloadedFile(String fileName, InputStream fileContent) {
    this.fileName = fileName;
    this.fileContent = fileContent;
  }

  public InputStream getFileContent() {
    return fileContent;
  }

  public String getFileName() {
    return fileName;
  }

}
