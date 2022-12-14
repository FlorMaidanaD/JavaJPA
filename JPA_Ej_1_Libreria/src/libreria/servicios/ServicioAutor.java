package libreria.servicios;

import java.util.Collection;
import java.util.Scanner;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.NoResultException;
import javax.persistence.Persistence;
import libreria.entidades.Autor;

public class ServicioAutor {

    Scanner leer = new Scanner(System.in).useDelimiter("\n");
    EntityManagerFactory emf = Persistence.createEntityManagerFactory("LibreriaPersistencia");
    EntityManager em = emf.createEntityManager();

    /////////////////// creacion
    public void crearAutor() {
        try {
            //aca puedo hacer los insert...
            try {
                Autor a1 = new Autor();
                System.out.print("Ingrese el nombre: ");
                a1.setNombre(leer.next());
                a1.setAlta(Boolean.TRUE);

                em.getTransaction().begin();
                em.persist(a1);
                em.getTransaction().commit();

            } catch (Exception e) {
                System.out.println("Error del sistema");
            }
        } catch (Exception e) {
            System.out.println("Error del sistema");

        }
    }

  
    //creacion pasando nombre
    public Autor crearAutorAuto(String nombre) {
        Autor a1 = new Autor();
        a1.setNombre(nombre);
        a1.setAlta(Boolean.TRUE);

        em.getTransaction().begin();
        em.persist(a1);
        em.getTransaction().commit();
        return a1;

    }

    //consulta
    
    public void  consultaAutor (String nombre){
       Collection <Autor> autores=em.createQuery("SELECT a FROM Autor a"
                                                +" WHERE a.nombre = :nombre").setParameter("nombre", nombre).getResultList();
              
        for (Autor autore : autores) {
            if (autore.getAlta()==true)
                autore.imprimirLindo();
            //System.out.println(autore.toString());           
        }
 
    }
    
    
    
    //modificacion
    
    public void modificacionAutor(String nombre, String nombreNuevo) {

        try {

            Autor a = (Autor) em.createQuery("SELECT a FROM Autor a"
                    + " WHERE a.nombre = :nombre").setParameter("nombre", nombre).getSingleResult();
            Autor a1 = em.find(Autor.class, a.getId());
            a1.setNombre(nombreNuevo);
            em.getTransaction().begin();
            em.merge(a1);
            em.getTransaction().commit();

        } catch (NoResultException e) {
            System.out.println("No se encont?? ningun autor con ese nombre.");
        }

    }
    
        //eliminacion
    public void EliminacionAutor (String nombre) {

        try {

            Autor a = (Autor) em.createQuery("SELECT a FROM Autor a"
                    + " WHERE a.nombre = :nombre").setParameter("nombre", nombre).getSingleResult();
            Autor a1 = em.find(Autor.class, a.getId());
            a1.setAlta(Boolean.FALSE);
            //em.getTransaction().begin();
            //em.remove(a1);
            //em.getTransaction().commit();

        } catch (NoResultException e) {
            System.out.println("No se encont?? ningun autor con ese nombre.");
        }

    }

}
