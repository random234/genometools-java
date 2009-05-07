package core;

import com.sun.jna.*;

public class GTerror
{
  protected Pointer error;

  public interface GT extends Library
  {
    GT INSTANCE = (GT) Native.loadLibrary("genometools", GT.class);

    Pointer gt_error_new();
    String gt_error_get(Pointer err);
    Boolean gt_error_is_set(Pointer err);
    void gt_error_unset(Pointer err);
    void gt_error_delete(Pointer err);
  }

  public GTerror()
  {
    error = GT.INSTANCE.gt_error_new();
  }
  public GTerror(Pointer err)
  {
    error = err;
  }
  protected void finalize()
  {
    GT.INSTANCE.gt_error_delete(error);
  }
  private String get()
  {
    return GT.INSTANCE.gt_error_get(this.error);
  }
  public Boolean is_set()
  {
    return GT.INSTANCE.gt_error_is_set(this.error);
  }
  public void unset()
  {
    GT.INSTANCE.gt_error_unset(this.error);
  }
  public static void gtexcept(GTerror err)
  {
    try {
      if (err instanceof GTerror) {
        throw new Exception("Genometools Error " + err.get() + "");
      }
    } catch (Exception e) {
      e.printStackTrace();
    }
  }
  public static void gtexcept(String err)
  {
    try {
      throw new Exception("Java Wrapper Exception: " + err + "");
    } catch (Exception e) {
      e.printStackTrace();
    }
  }

  public Pointer to_ptr()
  {
    return error;
  }
}
