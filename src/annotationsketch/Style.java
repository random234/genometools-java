package annotationsketch;

import com.sun.jna.Library;
import com.sun.jna.Native;
import com.sun.jna.Pointer;
import com.sun.jna.ptr.DoubleByReference;
import com.sun.jna.ptr.IntByReference;
import core.GTerror;
import core.GTerrorJava;
import core.Str;

public class Style
{
  private Pointer style_ptr;

  public interface GT extends Library
  {
    GT INSTANCE = (GT) Native.loadLibrary("genometools", GT.class);

    Pointer gt_style_new(Pointer err);
    int gt_style_load_file(Pointer style, String str, Pointer err);
    int gt_style_load_str(Pointer style, Pointer str, Pointer err);
    int gt_style_to_str(Pointer style, Pointer str, Pointer err);
    // feature_node Object should be set to null as long as it isn't used
    boolean gt_style_get_color(Pointer style, String sect, String key,
        Pointer color, Pointer feat_node);
    void gt_style_set_color(Pointer style, String sect, String key,
        Pointer color);
    // feature_node Object should be set to null as long as it isn't used
    boolean gt_style_get_str(Pointer style, String sect, String key,
        Pointer str, Pointer feat_node);
    void gt_style_set_str(Pointer style, String sect, String key, Pointer str);
    // feature_node Object should be set to null as long as it isn't used
    boolean gt_style_get_num(Pointer style, String sect, String key,
        DoubleByReference i, Pointer feat_node);
    void gt_style_set_num(Pointer style, String sect, String key,
        DoubleByReference i);
    // feature_node Object should be set to null as long as it isn't used
    boolean gt_style_get_bool(Pointer style, String sect, String key,
        IntByReference b, Pointer feat_node);
    void gt_style_set_bool(Pointer style, String sect, String key, int b);
    void gt_style_unset(Pointer style, String sect, String key);
    void gt_style_delete(Pointer style);
  }

  public Style() throws GTerrorJava
  {
    GTerror err = new GTerror();
    style_ptr = GT.INSTANCE.gt_style_new(err.to_ptr());
    if (style_ptr == null) {
      throw new GTerrorJava(err.get_err());
    }
  }

  public void load_file(String filename) throws GTerrorJava
  {
    GTerror err = new GTerror();
    int rval = GT.INSTANCE
        .gt_style_load_file(style_ptr, filename, err.to_ptr());
    if (rval != 0) {
      throw new GTerrorJava(err.get_err());
    }
  }

  public void load_str(String str) throws GTerrorJava
  {
    GTerror err = new GTerror();
    Str str_obj = new Str(str);
    int rval = GT.INSTANCE.gt_style_load_str(style_ptr, str_obj.to_ptr(), err
        .to_ptr());
    if (rval != 0) {
      throw new GTerrorJava(err.get_err());
    }
  }

  public Str to_str() throws GTerrorJava
  {
    GTerror err = new GTerror();
    Str str_obj = new Str("");
    int rval = GT.INSTANCE.gt_style_to_str(style_ptr, str_obj.to_ptr(), err
        .to_ptr());
    if (rval == 0) {
      return str_obj;
    } else {
      throw new GTerrorJava(err.get_err());
    }

  }

  public Style clone_style() throws GTerrorJava
  {
    Style sty = new Style();
    Str str_obj = sty.to_str();
    sty.load_str(str_obj.to_s());
    return sty;
  }

  public Color get_color(String sect, String key)
  {
    Pointer feat_node = null;
    Color color = new Color();
    boolean rval = GT.INSTANCE.gt_style_get_color(style_ptr, sect, key, color
        .getPointer(), feat_node);
    // true == 1
    if (rval == true) {
      return color;
    } else {
      return null;
    }
  }

  public void set_color(String sect, String key, Color color)
  {

    GT.INSTANCE.gt_style_set_color(style_ptr, sect, key, color.getPointer());
  }

  public Str get_cstr(String sect, String key, String value)
  {
    Pointer feat_node = null;
    Str string = new Str(value);
    boolean rval = GT.INSTANCE.gt_style_get_str(style_ptr, sect, key, string
        .to_ptr(), feat_node);
    if (rval == true) {
      return string;
    } else {
      return null;
    }
  }

  public void set_cstr(String sect, String key, String value)
  {
    Str string = new Str(value);
    GT.INSTANCE.gt_style_set_str(style_ptr, sect, key, string.to_ptr());
  }

  public double get_num(String sect, String key)
  {
    Pointer feat_node = null;
    double number = 0;
    DoubleByReference num_byref = new DoubleByReference(number);
    // number should be given by Reference
    boolean rval = GT.INSTANCE.gt_style_get_num(style_ptr, sect, key,
        num_byref, feat_node);
    if (rval == true) {
      return number;
    } else {
      return 0;
    }
  }

  public void set_num(String sect, String key, double number)
  {
    DoubleByReference d_num = new DoubleByReference(number);
    GT.INSTANCE.gt_style_set_num(style_ptr, sect, key, d_num);
  }

  public boolean get_bool(String sect, String key) throws GTerrorJava {
    Pointer feat_node = null;
    int bool = 2;
    IntByReference i_bool = new IntByReference(bool);
    boolean rval = GT.INSTANCE.gt_style_get_bool(style_ptr, sect, key, i_bool, feat_node);
    if(rval == true) {
      if(bool == 1) { return true; } else { throw new GTerrorJava("Section doesn't exist"); }  
    } else { throw new GTerrorJava("Section doesn't exist"); }  
  }
  
  public void set_bool(String sect, String key, boolean value) {
    if (value == true) { GT.INSTANCE.gt_style_set_bool(style_ptr, sect, key, 1); }
    else { GT.INSTANCE.gt_style_set_bool(style_ptr, sect, key, 0); }
    }

  public void unset(String sect, String key) {
    GT.INSTANCE.gt_style_unset(style_ptr, sect, key);
  }
  
  public Pointer to_ptr()
  {
    return style_ptr;
  }

  protected void finalize()
  {
    GT.INSTANCE.gt_style_delete(style_ptr);
  }
}
