package core;
import com.sun.jna.Library;
import com.sun.jna.Native;

public abstract class Allocators {
  
  private static boolean hasrun = false;
  
  public interface GT extends Library
  {
    GT INSTANCE = (GT) Native.loadLibrary("genometools", GT.class);

    void gt_allocators_init();
    void gt_allocators_reg_atexit_func();
  }
  
  public static void init() {
    if (!hasrun) {
      GT.INSTANCE.gt_allocators_init();
      GT.INSTANCE.gt_allocators_reg_atexit_func();
      hasrun = true;
    }
  }
}
