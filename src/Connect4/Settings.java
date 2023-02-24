package Connect4;
import processing.core.*;

public class Settings extends PApplet {
    private int state = 1;
    private double counter = 0;
    boolean rowSliderOn = false;
    boolean columnSliderOn = false;   //czy suwak włączony
    private int rowsValue = 100;
    private int columnsValue = 450;

    public void settings() {
        size(800, 800);
    }

    public void draw() {
        if (state == 1) {        //ekran powitalny
            background(73, 113, 137);
            textSize(90);
            fill(218, 243, 11);
            text("CONN", 160, 180);
            fill(242, 49, 6);
            text("ECT4", 425, 180);
            if (counter > 1) {
                textSize(20);
                fill(255, 255, 255);
                text("Kliknij, aby kontynuowac", 220, 650);
            }
            if (counter > 2)
                counter = 0;

            fill(218, 243, 11);
            ellipse(300, 400, 30, 30);
            ellipse(300, 360, 30, 30);
            ellipse(340, 400, 30, 30);
            ellipse(380, 360, 30, 30);
            ellipse(420, 320, 30, 30);
            ellipse(460, 280, 30, 30);
            ellipse(460, 360, 30, 30);

            fill(242, 49, 6);
            ellipse(380, 400, 30, 30);
            ellipse(420, 400, 30, 30);
            ellipse(460, 400, 30, 30);
            ellipse(420, 360, 30, 30);
            ellipse(460, 320, 30, 30);
            ellipse(380, 320, 30, 30);

            counter = counter + 0.0183;
            //System.out.println("waiting");

            if (mousePressed) {
                state = 2;
            }
        }
        if (state == 2) {    //ustawienia
            background(73,113,137);

            textSize(70);
            fill(255,255,255);
            text("USTAWIENIA", 100, 100);

            textSize(30);
            text("ROZMIAR PLANSZY:", 100, 200);

            textSize(20);
            stroke(218,243,11);
            fill(218,243,11);
            text("WIERSZE", 100, 240);
            text(str(rowsValueToNumber(rowsValue)), 300, 240);

            rect(100, 265, 175, 8);


            textSize(20);
            stroke(242,49,6);
            fill(242,49,6);
            text("KOLUMNY", 450, 240);

            rect(450, 265, 175, 8);
            stroke(0);
            fill(0);

            if (rowSliderOn) {
                if (mouseX < 100)
                    ellipse(100, 269, 24, 24);
                else if (mouseX > 275)
                    ellipse(275, 269, 24, 24);
                if (mouseX > 100 && mouseX < 275)
                    ellipse(mouseX, 269, 24, 24);
            }
            else {
                ellipse(rowsValue, 269, 24, 24);
            }

            if (columnSliderOn) {
                if (mouseX < 450)
                    ellipse(450, 269, 24, 24);
                else if (mouseX > 625)
                    ellipse(625, 269, 24, 24);
                if (mouseX > 450 && mouseX < 625)
                    ellipse(mouseX, 269, 24, 24);
            }
            else {
                ellipse(columnsValue, 269, 24, 24);
            }
            if (keyPressed) {
                noLoop();
            }
        }
    }

    public void mouseDragged() {
        if (state == 2) {
            if (mouseX > columnsValue - 24 && mouseX < columnsValue + 24)
                columnSliderOn = true;

            if (mouseX > rowsValue - 24 && mouseX < rowsValue + 24)
                rowSliderOn = true;
        }
    }

    public void mouseReleased() {
        if (rowSliderOn) {
            if (mouseX < 100)
                rowsValue = 100;
            else if (mouseX > 275)
                rowsValue = 275;
            else
                rowsValue = mouseX;
        }

        if (columnSliderOn) {
            if (mouseX < 450)
                columnsValue = 450;
            else if (mouseX > 625)
                columnsValue = 625;
            else
                columnsValue = mouseX;
        }

        rowSliderOn = false;
        columnSliderOn = false;
    }

    int rowsValueToNumber(int value) {
        int number = 4;
        for (int i = 100; i < 275; i += 25, number++)
            if (value >= i && value < i + 25) {
                return number;
            }
        return number - 1;
    }

    int columnsValueToNumber(int value) {
        int number = 4;
        for (int i = 450; i < 625; i += 25, number++)
            if (value >= i && value < i + 25) {
                return number;
            }
        return number - 1;
    }
}