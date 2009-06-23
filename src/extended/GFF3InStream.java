package extended;

import java.io.File;
import java.io.IOException;

import com.sun.jna.Library;
import com.sun.jna.Native;
import com.sun.jna.Pointer;
import core.StrArray;

public class GFF3InStream extends GenomeStream
{
  public interface GT extends Library
  {
    GT INSTANCE = (GT) Native.loadLibrary("genometools", GT.class);
    Pointer gt_gff3_in_stream_new_sorted(String filename);
    Pointer gt_gff3_in_stream_get_used_types(Pointer node_stream);    
  }
  
  public GFF3InStream(String filename) throws IOException {
    
      File fi = new File(filename);
      if(!fi.exists()) {
        throw new IOException(filename + " does not exist");
      } else {
        genome_stream = GT.INSTANCE.gt_gff3_in_stream_new_sorted(filename);
      }    
  }
  
  public StrArray used_types() {
    Pointer str_array_ptr = GT.INSTANCE.gt_gff3_in_stream_get_used_types(this.genome_stream);
    StrArray str_arr = new StrArray(str_array_ptr);
    return str_arr;
  }
}
