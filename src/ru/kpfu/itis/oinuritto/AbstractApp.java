package ru.kpfu.itis.oinuritto;

public abstract class AbstractApp {
    public AbstractApp() {
        this.init();
        this.start();
    }

    protected abstract void init();
    protected abstract void start();
}
