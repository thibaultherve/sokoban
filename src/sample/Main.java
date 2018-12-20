package sample;

public class Main {
    public static void main(String[] args) {

        Thread thread1 = new Thread() {
            public void run() {
                new IHMFX().lance();
            }
        };

        thread1.start();
    }
}
