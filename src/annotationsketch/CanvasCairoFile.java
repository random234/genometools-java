package annotationsketch;

import com.sun.jna.Library;
import com.sun.jna.Native;
import com.sun.jna.NativeLong;
import com.sun.jna.Pointer;

import core.GTerror;

public class CanvasCairoFile extends CanvasCairo
{
  public interface GT extends Library
  {
    GT INSTANCE = (GT) Native.loadLibrary("genometools", GT.class);
    Pointer gt_canvas_cairo_file_new(Pointer style ,int output_type, NativeLong width, NativeLong height, Pointer image_info);
    int gt_canvas_cairo_file_to_file(Pointer canvas, String filename, GTerror err);
    void gt_canvas_delete(Pointer canvas);
  }
  
  public CanvasCairoFile(Pointer style, int width, int height, Pointer image_info) {
    NativeLong n_width = new NativeLong(width);
    NativeLong n_height = new NativeLong(height);
    
    if(image_info == null) {
      canvas_ptr = GT.INSTANCE.gt_canvas_cairo_file_new(style, 1, n_width, n_height, null);
    } else {
      canvas_ptr = GT.INSTANCE.gt_canvas_cairo_file_new(style, 1, n_width, n_height, image_info);
    }
  }
  
  public void finalize() {
    GT.INSTANCE.gt_canvas_delete(canvas_ptr);
  }
  
  public void to_file(String filename) {
    GTerror err = new GTerror();
    int rval = GT.INSTANCE.gt_canvas_cairo_file_to_file(canvas_ptr, filename, err);
    if (rval != 0) { GTerror.gtexcept(err);
  } 
}
 
}