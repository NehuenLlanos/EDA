import org.joda.time.Instant;

public class MyTimer {
    private Instant start;
    private java.time.Instant interval;
    private static final long DAY = 86400000;
    private static final long HR = 3600000;
    private static final long MIN = 60000;
    public MyTimer(){
        start = Instant.now();
    }
    public MyTimer(long start){
        this.start = Instant.ofEpochMilli(start);
    }
    public void stop(){
        java.time.Instant end = java.time.Instant.now();
        interval = end.minusMillis(start.getMillis());
    }
    public void stop(long end){
        interval = java.time.Instant.ofEpochMilli(end).minusMillis(start.getMillis());
    }
    public String toString(){
        return String.format("(%d ms) %d dia %d hs %d min %f s", interval.toEpochMilli(),
                interval.toEpochMilli()/DAY, (interval.toEpochMilli()%DAY) / HR,
                ((interval.toEpochMilli()%DAY)%HR) / MIN,
                (((interval.toEpochMilli()%DAY)%HR)%MIN) / (double)1000);
    }

}
