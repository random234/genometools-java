package annotationsketch;

import annotationsketch.Diagram.GT.TRACKSELECTOR;

import com.sun.jna.Library;
import com.sun.jna.Native;
import com.sun.jna.Pointer;
import com.sun.jna.Callback;

import core.Array;
import core.GTerror;
import core.Range;
import extended.FeatureNode;

public class Diagram
{
  protected Pointer diagram_ptr;
  @SuppressWarnings("unused")
  private TRACKSELECTOR tsf;

  public interface GT extends Library
  {
    GT INSTANCE = (GT) Native.loadLibrary("genometools", GT.class);

    Pointer gt_diagram_new(Pointer feat_index, String seqid, Pointer gt_range,
        Pointer gt_style, Pointer gt_err);
    Pointer gt_diagram_new_from_array(Pointer gt_array, Range gt_range,
        Pointer gt_style);
    void gt_diagram_set_track_selector_func(Pointer gt_diagram,
        TRACKSELECTOR func);
    void gt_diagram_add_custom_track(Pointer gt_diagram, Pointer gt_customtrack);
    void gt_diagram_delete(Pointer gt_diagram);

    interface TRACKSELECTOR extends Callback
    {
      String callback(Pointer block_ptr, Pointer data_ptr) throws GTerror;
    }
  }

  public Diagram(FeatureNode[] arr, Range rng, Style sty) throws GTerror
  {
    if (rng.get_start() > rng.get_end()) {
      throw new GTerror("range.start > range.end");
    }
    Array gtarr = new Array(4);
    for (int i = 0; i < arr.length; i++) {
      gtarr.add(arr[i]);
    }
    Pointer dia = GT.INSTANCE.gt_diagram_new_from_array(gtarr.to_ptr(), rng,
        sty.to_ptr());
    if (dia == null) {
      throw new GTerror("Diagram Pointer could not be allocated");
    } else {
      this.diagram_ptr = dia;
    }
  }

  public Diagram(FeatureIndex feat_index, String seqid, Range ran, Style style) throws GTerror
  {

    if (ran.start.longValue() < ran.end.longValue()) {
      throw new GTerror("range.start > range.end");
    }
    if (style.equals(Style.class)) {
      throw new GTerror("style parameter has to be a style Object");
    }
    GTerror err = new GTerror();
    Pointer dia = GT.INSTANCE.gt_diagram_new(feat_index.to_ptr(), seqid, ran
        .getPointer(), style.to_ptr(), err.to_ptr());
    if (dia == null) {
      throw new GTerror(err.get_err(),err.to_ptr());
    } else {
      this.diagram_ptr = dia;
    }
  }

  public void set_track_selector_func(final TrackSelector ts)
  {
    TRACKSELECTOR tsf = new GT.TRACKSELECTOR()
    {
      public String callback(Pointer block_ptr, Pointer data_ptr) throws GTerror
      {
        Block b = new Block(block_ptr);
        String string = ts.getTrackId(b);
        if (string != null) {
          return string;
        } else {
          throw new GTerror("String returned by Trackselector function can not be null");
        }
      }
    };
    this.tsf = tsf;
    GT.INSTANCE.gt_diagram_set_track_selector_func(diagram_ptr, tsf);
  }

  public void add_custom_track(Pointer ct)
  {
    GT.INSTANCE.gt_diagram_add_custom_track(diagram_ptr, ct);
  }

  public void finalize()
  {
    GT.INSTANCE.gt_diagram_delete(diagram_ptr);
  }

  public Pointer to_ptr()
  {
    return diagram_ptr;
  }
}
