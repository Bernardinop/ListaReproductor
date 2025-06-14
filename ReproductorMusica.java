/*
 * Bernardino José Payeras Molina
 * 0900-24-3363
 * Programacion 1
 * Examen Final
 * Reproductor de Música
 */

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
                if (duracion > 0)
                    break;
                else
                    System.out.println("Ingrese un número mayor a 0.");
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

    public static void asignarCancionAPlaylist() {
        clearScreen();
        if (baseCanciones.estaVacia() || basePlaylists.estaVacia()) {
            System.out.println("Debe haber al menos una canción y una playlist para asignar.");
            pressEnterToContinue();
            return;
        }

        System.out.println("Seleccione una canción:");
        baseCanciones.mostrarCancionesConIndice();
        int indexCancion = leerOpcion(1, Integer.MAX_VALUE) - 1;
        Cancion cancion = baseCanciones.obtenerCancionPorIndice(indexCancion);
        if (cancion == null) {
            System.out.println("Índice de canción inválido.");
            pressEnterToContinue();
            return;
        }

        System.out.println("Seleccione una playlist:");
        basePlaylists.mostrarPlaylistsConIndice();
        int indexPlaylist = leerOpcion(1, Integer.MAX_VALUE) - 1;
        Playlist playlist = basePlaylists.obtenerPlaylistPorIndice(indexPlaylist);
        if (playlist == null) {
            System.out.println("Índice de playlist inválido.");
            pressEnterToContinue();
            return;
        }

        // Verificar si la canción ya está en la playlist
        NodoCancion actual = playlist.cabeza;
        while (actual != null) {
            if (actual.cancion == cancion) {
                System.out.println("La canción ya está en la playlist.");
                pressEnterToContinue();
                return;
            }
            actual = actual.siguiente;
        }

        NodoCancion nuevoNodo = new NodoCancion(cancion);
        if (playlist.cabeza == null) {
            playlist.cabeza = nuevoNodo;
        } else {
            actual = playlist.cabeza;
            while (actual.siguiente != null) {
                actual = actual.siguiente;
            }
            actual.siguiente = nuevoNodo;
        }

        System.out.println("Canción asignada correctamente a la playlist.");
        pressEnterToContinue();
    }

    public static void verInformacion() {
        int opcion;
        do {
            clearScreen();
            System.out.println("=== VER INFORMACIÓN ===");
            System.out.println("1. Ver canciones registradas");
            System.out.println("2. Ver playlists creadas");
            System.out.println("3. Ver playlists con sus canciones");
            System.out.println("4. Volver al menú principal");

            opcion = leerOpcion(1, 4);
            clearScreen();

            switch (opcion) {
                case 1 -> {
                    System.out.println("=== CANCIONES REGISTRADAS ===");
                    baseCanciones.mostrarCancionesConIndice();
                    pressEnterToContinue();
                }
                case 2 -> {
                    System.out.println("=== PLAYLISTS CREADAS ===");
                    basePlaylists.mostrarPlaylistsConIndice();
                    pressEnterToContinue();
                }
                case 3 -> {
                    System.out.println("=== PLAYLISTS CON SUS CANCIONES ===");
                    basePlaylists.mostrarPlaylistsConCanciones();
                    pressEnterToContinue();
                }
                case 4 -> System.out.println("Volviendo al menú principal...");
            }
        } while (opcion != 4);
    }

    public static void buscarCancion() {
        clearScreen();
        System.out.println("=== BUSCAR CANCIÓN POR TÍTULO ===");
        System.out.print("Ingrese el título de la canción: ");
        String titulo = scanner.nextLine().trim();

        Cancion cancion = baseCanciones.buscarCancion(titulo);
        if (cancion == null) {
            System.out.println("Canción no encontrada.");
        } else {
            System.out.println("Título: " + cancion.titulo);
            System.out.println("Artista: " + cancion.artista);
            System.out.println("Duración: " + cancion.duracion + "s");
            System.out.println("Presente en las siguientes playlists:");
            basePlaylists.listarPresenciaCancion(cancion);
        }

        pressEnterToContinue();
    }

    public static void eliminarCancion() {
        clearScreen();
        if (baseCanciones.estaVacia()) {
            System.out.println("No hay canciones registradas.");
            pressEnterToContinue();
            return;
        }

        System.out.println("Seleccione la canción a eliminar:");
        baseCanciones.mostrarCancionesConIndice();
        int index = leerOpcion(1, Integer.MAX_VALUE) - 1;
        Cancion cancion = baseCanciones.obtenerCancionPorIndice(index);

        if (cancion == null) {
            System.out.println("Índice inválido.");
            pressEnterToContinue();
            return;
        }

        System.out.println("¿Desea eliminar la canción de:");
        System.out.println("1. Toda la base y playlists");
        System.out.println("2. Solo de una playlist específica");
        int opcion = leerOpcion(1, 2);

        switch (opcion) {
            case 1 -> {
                baseCanciones.eliminarCancion(cancion);
                basePlaylists.eliminarCancionDeTodas(cancion);
                System.out.println("Canción eliminada completamente.");
            }
            case 2 -> {
                Playlist[] playlists = basePlaylists.obtenerPlaylistsConCancion(cancion);
                if (playlists.length == 0) {
                    System.out.println("Esta canción no está en ninguna playlist.");
                    break;
                }

                for (int i = 0; i < playlists.length; i++) {
                    System.out.println((i + 1) + ". " + playlists[i].nombre);
                }

                int sel = leerOpcion(1, playlists.length);
                basePlaylists.eliminarCancionDePlaylist(playlists[sel - 1], cancion);
                System.out.println("Canción eliminada de la playlist seleccionada.");
            }
        }

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
            if (i == index)
                return actual;
            actual = actual.siguiente;
            i++;
        }
        return null;
    }

    public void eliminarCancion(Cancion c) {
        if (cabeza == null)
            return;

        if (cabeza == c) {
            cabeza = cabeza.siguiente;
            return;
        }

        Cancion anterior = cabeza;
        Cancion actual = cabeza.siguiente;

        while (actual != null) {
            if (actual == c) {
                anterior.siguiente = actual.siguiente;
                return;
            }
            anterior = actual;
            actual = actual.siguiente;
        }
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
            if (i == index)
                return actual;
            actual = actual.siguiente;
            i++;
        }
        return null;
    }

    public void mostrarPlaylistsConCanciones() {
        Playlist actual = cabeza;
        if (actual == null) {
            System.out.println("No hay playlists disponibles.");
            return;
        }

        while (actual != null) {
            System.out.println("Playlist: " + actual.nombre);
            NodoCancion nodo = actual.cabeza;
            if (nodo == null) {
                System.out.println("  (Sin canciones)");
            } else {
                while (nodo != null) {
                    Cancion c = nodo.cancion;
                    System.out.println("  - " + c.titulo + " / " + c.artista + " (" + c.duracion + "s)");
                    nodo = nodo.siguiente;
                }
            }
            System.out.println("--------------------");
            actual = actual.siguiente;
        }
    }

    public void listarPresenciaCancion(Cancion c) {
        Playlist actual = cabeza;
        boolean encontrada = false;

        while (actual != null) {
            NodoCancion nodo = actual.cabeza;
            while (nodo != null) {
                if (nodo.cancion == c) {
                    System.out.println("- " + actual.nombre);
                    encontrada = true;
                    break;
                }
                nodo = nodo.siguiente;
            }
            actual = actual.siguiente;
        }

        if (!encontrada) {
            System.out.println("No se encuentra en ninguna playlist.");
        }
    }

    public void eliminarCancionDeTodas(Cancion c) {
        Playlist actual = cabeza;
        while (actual != null) {
            eliminarCancionDePlaylist(actual, c);
            actual = actual.siguiente;
        }
    }

    public void eliminarCancionDePlaylist(Playlist p, Cancion c) {
        if (p.cabeza == null)
            return;

        if (p.cabeza.cancion == c) {
            p.cabeza = p.cabeza.siguiente;
            return;
        }

        NodoCancion anterior = p.cabeza;
        NodoCancion actual = p.cabeza.siguiente;

        while (actual != null) {
            if (actual.cancion == c) {
                anterior.siguiente = actual.siguiente;
                return;
            }
            anterior = actual;
            actual = actual.siguiente;
        }
    }

    public Playlist[] obtenerPlaylistsConCancion(Cancion c) {
        int contador = 0;
        Playlist actual = cabeza;
        while (actual != null) {
            NodoCancion nodo = actual.cabeza;
            while (nodo != null) {
                if (nodo.cancion == c) {
                    contador++;
                    break;
                }
                nodo = nodo.siguiente;
            }
            actual = actual.siguiente;
        }

        Playlist[] resultado = new Playlist[contador];
        actual = cabeza;
        int i = 0;

        while (actual != null) {
            NodoCancion nodo = actual.cabeza;
            while (nodo != null) {
                if (nodo.cancion == c) {
                    resultado[i++] = actual;
                    break;
                }
                nodo = nodo.siguiente;
            }
            actual = actual.siguiente;
        }

        return resultado;
    }

}
