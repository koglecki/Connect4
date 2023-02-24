package Processing;

import processing.core.PApplet;

public class Test extends PApplet {

    public static void main(String[] args) {
        PApplet.main("Processing.Test");
    }

    public void settings() {
        size(400, 400);
    }

    public void setup() {

    }

    public void draw() {
        background(216,219,19);
        stroke(237,28,20);
        fill(237,28,20);
        rect(100,100,200,200);
        fill(56,179,19);
        ellipse(mouseX, mouseY, 50, 70);//200 150
    }
}