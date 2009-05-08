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
  
  public void set_start(int start){
    NativeLong tmp = new NativeLong(start);
    this.start = tmp;
  }
  public void set_end(int end){
    NativeLong tmp = new NativeLong(end);
    this.end = tmp;
  }
  public int get_start() {
    long l_tmp = this.start.longValue();
    return (int)l_tmp;
  }
  public int get_end() {
    long l_tmp = this.end.longValue();
    return (int)l_tmp;
  }
}
