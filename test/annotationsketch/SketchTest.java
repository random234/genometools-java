package annotationsketch;

import org.junit.*;

import core.GTerror;
import core.Range;

import extended.FeatureNode;
public class SketchTest
{
  @Before
  public void init() {
    // construct a gene on the forward strand with two exons
    String seqid = "chromosome21";
    try {
    FeatureNode gene = new FeatureNode(seqid, "gene", 100, 900, "+");
    FeatureNode exon = new FeatureNode(seqid, "exon", 100, 200, "+");
    gene.add_child(exon);
    FeatureNode intron = new FeatureNode(seqid, "intron", 201, 799, "+");
    gene.add_child(intron);
    FeatureNode exon2 = new FeatureNode(seqid, "exon", 800, 900, "+");
    FeatureNode exon3 = new FeatureNode(seqid, "exon", 850, 900, "-");
    FeatureNode exon4 = new FeatureNode(seqid, "exon", 50, 150, "?");
    gene.add_child(exon2);
    gene.add_child(exon3);
    gene.add_child(exon4);
    // construct a single-exon gene on the reverse strand
    // (within the intron of the forward strand gene)
    FeatureNode reverse_gene = new FeatureNode(seqid, "gene", 400, 600, "-");
    FeatureNode reverse_exon = new FeatureNode(seqid, "exon", 400, 600, "-");
    reverse_gene.add_child(reverse_exon);
    Style sty = new Style();
    sty.load_file("default.style");
    Range rng = new Range();
    rng.set_start(1);
    rng.set_end(1000);
    FeatureNode[] arr = { gene,reverse_gene };
    Diagram dia = new Diagram(arr, rng, sty);
    TrackSelector ts = new TrackSelector() {
      @Override
      public String getTrackId(Block b)
      {
        return b.get_type() + b.get_strand();
      }};
    dia.set_track_selector_func(ts);
    Layout lay = new Layout(dia, 800, sty);
    int height = lay.get_height();
    CanvasCairoFile can = new CanvasCairoFile(sty, 800, height);
    lay.sketch(can);
    can.to_file("test.png");
    } catch (GTerror e) { System.out.println(e.getCause()); e.printStackTrace(); }
  }
  
  @Test
  public void test_sketch() {
    init();
  }
}
