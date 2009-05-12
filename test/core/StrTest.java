package core;

import org.junit.*;
import static org.junit.Assert.*;

public class StrTest
{
  @Test
  public void test_to_ptr()
  {
    System.out.println("Testing the to_ptr() method of the Str class");
    Str s = new Str("ATATAT");
    assertTrue(s.to_ptr() != null);
    System.out.println("--- Done ---");
  }
  @Test
  public void test_length()
  {
    System.out.println("Testing the length() method of the Str class");
    Str s = new Str("ATATAT");
    assertTrue(s.length() == 6);
    System.out.println("--- Done ---");
  }
  @Test
  public void test_to_s()
  {
    System.out.println("Testing the to_s() method of the Str class");
    Str s = new Str("ATATAT");
    assertTrue(s.to_s().equals("ATATAT"));
    String string = s.to_s();
    assertTrue(string.equals("ATATAT"));
    assertTrue(!s.to_s().equals("GCGCGC"));
    System.out.println("--- Done ---");
  }
  @Test
  public void test_append_str()
  {
    System.out.println("Testing the append_str() method of the Str class");
    Str s = new Str("ATATAT");
    Str s2 = new Str("GCGCGC");
    s.append_str(s2);
    assertTrue(s.to_s().equals("ATATATGCGCGC"));
    assertTrue(!s.to_s().equals("GCGCGCATATAT"));
    assertTrue(s.length() == 12);
    System.out.println("--- Done ---");
  }
  @Test
  public void testStrPointer()
  {
    System.out
        .println("Testing the construtor Str(Pointer cstr) of the Str class");
    Str s = new Str("ATATAT");
    Str s2 = new Str(s.to_ptr());
    assertTrue(s2.to_s().equals("ATATAT"));
    assertTrue(!s2.to_s().equals("ATAT"));
    assertTrue(s.to_ptr() == s2.to_ptr());
    System.out.println("--- Done ---");
  }
  @Test
  public void testStrString()
  {
    System.out
        .println("Testing the constructor Str(String string) of the Str class");
    Str s = new Str("ATATAT");
    assertTrue(s.length() == 6);
    assertTrue(s.to_ptr() != null);
    assertTrue(s.to_s().equals("ATATAT"));
    System.out.println("--- Done ---");
  }
}
