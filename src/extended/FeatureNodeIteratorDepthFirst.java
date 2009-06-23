package extended;

public class FeatureNodeIteratorDepthFirst extends FeatureNodeIterator
{
  public FeatureNodeIteratorDepthFirst(FeatureNode node)
  {
    synchronized (this) {
      feat_ptr = GT.INSTANCE.gt_feature_node_iterator_new(node.to_ptr());
    }
  }
}