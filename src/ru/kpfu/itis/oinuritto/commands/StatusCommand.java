package ru.kpfu.itis.oinuritto.commands;

import ru.kpfu.itis.oinuritto.FileDownloader;
import ru.kpfu.itis.oinuritto.utils.FontSettings;

import java.util.ArrayList;

public class StatusCommand implements Command {
    @Override
    public void execute(ArrayList<FileDownloader> fileDownloadersList, ArrayList<Thread> threadsList) {
        if (fileDownloadersList.size() != 0) {
            for (int i = 0; i < fileDownloadersList.size(); i++) {
                FileDownloader fileDownloader = fileDownloadersList.get(i);

                // getting info about downloads
                String fileName = fileDownloader.getFileName();
                boolean isDownloading = fileDownloader.isDownloading();
                long downloadedSize = fileDownloader.getDownloadedFileSize();
                long fileSize = fileDownloader.getFileSize();
                int downloadedPercent = (int) (downloadedSize * 100 / fileSize);

                String[] downloadedSizeOut = sizeCut(downloadedSize);
                String[] fileSizeOut = sizeCut(fileSize);

                // printing info about downloads
                System.out.printf(FontSettings.ANSI_PURPLE + "ID: %d \t %s \t download status: %b \t downloaded: %d%% \t downloaded size: " +
                                "%.2f %s \t file size: %.2f %s%n" + FontSettings.ANSI_RESET, i, fileName, isDownloading, downloadedPercent,
                        Float.parseFloat(downloadedSizeOut[0]), downloadedSizeOut[1], Float.parseFloat(fileSizeOut[0]), fileSizeOut[1]);
            }
        } else {
            System.out.println(FontSettings.ANSI_YELLOW + "No downloads in progress now..." + FontSettings.ANSI_RESET);
        }
    }

    private String[] sizeCut(long size) {
        String[] res = new String[2];
        int i = 0;
        int koeff = 1024;
        float newSize = size;
        String[] sizeCutsNames = {"B", "KB", "MB", "GB", "TB"};
        while (newSize >= koeff) {
            newSize /= koeff;
            i++;
            if (i == sizeCutsNames.length) {
                break;
            }
        }
        res[0] = String.valueOf(newSize);
        res[1] = sizeCutsNames[i];
        return res;
    }
}
