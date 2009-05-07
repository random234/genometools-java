package annotationsketch;

public class FeatureIndexMemory extends FeatureIndex {
  public FeatureIndexMemory() {
    feat_index = GT.INSTANCE.gt_feature_index_memory_new(); 
  }
  public void finalize() {
    GT.INSTANCE.gt_feature_index_delete(feat_index);
  }
}