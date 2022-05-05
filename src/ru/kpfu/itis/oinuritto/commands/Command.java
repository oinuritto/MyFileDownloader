package ru.kpfu.itis.oinuritto.commands;

import ru.kpfu.itis.oinuritto.FileDownloader;
import java.util.ArrayList;

public interface Command {
    void execute(ArrayList<FileDownloader> fileDownloadersList, ArrayList<Thread> threadsList);
}
