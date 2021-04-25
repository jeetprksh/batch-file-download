package com.jeetprksh.file.download.config;

import java.util.List;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class AllDownloads {

  @SerializedName("createLogs")
  @Expose
  private final Boolean createLogs;

  @SerializedName("appName")
  @Expose
  private final String appName;

  @SerializedName("downloadSets")
  @Expose
  private final List<DownloadSet> downloadSets;

  public AllDownloads(Boolean createLogs, String appName, List<DownloadSet> downloadSets) {
    this.createLogs = createLogs;
    this.appName = appName;
    this.downloadSets = downloadSets;
  }

  public Boolean getCreateLogs() {
    return createLogs;
  }

  public String getAppName() {
    return appName;
  }

  public List<DownloadSet> getDownloadSets() {
    return downloadSets;
  }

  @Override
  public String toString() {
    return "AllDownloads{" +
            "createLogs=" + createLogs +
            ", appName='" + appName + '\'' +
            ", downloadSets=" + downloadSets +
            '}';
  }
}