package kth.bookdesk.repository;

import kth.bookdesk.model.Booker;
import org.springframework.data.domain.Example;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.repository.query.FluentQuery;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import javax.transaction.Transactional;
import java.util.List;
import java.util.Optional;
import java.util.function.Function;

public class BookerRepositoryImpl implements BookerRepository{

    //Creates a unique em. Injected by PersistenceContext->thread safe
    @PersistenceContext
    private EntityManager em;

    @Override
    @Transactional
    public Booker save(Booker booker) {
        em.persist(booker); //insert new identity into context, no other entity with same id.
        em.flush(); //run query on database
        return booker;
    }

    @Override
    @Transactional
    public void removeBookedDesk(int deskId){
        Query query = em.createQuery("DELETE FROM booker b WHERE b.deskId=" + deskId);
        long rows = query.executeUpdate();
        em.close();
    }

    @Override
    public Booker getById(int id) {
        Booker booker = em.find(Booker.class, id);
        if(booker == null) return null;
        else return booker;


    }
    @Override
    @Transactional
    public void deleteById(Integer id) {
        Booker booker = em.find(Booker.class, id);
        em.remove(booker);
    }

    @Override
    public List<Booker> findAll() {
        return null;
    }

    @Override
    public List<Booker> findAll(Sort sort) {
        return null;
    }

    @Override
    public Page<Booker> findAll(Pageable pageable) {
        return null;
    }

    @Override
    public List<Booker> findAllById(Iterable<Integer> integers) {
        return null;
    }

    @Override
    public long count() {
        return 0;
    }

    @Override
    public void delete(Booker entity) {

    }

    @Override
    public void deleteAllById(Iterable<? extends Integer> integers) {

    }

    @Override
    public void deleteAll(Iterable<? extends Booker> entities) {

    }

    @Override
    public void deleteAll() {

    }

    @Override
    public <S extends Booker> List<S> saveAll(Iterable<S> entities) {
        return null;
    }

    @Override
    public Optional<Booker> findById(Integer integer) {
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
    public <S extends Booker> S saveAndFlush(S entity) {
        return null;
    }

    @Override
    public <S extends Booker> List<S> saveAllAndFlush(Iterable<S> entities) {
        return null;
    }

    @Override
    public void deleteAllInBatch(Iterable<Booker> entities) {

    }

    @Override
    public void deleteAllByIdInBatch(Iterable<Integer> integers) {

    }

    @Override
    public void deleteAllInBatch() {

    }

    @Override
    public Booker getOne(Integer integer) {
        return null;
    }

    @Override
    public Booker getById(Integer integer) {
        return null;
    }


    @Override
    public <S extends Booker> Optional<S> findOne(Example<S> example) {
        return Optional.empty();
    }

    @Override
    public <S extends Booker> List<S> findAll(Example<S> example) {
        return null;
    }

    @Override
    public <S extends Booker> List<S> findAll(Example<S> example, Sort sort) {
        return null;
    }

    @Override
    public <S extends Booker> Page<S> findAll(Example<S> example, Pageable pageable) {
        return null;
    }

    @Override
    public <S extends Booker> long count(Example<S> example) {
        return 0;
    }

    @Override
    public <S extends Booker> boolean exists(Example<S> example) {
        return false;
    }

    @Override
    public <S extends Booker, R> R findBy(Example<S> example, Function<FluentQuery.FetchableFluentQuery<S>, R> queryFunction) {
        return null;
    }

}
