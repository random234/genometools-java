package core;
import org.junit.*;

import com.sun.jna.Pointer;

import extended.FeatureNode;
import static org.junit.Assert.*;

public class ArrayTest
{
  static FeatureNode fn;
  
  @BeforeClass
  public static void init() throws GTerrorJava {
    Allocators.init();
    fn = new FeatureNode("test","type",1000,8000,".");
  }
  
  @Test
  public void test_to_ptr() {
    Array arr = new Array(Pointer.SIZE);
    assertTrue(arr.to_ptr() != null);
  }
  
  @Test
  public void test_size() {
    // Size of Element is meant to be the size of a Pointer on the Target system
    Array arr = new Array(Pointer.SIZE);
    assertTrue(arr.size() == 0);
    for (int i = 0; i<8 ; i++){
    arr.add(fn); }
    assertTrue(arr.size() == 8);
    assertTrue(arr.size() != 7);
  }
  @Test
  public void test_add() {
    Array arr = new Array(Pointer.SIZE);
    arr.add(fn);
    assertTrue(arr.get(0).equals(fn.to_ptr()));
  }
  
  @Test
  public void test_get() {
    Array arr = new Array(Pointer.SIZE);
    arr.add(fn);
    assertEquals(fn.to_ptr(), arr.get(0));
  }
  
  @Test
  public void test_ArrayPointer() {
    Array arr = new Array(Pointer.SIZE);
    Array arr2 = new Array(arr.to_ptr());
    arr.add(fn);
    assertTrue(arr.get(0).equals(fn.to_ptr()));
    assertNotNull(arr);
    assertNotNull(arr2);
  }
}
