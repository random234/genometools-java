package annotationsketch;

import com.sun.jna.Library;
import com.sun.jna.Native;
import com.sun.jna.NativeLong;
import com.sun.jna.Pointer;

import core.GTerror;

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
  public FeatureNode get_node_by_ptr(String id) throws GTerror {
    GTerror err = new GTerror();
    int int_tmp = Integer.parseInt(id);
    NativeLong id_long = new NativeLong(int_tmp);
    System.out.println("FeatureIndexDebugBefore");
    Pointer tmp = GT.INSTANCE.gt_feature_index_memory_get_node_by_ptr(this.feat_index, id_long, err.to_ptr());
    FeatureNode feat;
    if(tmp == Pointer.NULL) { throw err; } else {
       feat = new FeatureNode(tmp);
    }
    System.out.println("FeatureIndexDebugAfter");
    return feat;
  }
  protected synchronized void finalize() throws Throwable {
    super.finalize();
  }
}