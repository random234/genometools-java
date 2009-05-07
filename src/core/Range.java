package core;

import com.sun.jna.*;

public class Range extends Structure
{
  public NativeLong start;
  public NativeLong end;
  
  public interface GT extends Library
  {
    GT INSTANCE = (GT) Native.loadLibrary("genometools", GT.class);
  }

  public Range() {
    this.start = new NativeLong(0);
    this.end = new NativeLong(0);
  }
  
  public Range(int start, int end) {
    this.start = new NativeLong(start);
    this.end = new NativeLong(end);
  }
  
  public int get_size() {
    long istart = start.longValue();
    long iend = end.longValue();
    return (int)iend - (int)istart;
  }
}
