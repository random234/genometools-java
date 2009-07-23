package annotationsketch;

import com.sun.jna.Library;
import com.sun.jna.Native;
import com.sun.jna.NativeLong;
import com.sun.jna.Pointer;

import core.GTerror;
import core.GTerrorJava;

import extended.FeatureNode;

public class FeatureIndexMemory extends FeatureIndex {
  public interface GT extends Library
  {
    GT INSTANCE = (GT) Native.loadLibrary("genometools", GT.class);
    Pointer gt_feature_index_memory_new();
    Pointer gt_feature_index_memory_get_node_by_ptr(Pointer fim, NativeLong id, Pointer err);
  }
  
  
  public FeatureIndexMemory() {
    synchronized(this) {
      feat_index = GT.INSTANCE.gt_feature_index_memory_new();
    }
  }
  public FeatureNode get_node_by_ptr(long id) throws GTerrorJava {
    GTerror err = new GTerror();
    NativeLong id_long = new NativeLong(id);
    Pointer tmp = GT.INSTANCE.gt_feature_index_memory_get_node_by_ptr(this.feat_index, id_long, err.to_ptr());
    FeatureNode feat;
    if(tmp == Pointer.NULL) { throw new GTerrorJava(err.get_err()); } else {
       feat = new FeatureNode(tmp);
    }
    return feat;
  }
  protected synchronized void finalize() throws Throwable {
    super.finalize();
  }
}