package ru.kpfu.itis.oinuritto.exceptions;

public class FileDownloaderException extends Exception {
    public FileDownloaderException() {
        super();
    }

    public FileDownloaderException(String message) {
        super(message);
    }

    public FileDownloaderException(String message, Throwable cause) {
        super(message, cause);
    }

    public FileDownloaderException(Throwable cause) {
        super(cause);
    }

    protected FileDownloaderException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
