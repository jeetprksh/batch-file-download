package com.jeetprksh.file.download.config;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class File {

  @SerializedName("fileName")
  @Expose
  private final String fileName;

  @SerializedName("url")
  @Expose
  private final String url;

  public File(String fileName, String url) {
    this.fileName = fileName;
    this.url = url;
  }

  public String getFileName() {
    return fileName;
  }

  public String getUrl() {
    return url;
  }

}
