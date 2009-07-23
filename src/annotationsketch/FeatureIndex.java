package annotationsketch;

import java.util.ArrayList;

import com.sun.jna.Library;
import com.sun.jna.Native;
import com.sun.jna.Pointer;

import core.GTerror;
import core.Array;
import core.GTerrorJava;
import core.Range;
import core.StrArray;
import extended.FeatureNode;


public abstract class FeatureIndex
{
  protected Pointer feat_index;

  public interface GT extends Library
  {
    GT INSTANCE = (GT) Native.loadLibrary("genometools", GT.class);
    void gt_feature_index_delete(Pointer fi);
    void gt_feature_index_add_feature_node(Pointer feature_index,
        Pointer feature_node);
    int gt_feature_index_add_gff3file(Pointer fi, String gff3file, Pointer err);
    Pointer gt_feature_index_get_features_for_seqid(Pointer fi, String seqid);
    String gt_feature_index_get_first_seqid(Pointer fi);
    Pointer gt_feature_index_get_seqids(Pointer fi);
    void gt_feature_index_get_range_for_seqid(Pointer fi, Range rng,
        String seqid);
    Boolean gt_feature_index_has_seqid(Pointer fi, String seqid);
  }

  protected synchronized void finalize() throws Throwable {
    GT.INSTANCE.gt_feature_index_delete(feat_index);
  }
  
  public synchronized ArrayList<FeatureNode> get_features_for_seqid(String seqid)
  {
    Pointer rval = GT.INSTANCE.gt_feature_index_get_features_for_seqid(
        this.feat_index, seqid);
    if (!(rval == null)) {
      Array a = new Array(rval);
      ArrayList<FeatureNode> results = new ArrayList<FeatureNode>();
      for (int i = 0; i < a.size(); i++) {
        FeatureNode fn = new FeatureNode(a.get(i));
        results.add(fn);
      }
      return results;
    }
    return null;
  }
  
  public synchronized void add_feature_node(FeatureNode fn) throws GTerrorJava {
    if (fn.to_ptr() == null) 
      throw new GTerrorJava("feature node must not be NULL");
    GT.INSTANCE.gt_feature_index_add_feature_node(this.feat_index, fn.to_ptr());
  }
  
  public synchronized void add_gff3file(String filename) throws GTerrorJava {
    GTerror err = new GTerror();
    int rval = GT.INSTANCE.gt_feature_index_add_gff3file(this.feat_index, filename, err.to_ptr());
    if (rval != 0) {
      throw new GTerrorJava(err.get_err());
    }
  }
  
  public String get_first_seqid() {
    return GT.INSTANCE.gt_feature_index_get_first_seqid(this.feat_index);
  }
  
  public ArrayList<String> get_seqids() {
    ArrayList<String> results = new ArrayList<String>();
    StrArray stra = new StrArray(GT.INSTANCE.gt_feature_index_get_seqids(this.feat_index));
    for(int i = 0; i < stra.length(); i++ ){
      results.add(stra.get(i));
    }
    return results;
  }
  
  public Range get_range_for_seqid(String seqid) throws GTerrorJava {
    if(GT.INSTANCE.gt_feature_index_has_seqid(this.feat_index, seqid) == false) {
      throw new GTerrorJava("FeatureIndex does not contain seqid");
    }
    Range ran = new Range();
    GT.INSTANCE.gt_feature_index_get_range_for_seqid(this.feat_index, ran, seqid);
    return ran;
  }
  
  public Pointer to_ptr() {
    return feat_index;
  }
  
}






