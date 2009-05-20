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
  
  public Layout(Diagram diagram, int width, Style style) throws GTerror {
    GTerror err = new GTerror();
    NativeLong w_long = new NativeLong(width);
    layout_ptr = GT.INSTANCE.gt_layout_new(diagram.to_ptr(), w_long, style.to_ptr(), err.to_ptr());
    if(layout_ptr == null) { throw new GTerror(err.get_err(),err.to_ptr()); }
  }
  
  protected void finalize() throws Throwable {
	try {
      GT.INSTANCE.gt_layout_delete(layout_ptr);
	} finally {
	  super.finalize();
	}
  }
  
  public int get_height(){
    NativeLong tmp = GT.INSTANCE.gt_layout_get_height(layout_ptr);
    long i_tmp = tmp.longValue();
    return (int)i_tmp;
  }
  
  public void sketch(Canvas canvas) throws GTerror {
    GTerror err = new GTerror();
    int had_err = GT.INSTANCE.gt_layout_sketch(layout_ptr, canvas.to_ptr(), err.to_ptr());
    if(had_err < 0) { throw new GTerror(err.get_err(),err.to_ptr());  }
  }
  
  public Pointer to_ptr() {
    return layout_ptr;
  }
}