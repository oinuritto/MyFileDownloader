package ru.kpfu.itis.oinuritto;

import ru.kpfu.itis.oinuritto.commands.*;
import ru.kpfu.itis.oinuritto.utils.FontSettings;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;

public class App extends AbstractApp {
    private Scanner scanner;
    private static Map<String, Command> commandsMap;
    private ArrayList<FileDownloader> fileDownloadersList;
    private ArrayList<Thread> threadsList;

    public App() {
        super();
    }

    @Override
    protected void init() {
        scanner = new Scanner(System.in);
        fileDownloadersList = new ArrayList<>();
        threadsList = new ArrayList<>();
        commandsMap = new HashMap<>();
        commandsMap.put("help", new HelpCommand());
        commandsMap.put("download", new DownloadCommand());
        commandsMap.put("pause", new PauseCommand());
        commandsMap.put("status", new StatusCommand());
        commandsMap.put("exit", new ExitCommand());
        commandsMap.put("continue", new ContinueCommand());
    }

    @Override
    protected void start() {
        System.out.println(FontSettings.ANSI_GREEN + "---------- File Downloader ----------" + FontSettings.ANSI_RESET);
        System.out.println(FontSettings.GREEN_BACK + "Use \"help\" to get info about commands." + FontSettings.NORMAL_BACK);
        String inputCommand;
        while (true) {
            inputCommand = scanner.nextLine().trim();
            if (commandsMap.containsKey(inputCommand)) {
                commandsMap.get(inputCommand).execute(fileDownloadersList, threadsList);
            } else {
                System.out.println(FontSettings.ANSI_RED + "This command doesn't exist...\nPlease try other command." + FontSettings.ANSI_RESET);
            }

        }
    }

    public static Map<String, Command> getCommandsMap() {
        return commandsMap;
    }
}
