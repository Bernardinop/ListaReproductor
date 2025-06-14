import java.util.Scanner;

public class ReproductorMusica {

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

    public static void crearPlaylist() {
        clearScreen();
        System.out.println("=== Crear nueva playlist ===");
        System.out.print("Nombre de la playlist: ");
        String nombre = scanner.nextLine().trim();

        if (nombre.isEmpty()) {
            System.out.println("El nombre no puede estar vacío.");
            return;
        }

        basePlaylists.agregarPlaylist(new Playlist(nombre));
        System.out.println("Playlist creada correctamente.");
        pressEnterToContinue();
    }

    public static void agregarCancionGeneral() {
        clearScreen();
        System.out.println("=== Agregar nueva canción ===");
        System.out.print("Título: ");
        String titulo = scanner.nextLine().trim();
        if (titulo.isEmpty()) {
            System.out.println("El título no puede estar vacío.");
            return;
        }

        System.out.print("Artista: ");
        String artista = scanner.nextLine().trim();
        if (artista.isEmpty()) {
            System.out.println("El artista no puede estar vacío.");
            return;
        }

        int duracion = 0;
        while (true) {
            System.out.print("Duración en segundos: ");
            try {
                duracion = Integer.parseInt(scanner.nextLine());
                if (duracion > 0) break;
                else System.out.println("Ingrese un número mayor a 0.");
            } catch (Exception e) {
                System.out.println("Entrada inválida. Solo se permiten números enteros.");
            }
        }

        if (baseCanciones.buscarCancion(titulo) != null) {
            System.out.println("Ya existe una canción con ese título.");
            return;
        }

        baseCanciones.agregarCancion(new Cancion(titulo, artista, duracion));
        System.out.println("Canción agregada correctamente.");
        pressEnterToContinue();
    }
}

class Cancion {
    String titulo;
    String artista;
    int duracion; // en segundos
    Cancion siguiente;

    public Cancion(String titulo, String artista, int duracion) {
        this.titulo = titulo;
        this.artista = artista;
        this.duracion = duracion;
    }
}

class ListaCanciones {
    private Cancion cabeza;

    public void agregarCancion(Cancion nueva) {
        if (cabeza == null) {
            cabeza = nueva;
        } else {
            Cancion actual = cabeza;
            while (actual.siguiente != null) {
                actual = actual.siguiente;
            }
            actual.siguiente = nueva;
        }
    }

    public Cancion buscarCancion(String titulo) {
        Cancion actual = cabeza;
        while (actual != null) {
            if (actual.titulo.equalsIgnoreCase(titulo)) {
                return actual;
            }
            actual = actual.siguiente;
        }
        return null;
    }

    public boolean estaVacia() {
        return cabeza == null;
    }

    public void mostrarCancionesConIndice() {
        if (cabeza == null) {
            System.out.println("No hay canciones disponibles.");
            return;
        }
        int i = 1;
        Cancion actual = cabeza;
        while (actual != null) {
            System.out.println(i++ + ". " + actual.titulo + " - " + actual.artista + " (" + actual.duracion + "s)");
            actual = actual.siguiente;
        }
    }

    public Cancion obtenerCancionPorIndice(int index) {
        Cancion actual = cabeza;
        int i = 0;
        while (actual != null) {
            if (i == index) return actual;
            actual = actual.siguiente;
            i++;
        }
        return null;
    }
}

class Playlist {
    String nombre;
    NodoCancion cabeza;
    Playlist siguiente;

    public Playlist(String nombre) {
        this.nombre = nombre;
    }
}

class NodoCancion {
    Cancion cancion;
    NodoCancion siguiente;

    public NodoCancion(Cancion cancion) {
        this.cancion = cancion;
    }
}

class ListaPlaylists {
    private Playlist cabeza;

    public void agregarPlaylist(Playlist nueva) {
        if (cabeza == null) {
            cabeza = nueva;
        } else {
            Playlist actual = cabeza;
            while (actual.siguiente != null) {
                actual = actual.siguiente;
            }
            actual.siguiente = nueva;
        }
    }

    public boolean estaVacia() {
        return cabeza == null;
    }

    public void mostrarPlaylistsConIndice() {
        if (cabeza == null) {
            System.out.println("No hay playlists creadas.");
            return;
        }
        int i = 1;
        Playlist actual = cabeza;
        while (actual != null) {
            System.out.println(i++ + ". " + actual.nombre);
            actual = actual.siguiente;
        }
    }

    public Playlist obtenerPlaylistPorIndice(int index) {
        Playlist actual = cabeza;
        int i = 0;
        while (actual != null) {
            if (i == index) return actual;
            actual = actual.siguiente;
            i++;
        }
        return null;
    }
}
