package org.fastpoke.jcalc;

import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.atomic.AtomicInteger;

import static org.fastpoke.jcalc.Main.log;

public class Data {

    private final AtomicInteger value = new AtomicInteger();

    private final ExecutorService executorService;

    private final List<Runnable> listeners = new CopyOnWriteArrayList<>();

    public Data(ExecutorService executorService) {
        this.executorService = executorService;
    }

    public int getValue() {
        log("getValue");
        return value.get();
    }

    public void addListener(Runnable listener) {
        log("addListener");
        listeners.add(listener);
    }

    public void increment() {
        log("inc");
        value.incrementAndGet();
        fireUpdateEvent();
    }

    public void fireUpdateEvent() {
        log("fire");
        for (Runnable listener : listeners) {
            executorService.execute(listener);
        }
    }

}
