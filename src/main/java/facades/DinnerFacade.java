package facades;

import dtos.DinnerEventDTO;
import entities.DinnerEvent;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.TypedQuery;
import java.util.ArrayList;
import java.util.List;

public class DinnerFacade {

    private static EntityManagerFactory emf;
    private static DinnerFacade instance;

    private DinnerFacade(){
    }

    public static DinnerFacade getDinnerFacade(EntityManagerFactory _emf) {
        if (instance == null) {
            emf = _emf;
            instance = new DinnerFacade();
        }
        return instance;
    }

    public List<DinnerEventDTO> getAllEvents (){
        EntityManager em = emf.createEntityManager();
        try{
            TypedQuery<DinnerEvent> query = em.createQuery("SELECT d FROM DinnerEvent d", DinnerEvent.class);
            List<DinnerEvent> dinnerEvents = query.getResultList();
            List<DinnerEventDTO> dinnerEventDTOS = DinnerEventDTO.getDtos(dinnerEvents);
            return dinnerEventDTOS;
        }finally {
            em.close();
        }
    }

    public DinnerEventDTO createNewEvent(DinnerEventDTO dinnerEventDTO){
        EntityManager em = emf.createEntityManager();
        DinnerEvent dinnerEvent = new DinnerEvent(dinnerEventDTO);

        try{
            em.getTransaction().begin();
            em.persist(dinnerEvent);
            em.getTransaction().commit();
        }finally {
            em.close();
        }
        return new DinnerEventDTO(dinnerEvent);
    }

    public DinnerEventDTO updateEvent(DinnerEventDTO dinnerEventDTO){
        EntityManager em = emf.createEntityManager();
        try{
            em.getTransaction().begin();
            DinnerEvent dinnerEvent = em.find(DinnerEvent.class,dinnerEventDTO.getId());

            dinnerEvent.setLocation(dinnerEventDTO.getLocation());
            dinnerEvent.setDish(dinnerEventDTO.getDish());
            dinnerEvent.setPricePrPerson(dinnerEventDTO.getPricePrPerson());
            dinnerEvent.setDate(dinnerEventDTO.getDate());

            em.merge(dinnerEvent);
            em.getTransaction().commit();
            return new DinnerEventDTO(dinnerEvent);
        }finally {
            em.close();
        }
    }

    public DinnerEventDTO deleteEvent(Long id){
        EntityManager em = emf.createEntityManager();
        try{
            em.getTransaction().begin();
            DinnerEvent dinnerEvent = em.find(DinnerEvent.class,id);
            em.remove(dinnerEvent);
            em.getTransaction().commit();
            return new DinnerEventDTO(dinnerEvent);
        }finally {
            em.close();
        }
    }

    public DinnerEventDTO getSingleEvent(Long id){
        EntityManager em = emf.createEntityManager();
        try{
            DinnerEvent dinnerEvent = em.find(DinnerEvent.class,id);
            return new DinnerEventDTO(dinnerEvent);
        }finally {
            em.close();
        }
    }

}
