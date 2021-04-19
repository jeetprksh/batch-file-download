package com.jeetprksh.file.download.config;

import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import org.apache.commons.io.IOUtils;

import java.io.FileInputStream;
import java.io.InputStream;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class DownloadConfig {

  private boolean createLogs;
  private String configPath;

  private DownloadConfig() {
  }

  public static AllDownloads createDefault() throws Exception {
    InputStream stream = new FileInputStream(System.getProperty("user.home") + "\\Batch File Downloader\\config.json");
    String configString = IOUtils.toString(stream, StandardCharsets.UTF_8);
    JsonObject configObject = JsonParser.parseString(configString).getAsJsonObject();

    AllDownloads allDownloads;
    if (Objects.isNull(configObject.get("configVersion"))
            || configObject.get("configVersion").getAsString().equals("v2")) {
      allDownloads = new Gson().fromJson(configString, AllDownloads.class);
    } else {
      List<DownloadSet> downloadSets = new ArrayList<>();
      JsonArray downloadSetObjects = configObject.getAsJsonArray("downloadSets");
      downloadSetObjects.forEach(dso -> {
        JsonObject downloadSetObject = dso.getAsJsonObject();
        JsonArray urls = downloadSetObject.get("urls").getAsJsonArray();

        List<File> files = new ArrayList<>();
        urls.forEach(u -> {
          files.add(new File(null, u.getAsString()));

        });

        String folderName = downloadSetObject.get("folderName").getAsString();
        downloadSets.add(new DownloadSet(files, folderName));
      });
      boolean createLogs = configObject.get("createLogs").getAsBoolean();
      String appName = configObject.get("appName").getAsString();
      allDownloads = new AllDownloads(createLogs, appName, downloadSets);
    }

    return allDownloads;
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
