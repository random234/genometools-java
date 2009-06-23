package extended;

public class FeatureNodeIteratorDirect extends FeatureNodeIterator
{
  public FeatureNodeIteratorDirect(FeatureNode node)
  {
    synchronized (this) {
      feat_ptr = GT.INSTANCE.gt_feature_node_iterator_new_direct(node.to_ptr());
    }
  }
}