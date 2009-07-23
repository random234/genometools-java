package annotationsketch;

import com.sun.jna.Library;
import com.sun.jna.Native;
import com.sun.jna.NativeLong;
import com.sun.jna.Pointer;

import core.GTerror;
import core.GTerrorJava;

public class CanvasCairoFile extends CanvasCairo
{
  public interface GT extends Library
  {
    GT INSTANCE = (GT) Native.loadLibrary("genometools", GT.class);
    Pointer gt_canvas_cairo_file_new(Pointer style, int output_type, NativeLong width, NativeLong height, Pointer image_info);
    int gt_canvas_cairo_file_to_file(Pointer canvas, String filename, Pointer err);
    void gt_canvas_delete(Pointer canvas);
  }
  
  public CanvasCairoFile(Style style, int width, int height, ImageInfo image_info) throws GTerrorJava{
    NativeLong n_width = new NativeLong(width);
    NativeLong n_height = new NativeLong(height);
    
    if(image_info == null) {
      throw new GTerrorJava("ImageInfo is not initialized");
    } else {
      canvas_ptr = GT.INSTANCE.gt_canvas_cairo_file_new(style.to_ptr(), 1, n_width, n_height, image_info.to_ptr());
    }
  }
  
  public CanvasCairoFile(Style style, int width, int height) {
    NativeLong n_width = new NativeLong(width);
    NativeLong n_height = new NativeLong(height);
    canvas_ptr = GT.INSTANCE.gt_canvas_cairo_file_new(style.to_ptr(), 1, n_width, n_height,null);
  }
  
  protected void finalize() {
    super.finalize();
  }
  
  public void to_file(String filename) throws GTerrorJava {
    GTerror err = new GTerror();
    int rval = GT.INSTANCE.gt_canvas_cairo_file_to_file(canvas_ptr, filename, err.to_ptr());
    if (rval != 0) { throw new GTerrorJava(err.get_err()); } 
}
 
}