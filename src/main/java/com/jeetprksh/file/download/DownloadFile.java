package com.jeetprksh.file.download;

import org.apache.hc.client5.http.classic.HttpClient;
import org.apache.hc.client5.http.classic.methods.HttpGet;
import org.apache.hc.client5.http.impl.classic.CloseableHttpResponse;
import org.apache.hc.client5.http.impl.classic.HttpClients;

import java.util.logging.Logger;

class DownloadFile {

  private final Logger logger = Logger.getLogger(DownloadFile.class.getName());

  protected final HttpClient client = HttpClients.createDefault();

  private final String imageUrl;

  public DownloadFile(String imageUrl) {
    this.imageUrl = imageUrl;
  }

  public DownloadedFile execute() throws Exception {
    logger.info("Downloading file from " + this.imageUrl);
    HttpGet get = new HttpGet(this.imageUrl);
    CloseableHttpResponse response = (CloseableHttpResponse) client.execute(get);
    String fileName = imageUrl.substring(imageUrl.lastIndexOf("/") + 1);
    return new DownloadedFile(fileName, response.getEntity().getContent());
  }
}
