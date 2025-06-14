import java.util.Scanner;

public class ReproductorMain {

    static Scanner scanner = new Scanner(System.in);
    static ListaCanciones baseCanciones = new ListaCanciones();
    static ListaPlaylists basePlaylists = new ListaPlaylists();

    public static void clearScreen() {
        System.out.print("\033[H\033[2J");
        System.out.flush();
    }

    public static void pressEnterToContinue() {
        System.out.println("\nPresione Enter para continuar...");
        scanner.nextLine();
    }

    public static void main(String[] args) {
        menu();
    }

    public static void menu() {
        int opcion;

        do {
            clearScreen();
            System.out.println("==== REPRODUCTOR DE MÚSICA ====");
            System.out.println("1. Crear nueva playlist");
            System.out.println("2. Agregar canción a la base general");
            System.out.println("3. Asignar canción a una playlist");
            System.out.println("4. Ver información");
            System.out.println("5. Buscar canción por título");
            System.out.println("6. Eliminar canción");
            System.out.println("7. Salir");

            opcion = leerOpcion(1, 7);

            switch (opcion) {
                case 1 -> crearPlaylist();
                case 2 -> agregarCancionGeneral();
                case 3 -> asignarCancionAPlaylist();
                case 4 -> verInformacion();
                case 5 -> buscarCancion();
                case 6 -> eliminarCancion();
                case 7 -> System.out.println("Saliendo...");
            }
        } while (opcion != 7);
    }

    public static int leerOpcion(int min, int max) {
        while (true) {
            System.out.print("Seleccione una opción: ");
            try {
                int valor = Integer.parseInt(scanner.nextLine());
                if (valor >= min && valor <= max)
                    return valor;
                else
                    System.out.println("Debe ingresar un número entre " + min + " y " + max + ".");
            } catch (Exception e) {
                System.out.println("Entrada inválida. Solo se permiten números enteros.");
            }
        }
    }

} 
