package com.jeetprksh.file.download.config;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class DownloadSet {

  @SerializedName("files")
  @Expose
  private final List<File> files;

  @SerializedName("folderName")
  @Expose
  private final String folderName;

  public DownloadSet(List<File> files, String folderName) {
    this.files = files;
    this.folderName = folderName;
  }

  public List<File> getFiles() {
    return files;
  }

  public String getFolderName() {
    return folderName;
  }

}
