package com.jeetprksh.file.download.config;

import com.google.gson.Gson;
import org.apache.commons.io.IOUtils;

import java.io.InputStream;
import java.nio.charset.StandardCharsets;

public class DownloadConfig {

  private boolean createLogs;
  private String configPath;

  private DownloadConfig() {
  }

  public static AllDownloads createDefault() throws Exception {
    InputStream stream = ClassLoader.getSystemResourceAsStream("downloads.json");
    return new Gson().fromJson(IOUtils.toString(stream, StandardCharsets.UTF_8), AllDownloads.class);
  }

  public static final class DownloadConfigBuilder {

    private boolean createLogs;
    private String configPath;

    private DownloadConfigBuilder() {
    }

    public static DownloadConfigBuilder getBuilder() {
      return new DownloadConfigBuilder();
    }

    public DownloadConfigBuilder createLogs(boolean createLogs) {
      this.createLogs = createLogs;
      return this;
    }

    public DownloadConfigBuilder withConfigPath(String configPath) {
      this.configPath = configPath;
      return this;
    }

    public DownloadConfig build() {
      DownloadConfig downloadConfig = new DownloadConfig();
      downloadConfig.createLogs = this.createLogs;
      downloadConfig.configPath = this.configPath;
      return downloadConfig;
    }
  }
}
