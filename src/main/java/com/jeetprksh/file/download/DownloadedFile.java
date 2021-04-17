package com.jeetprksh.file.download;

import java.io.InputStream;

class DownloadedFile {

  private String fileName;

  private final String extension;
  private final InputStream fileContent;

  public DownloadedFile(String extension, InputStream fileContent) {
    this.extension = extension;
    this.fileContent = fileContent;
  }

  public InputStream getFileContent() {
    return fileContent;
  }

  public String getExtension() {
    return extension;
  }

  public String getFileName() {
    return fileName;
  }

  public void setFileName(String fileName) {
    this.fileName = fileName;
  }
}
