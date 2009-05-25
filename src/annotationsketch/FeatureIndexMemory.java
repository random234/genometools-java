package annotationsketch;

public class FeatureIndexMemory extends FeatureIndex {
  public FeatureIndexMemory() {
    synchronized(this) {
      feat_index = GT.INSTANCE.gt_feature_index_memory_new();
    }
  }
  
  protected synchronized void finalize() throws Throwable {
    super.finalize();
  }
}