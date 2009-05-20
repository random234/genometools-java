package extended;

import com.sun.jna.*;

import core.GTerror;
import core.Str;
import extended.GenomeNode;


public class FeatureNode extends GenomeNode
{
  private char[] STRANDCHARS = { '+','-','.','?' };
  
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
  
  public FeatureNode(Pointer node_ptr) 
  {
    super(node_ptr);
  }

  public FeatureNode(String seqid, String type, int start, int end, String stra)
  {
    try {
    char strand = stra.charAt(0);
    switch (strand) {
    case '+': strand = 0;
      break;
    case '-': strand = 1;
      break;
    case '.': strand = 2;
      break;
    case '?': strand = 3;
      break;
    default:
      throw new GTerror("Invalid Strand " + (char) strand
          + " must be one of: [+ - . ?]");
    }
    Str s = new Str(seqid);
    NativeLong stmp = new NativeLong(start);
    NativeLong etmp = new NativeLong(end);
    Pointer newfn = GT.INSTANCE.gt_feature_node_new(s.to_ptr(), type, stmp,
        etmp, strand);
    this.genome_node_ptr = newfn;
    } catch (GTerror e) { e.printStackTrace(); }
  }

  public void add_child(FeatureNode child)
  {
    GT.INSTANCE.gt_feature_node_add_child(this.genome_node_ptr, child.to_ptr());
  }

  public String get_source()
  {
    return GT.INSTANCE.gt_feature_node_get_source(this.genome_node_ptr);
  }

  public void set_source(Pointer source)
  {
    GT.INSTANCE.gt_feature_node_set_source(this.genome_node_ptr, source);
  }

  public String get_type()
  {
    return GT.INSTANCE.gt_feature_node_get_type(this.genome_node_ptr);
  }

  public Boolean has_type(String type)
  {
    return GT.INSTANCE.gt_feature_node_has_type(this.genome_node_ptr, type);
  }

  public Boolean score_is_defined()
  {
    return GT.INSTANCE.gt_feature_node_score_is_defined(this.genome_node_ptr);
  }

  public float get_score()
  {
    if (this.score_is_defined() == true) {
      return GT.INSTANCE.gt_feature_node_get_score(this.genome_node_ptr);
    } else {
      return (float) 0;
    }
  }

  public void set_score(float score)
  {
    GT.INSTANCE.gt_feature_node_set_score(this.genome_node_ptr, score);
  }

  public void unset_score()
  {
    GT.INSTANCE.gt_feature_node_unset_score(this.genome_node_ptr);
  }

  public char get_strand()
  {
    return STRANDCHARS[GT.INSTANCE.gt_feature_node_get_strand(this.genome_node_ptr)];
  }

  public void set_strand(String stra) throws GTerror
  {
    char strand = stra.charAt(0);
    switch (strand) {
    case '+': strand = 0; 
      break;
    case '-': strand = 1;
      break;
    case '.': strand = 2;
      break;
    case '?': strand = 3;
    default:
      throw new GTerror("Invalid Strand " + (char) strand
          + " must be one of: [+ - . ?]");
    }
    GT.INSTANCE.gt_feature_node_set_strand(genome_node_ptr, strand);
  }

  public int get_phase()
  {
    return GT.INSTANCE.gt_feature_node_get_phase(this.genome_node_ptr);
  }

  public void set_phase(int strand)
  {
    GT.INSTANCE.gt_feature_node_set_phase(this.genome_node_ptr, strand);
  }

  public String get_attribute(String name)
  {
    return GT.INSTANCE.gt_feature_node_get_attribute(this.genome_node_ptr, name);
  }

  public void add_attribute(String tag, String value) throws GTerror
  {
    if (tag.toString() == "" || value.toString() == "") {
      throw new GTerror("attribute keys or values must not be empty");
    } else {
      GT.INSTANCE.gt_feature_node_add_attribute(this.genome_node_ptr, tag, value);
    }
  }
}
