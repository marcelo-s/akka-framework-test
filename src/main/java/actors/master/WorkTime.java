package actors.master;

import java.time.Duration;
import java.time.Instant;

public class WorkTime {
    private Instant start;
    private Instant finish;
    private long timeElapsed;

    WorkTime(Instant start) {
        this.start = start;
    }

    public void setStart(Instant start) {
        this.start = start;
    }

    void setFinish(Instant finish) {
        this.finish = finish;
        //in millis
        timeElapsed = Duration.between(start, finish).toMillis();
    }

    long getTimeElapsed() {
        return timeElapsed;
    }

}
