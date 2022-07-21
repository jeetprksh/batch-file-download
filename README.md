# Batch File Download

![Java CI with Maven](https://github.com/jeetprksh/batch-file-download/workflows/Java%20CI%20with%20Maven/badge.svg?branch=master)

Batch File Download is a utility that allows the files to be downloaded from the internet from another Java program.

There are two ways to use the utility:

## 1. Programmatically

Include this library in the project with:

```xml
<dependency>
  <groupId>com.jeetprksh.file.download</groupId>
  <artifactId>batch-file-download</artifactId>
  <version>1.0.1</version>
</dependency>
```

Sample Java program to demonstrate downloading of two files:

```java
import com.jeetprksh.file.download.BatchFileDownloader;
import com.jeetprksh.file.download.config.AllDownloads;
import com.jeetprksh.file.download.config.DownloadSet;
import com.jeetprksh.file.download.config.File;

import java.util.Arrays;
import java.util.List;

public class BatchDownloader {

  public static void main(String[] args) throws Exception {
    boolean createLogs = true;            // false for not creating the logs for downloaded files
    String appName = "TestApp";           // name of your app from which the files are being downloaded

    File fileOne = new File("ImageOne.jpg", "https://i.imgur.com/pQ2VfHI.jpg");
    File fileTwo = new File("ImageTwo.jpg", "https://i.imgur.com/jTS4geM.jpeg");
    List<File> files = Arrays.asList(fileOne, fileTwo);
    String folderName = "MyFolder";         // name of the folder in which the above two files will be saved once downloaded

    List<DownloadSet> downloadSet = Arrays.asList(new DownloadSet(files, folderName));

    AllDownloads allDownloads = new AllDownloads(createLogs, appName, downloadSet);

    BatchFileDownloader downloader = new BatchFileDownloader(allDownloads);
    downloader.start();
  }

}
```

Upon successful completion of the above program the files would be downloaded into the following path inside `USER_HOME` directory:

`<USER_HOME>/TestApp/MyFolder`

## 2. Building the project Jar

For this we need to specify the JSON configuration in a file placed on:

`<USER_HOME>/Batch File Downloader/config.json`

The configuration file will look like:

```json
{
  "createLogs": true,
  "appName": "TestApp",
  "downloadSets": [
    {
      "files" : [{
        "fileName" : "nameOfTheFile",
        "url": "urlOfTheFile"
      }],
      "folderName" : "folderName"
    }
  ]
}
```

Once the configuration file is in place we need to build the repository and build the Jar
using following commands:

```sh
> git clone https://github.com/jeetprksh/batch-file-download.git
> cd batch-file-download
> mvn install
> java -jar target/batch-file-download-1.0.0.jar
```

Upon successful completion of the above program the files would be downloaded into the following path inside `USER_HOME` directory:

`<USER_HOME>/TestApp/folderName`

## Download Logs

In both cases the download logs, if set to `true`, will be generated and will consists of all the link that got successfully downloaded on the file:

`<USER_HOME>/TestApp/downloadLog-yyyy-mm-dd.txt`
