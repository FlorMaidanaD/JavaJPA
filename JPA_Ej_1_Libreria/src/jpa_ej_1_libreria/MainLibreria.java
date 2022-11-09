package jpa_ej_1_libreria;

import java.util.Collection;
import java.util.Scanner;
import libreria.entidades.Autor;
import libreria.entidades.Editorial;
import static libreria.entidades.Libro_.editorial;
import libreria.servicios.ServicioAutor;
import libreria.servicios.ServicioEditorial;
import libreria.servicios.ServicioLibro;

public class MainLibreria {

    public static void main(String[] args) {
        Scanner leer = new Scanner(System.in).useDelimiter("\n");
        ServicioEditorial se = new ServicioEditorial();
        ServicioAutor sa = new ServicioAutor();
        ServicioLibro sl = new ServicioLibro();
        int op = 0;
        int op2 = 0;

        System.out.println("*****************************************************");
        System.out.println("*                                                   *");
        System.out.println("*                     LIBRERIA                      *");
        System.out.println("*                                                   *");
        System.out.println("*****************************************************");
        
        try {
            do {
                System.out.println("");
                System.out.println("*****************************************************");
                System.out.println("                        MENÚ                         ");
                System.out.println("*****************************************************");
                System.out.println("1. ALTAS");
                System.out.println("2. EDITAR");
                System.out.println("3. BAJAS");
                System.out.println("4. CONSULTAS");
                System.out.println("0. SALIR");
                System.out.println("*****************************************************");
                System.out.print("***_ ");
                op = leer.nextInt();
                switch (op) {
                    case 1:
                        try {
                            do {
                                System.out.println("1. NUEVA EDITORIAL");
                                System.out.println("2. NUEVO AUTOR");
                                System.out.println("3. NUEVO LIBRO");
                                System.out.println("4. SALIR");
                                System.out.print("***_ ");
                                op2 = leer.nextInt();
                                switch (op2) {
                                    case 1:
                                        se.crearEditorial();
                                        System.out.println("");
                                        break;
                                    case 2:
                                        sa.crearAutor();
                                        System.out.println("");
                                        break;
                                    case 3:
                                        sl.crearLibro();
                                        System.out.println("");
                                        break;
                                }
                            } while (op2 != 4);
                            op2 = 100;
                        } catch (Exception e) {
                            System.out.println("Error del sistema");
                        }
                        break;

                    case 2:
                        System.out.println("");
                        try {
                            do {
                                System.out.println("1. EDITAR EDITORIAL");
                                System.out.println("2. EDITAR AUTOR");
                                System.out.println("3. EDITAR LIBRO");
                                System.out.println("4. SALIR");
                                System.out.print("***_ ");
                                op2 = leer.nextInt();
                                switch (op2) {
                                    case 1:
                                        System.out.println("");
                                        System.out.print("*** Ingrese nombre de editorial a modificar: -");
                                        String nombre = leer.next();
                                        System.out.print("*** Ingrese nuevo nombre: -");
                                        String nombreNuevo = leer.next();
                                        se.modificacionEditorial(nombre, nombreNuevo);
                                        System.out.println("");
                                        break;
                                    case 2:
                                        System.out.println("");
                                        System.out.print("*** Ingrese nombre del autor a modificar: -");
                                        String nombreA = leer.next();
                                        System.out.print("*** Ingrese nuevo nombre: -");
                                        String nombreNuevoA = leer.next();
                                        sa.modificacionAutor(nombreA, nombreNuevoA);
                                        System.out.println("");
                                        break;
                                    case 3:
                                        System.out.println("");
                                        System.out.print("*** Ingrese ISBN del libro a modificar: -");
                                        Long isbn = leer.nextLong();
                                        System.out.print("*** Ingrese nuevo nombre: -");
                                        String nombreNuevoL = leer.next();
                                        sl.modificacionLibroNombre(isbn, nombreNuevoL);
                                        System.out.println("");
                                        break;
                                }
                            } while (op2 != 4);
                            op2 = 100;
                        } catch (Exception e) {
                            System.out.println("Error del sistema");
                        }
                        break;
                    case 3:
                        System.out.println("");
                        try {
                            do {
                                System.out.println("1. BAJA EDITORIAL");
                                System.out.println("2. BAJA AUTOR");
                                System.out.println("3. BAJA LIBRO");
                                System.out.println("4. SALIR");
                                System.out.print("***_ ");
                                op2 = leer.nextInt();
                                switch (op2) {
                                    case 1:
                                        System.out.println("");
                                        System.out.print("*** Ingrese nombre de editorial a ELIMINAR: -");
                                        String nombre = leer.next();
                                        se.EliminacionEditorial(nombre);
                                        System.out.println("");
                                        break;
                                    case 2:
                                        System.out.println("");
                                        System.out.print("*** Ingrese nombre del autor a ELIMINAR: -");
                                        String nombreA = leer.next();
                                        sa.EliminacionAutor(nombreA);
                                        System.out.println("");
                                        break;
                                    case 3:
                                        System.out.println("");
                                        System.out.print("*** Ingrese ISBN del libro a ELIMINAR: -");
                                        Long isbn = leer.nextLong();
                                        //sl.eliminacionLibroPorNombre(nombre);
                                        sl.eliminarLibroIsbn(isbn);
                                        System.out.println("");
                                        break;
                                }
                            } while (op2 != 4);
                            op2 = 100;
                        } catch (Exception e) {
                            System.out.println("Error del sistema");
                        }
                        break;
                    case 4:
                        System.out.println("");
                        try {
                            do {
                                System.out.println("1. CONSULTA EDITORIAL");
                                System.out.println("2. CONSULTA AUTOR");
                                System.out.println("3. CONSULTA LIBRO por ISBN");
                                System.out.println("4. CONSULTA LIBRO por NOMBRE");
                                System.out.println("5. CONSULTA LIBRO por AUTOR");
                                System.out.println("6. CONSULTA LIBRO por EDITORIAL");
                                System.out.println("7. SALIR");
                                System.out.print("***_ ");
                                op2 = leer.nextInt();
                                switch (op2) {
                                    case 1:
                                        System.out.println("");
                                        System.out.print("*** Ingrese nombre de editorial a BUSCAR: -");
                                        String nombre = leer.next();
                                        Collection<Editorial> editoriales = se.consultaEditorial(nombre);
                                        for (Editorial editoriale : editoriales) {
                                            editoriale.toString();
                                            System.out.println("");
                                        }

                                        System.out.println("");
                                        break;
                                    case 2:
                                        System.out.println("");
                                        System.out.print("*** Ingrese nombre del autor a CONSULTAR: -");
                                        String nombreA = leer.next();
                                        sa.consultaAutor(nombreA);
                                        System.out.println("");
                                        break;
                                    case 3:
                                        System.out.println("");
                                        System.out.print("*** Ingrese ISBN del libro a CONSULTAR: -");
                                        Long isbn = leer.nextLong();
                                        sl.consultaLibroId(isbn);
                                        System.out.println("");
                                        break;
                                    case 4:
                                        System.out.println("");
                                        System.out.println("*** Ingrese NOMBRE del libro a CONSULTAR: -");
                                        String nombrel=leer.next();
                                        sl.eliminacionLibroPorNombre(nombrel);
                                        break;
                                    case 5:
                                     System.out.println("");
                                        System.out.println("*** Ingrese NOMBRE DEL AUTOR del libro a CONSULTAR: -");
                                        String nombrea=leer.next();   
                                        sl.consultaLibroAutor(nombrea);
                                        break;
                                    case 6:
                                        System.out.println("");
                                        System.out.println("*** Ingrese NOMBRE DE LA EDITORIAL del libro a CONSULTAR: -");
                                        String nombree=leer.next();
                                        sl.consultaLibroEditorial(nombree);
                                        break;
                                }
                            } while (op2 != 7);
                        
                        } catch (Exception e) {
                            System.out.println("Error del sistema");
                        }
                        break;

                }
            } while (op != 0);
        } catch (Exception e) {
            System.out.println("Error del sistema");
        }
    }
}
