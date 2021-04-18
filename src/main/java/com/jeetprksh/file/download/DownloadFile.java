package com.jeetprksh.file.download;

import org.apache.hc.client5.http.classic.HttpClient;
import org.apache.hc.client5.http.classic.methods.HttpGet;
import org.apache.hc.client5.http.impl.classic.CloseableHttpResponse;
import org.apache.hc.client5.http.impl.classic.HttpClients;
import org.apache.hc.core5.http.Header;

import java.util.Objects;
import java.util.logging.Logger;

class DownloadFile {

  private final Logger logger = Logger.getLogger(DownloadFile.class.getName());

  protected final HttpClient client = HttpClients.createDefault();

  private final String fileUrl;

  public DownloadFile(String fileUrl) {
    this.fileUrl = fileUrl;
  }

  public DownloadedFile execute() throws Exception {
    logger.info("Downloading file from " + this.fileUrl);
    HttpGet get = new HttpGet(this.fileUrl);
    CloseableHttpResponse response = (CloseableHttpResponse) client.execute(get);
    String fileExtension = "default";
    Header header = response.getHeader("content-type");
    if (!Objects.isNull(header)) {
      String contentType = header.getValue();
      fileExtension = contentType.split("/")[1];
    }
    return new DownloadedFile(fileExtension, response.getEntity().getContent());
  }
}
