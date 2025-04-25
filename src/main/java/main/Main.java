package main;

public class Main {
    public static void main(String[] args) {
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new GUI(new Controller()).setVisible(true);
            }
        });
    }
}
