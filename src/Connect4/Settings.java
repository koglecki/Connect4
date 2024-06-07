package Connect4;
import processing.core.*;

public class Settings extends PApplet {
    private int state = 1;
    private double counter = 0;
    boolean rowSliderOn = false;
    boolean columnSliderOn = false;   //czy suwak włączony
    private int rowsValue = 100;
    private int columnsValue = 450;
    private Controller instance;
    private boolean selectedPlayer = true;
    private boolean selectedRed = false;
    private boolean ceilingRule = true;
    private boolean start = false;

    public Settings(Controller controller) {
        this.instance = controller;
    }

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
            textSize(40);
            text(str(rowsValueToNumber(rowsValue)), 300, 260);

            rect(100, 265, 175, 8);


            textSize(20);
            stroke(242,49,6);
            fill(242,49,6);
            text("KOLUMNY", 450, 240);
            textSize(40);
            text(str(columnsValueToNumber(columnsValue)), 650, 260);

            rect(450, 265, 175, 8);
            stroke(0);
            fill(0);

            ///rozpoczynający
            textSize(25);
            fill(255,255,255);
            text("ROZPOCZYNAJACY:", 100, 350);

            stroke(255);
            if (selectedPlayer) {
                fill(218,243,11);
                rect(100, 370, 160, 40);
                fill(255);
                rect(350, 370, 160, 40);
            }
            else {
                fill(255);
                rect(100, 370, 160, 40);
                fill(218,243,11);
                rect(350, 370, 160, 40);
            }
            fill(0);
            stroke(0);

            textSize(20);
            text("GRACZ", 140, 398);
            textSize(20);
            text("AI", 420, 398);

            // reguła sufitu
            stroke(255);
            fill(255);
            if (ceilingRule) {
                fill(218,243,11);
                rect(545, 500, 77, 40);
                fill(255);
                rect(545, 500, 77, 40);
            }
            else {
                fill(218,243,11);
                rect(660, 500, 77, 40);
                fill(255);
                rect(660, 500, 77, 40);
            }
            textSize(25);
            fill(255,255,255);
            text("REGULA SUFITU:", 540, 470);

            textSize(25);
            fill(0,0,0);
            text("TAK", 560, 530);
            text("NIE", 680, 530);

            ///kolor
            textSize(25);
            fill(255,255,255);
            text("KOLOR GRACZA:", 100, 470);

            stroke(218,243,11);
            fill(218,243,11);
            rect(110, 500, 40, 40);

            stroke(242,49,6);
            fill(242,49,6);
            rect(230, 500, 40, 40);

            stroke(255);
            fill(255);
            if (selectedRed) {
                rect(220, 490 ,60, 10);
                rect(220, 540 ,60, 10);
                rect(220, 490 ,10, 60);
                rect(270, 490 ,10, 60);
            }
            else {
                rect(100, 490 ,60, 10);
                rect(100, 540 ,60, 10);
                rect(100, 490 ,10, 60);
                rect(150, 490 ,10, 60);
            }
            ///

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

            fill(85,214,97);
            stroke(85,214,97);
            rect(220, 620 ,320, 80);
            fill(0);
            stroke(0);
            textSize(35);
            text("ROZPOCZNIJ GRE", 233, 670);

            if (start) {
                instance.getParameters(rowsValueToNumber(rowsValue), columnsValueToNumber(columnsValue), selectedPlayer, selectedRed, ceilingRule);
                noLoop();
            }
        }
    }

    public void mouseClicked() {
        if (state == 2) {
            if (mouseX > 100 && mouseX < 260 && mouseY > 370 && mouseY < 410)
                selectedPlayer = true;
            if (mouseX > 350 && mouseX < 510 && mouseY > 370 && mouseY < 410)
                selectedPlayer = false;

            if (mouseX > 110 && mouseX < 150 && mouseY > 500 && mouseY < 540)
                selectedRed = false;
            if (mouseX > 230 && mouseX < 270 && mouseY > 500 && mouseY < 540)
                selectedRed = true;

            if (mouseX > 660 && mouseX < 737 && mouseY > 500 && mouseY < 540)
                ceilingRule = false;
            if (mouseX > 545 && mouseX < 622 && mouseY > 500 && mouseY < 540)
                ceilingRule = true;

            if (mouseX > 220 && mouseX < 540 && mouseY > 620 && mouseY < 700)
                start = true;
        }
    }

    public void mouseDragged() {
        if (state == 2) {
            if (mouseX > columnsValue - 24 && mouseX < columnsValue + 24 && mouseY > 257 && mouseY < 281)
                columnSliderOn = true;

            if (mouseX > rowsValue - 24 && mouseX < rowsValue + 24 && mouseY > 257 && mouseY < 281)
                rowSliderOn = true;

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
        }
    }

    public void mouseReleased() {
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