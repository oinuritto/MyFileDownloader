package ru.kpfu.itis.oinuritto.commands;

import ru.kpfu.itis.oinuritto.FileDownloader;
import ru.kpfu.itis.oinuritto.exceptions.FileDownloaderException;
import ru.kpfu.itis.oinuritto.utils.FontSettings;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Scanner;

public class DownloadCommand implements Command{
    private final String DOWNLOAD_PATH = "downloads";
    private Scanner scanner;

    @Override
    public void execute(ArrayList<FileDownloader> fileDownloadersList, ArrayList<Thread> threadsList) {
        File file = new File(DOWNLOAD_PATH);
        file.mkdir();

        System.out.print(FontSettings.ANSI_YELLOW + "Write address from do you want to download file: " + FontSettings.ANSI_RESET);
        scanner = new Scanner(System.in);
        String urlFromDownload = scanner.nextLine();
        String fileName = urlFromDownload.substring(urlFromDownload.lastIndexOf("/") + 1);

        try {
            FileDownloader fd = new FileDownloader(DOWNLOAD_PATH, urlFromDownload, fileName);
            for (FileDownloader fileDownloader: fileDownloadersList) {
                if (fileDownloader.equals(fd)) {
                    throw new FileDownloaderException(FontSettings.ANSI_YELLOW + "This file is already in downloaders list..." +
                            FontSettings.ANSI_RESET);
                }
            }
            fileDownloadersList.add(fd);
            threadsList.add(new Thread(fileDownloadersList.get(fileDownloadersList.size() - 1)));
            threadsList.get(threadsList.size() - 1).start();
            System.out.println(FontSettings.ANSI_CYAN + "Downloading of file: " + fileName + " started." + FontSettings.ANSI_RESET);
        } catch (IOException | FileDownloaderException e) {
            System.out.println(e.getMessage());
        }

    }
}
