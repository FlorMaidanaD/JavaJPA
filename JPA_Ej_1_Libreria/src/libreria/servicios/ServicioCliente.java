package libreria.servicios;

import java.util.Collection;
import java.util.Scanner;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.NoResultException;
import javax.persistence.Persistence;
import libreria.entidades.Cliente;

public class ServicioCliente {

    Scanner leer = new Scanner(System.in).useDelimiter("\n");
    EntityManagerFactory emf = Persistence.createEntityManagerFactory("LibreriaPersistencia");
    EntityManager em = emf.createEntityManager();

    public void crearCliente() {
    Cliente c=new Cliente ();
    System.out.println("DNI: ");
    c.setDocumento(leer.nextLong());
    System.out.println("Nombre: ");
    c.setNombre(leer.next());
        System.out.println("Apellido: ");
        c.setApellido(leer.next());
        System.out.println("Telefono: ");
        c.setTelefono(leer.next());
        em.getTransaction().begin();
        em.persist(c);
        em.getTransaction().commit();
    }    
    public void consultaCliente (long dni){
        Collection <Cliente> clientes=em.createQuery("SELECT c FROM c"
        + "WHERE c.dni = :dni").setParameter("dni", dni).getResultList();
        System.out.printf("%-25s %-30s %-30s %-30s\n", "DNI", "NOMBRE", "APELLIDO", "TELEFONO");
        for (Cliente cliente : clientes) {
            cliente.imprimirLindo ();
            System.out.println("");            
        }
    }
    
    public void mostrarClientes (){
        Collection <Cliente> clientes=em.createQuery("SELECT c FROM c").getResultList();
        System.out.printf("%-25s %-30s %-30s %-30s\n", "DNI", "NOMBRE", "APELLIDO", "TELEFONO");
        for (Cliente cliente : clientes) {
            cliente.imprimirLindo ();                      
        }        
    }
    
    public void eliminarCliente (Long dni){
        try{
            Cliente c = (Cliente) em.createQuery("SELECT c FROM Cliente c"
                    + " WHERE c.dni = :dni").setParameter ("dni", dni).getSingleResult();
            Cliente c1= em.find(Cliente.class, c.getId());
            em.getTransaction().begin();
           em.remove(c1);
           em.getTransaction().commit();
        }
        catch (NoResultException e) {
            System.out.println("Error sistema");
        }
    }
    
    

 
}
        
        
        
        
    



