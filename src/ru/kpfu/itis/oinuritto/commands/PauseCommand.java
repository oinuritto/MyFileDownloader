package ru.kpfu.itis.oinuritto.commands;

import ru.kpfu.itis.oinuritto.FileDownloader;
import ru.kpfu.itis.oinuritto.exceptions.FileDownloaderException;
import ru.kpfu.itis.oinuritto.utils.FontSettings;


import java.util.ArrayList;
import java.util.Scanner;

public class PauseCommand implements Command{
    private Scanner scanner;

    @Override
    public void execute(ArrayList<FileDownloader> fileDownloadersList, ArrayList<Thread> threadsList) {
        if (fileDownloadersList.size() != 0) {
            new StatusCommand().execute(fileDownloadersList, threadsList);
            System.out.print(FontSettings.ANSI_YELLOW + "Choose ID of file, which you want to pause: " + FontSettings.ANSI_RESET);

            scanner = new Scanner(System.in);
            int id = scanner.nextInt();

            if (id <= fileDownloadersList.size()) {
                try {
                    fileDownloadersList.get(id).downloadStop();
                    System.out.println(FontSettings.ANSI_CYAN + "File: " + fileDownloadersList.get(id).getFileName() + " is in pause now." +
                            FontSettings.ANSI_RESET);
                } catch (FileDownloaderException e) {
                    System.out.println(e.getMessage());
                }
            }
        } else {
            System.out.println(FontSettings.ANSI_YELLOW + "Nothing to pause..." + FontSettings.ANSI_RESET);
        }
    }
}
