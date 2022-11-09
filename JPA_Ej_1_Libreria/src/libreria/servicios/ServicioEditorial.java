package libreria.servicios;

import java.util.Collection;
import java.util.Scanner;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.NoResultException;
import javax.persistence.Persistence;
import libreria.entidades.Editorial;

public class ServicioEditorial {

           Scanner leer = new Scanner(System.in).useDelimiter("\n");
    EntityManagerFactory emf = Persistence.createEntityManagerFactory("LibreriaPersistencia");
    EntityManager em = emf.createEntityManager();
    

    public void crearEditorial() {
        try {

            //aca puedo hacer los insert...
            try {
                Editorial e1 = new Editorial();
                System.out.print("Ingrese el nombre de la Editorial: ");
                e1.setNombre(leer.next());
                e1.setAlta(Boolean.TRUE);

                em.getTransaction().begin();
                em.persist(e1);
                em.getTransaction().commit();

            } catch (Exception e) {
                System.out.println("Error del sistema");
            }
        } catch (Exception e) {
            System.out.println("Error del sistema");

        }
    }

    public Editorial crearEditorialAuto(String nombre) {

        Editorial e1 = new Editorial();

        e1.setNombre(nombre);
        e1.setAlta(Boolean.TRUE);

        em.getTransaction().begin();
        em.persist(e1);
        em.getTransaction().commit();
        return e1;
    }

    // aca si lo quiero hacer con try catch me salta error por el return dentro del try
    /*
    public Collection <Editorial>  consultaEditorial (String nombre){
      
        try{
              Collection <Editorial> editoriales=em.createQuery("SELECT a FROM Editorial a"
                                                +" WHERE a.nombre = :nombre").setParameter("nombre", nombre).getResultList();
       
        return editoriales; 
   
        }catch (NoResultException e){
            System.out.println("No se encontó ninguna editorial con ese nombre.");
        }
           
    }    */
    public void consultaEditorial(String nombre) {

        Collection<Editorial> editoriales = em.createQuery("SELECT a FROM Editorial a"
                + " WHERE a.nombre = :nombre").setParameter("nombre", nombre).getResultList();
        for (Editorial editoriale : editoriales) {
            System.out.println(editoriale.toString());
            System.out.println("");
        }

    }

    public void modificacionEditorial(String nombre, String nombreNuevo) {

        try {

            Editorial e = (Editorial) em.createQuery("SELECT a FROM Editorial a"
                    + " WHERE a.nombre = :nombre").setParameter("nombre", nombre).getSingleResult();
            Editorial e1 = em.find(Editorial.class, e.getId());
            e1.setNombre(nombreNuevo);
            em.getTransaction().begin();
            em.merge(e1);
            em.getTransaction().commit();

        } catch (NoResultException e) {
            System.out.println("No se encontó ninguna editorial con ese nombre.");
        }

    }
    
    
    public void EliminacionEditorial(String nombre) {

        try {

            Editorial e = (Editorial) em.createQuery("SELECT a FROM Editorial a"
                    + " WHERE a.nombre = :nombre").setParameter("nombre", nombre).getSingleResult();
            Editorial e1 = em.find(Editorial.class, e.getId());
            e1.setAlta(Boolean.FALSE);
            //em.getTransaction().begin();
            //em.remove(e1);
            //em.getTransaction().commit();

        } catch (NoResultException e) {
            System.out.println("No se encontó ninguna editorial con ese nombre.");
        }

    }


}
