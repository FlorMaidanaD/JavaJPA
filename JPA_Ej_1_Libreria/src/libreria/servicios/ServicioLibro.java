package libreria.servicios;

import java.util.List;
import java.util.Scanner;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.NoResultException;
import javax.persistence.Persistence;
import libreria.entidades.Autor;
import libreria.entidades.Editorial;
import libreria.entidades.Libro;

public class ServicioLibro {

    Scanner leer = new Scanner(System.in).useDelimiter("\n");
    EntityManagerFactory emf = Persistence.createEntityManagerFactory("LibreriaPersistencia");
    EntityManager em = emf.createEntityManager();

    public void crearLibro() {
        try {
            Libro l1 = new Libro();
            System.out.print("- ISBN: ");
            Long isbn = leer.nextLong();
            if (validaLibroId(isbn)) {
                l1.setId(isbn);
                System.out.print("- TITULO: ");
                l1.setTitulo(leer.next());
                System.out.print("- AÑO: ");
                l1.setAnio(leer.nextInt());
                System.out.print("- EJEMPLARES: ");
                l1.setEjemplares(leer.nextInt());
                System.out.print("- PRESTADOS: ");
                l1.setEjemplaresPrestados(leer.nextInt());
                //aca hacer un metodo y llamarlo directamente, pero habria que ponerle variables...
                try {
                    l1.setEjemplaresRestantes(l1.getEjemplares() - l1.getEjemplaresPrestados());
                } catch (Exception e) {
                    System.out.println("Error del sistema al ingresar las cantidades");
                }
                l1.setAlta(Boolean.TRUE);
                System.out.print("AUTOR: ");
                String nombreA = leer.next();
                List<Autor> autores = em.createQuery("SELECT a FROM Autor a").getResultList();
                boolean a = false;
                for (Autor autore : autores) {
                    if (autore.getNombre().equalsIgnoreCase(nombreA)) {
                        l1.setAutor(autore);
                        a = true;
                        break;
                    }
                }
                if (a == false) {
                    ServicioAutor sa = new ServicioAutor();
                    l1.setAutor(sa.crearAutorAuto(nombreA));
                }

                System.out.println("");
                System.out.println("-----------------------");
                System.out.print("EDITORIAL: ");
                String nombreE = leer.next();
                List<Editorial> editoriales = em.createQuery("SELECT e FROM Editorial e").getResultList();
                boolean ed = false;
                for (Editorial editoriale : editoriales) {
                    if (editoriale.getNombre().equalsIgnoreCase(nombreE)) {
                        l1.setEditorial(editoriale);
                        ed = true;
                        break;
                    }
                }
                if (ed == false) {
                    ServicioEditorial se = new ServicioEditorial();
                    l1.setEditorial(se.crearEditorialAuto(nombreE));
                }

                em.getTransaction().begin();
                em.persist(l1);
                em.getTransaction().commit();
            } else {
                System.out.println("EL LIBRO YA ESTÁ CARGADO");

            }
        } catch (Exception e) {
            System.out.println("Datos invalidos, ISBN ya ingresado");
        }

        /*List<Autor> autores = em.createQuery("SELECT a FROM Autor a").getResultList();
                for (Autor autore : autores) {
                    System.out.println(autore);
                }*/
    }

//consulta          
    public void consultaLibroId(Long isbn) {
        Libro l = em.find(Libro.class, isbn);
        System.out.println(l.toString());
    }

    public boolean validaLibroId(Long isbn) {
        Libro l = em.find(Libro.class, isbn);
        return l == null;
    }

    public void consutaLibroNombre(String titulo) {
        try {
            List<Libro> l = em.createQuery("SELECT a FROM Libro a"
                    + " WHERE a.titulo = :titulo").setParameter("titulo", titulo).getResultList();
            System.out.printf("%-5s %-20s %-20s %-20s %-20s %-20s %-20s %-20s %-20s\n", "ISBN", "TITULO", "AÑO", "CANT. EJEMPLARES", "PRESTADOS", "DISPONIBLES", "ALTA", "AUTOR", "EDITORIAL");

            for (Libro libro : l) {
                libro.imprimirLindo();

            }
        } catch (Exception e) {
            System.out.println("Nombre no encontrado");
            System.out.println(e);
        }
    }

    public void consultaLibroAutor(String autor) {
        try {
            //List<Libro> libros = em.createQuery("SELECT a FROM Libro a Autor b"
            //  + " JOIN a.autor.nombre, b.nombre  = :autor").setParameter("autor", autor).getResultList();

            List<Libro> libros = em.createQuery("SELECT a FROM Libro a"
                    + " WHERE a.autor.nombre LIKE :autor").setParameter("autor", autor).getResultList();
            System.out.printf("%-5s %-20s %-20s %-20s %-20s %-20s %-20s %-20s %-20s\n", "ISBN", "TITULO", "AÑO", "CANT. EJEMPLARES", "PRESTADOS", "DISPONIBLES", "ALTA", "AUTOR", "EDITORIAL");
            for (Libro libro : libros) {
                libro.imprimirLindo();
                System.out.println("");
            }

        } catch (Exception e) {
            System.out.println("Nombre no encontrado");
            System.out.println(e);
        }

    }

    public void mostrarLibros() {
        List<Libro> libros = em.createQuery("SELECT a FROM Libro a").getResultList();
        
        System.out.printf("%-5s %-20s %-20s %-20s %-20s %-20s %-20s %-20s %-20s\n", "ISBN", "TITULO", "AÑO", "TOTAL", "PRESTADOS", "DISPONIBLES", "ALTA", "AUTOR", "EDITORIAL");
        for (Libro libro : libros) {
            libro.imprimirLindo();
            System.out.println("");
        }
    }

    public void consultaLibroEditorial(String editorial) {
        try {
            List<Libro> libros = em.createQuery("SELECT a FROM Libro a"
                    + " WHERE a.editorial.nombre = :editorial").setParameter("editorial", editorial).getResultList();
           
            System.out.printf("%-5s %-20s %-20s %-20s %-20s %-20s %-20s %-20s %-20s\n", "ISBN", "TITULO", "AÑO", "TOTAL", "PRESTADOS", "DISPONIBLES", "ALTA", "AUTOR", "EDITORIAL");
            for (Libro libro : libros) {
                libro.imprimirLindo();
                System.out.println("");
            }
        } catch (Exception e) {
            System.out.println("Editorial no encontrada");
            System.out.println(e);
        }
    }

    //modificar
    public void modificacionLibroNombre(Long isbn, String nombreNuevo) {

        try {
            Libro l = em.find(Libro.class,
                    isbn);
            l.setTitulo(nombreNuevo);
            em.getTransaction().begin();
            em.merge(l);
            em.getTransaction().commit();

        } catch (NoResultException e) {
            System.out.println("No se encontó ninguna libro con ese ISBN.");
        }

    }

    //eliminacion
    public void eliminacionLibroPorNombre(String nombre) {
        try {
            Libro l = (Libro) em.createQuery("SELECT a FROM Libro a"
                    + " WHERE a.nombre = :nombre").setParameter("nombre", nombre).getSingleResult();
            Libro l2 = em.find(Libro.class,
                    l.getId());
            l2.setAlta(Boolean.FALSE);
            //em.getTransaction().begin();
            //em.remove(l2);
            //em.getTransaction().commit();

        } catch (NoResultException e) {
            System.out.println("No se encontó ninguna libro con ese ISBN.");
        }
    }

    public void eliminarLibroIsbn(Long isbn) {

        try {
            Libro l = em.find(Libro.class,
                    isbn);
            l.setAlta(Boolean.FALSE);
            //em.getTransaction().begin();
            //em.remove(l);
            //em.getTransaction().commit();

        } catch (NoResultException e) {
            System.out.println("No se encontó ninguna libro con ese ISBN.");
            e.getMessage();
        }

    }
}
