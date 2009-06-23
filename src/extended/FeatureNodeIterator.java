package extended;
import com.sun.jna.*;

  abstract class FeatureNodeIterator
  {
    Pointer feat_ptr;
    public interface GT extends Library
    {
      GT INSTANCE = (GT) Native.loadLibrary("genometools", GT.class);

      Pointer gt_feature_node_iterator_next(Pointer gn);
      void gt_feature_node_iterator_delete(Pointer gn);
      Pointer gt_feature_node_iterator_new(Pointer gn);
      Pointer gt_feature_node_iterator_new_direct(Pointer gn);
    }
    
    public FeatureNode next()
    {
    	
      Pointer ret = GT.INSTANCE.gt_feature_node_iterator_next(feat_ptr);
      if (ret != null) {
        return new FeatureNode(ret);
      }
      return null;
    }
    protected void finalize()
    {
      GT.INSTANCE.gt_feature_node_iterator_delete(feat_ptr);
    }
  }