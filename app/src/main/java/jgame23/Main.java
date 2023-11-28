package jgame23;

import java.util.Random;

public class Main {
    public static void main(String[] args) {
        new SistemaJuego();
        //new Main().test();
    }

    private void test() {
        int nuevo, viejo = 0;
        for (int i = 0; i < 10; i++) {
            nuevo = new Random().nextInt(10);
            if (nuevo == viejo) {
                nuevo = new Random().nextInt(10);
            }
            viejo = nuevo;
            System.out.println(nuevo);
        }
    }
}
