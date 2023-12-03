package jgame23;

import java.util.Random;

public class Main {
    public static void main(String[] args) {
        //new SistemaJuego();
        new Main().test();
    }

    private void test() {
        System.out.println("Test");
        Random r = new Random();
        for (int i = 0; i < 20; i++) {
            int x = r.nextInt(2) + 1;
            System.out.println(x);
        }
    }
}
