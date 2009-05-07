package core;
import com.sun.jna.*;

public class Allocators
{
  public interface GT extends Library
  {
    GT INSTANCE = (GT) Native.loadLibrary("genometools", GT.class);
    void gt_allocators_init();
    void gt_allocators_reg_atexit_func();
  }
}
