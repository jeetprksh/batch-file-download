package com.jeetprksh.file.download;

import java.util.List;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class DownloadSet {

  @SerializedName("urls")
  @Expose
  private List<String> urls = null;
  @SerializedName("folderName")
  @Expose
  private String folderName;

  public List<String> getUrls() {
    return urls;
  }

  public void setUrls(List<String> urls) {
    this.urls = urls;
  }

  public String getFolderName() {
    return folderName;
  }

  public void setFolderName(String folderName) {
    this.folderName = folderName;
  }

}