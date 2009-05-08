package core;
import org.junit.*;

import extended.FeatureNode;
import static org.junit.Assert.*;

public class ArrayTest
{
  FeatureNode fn = new FeatureNode("test","type",1000,8000,".");
  @Test
  public void test_to_ptr() {
    System.out.println("Testing the to_ptr() method of the Array class");
    Array arr = new Array(4);
    assertTrue(arr.to_ptr() != null);
    System.out.println("--- Done ---");
  }
  
  @Test
  public void test_size() {
    System.out.println("Testing the size() method of the Array class");
    // Size of Element is meant to be the size of a Pointer on the Target system
    Array arr = new Array(4);
    assertTrue(arr.size() == 0);
    for (int i = 0; i<8 ; i++){
    arr.add(fn); }
    assertTrue(arr.size() == 8);
    assertTrue(arr.size() != 7);
    System.out.println("--- Done ---");
  }
  @Test
  public void test_add() {
    System.out.println("Testing the add() method of the Array class");
    Array arr = new Array(4);
    arr.add(fn);
    assertTrue(arr.get(0).equals(fn.to_ptr()));
    System.out.println("--- Done ---");
  }
  
  @Test
  public void test_get() {
    System.out.println("Testing the get() method of the Array class");
    Array arr = new Array(4);
    arr.add(fn);
    assertEquals(fn.to_ptr(), arr.get(0));
    System.out.println("--- Done ---");
  }
  @Test
  public void test_ArrayPointer() {
    System.out.println("Testing the Constructor Array(Pointer ptr) of the Array class");
    Array arr = new Array(4);
    Array arr2 = new Array(arr.to_ptr());
    arr.add(fn);
    assertTrue(arr.get(0).equals(fn.to_ptr()));
    assertNotNull(arr);
    assertNotNull(arr2);
    System.out.println("--- Done ---");    
  }
  @Test
  public void test_ArraySizeOfElem() {
    System.out.println("Testing the Constructor Array(int size_of_elem) of the Array class");
    Array arr = new Array(4);    
    arr.add(fn);
    Array arr2 = new Array(8);
    assertNotNull(arr);
    assertNotNull(arr2);
    System.out.println("--- Done ---");
    
  } 
}
