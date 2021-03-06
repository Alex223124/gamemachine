package io.gamemachine.objectdb;

import java.util.concurrent.Delayed;
import java.util.concurrent.TimeUnit;

public class DelayElement implements Delayed {
    private String element;
    private long expiryTime;

    public DelayElement(String element, long delay) {
        this.element = element;
        this.expiryTime = System.currentTimeMillis() + delay;
    }

    public String getElement() {
        return this.element;
    }

    @Override
    public long getDelay(TimeUnit timeUnit) {
        long diff = expiryTime - System.currentTimeMillis();
        return timeUnit.convert(diff, TimeUnit.MILLISECONDS);
    }

    @Override
    public int compareTo(Delayed o) {
        if (this.expiryTime < ((DelayElement) o).expiryTime) {
            return -1;
        }
        if (this.expiryTime > ((DelayElement) o).expiryTime) {
            return 1;
        }
        return 0;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }

        if (obj == null) {
            return false;
        }

        final DelayElement delay = (DelayElement) obj;
        if (this.element.equals(delay.element)) {
            return true;
        }

        return false;
    }

    @Override
    public String toString() {
        return element + ": " + expiryTime;
    }
}