package core;

import com.sun.jna.*;

public class Array
{
  protected Pointer array_ptr;

  public interface GT extends Library
  {
    GT INSTANCE = (GT) Native.loadLibrary("genometools", GT.class);

    Pointer gt_array_new(NativeLong size_of_elem);
    Pointer gt_array_get(Pointer array, NativeLong index);
    void gt_array_add_ptr(Pointer array, Pointer elem);
    NativeLong gt_array_size(Pointer array);
    void gt_array_delete(Pointer array);
    Pointer gt_array_ref(Pointer array);

  }

  public Array(int size_of_elem)
  {
    NativeLong tmp = new NativeLong(size_of_elem);
    array_ptr = GT.INSTANCE.gt_array_new(tmp);
    array_ptr = GT.INSTANCE.gt_array_ref(this.array_ptr);
  }
  public Array(Pointer array)
  {
    array_ptr = array;
  }
  protected void finalize()
  {
    GT.INSTANCE.gt_array_delete(this.array_ptr);
  }
  public Pointer get(int index)
  {
    NativeLong tmp = new NativeLong(index);
    return GT.INSTANCE.gt_array_get(this.array_ptr, tmp).getPointer(0);
  }
  public void add(Pointer elem)
  {
    GT.INSTANCE.gt_array_add_ptr(this.array_ptr, elem);
  }
  public int size()
  {
    NativeLong ntmp = GT.INSTANCE.gt_array_size(this.array_ptr);
    return (int) ntmp.longValue();
  }
  public Pointer to_ptr() {
    return this.array_ptr;
  }
}
