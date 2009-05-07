package annotationsketch;

import com.sun.jna.Library;
import com.sun.jna.Native;
import com.sun.jna.Pointer;

import core.GTerror;
import core.Range;

public class Diagram
{
  protected Pointer diagram_ptr;
  
  public interface GT extends Library
  {
    GT INSTANCE = (GT) Native.loadLibrary("genometools", GT.class);
    Pointer gt_diagram_new(Pointer feat_index, String seqid, Pointer gt_range, Pointer gt_style, Pointer gt_err);
    Pointer gt_diagram_new_from_array(Pointer gt_array, Pointer gt_range, Pointer gt_style);
    void gt_diagram_set_track_selector_func(Pointer gt_diagram, Pointer func);
    void gt_diagram_add_custom_track(Pointer gt_diagram, Pointer gt_customtrack);
    void gt_diagram_delete(Pointer gt_diagram);
  }
  
  public Diagram(Pointer dia_ptr)
  {
    diagram_ptr = dia_ptr;
  }




  public Diagram diagram_from_index(FeatureIndex feat_index, String seqid, Range ran, Style style ) {
    GTerror err = new GTerror();
    if(ran.start.longValue() < ran.end.longValue()) {
      GTerror.gtexcept("range.start > range.end");
    }
    if(style.equals(Style.class)) { GTerror.gtexcept("style parameter has to be a style Object"); }
    diagram_ptr = GT.INSTANCE.gt_diagram_new(feat_index.to_ptr(), seqid, ran.getPointer(), style.to_ptr(), err.to_ptr());
    if(diagram_ptr == null) { GTerror.gtexcept(err); }
    Diagram d = new Diagram(diagram_ptr);
    return d;
   }
  
  public void set_track_selector_func(Pointer proc) {
    
  }
  
  public void add_custom_track(Pointer ct) {
    GT.INSTANCE.gt_diagram_add_custom_track(diagram_ptr, ct);
  }
  
  public void finalize() {
    GT.INSTANCE.gt_diagram_delete(diagram_ptr);
  }
  
  public Pointer to_ptr() {
    return diagram_ptr;
  }
}
