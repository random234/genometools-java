package extended;
import org.junit.*;

import core.GTerror;
import core.Range;
import static org.junit.Assert.*;

public class FeatureNodeTest
{
  FeatureNode fn = new FeatureNode("test","type",1000,8000,".");
  FeatureNode fn2 = new FeatureNode("test", "type2",600,700,"+");
  @Before
  public void init() {
    try {
    fn.add_attribute("test", "testval");
    fn.add_attribute("test2", "testval");
    } catch (GTerror e) { System.err.println(e.getCause()); e.printStackTrace(); }
  }
  
  @Test
  public void test_score_defined() {
    fn.set_score(2);
    assertTrue(fn.score_is_defined());
  }
  @Test
  public void test_get_score() {
    fn.set_score(2);
    assertTrue(fn.get_score() == 2);
  }
  @Test
  public void test_unset_score() {
    fn.unset_score();
    assertTrue(!fn.score_is_defined());
  }
  @Test
  public void test_has_type() {
    assertTrue(!fn.has_type("Horst"));
    assertTrue(fn.has_type("type"));
  }
  @Test
  public void test_get_strand() {
    assertTrue(fn.get_strand() == '.');
  }
  public void init2() {
    fn.add_child(fn2);
  }
  @Test 
  public void test_set_strand() {
    try {
      fn.set_strand("+");
    } catch (GTerror e) {     
      e.printStackTrace();
    }
    assertTrue(fn.get_strand() == '+');
  }
  @Test
  public void test_get_phase() {
    assertTrue(fn.get_phase() == 3 );
  }
  @Test
  public void test_set_phase() {
    fn.set_phase(0);
    assertTrue(fn.get_phase() == 0);
  }
  @Test
  public void test_get_source() {
    assertTrue(fn.get_source().equals("."));
  }
  @Test
  public void test_get_type() {
    assertTrue(fn.get_type().equals("type"));
    assertTrue(fn2.get_type().equals("type2"));
  }
  @Test
  public void test_get_attribute() {
    assertTrue(fn.get_attribute("test").equals("testval"));
  }
  // This is the beginning of the tests for the Underlying GenomeNode class
  @Test
  public void test_get_range() {
    Range r = new Range();
    r = fn.get_range();
    assertTrue(r.get_size() == 7000);
  }
  @Test
  public void test_get_filename() {
    assertTrue(fn.get_filename().equals("generated"));
  }
}
