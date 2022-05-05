package ru.kpfu.itis.oinuritto;

import ru.kpfu.itis.oinuritto.exceptions.FileDownloaderException;
import ru.kpfu.itis.oinuritto.utils.FontSettings;

import java.io.*;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;
import java.nio.file.Paths;

public class FileDownloader implements Runnable {
    private final static int BUFFER_SIZE = 1024;
    private URL url;
    private String fileName;
    private File downloadingFile;
    private long fileSize;
    private long downloadedFileSize;
    private boolean isDownloading;
    private URLConnection urlConnection;



    public FileDownloader(String downloadingPath, String urlFromDownload, String fileName) throws IOException {
        try {
            this.url = new URL(urlFromDownload);
        } catch (MalformedURLException ex) {
            throw new MalformedURLException(FontSettings.ANSI_RED + "Wrong URL address..." + FontSettings.ANSI_RESET);
        }
        this.fileName = fileName;
        this.downloadingFile = new File(Paths.get(downloadingPath).resolve(fileName).toString());
        fileSize = calcFileSize();
        this.isDownloading = true;
        this.downloadedFileSize = 0;
    }

    @Override
    public void run() {
        try {
            startDownloadingFile();
        } catch (IOException e) {
            System.out.println(e.getMessage());
        }
    }

    private void startDownloadingFile() throws IOException {
        while (isDownloading && !isFileDownloaded()) {
            if (downloadingFile.exists()) {
                urlConnection = url.openConnection();
                urlConnection.setRequestProperty("Range", "bytes=" + downloadedFileSize + "-");

                try (BufferedOutputStream out = new BufferedOutputStream(new FileOutputStream(downloadingFile, true));
                     BufferedInputStream in = new BufferedInputStream(url.openStream())) {
                    download(in, out);
                } catch (IOException e) {
                    throw new IOException(FontSettings.ANSI_RED + "Problems with startDownloadingFile() in FileDownloader. " +
                            "FileOutputStream with append=true. " + FontSettings.ANSI_RESET);
                }
            } else {
                try (BufferedOutputStream out = new BufferedOutputStream(new FileOutputStream(downloadingFile));
                     BufferedInputStream in = new BufferedInputStream(url.openStream())) {
                    download(in, out);
                } catch (IOException e) {
                    throw new IOException(FontSettings.ANSI_RED + "Problems with startDownloadingFile() in FileDownloader. " +
                            "FileOutputStream with append=false. " + FontSettings.ANSI_RESET);
                }
            }
        }
    }

    private long calcFileSize() throws IOException {
        URLConnection urlConnection = url.openConnection();
        try {
            urlConnection.connect();
        } catch (IOException e) {
            throw new IOException(FontSettings.ANSI_RED + "Problems with getFileSize() in FileDownloader." + FontSettings.ANSI_RESET);
        }
        return urlConnection.getContentLengthLong();
    }

    private void download(BufferedInputStream in, BufferedOutputStream out) throws IOException {
        byte bytes[] = new byte[BUFFER_SIZE];
        int b = 0;
        try {
            while (isDownloading && ((b = in.read(bytes)) != -1)) {
                out.write(bytes, 0, b);
                downloadedFileSize += BUFFER_SIZE;
            }
        } catch (IOException ex) {
            throw new IOException(FontSettings.ANSI_RED + "Some problems with download() in FileDownloader..." + FontSettings.ANSI_RESET);
        }
    }

    private boolean isFileDownloaded() {
        if (downloadedFileSize >= fileSize) {
            isDownloading = false;
            return true;
        } else {
            return false;
        }
    }

    public void downloadStop() throws FileDownloaderException {
        isDownloading = false;
        if (isFileDownloaded()) {
            throw new FileDownloaderException(FontSettings.ANSI_RED + "File is already downloaded..." + FontSettings.ANSI_RESET);
        }
    }

    public void downloadContinue() throws FileDownloaderException {
        if (!isFileDownloaded()) {
            isDownloading = true;
        } else {
            throw new FileDownloaderException(FontSettings.ANSI_RED + "File is already downloaded..." + FontSettings.ANSI_RESET);
        }
    }


    // for printing info in StatusCommand()
    public String getFileName() {
        return fileName;
    }

    public long getDownloadedFileSize() {
        return downloadedFileSize;
    }

    public boolean isDownloading() {
        return isDownloading;
    }

    public long getFileSize() {
        return fileSize;
    }
}
