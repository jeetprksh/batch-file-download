package com.jeetprksh.file.download.config;

import java.util.ArrayList;
import java.util.List;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class AllDownloads {

  @SerializedName("createLogs")
  @Expose
  private Boolean createLogs;

  @SerializedName("downloadSets")
  @Expose
  private List<DownloadSet> downloadSets = new ArrayList<>();

  public Boolean getCreateLogs() {
    return createLogs;
  }

  public void setCreateLogs(Boolean createLogs) {
    this.createLogs = createLogs;
  }

  public List<DownloadSet> getDownloadSets() {
    return downloadSets;
  }

  public void setDownloadSets(List<DownloadSet> downloadSets) {
    this.downloadSets = downloadSets;
  }

}