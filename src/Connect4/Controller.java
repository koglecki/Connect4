package Connect4;

public class Controller {
    private int rows = 6;
    private int columns = 7;
    private boolean startingPlayer = true;
    private boolean colorRed = false;

    public static void main(String args[]) {
        Settings sketch1 = new Settings();
        Settings.runSketch(new String[] {"Connect4.Settings"}, sketch1);
//        GUI sketch2 = new GUI();
//        GUI.runSketch(new String[] {"Connect4.GUI"}, sketch2);
        Connect4 c4 = new Connect4();
        c4.main();
        System.out.println("xd");
    }

}
