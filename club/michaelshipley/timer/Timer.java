package club.michaelshipley.timer;

public class Timer
{
  private long lastTime;
  
  public Timer()
  {
    reset();
  }
  
  public long getCurrentTime()
  {
    return System.nanoTime() / 1000000L;
  }
  
  public long getLastTime()
  {
    return this.lastTime;
  }
  
  public long getDifference()
  {
    return getCurrentTime() - this.lastTime;
  }
  
  public void reset()
  {
    this.lastTime = getCurrentTime();
  }
  
  public boolean hasReached(long milliseconds)
  {
    return getDifference() >= milliseconds;
  }
}
