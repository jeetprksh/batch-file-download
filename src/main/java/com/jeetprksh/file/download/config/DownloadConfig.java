package com.jeetprksh.file.download.config;

import com.google.gson.Gson;
import org.apache.commons.io.IOUtils;

import java.io.InputStream;
import java.nio.charset.StandardCharsets;

public class DownloadConfig {

  public static AllDownloads createDefault() throws Exception {
    InputStream stream = ClassLoader.getSystemResourceAsStream("downloads.json");
    return new Gson().fromJson(IOUtils.toString(stream, StandardCharsets.UTF_8), AllDownloads.class);
  }

}
