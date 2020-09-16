package dao;

import entities.PassengerEntity;
import org.springframework.stereotype.Repository;

import javax.persistence.*;
import java.util.List;

@Repository
public class PassengerDaoImpl implements PassengerDao {

    private static final EntityManagerFactory emFactoryObj =
                Persistence.createEntityManagerFactory("sbb-pu");

    private final EntityManager entityManager = emFactoryObj.createEntityManager();

    public PassengerEntity getPassenger(int id) {
        return entityManager.find(PassengerEntity.class, id);
    }

    @Override
    public List<PassengerEntity> getAllPassengers() {
        Query query = entityManager.createQuery("SELECT e FROM PassengerEntity e");
        return query.getResultList();
    }

    @Override
    public void addPassenger(PassengerEntity passenger) {
        EntityTransaction transaction = entityManager.getTransaction();
        transaction.begin();
        entityManager.persist(passenger);
        transaction.commit();
    }

    @Override
    public void updatePassenger(PassengerEntity passenger) {
        EntityTransaction transaction = entityManager.getTransaction();
        transaction.begin();
        entityManager.merge(passenger);
        transaction.commit();
    }

    @Override
    public void deletePassenger(PassengerEntity passenger) {
            EntityTransaction transaction = entityManager.getTransaction();
            transaction.begin();
            entityManager.remove(passenger);
            transaction.commit();
    }
}