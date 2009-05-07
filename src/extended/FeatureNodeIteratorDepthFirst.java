package extended;

import com.sun.jna.Pointer;

public class FeatureNodeIteratorDepthFirst extends FeatureNodeIterator
{
  public FeatureNodeIteratorDepthFirst(Pointer node)
  {
    feat_ptr = GT.INSTANCE.gt_feature_node_iterator_new(node);
  }
}