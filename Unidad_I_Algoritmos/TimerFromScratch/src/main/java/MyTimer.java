public class MyTimer {
    private long start;
    private long interval;
    private boolean done = false;
    private static final long DAY = 86400000;
    private static final long HR = 3600000;
    private static final long MIN = 60000;
    // Ejercicio 1
    public MyTimer(){
        start = System.currentTimeMillis();
    }
    // Ejercicio 2
    public MyTimer(long start){
        this.start = start;
    }
    // Ejercicio 1
    public void stop(){
        done = true;
        interval = System.currentTimeMillis() - start;
    }
    // Ejercicio 2
    public void stop(long end){
        if(end < start){
            throw new RuntimeException("End should be higher than start.");
        }
        done = true;
        interval = end - start;
    }
    public String toString(){
        return String.format("(%d ms) %d dia %d hs %d min %f s", interval,
        interval/DAY, (interval%DAY) / HR,
        ((interval%DAY)%HR) / MIN,
        (((interval%DAY)%HR)%MIN) / (double)1000);
    }
    public long getElapsedTime(){
        validation();
        return interval;
    }
    public double getSeconds(){
        validation();
        return (((interval%DAY)%HR)%MIN) / (double)1000;
    }
    public long getDay(){
        validation();
        return interval / DAY;
    }
    public long getHour(){
        validation();
        return (interval%DAY) / HR;
    }
    public long getMin(){
        validation();
        return ((interval%DAY)%HR) / MIN;
    }
    private void validation(){
        if(!done){
            throw new RuntimeException();
        }
    }
    public static void esperar(long ms) {
        try {
            Thread.sleep(ms);
        }
        catch (Exception e) {
            e.printStackTrace();
        }
    }
}
