package Controller;

/**
 *
 * @author achilleas
 */

/*
it contains methods that are used to find the time that each round lasted
*/
public class Stopwatch {

    private long startTime = 0;
    private long stopTime = 0;

    //sets the starting time in milliseconds
    public void start() {
        this.setStartTime(System.currentTimeMillis());
    }
    //sets the stoping time in milliseconds
    public void stop() {
        this.setStopTime(System.currentTimeMillis());
    }
    //calculates the difference between startTime and stopTime to determine the elapsed time
    public long getElapsedTime() {

        long elapsedtime = (getStopTime() - getStartTime());

        return elapsedtime;
    }

    /**
     * @return the startTime
     */
    public long getStartTime() {
        return startTime;
    }

    /**
     * @param startTime the startTime to set
     */
    public void setStartTime(long startTime) {
        this.startTime = startTime;
    }

    /**
     * @return the stopTime
     */
    public long getStopTime() {
        return stopTime;
    }

    /**
     * @param stopTime the stopTime to set
     */
    public void setStopTime(long stopTime) {
        this.stopTime = stopTime;
    }

    
}
