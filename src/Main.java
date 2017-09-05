import processing.core.*;
import processing.pdf.*;

//
import java.awt.*;
import java.util.ArrayList;

public class Main extends PApplet{
    String[] palette = {"#6C8D84","#CA9D59","#AD624B","#8A5354","#3D3E53","#6C8D84","#CA9D59","#AD624B"};


    ArrayList<Shape> pol = new ArrayList<>();
    ArrayList<PVector> spots = new ArrayList<>();
    PImage img;


    public void settings(){
        size(700   ,500,PDF, "output/poker.pdf"); //
        smooth(2);
    }

    public void setup() {

        background(255);
        strokeWeight(1);
 //       frameRate(10);
        img = loadImage("data/poker.png");
        img.loadPixels();

        for (int x = 0; x < img.width; x++) {
            for (int y = 0; y < img.height; y++) {
                int c = img.pixels[x + y * img.width];
                if (brightness(c) > 1) {
                    spots.add(new PVector(x, y));
                }
            }
        }
        println(spots.size());

    }

    public void draw() {
//        background(255);

        int total =40;
        int count = 0;
        int attempts = 0;
        while (count < total) {
            Shape newP = newPol();
            if (newP != null) {
                pol.add(newP);
                count++;
            }
            attempts++;
            if (attempts > 8000) {
                println("Finished");
                noLoop();
                exit();

                break;

            }

        }

        for (Shape r : pol) {
            if (r.outFrame(r.getShape(), width, height)) {
                r.setGrowing(false);// = false;
            } else {
                for (Shape other : pol) {
                    if (r != other) {
                        if (other.polyPoly(r.getShape(),other.getShape())) {
                            r.setGrowing(false);
                            break;
                        }
                    }
                }
            }
            r.show();
            r.grow();

        }
        PGraphicsPDF pdf = (PGraphicsPDF) g;  // Get the renderer
        pdf.nextPage();
       // if ( frameCount % 2 == 0 ) saveFrame("output/image-####.gif"); // if ( frameCount % 2 == 0 )
    }

    Shape newPol() {
        int index = (int) random(spots.size());
        PVector spot = spots.get(index);

        float x = random(30,width-30);
        float y = random(30,height-30);
        float sz = random((float) 0.2,(float) 0.4);
        Shape tmp, tmp1,tmp2, tmp3, tmp4;
        String nmbr1 = palette[(int) random(palette.length)];
        tmp = tmp1 = new Clubs(this,spot.x,spot.y,sz, pickColor(nmbr1));
        tmp2 = new Spades(this,spot.x,spot.y,sz, pickColor(nmbr1));
        tmp3 = new Heart(this,spot.x,spot.y,sz, pickColor(nmbr1));
        tmp4 = new Diamond(this,spot.x,spot.y,sz, pickColor(nmbr1));

        int rndtmp = (int) random(1,5);
        if (rndtmp == 2) tmp = tmp2;
        else if (rndtmp == 3) tmp = tmp3;
        else if (rndtmp == 4) tmp = tmp4;

        boolean valid = true;

        for (Shape r : pol) {
            if (r.polyPoly(r.getShape(),tmp.getShape())){
                valid = false;
                break;
            }
        }
        if (valid)
            return tmp;
        else
            return null;
    }
    public int pickColor(String clr) {
        return Color.decode(clr).getRGB();
    }
    public static void main(String[] args) {
        PApplet.main("Main", args);

    }
}
