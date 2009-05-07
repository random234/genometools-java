package extended;

import com.sun.jna.Pointer;

public class FeatureNodeIteratorDirect extends FeatureNodeIterator
{
  public FeatureNodeIteratorDirect(Pointer node)
  {
    feat_ptr = GT.INSTANCE.gt_feature_node_iterator_new_direct(node);
  }
}