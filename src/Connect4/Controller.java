package Connect4;

public class Controller {
    private int rows = 6;
    private int columns = 7;
    private boolean startingPlayer = true;
    private boolean colorRed = false;

    public static void main(String args[]) {
        Controller controller = new Controller();
        Settings sketch1 = new Settings(controller);
        Settings.runSketch(new String[] {"Connect4.Settings"}, sketch1);
        while(sketch1.isLooping()) {
            System.out.println("settings");
        }
        System.out.println("end settings");
        Connect4 c4 = new Connect4(controller.rows, controller.columns, controller.startingPlayer, controller.colorRed);
        c4.main();
    }

    public void getParameters(int r, int c, boolean p, boolean color) {
        this.rows = r;
        this.columns = c;
        this.startingPlayer = p;
        this.colorRed = color;
    }

}
