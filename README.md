# Batch File Download

Batch File Download is a utility that allows the files to be downloaded from the internet from another Java program.

Sample Java program to demonstrate download of the two files: 

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

    File fileOne = new File("memeOne.jpg", "https://i.imgur.com/pQ2VfHI.jpg");
    File fileTwo = new File("memeTwo.jpg", "https://i.imgur.com/jTS4geM.jpeg");
    List<File> files = Arrays.asList(fileOne, fileTwo);
    String folderName = "MyFolder";         // name of the folder in which the above two files will be saved once downloaded

    List<DownloadSet> downloadSet = Arrays.asList(new DownloadSet(files, folderName));

    AllDownloads allDownloads = new AllDownloads(createLogs, appName, downloadSet);

    BatchFileDownloader downloader = new BatchFileDownloader(allDownloads);
    downloader.start();
  }

}
```

Upon successful completion of the above program the file would be downloaded into the following path inside USER_HOME directory:

`<USER_HOME>/TestApp/MyFolder`

The logs, if set to `true`, will consist of all the link that got successfully downloaded and will be written into:

`<USER_HOME>/TestApp/downloadLog-yyyy-mm-dd.txt`