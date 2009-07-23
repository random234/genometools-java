package core;

import com.sun.jna.*;

// The GtError class is a Throwable that is able to communicate low level genometools API Errors to the user of the Api
// Therefore it has four Constructos which can be used to attach a cause to the GTerror Object
public class GTerror
{
  private static final long serialVersionUID = 813149150202370867L;
  private Pointer err;

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
    this.err = GT.INSTANCE.gt_error_new();
  }
//  public GTerror(String err_mess)
//  {
//    super(err_mess);
//    this.err = GT.INSTANCE.gt_error_new();
//  }
//
//  public GTerror(String err_mess, Throwable cause)
//  {
//    super(err_mess, cause);
//    this.err = GT.INSTANCE.gt_error_new();
//  }
//
//  public GTerror(String err_mess, Pointer err)
//  {
//    super(err_mess);
//    this.err = err;
//  }
//
//  public GTerror(String err_mess, Throwable cause, Pointer err)
//  {
//    super(err_mess, cause);
//    this.err = err;
//  }
 protected void finalize()
  {
    GT.INSTANCE.gt_error_delete(this.err);
    this.err = null;
  }
  public String get_err()
  {
    return GT.INSTANCE.gt_error_get(this.err);
  }
  public Boolean is_set()
  {
    return GT.INSTANCE.gt_error_is_set(this.err);
  }
  public void unset()
  {
    GT.INSTANCE.gt_error_unset(this.err);
  }
  // public static void gtexcept(GTerror err) throws GTerror
  // {
  // if (err instanceof GTerror) {
  // throw new GTerror("Genometools Error " + err.get() + "");
  // }
  // }
  // public static void gtexcept(String err) throws GTerror
  // {
  // throw new GTerror("Java Wrapper Exception: " + err + "");
  // }

  public Pointer to_ptr()
  {
    return err;
  }
}
