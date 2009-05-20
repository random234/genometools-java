package annotationsketch;

import com.sun.jna.Library;
import com.sun.jna.Native;
import com.sun.jna.NativeLong;
import com.sun.jna.Pointer;

public class ImageInfo
{
  private Pointer image_info_ptr;
  public interface GT extends Library
  {
    GT INSTANCE = (GT) Native.loadLibrary("genometools", GT.class);
    Pointer gt_image_info_new();
    NativeLong gt_image_info_get_height(Pointer ii_ptr);
    NativeLong gt_image_info_num_of_rec_maps(Pointer ii_ptr);
    Pointer gt_image_info_get_rec_map(Pointer ii_ptr, NativeLong i);
    void gt_image_info_delete(Pointer ii_ptr);
  }
  
  public ImageInfo() {
    image_info_ptr = GT.INSTANCE.gt_image_info_new();
  }
  
  protected void finalize() {
    GT.INSTANCE.gt_image_info_delete(image_info_ptr);
  }
  
  public int get_height() {
    NativeLong tmp = GT.INSTANCE.gt_image_info_get_height(image_info_ptr);
    int i_tmp = (int)tmp.longValue();
    return i_tmp;
  }
  
  public int num_of_rec_maps() {
    NativeLong tmp = GT.INSTANCE.gt_image_info_num_of_rec_maps(image_info_ptr);
    int i_tmp = (int)tmp.longValue();
    return i_tmp;
  }
  
  
  public RecMap get_rec_map(int i) {
    NativeLong tmp = new NativeLong(i);
    RecMap rm = new RecMap(GT.INSTANCE.gt_image_info_get_rec_map(image_info_ptr, tmp));
    return rm;
  }
  
  
  
  public Pointer to_ptr() {
    return image_info_ptr;
  }
  
  
}
