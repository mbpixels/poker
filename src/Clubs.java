import processing.core.PApplet;
import processing.core.PConstants;
import processing.core.PShape;
import processing.core.PVector;

public class Clubs extends Shape {
    PApplet parent;
    PShape myShapeBody, myShape;
    PShape child, child1;

    int total;
    PVector[] CShape;

    float x;
    float y;
    float sz;
    int color;
    boolean growing = true;


    public Clubs(PApplet p, float x_, float y_, float sz_, int color_) {
        parent = p;
        x = x_;
        y = y_;
        sz = sz_;
        color = color_;
        setShape(x,y,sz);

    }

    protected PVector[] getShape() {
        return CShape;
    }

    protected boolean setGrowing(boolean g) {
        return this.growing = g;
    }

    public void setShape(float x, float y, float sz) {
        myShape = parent.loadShape ("data/clubs.svg");
        myShapeBody = parent.loadShape ("data/clubs body.svg");
        child = myShapeBody.getChild(1);
        child1 = child.getChild(0);
        total = child1.getVertexCount();
        CShape = new PVector[total];

        for (int j = 0; j < total; j++) {
            PVector v = child1.getVertex(j);
            CShape[j] = new PVector(x+v.x*sz,y+v.y *sz);

        }
    }

    public void show() {
//        parent.fill(color, 250);
//        parent.beginShape();
//        for (PVector v : CShape) {
//            parent.vertex(v.x, v.y);
//        }
//        parent.endShape(PConstants.CLOSE);
//        child1my.fill(color, 250);
        parent.shape(myShape, x,y, sz*200,sz*300);
    }


    public void grow() {
        if (growing) {
            sz += 0.05;
            setShape(x, y, sz);
        }
    }
}