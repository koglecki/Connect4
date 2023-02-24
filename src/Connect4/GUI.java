package Connect4;
import processing.core.*;

import java.util.concurrent.TimeUnit;

public class GUI extends PApplet {
    private int x = 7, y = 6;
    private int board[];
    private int [][] playerMoves;
    private int [][] AIMoves;
    int value = -1;
    boolean color = false;
    private Connect4 instance;
    private int AIMove = -1;
    private boolean mouseOn = true;
    int gamestate = 3;
    double counter = 0;

    public GUI() {

    }

    public GUI(Connect4 c4, int X, int Y) {
        this.instance = c4;
        this.x = X;
        this.y = Y;
        board = new int[x];
        for(int i = 0; i < x; i++)
            board[i] = -1;
        playerMoves = new int[(x*y)/2 + 1][2];
        AIMoves = new int[(x*y)/2 + 1][2];
        for(int i = 0; i < playerMoves.length; i++)
            for(int j = 0; j < 2; j++) {
                playerMoves[i][j] = -1;
                AIMoves[i][j] = -1;
            }
    }

    public void setAIMove(int m) {
        this.AIMove = m;
    }

    public void settings() {
        size(800, 800);
    }

    void drawC() {
        if (color) {
            stroke(227, 11, 9);
            fill(227, 11, 9);
        }
        else {
            stroke(222, 234, 25);
            fill(222, 234, 25);
        }
        ellipse(700, 260, 40, 40);
    }

    void printText() {
        String txt;
        if (mouseOn) {
            txt = "Twój ruch!";
        }
        else {
            txt = "Ruch AI";
        }
        textSize(30);
        fill(0, 408, 612);
        text(txt, 625, 200);
    }

    void printScene() {
        background(30,30,30);
        stroke(4,88,176);
        fill(4,88,176);
        rect(100,100,50 * x,50 * y);
        for (int i = 125; i < 50 * x + 125; i += 50)
            for (int j = 125; j < 50 * y + 125; j+= 50) {
                stroke(30,30,30);
                fill(30,30,30);
                ellipse(i, j, 40, 40);
            }
    }

    void drawCurrentCircles() {
        stroke(222, 234, 25);
        fill(222, 234, 25);
        for(int i = 0; playerMoves[i][0] != -1; i++)
            ellipse(playerMoves[i][0], playerMoves[i][1], 40, 40);
        stroke(227, 11, 9);
        fill(227, 11, 9);
        for(int i = 0; AIMoves[i][0] != -1; i++)
            ellipse(AIMoves[i][0], AIMoves[i][1], 40, 40);
    }

    public void draw() {
        if(gamestate == 1) {        //ekran powitalny
            background(73,113,137);
            textSize(90);
            fill(218,243,11);
            text("CONN", 160, 180);
            fill(242,49,6);
            text("ECT4", 425, 180);
            if (counter > 1) {
                textSize(20);
                fill(255, 255, 255);
                text("Kliknij, aby kontynuowac", 220, 650);
            }
            if (counter > 2)
                counter = 0;

            fill(218,243,11);
            ellipse(300, 400, 30, 30);
            ellipse(300, 360, 30, 30);
            ellipse(340, 400, 30, 30);
            ellipse(380, 360, 30, 30);
            ellipse(420, 320, 30, 30);
            ellipse(460, 280, 30, 30);
            ellipse(460, 360, 30, 30);

            fill(242,49,6);
            ellipse(380, 400, 30, 30);
            ellipse(420, 400, 30, 30);
            ellipse(460, 400, 30, 30);
            ellipse(420, 360, 30, 30);
            ellipse(460, 320, 30, 30);
            ellipse(380, 320, 30, 30);

            counter = counter + 0.0183;
            //System.out.println("waiting");

            if(mousePressed) {
                gamestate = 3;
            }
        }
        if(gamestate == 2) {    //ustawienia
            if (mousePressed) {

            }
        }
        if(gamestate == 3) {    //gra
            printScene();
            printText();
            drawC();
            drawCurrentCircles();

            if (value != -1 && board[value] < y) {
                value = -1;
                color = !color;
            }

            if (AIMove != -1) {
                value = AIMove;
                board[value]++;
                color = !color;
                setAIMove(-1);
                for (int i = 0; i < AIMoves.length; i++) {
                    if (AIMoves[i][0] == -1) {
                        AIMoves[i][0] = 125 + value * 50;
                        AIMoves[i][1] = 125 + 50 * (y - 1) - board[value] * 50;
                        break;
                    }
                }
                value = -1;
                mouseOn = true;
            }
            if (instance.isWinner() && AIMove == -1) {
                mouseOn = false;
                gamestate = 4;
            }
//            try {
//                TimeUnit.MILLISECONDS.sleep(5000);
//            } catch (InterruptedException e) {
//                e.printStackTrace();
//            }


            System.out.println("AI");
        }
        if (gamestate == 4) {       //koniec gry
            printScene();
            drawCurrentCircles();
            textSize(30);
            fill(0, 408, 612);
            if (instance.isMaximizingTurnNow()) {
                text("Niestety, przegrales :(", 200, 650);
            }
            else {
                text("Wygrales, gratulacje!", 200, 650);
            }
            textSize(20);
            text("Aby zakonczyc, nacisnij dowolny przycisk.", 300, 700);
            if (keyPressed)
                exit();
        }
    }

    public void mouseClicked() {
        if (mouseOn) {
            int X = mouseX;
            int Y = mouseY;
            if (!(Y < 100 || Y > 100 + 50 * y)) {
                int val = 0;

                for (int i = 100; i < 100 + 50 * x; i += 50, val++) {
                    if (X >= i && X < i + 50) {
                        value = val;
                        break;
                    }
                }
                if (value == -1) {
                    mouseOn = false;
                } else {
                    board[value]++;
                    instance.setPlayerMove(value);
                    for(int i = 0; i < playerMoves.length; i++) {
                        if (playerMoves[i][0] == -1) {
                            playerMoves[i][0] = 125 + value * 50;
                            playerMoves[i][1] = 125 + 50 * (y-1) - board[value] * 50;
                            break;
                        }
                    }
                }
                mouseOn = !mouseOn;
            }
        }
    }

    public void mouseReleased() {
        value = -1;
    }

}
