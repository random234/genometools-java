package core;

import org.junit.*;
import static org.junit.Assert.*;

public class StrTest
{
  
  @BeforeClass
  public static void init() {
    Allocators.init();   
  }
  
  @Test
  public void test_to_ptr()
  {
    Str s = new Str("ATATAT");
    assertTrue(s.to_ptr() != null);
  }
  
  @Test
  public void test_length()
  {
    Str s = new Str("ATATAT");
    assertTrue(s.length() == 6);
  }
  
  @Test
  public void test_to_s()
  {
    Str s = new Str("ATATAT");
    assertTrue(s.to_s().equals("ATATAT"));
    String string = s.to_s();
    assertTrue(string.equals("ATATAT"));
    assertTrue(!s.to_s().equals("GCGCGC"));
  }
  
  @Test
  public void test_append_str()
  {
    Str s = new Str("ATATAT");
    Str s2 = new Str("GCGCGC");
    s.append_str(s2);
    assertTrue(s.to_s().equals("ATATATGCGCGC"));
    assertTrue(!s.to_s().equals("GCGCGCATATAT"));
    assertTrue(s.length() == 12);
  }
  
  @Test
  public void testStrPointer()
  {
    Str s = new Str("ATATAT");
    Str s2 = new Str(s.to_ptr());
    assertTrue(s2.to_s().equals("ATATAT"));
    assertTrue(!s2.to_s().equals("ATAT"));
    assertTrue(s.to_ptr().equals(s2.to_ptr()));
  }
  
  @Test
  public void testStrString()
  {
    Str s = new Str("ATATAT");
    assertTrue(s.length() == 6);
    assertTrue(s.to_s().equals("ATATAT"));
  }
}
