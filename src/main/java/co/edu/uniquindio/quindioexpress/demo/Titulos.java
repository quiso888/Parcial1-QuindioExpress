package co.edu.uniquindio.quindioexpress.demo;

/** Utilidad para imprimir títulos de sección en la consola. */
final class Titulos {

    private Titulos() {
    }

    static void seccion(String titulo) {
        System.out.println();
        System.out.println("=".repeat(70));
        System.out.println(titulo);
        System.out.println("=".repeat(70));
    }

    static void sub(String titulo) {
        System.out.println();
        System.out.println("--- " + titulo);
    }
}
