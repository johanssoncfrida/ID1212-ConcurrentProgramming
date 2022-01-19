package kth.bookdesk.repository;

import kth.bookdesk.model.Desk;
import org.springframework.data.domain.Example;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.repository.query.FluentQuery;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import javax.transaction.Transactional;
import java.sql.Timestamp;
import java.util.List;
import java.util.Optional;
import java.util.function.Function;

public class DeskRepositoryImpl implements DeskRepository {

    @PersistenceContext
    private EntityManager em;

    @Override
    @Transactional
    public Desk save(Desk desk) {
        em.persist(desk);
        em.flush();
        return desk;
    }

    @Override
    public Desk getById(Integer id) {
        Desk desk = em.find(Desk.class, id);
        System.out.println("GetById: " + desk.getDeskId());
        if(desk == null) return null;
        else return desk;
    }

    @Override
    public Integer getDeskId(Integer id) {
        Desk desk = em.find(Desk.class, id);
        if(desk == null) return null;
        else return desk.getDeskId();
    }

    @Override
    public List<Desk> findAll() {
        String q = "SELECT d FROM desk d";
        Query query = em.createQuery(q);
        List<Desk> desks = query.getResultList();
        return desks;
    }
    @Override
    @Transactional
    public void update(Desk desk, Timestamp stampstart, Timestamp stampend){ //not used anymore
        desk.setBooked(1);
        desk.setStartTime(stampstart);
        desk.setEndTime(stampend);
        em.merge(desk);
        em.close();
    }

    @Override
    @Transactional
    public void removeDesk(Desk desk){ //not used anymore
        desk.setBooked(0);
        desk.setStartTime(null);
        desk.setEndTime(null);
        em.merge(desk);
        em.close();
    }
    @Override
    @Transactional
    public void deleteById(Integer id) {
        Desk desk = em.find(Desk.class, id);
        em.remove(desk);
    }

    @Override
    @Transactional
    public void delete(Desk desk) {
        em.remove(desk);
    }


    @Override
    public List<Desk> findAll(Sort sort) {
        return null;
    }

    @Override
    public Page<Desk> findAll(Pageable pageable) {
        return null;
    }

    @Override
    public List<Desk> findAllById(Iterable<Integer> integers) {
        return null;
    }

    @Override
    public long count() {
        return 0;
    }


    @Override
    public void deleteAllById(Iterable<? extends Integer> integers) {

    }

    @Override
    public void deleteAll(Iterable<? extends Desk> entities) {

    }

    @Override
    public void deleteAll() {

    }

    @Override
    public <S extends Desk> List<S> saveAll(Iterable<S> entities) {
        return null;
    }

    @Override
    public Optional<Desk> findById(Integer integer) {
        return Optional.empty();
    }

    @Override
    public boolean existsById(Integer integer) {
        return false;
    }

    @Override
    public void flush() {

    }

    @Override
    public <S extends Desk> S saveAndFlush(S entity) {
        return null;
    }

    @Override
    public <S extends Desk> List<S> saveAllAndFlush(Iterable<S> entities) {
        return null;
    }

    @Override
    public void deleteAllInBatch(Iterable<Desk> entities) {

    }

    @Override
    public void deleteAllByIdInBatch(Iterable<Integer> integers) {

    }

    @Override
    public void deleteAllInBatch() {

    }

    @Override
    public Desk getOne(Integer integer) {
        return null;
    }

    @Override
    public <S extends Desk> Optional<S> findOne(Example<S> example) {
        return Optional.empty();
    }

    @Override
    public <S extends Desk> List<S> findAll(Example<S> example) {
        return null;
    }

    @Override
    public <S extends Desk> List<S> findAll(Example<S> example, Sort sort) {
        return null;
    }

    @Override
    public <S extends Desk> Page<S> findAll(Example<S> example, Pageable pageable) {
        return null;
    }

    @Override
    public <S extends Desk> long count(Example<S> example) {
        return 0;
    }

    @Override
    public <S extends Desk> boolean exists(Example<S> example) {
        return false;
    }

    @Override
    public <S extends Desk, R> R findBy(Example<S> example, Function<FluentQuery.FetchableFluentQuery<S>, R> queryFunction) {
        return null;
    }

}
