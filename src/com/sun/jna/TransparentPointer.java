package com.sun.jna;

import com.sun.jna.Pointer;

public class TransparentPointer extends Pointer {
  public TransparentPointer(long peer) {
    this.peer = peer;
  }

  public TransparentPointer(Pointer pointer) {
    this.peer = pointer.peer;
  }
   
  public long getPeer() {
   return this.peer;
  }
}

