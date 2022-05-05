package ru.kpfu.itis.oinuritto.commands;

import ru.kpfu.itis.oinuritto.App;
import ru.kpfu.itis.oinuritto.FileDownloader;
import ru.kpfu.itis.oinuritto.utils.FontSettings;

import java.util.ArrayList;
import java.util.Map;

public class HelpCommand implements Command {
    private Map<String, Command> commandsMap;

    @Override
    public void execute(ArrayList<FileDownloader> fileDownloadersList, ArrayList<Thread> threadsList) {
        commandsMap = App.getCommandsMap();
        System.out.println(FontSettings.ANSI_YELLOW + "----- COMMANDS: -----");
        for (String command: commandsMap.keySet()) {
            System.out.println(command);
        }
        System.out.println("---------------------" + FontSettings.ANSI_RESET);
    }
}
