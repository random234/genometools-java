package extended;

import com.sun.jna.*;

import core.GTerror;
import core.Str;
import extended.GenomeNode;


public class FeatureNode extends GenomeNode
{
  private String[] STRANDCHARS = { "+", "-", ".", "?" };
  
  public interface GT extends Library
  {
    GT INSTANCE = (GT) Native.loadLibrary("genometools", GT.class);

    Pointer gt_feature_node_new(Pointer seqid, String type, NativeLong start,
        NativeLong end, int strand);
    void gt_feature_node_add_child(Pointer parent, Pointer child);
    String gt_feature_node_get_source(Pointer feature_node);
    void gt_feature_node_set_source(Pointer feature_node, Pointer source);
    String gt_feature_node_get_type(Pointer feature_node);
    Boolean gt_feature_node_has_type(Pointer feature_node, String type);
    Boolean gt_feature_node_score_is_defined(Pointer feature_node);
    float gt_feature_node_get_score(Pointer feature_node);
    void gt_feature_node_set_score(Pointer feature_node, float score);
    void gt_feature_node_unset_score(Pointer feature_node);
    int gt_feature_node_get_strand(Pointer feature_node);
    void gt_feature_node_set_strand(Pointer feature_node, int strand);
    int gt_feature_node_get_phase(Pointer feature_node);
    void gt_feature_node_set_phase(Pointer feature_node, int strand);
    String gt_feature_node_get_attribute(Pointer feature_node, String name);
    void gt_feature_node_add_attribute(Pointer feature_node, String tag,
        String value);
    void gt_feature_node_foreach_attribute(Pointer feature_node,
        Pointer attributeiterfunc, Pointer data);
  }

  public FeatureNode(Pointer node_ptr, Boolean newref)
  {
    super(node_ptr, newref);
  }

  protected void finalize()
  {
    
  }

  public FeatureNode(Pointer seqid, String type, int start, int end, int strand)
  {
    switch (strand) {
    case '+':
      break;
    case '-':
      break;
    case '.':
      break;
    default:
      GTerror.gtexcept("Invalid Strand " + (char) strand
          + " must be one of: [+ - .]");
    }
    Str s = new Str(seqid);
    NativeLong stmp = new NativeLong(start);
    NativeLong etmp = new NativeLong(end);
    Pointer newfn = GT.INSTANCE.gt_feature_node_new(s.to_ptr(), type, stmp,
        etmp, strand);
    this.genome_node_ptr = newfn;
  }

  public void node_add_child(Pointer child)
  {
    GT.INSTANCE.gt_feature_node_add_child(this.genome_node_ptr, child);
  }

  public String node_get_source()
  {
    return GT.INSTANCE.gt_feature_node_get_source(this.genome_node_ptr);
  }

  public void node_set_source(Pointer source)
  {
    GT.INSTANCE.gt_feature_node_set_source(this.genome_node_ptr, source);
  }

  public String node_get_type()
  {
    return GT.INSTANCE.gt_feature_node_get_type(this.genome_node_ptr);
  }

  public Boolean node_has_type(String type)
  {
    return GT.INSTANCE.gt_feature_node_has_type(this.genome_node_ptr, type);
  }

  public Boolean score_is_defined()
  {
    return GT.INSTANCE.gt_feature_node_score_is_defined(this.genome_node_ptr);
  }

  public float node_get_score()
  {
    if (this.score_is_defined() == true) {
      return GT.INSTANCE.gt_feature_node_get_score(this.genome_node_ptr);
    } else {
      return (float) 0;
    }
  }

  public void node_set_score(float score)
  {
    GT.INSTANCE.gt_feature_node_set_score(this.genome_node_ptr, score);
  }

  public void node_unset_score()
  {
    GT.INSTANCE.gt_feature_node_unset_score(this.genome_node_ptr);
  }

  public String node_get_strand()
  {
    return STRANDCHARS[GT.INSTANCE.gt_feature_node_get_strand(this.genome_node_ptr)];
  }

  public void node_set_strand(int strand)
  {
    switch (strand) {
    case '+':
      break;
    case '-':
      break;
    case '.':
      break;
    default:
      GTerror.gtexcept("Invalid Strand " + (char) strand
          + " must be one of: [+ - .]");
    }
    GT.INSTANCE.gt_feature_node_set_strand(genome_node_ptr, strand);
  }

  public int node_get_phase()
  {
    return GT.INSTANCE.gt_feature_node_get_phase(this.genome_node_ptr);
  }

  public void node_set_phase(int strand)
  {
    GT.INSTANCE.gt_feature_node_set_phase(this.genome_node_ptr, strand);
  }

  public String node_get_attribute(String name)
  {
    return GT.INSTANCE.gt_feature_node_get_attribute(this.genome_node_ptr, name);
  }

  public void node_add_attribute(String tag, String value)
  {
    if (tag.toString() == "" || value.toString() == "") {
      GTerror.gtexcept("attribute keys or values must not be empty");
    } else {
      GT.INSTANCE.gt_feature_node_add_attribute(this.genome_node_ptr, tag, value);
    }
  }

  public void node_foreach_attribute(Pointer feature_node)
  {
      
  }
}
