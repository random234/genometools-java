package core;

import com.sun.jna.*;

public class Str
{
  private Pointer str_ptr;

  public interface GT extends Library
  {
    GT INSTANCE = (GT) Native.loadLibrary("genometools", GT.class);

    Pointer gt_str_new();

    Pointer gt_str_new_cstr(String cstr);

    Pointer gt_str_ref(Pointer s);

    void gt_str_append_str(Pointer dest, Pointer src);

    void gt_str_append_cstr(Pointer dest, String str);

    String gt_str_get(Pointer s);

    NativeLong gt_str_length(Pointer str);

    void gt_str_delete(Pointer str);
  }

  public Str(String cstr)
  {
    if (cstr.equals(null)) {
      str_ptr = GT.INSTANCE.gt_str_new();
      GT.INSTANCE.gt_str_ref(str_ptr);
    } else {
      str_ptr = GT.INSTANCE.gt_str_new_cstr(cstr);
      GT.INSTANCE.gt_str_ref(str_ptr);
    }
  }

  public Str(Pointer cstr)
  {
    str_ptr = cstr;
  }

  protected void finalize()
  {
    GT.INSTANCE.gt_str_delete(str_ptr);
  }

  public void append_str(Str str)
  {
    GT.INSTANCE.gt_str_append_str(str_ptr, str.to_ptr());
  }

  public void append_str(String str)
  {
    GT.INSTANCE.gt_str_append_cstr(str_ptr, str);
  }

  public String to_s()
  {
    return GT.INSTANCE.gt_str_get(str_ptr);
  }

  public int length()
  {
    NativeLong ntmp = GT.INSTANCE.gt_str_length(str_ptr);
    return (int) ntmp.longValue();
  }
  public Pointer to_ptr()
  {
    return str_ptr;
  }

}
