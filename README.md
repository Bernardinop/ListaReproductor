# Reproductor de Música 🎵

Este es un proyecto de consola desarrollado en Java como parte del **Examen Final del curso de Programación 1**. Su objetivo es simular un sistema de gestión de canciones y playlists, implementado con listas enlazadas simples, sin uso de estructuras avanzadas de Java.

## Autor
- **Nombre:** Bernardino José Payeras Molina
- **Carné:** 0900-24-3363

## Funcionalidades principales

🔹 Crear playlists  
🔹 Agregar canciones a una base general  
🔹 Asignar canciones a playlists  
🔹 Visualizar información:
  - Lista de canciones
  - Lista de playlists
  - Canciones dentro de cada playlist  
🔹 Buscar canciones por título  
🔹 Eliminar canciones de la base o de playlists específicas  

## Características técnicas

- Uso de listas enlazadas simples para almacenar canciones y playlists.
- Menú interactivo con manejo de excepciones para entradas inválidas.
- Validación de entradas: no se permiten campos vacíos ni valores numéricos incorrectos.
- Organización estructurada en clases como `Cancion`, `Playlist`, `ListaCanciones`, `ListaPlaylists` y `NodoCancion`.

## Requisitos para ejecutar

- Java Development Kit (JDK) 17 o superior
- Consola compatible con secuencias ANSI (para limpiar pantalla)

## Cómo ejecutar

1. Compila el archivo:
   ```bash
   javac ReproductorMain.java
   ```

2. Ejecuta el programa:
   ```bash
   java ReproductorMain
   ```

## Notas adicionales

- El proyecto no utiliza persistencia, por lo que todos los datos se mantienen solo durante la ejecución del programa.
- El código está pensado para evaluarse en un entorno académico, demostrando dominio de estructuras básicas en Java.
