package annotationsketch;

import java.util.ArrayList;

import org.junit.*;

import core.Range;
import static org.junit.Assert.*;

public class FeatureIndexTest
{
  FeatureIndexMemory fi = new FeatureIndexMemory();
  @Before
  public void init() {
    fi.add_gff3file("../result.gff3");
  }
  @Test
  public void test_add_gff3file() {
    assertTrue(fi.get_first_seqid().equals("seq0"));
  }
  @Test
  public void test_get_first_seqid() {
    assertTrue(fi.get_first_seqid().equals("seq0"));
  }
  @Test
  public void test_get_seqids() {
   ArrayList<String> arr = new ArrayList<String>();
   arr = fi.get_seqids();
   assertTrue(arr.contains("seq11"));   
  }
  @Test
  public void test_get_range_for_seqid() {
    Range r = new Range();
    r = fi.get_range_for_seqid("seq0");
    assertTrue(r.get_size() == 5934);
  }
  @Test
  public void test_to_ptr() {
    assertTrue(fi.to_ptr() == fi.feat_index);
  }
  
  
}