package annotationsketch;

import com.sun.jna.Library;
import com.sun.jna.Native;
import com.sun.jna.NativeLong;
import com.sun.jna.Pointer;
import core.GTerror;
import core.Range;
import extended.FeatureNode;

public class Block
{
  private Pointer block_ptr;
  private final char STRANDCHARS[] = {'+', '-', '.'};
  
  public interface GT extends Library
  {
    GT INSTANCE = (GT) Native.loadLibrary("genometools", GT.class);
    Pointer gt_block_ref(Pointer gt_block);
    Range gt_block_get_range(Pointer gt_block);
    String gt_block_get_type(Pointer gt_block);
    boolean gt_block_has_only_fullsize_element(Pointer gt_block);
    Pointer gt_block_merge(Pointer gt_block, Pointer gt_block_sec);
    Pointer gt_block_clone(Pointer gt_block);
    void gt_block_set_strand(Pointer gt_block, int i);
    Pointer gt_block_get_top_level_feature(Pointer gt_block);
    int gt_block_get_strand(Pointer gt_block);
    NativeLong gt_block_get_size(Pointer gt_block);
    void gt_block_delete(Pointer gt_block);
  }
  
  public Block(Pointer ptr) {
    if(ptr == null) { GTerror.gtexcept("Block Pointer cannot be Null"); }
    block_ptr = GT.INSTANCE.gt_block_ref(ptr);
  }
  
  public Range get_range() {
    Range r = new Range(); 
    r = GT.INSTANCE.gt_block_get_range(block_ptr);
    return r;
  }
  
  public String get_type() {
    return GT.INSTANCE.gt_block_get_type(block_ptr);
  }
  
  public Boolean has_only_one_fullsize_element() {
    return GT.INSTANCE.gt_block_has_only_fullsize_element(block_ptr);
  }
  
  public Block merge(Pointer block2_ptr) {
    Block b = new Block(GT.INSTANCE.gt_block_merge(block_ptr, block2_ptr));
    return b;
  }
  
  public Block clone() {
    Block b = new Block(GT.INSTANCE.gt_block_clone(block_ptr));
    return b;
  }
  
  public void set_strand(char strand) {
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
    GT.INSTANCE.gt_block_set_strand(block_ptr, strand);    
}
  
  public char get_strand() {
    return STRANDCHARS[GT.INSTANCE.gt_block_get_strand(block_ptr)];
  }
  
  public FeatureNode get_top_level_feature() {
    Pointer f = GT.INSTANCE.gt_block_get_top_level_feature(block_ptr);
    if(f != null) { return new FeatureNode(f,true); } else { return null; }
  }
  
  public int get_size() {
    NativeLong tmp = new NativeLong();
    tmp = GT.INSTANCE.gt_block_get_size(block_ptr);
    long l_tmp = tmp.longValue();    
    return (int)l_tmp;
  }
  
  public Pointer to_ptr() {
    return block_ptr;
  }
  
  public void finalize() {
    GT.INSTANCE.gt_block_delete(block_ptr);
  } 
}
