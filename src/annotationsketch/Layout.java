package annotationsketch;

import com.sun.jna.Library;
import com.sun.jna.Native;
import com.sun.jna.NativeLong;
import com.sun.jna.Pointer;

import core.GTerror;

public class Layout
{
  protected Pointer layout_ptr;
  public interface GT extends Library
  {
    GT INSTANCE = (GT) Native.loadLibrary("genometools", GT.class);
    Pointer gt_layout_new(Pointer diagram, NativeLong width, Pointer gt_style, Pointer err);
    NativeLong gt_layout_get_height(Pointer gt_lay_ptr);
    int gt_layout_sketch(Pointer gt_lay_ptr, Pointer target_canvas, Pointer err);
    void gt_layout_delete(Pointer gt_lay_ptr);
  }
  
  public Layout(Diagram diagram, int width, Style style) {
    GTerror err = new GTerror();
    NativeLong w_long = new NativeLong(width);
    
    layout_ptr = GT.INSTANCE.gt_layout_new(diagram.to_ptr(), w_long, style.to_ptr(), err.to_ptr());
    if(layout_ptr == null) { GTerror.gtexcept(err); }
  }
  
  public void finalize() {
    GT.INSTANCE.gt_layout_delete(layout_ptr);
  }
  
  public int get_height(){
    NativeLong tmp = GT.INSTANCE.gt_layout_get_height(layout_ptr);
    long i_tmp = tmp.longValue();
    return (int)i_tmp;
  }
  
  public void sketch(Canvas canvas) {
    GTerror err = new GTerror();
    int had_err = GT.INSTANCE.gt_layout_sketch(layout_ptr, canvas.to_ptr(), err.to_ptr());
    if(had_err < 0) { GTerror.gtexcept(err);  }
  }
  
  public Pointer to_ptr() {
    return layout_ptr;
  }
}