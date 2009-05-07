package core;

import com.sun.jna.*;

public class StrArray
{
  protected Pointer str_array;

  public interface GT extends Library
  {
    GT INSTANCE = (GT) Native.loadLibrary("genometools", GT.class);

    Pointer gt_str_array_new();
    void gt_str_array_add_cstr(Pointer str_array, String cstr);
    String gt_str_array_get(Pointer str_array, NativeLong strnum);
    NativeLong gt_str_array_size(Pointer str_array);
    void gt_str_array_delete(Pointer str_array);
  }

  public StrArray()
  {
    this.str_array = GT.INSTANCE.gt_str_array_new();
  }
  public StrArray(Pointer str_array)
  {
    this.str_array = str_array;
  }

  public String get(int index)
  {
    NativeLong itmp = new NativeLong(index);
    return GT.INSTANCE.gt_str_array_get(this.str_array, itmp);
  }

  protected void finalize()
  {
    GT.INSTANCE.gt_str_array_delete(this.str_array);
  }
  public void add_array(String[] array)
  {
    for (String cstr : array) {
      GT.INSTANCE.gt_str_array_add_cstr(this.str_array, cstr);
    }
  }
  public String[] to_a()
  {
    String[] arr = new String[this.length()];
    for (int i = 0; i < this.length(); i++) {
      NativeLong tmp = new NativeLong(i);
      arr[i] = GT.INSTANCE.gt_str_array_get(this.str_array, tmp);
    }
    return arr;
  }
  public int length()
  {
    NativeLong ntmp = GT.INSTANCE.gt_str_array_size(this.str_array);
    return (int) ntmp.longValue();
  }
  public Pointer to_ptr()
  {
    return this.str_array;
  }

}
