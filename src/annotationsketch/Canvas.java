package annotationsketch;

import com.sun.jna.Library;
import com.sun.jna.Native;
import com.sun.jna.NativeLong;
import com.sun.jna.Pointer;
import core.GTerror;

public abstract class Canvas
{
  protected Pointer canvas_ptr; 
  public interface GT extends Library
  {
    GT INSTANCE = (GT) Native.loadLibrary("genometools", GT.class);
    Pointer gt_canvas_cairo_file_new(Pointer style ,int output_type, NativeLong width, NativeLong height, Pointer image_info);
    int gt_canvas_cairo_file_to_file(Pointer canvas, String filename, GTerror err);
    void gt_canvas_delete(Pointer canvas);
  }
  
  public Pointer to_ptr() {
    return canvas_ptr;
  }
  
  
}
