package org.fastpoke.jcalc;

import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.atomic.AtomicInteger;

import static org.fastpoke.jcalc.Main.log;

public class Data {

    private final ExecutorService executorService;

    private volatile int value;

    private final List<Runnable> listeners = new CopyOnWriteArrayList<>();

    public Data(ExecutorService executorService) {
        this.executorService = executorService;
    }

    public int getValue() {
        log("getValue");
        return value;
    }

    public void addListener(Runnable listener) {
        log("addListener");
        listeners.add(listener);
    }

    public synchronized void append(int digit) {
        log("appending " + digit);
        value = value * 10 + digit;
        fireUpdateEvent();
    }

    public synchronized void clear() {
        log("clear");
        value = 0;
        fireUpdateEvent();
    }

    private void fireUpdateEvent() {
        log("fire");
        for (Runnable listener : listeners) {
            executorService.execute(listener);
        }
    }

}
