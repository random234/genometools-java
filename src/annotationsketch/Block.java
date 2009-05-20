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
  private final char STRANDCHARS[] = { '+', '-', '.', '?' };

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

  public Block(Pointer ptr) throws GTerror
  {
    if (ptr == null) {
      throw new GTerror("Block Ptr wasn't initialized ");
    }
    block_ptr = GT.INSTANCE.gt_block_ref(ptr);
  }

  public Range get_range()
  {
    Range r = new Range();
    r = GT.INSTANCE.gt_block_get_range(this.block_ptr);
    return r;
  }

  public String get_type()
  {
    return GT.INSTANCE.gt_block_get_type(this.block_ptr);
  }

  public Boolean has_only_one_fullsize_element()
  {
    return GT.INSTANCE.gt_block_has_only_fullsize_element(this.block_ptr);
  }

  public Block merge(Pointer block2_ptr) throws NullPointerException, GTerror
  {
    try { if(block2_ptr == null) { throw new NullPointerException("Block ptr wasn't initialized"); }
    } catch (NullPointerException e) {
      throw new GTerror("GTerror occured: ", e);
    }
    Block b = new Block(GT.INSTANCE.gt_block_merge(this.block_ptr, block2_ptr));
    return b;
  }

  public Block clone_block() throws GTerror
  {
    Block b = new Block(GT.INSTANCE.gt_block_clone(block_ptr));
    return b;
  }

  public void set_strand(char strand) throws GTerror
  {
    switch (strand) {
    case '+':
    case '-':
    case '.':
    case '?':
      break;
    default:
      throw new GTerror("Invalid Strand " + (char) strand
          + " must be one of: [+ - . ?]");
    }
    GT.INSTANCE.gt_block_set_strand(block_ptr, strand);
  }

  public char get_strand()
  {
    return STRANDCHARS[GT.INSTANCE.gt_block_get_strand(block_ptr)];
  }

  public FeatureNode get_top_level_feature()
  {
    Pointer f = GT.INSTANCE.gt_block_get_top_level_feature(block_ptr);
    if (f != null) {
      return new FeatureNode(f, true);
    } else {
      return null;
    }
  }

  public int get_size()
  {
    NativeLong tmp = new NativeLong();
    tmp = GT.INSTANCE.gt_block_get_size(block_ptr);
    long l_tmp = tmp.longValue();
    return (int) l_tmp;
  }

  public Pointer to_ptr()
  {
    return block_ptr;
  }

  protected void finalize()
  {
    GT.INSTANCE.gt_block_delete(block_ptr);
  }
}
