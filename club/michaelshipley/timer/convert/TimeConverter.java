package club.michaelshipley.timer.convert;

public class TimeConverter
{
  public static final long SECOND = 1000L;
  public static final long MINUTE = 60000L;
  public static final long HOUR = 3600000L;
  public static final long DAY = 86400000L;
  public static final long WEEK = 604800000L;
  
  public double toMilliseconds(double amount, long convertFrom)
  {
    return convertFrom * Math.floor(amount);
  }
  
  public double fromMilliseconds(long milliseconds, long convertTo)
  {
    return milliseconds / convertTo;
  }
}
