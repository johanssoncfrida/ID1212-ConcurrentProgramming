package kth.bookdesk.repository;


import kth.bookdesk.model.Logger;
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

public class LoggerRepositoryImpl implements LoggerRepository{

    @PersistenceContext
    private EntityManager em;

    @Override
    @Transactional
    public Logger save(Logger logger) {
        em.persist(logger);;
        em.flush();
        return logger;
    }


    @Override
    public List<Logger> findAll() {
        String q = "SELECT l FROM logger l";
        Query query = em.createQuery(q);
        List<Logger> logger = query.getResultList();
        return logger;
    }


    @Override
    public List<Logger> findAll(Sort sort) {
        return null;
    }

    @Override
    public Page<Logger> findAll(Pageable pageable) {
        return null;
    }

    @Override
    public List<Logger> findAllById(Iterable<Integer> integers) {
        return null;
    }

    @Override
    public long count() {
        return 0;
    }

    @Override
    public void deleteById(Integer integer) {

    }

    @Override
    public void delete(Logger entity) {

    }

    @Override
    public void deleteAllById(Iterable<? extends Integer> integers) {

    }

    @Override
    public void deleteAll(Iterable<? extends Logger> entities) {

    }

    @Override
    public void deleteAll() {

    }

    @Override
    public <S extends Logger> List<S> saveAll(Iterable<S> entities) {
        return null;
    }

    @Override
    public Optional<Logger> findById(Integer integer) {
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
    public <S extends Logger> S saveAndFlush(S entity) {
        return null;
    }

    @Override
    public <S extends Logger> List<S> saveAllAndFlush(Iterable<S> entities) {
        return null;
    }

    @Override
    public void deleteAllInBatch(Iterable<Logger> entities) {

    }

    @Override
    public void deleteAllByIdInBatch(Iterable<Integer> integers) {

    }

    @Override
    public void deleteAllInBatch() {

    }

    @Override
    public Logger getOne(Integer integer) {
        return null;
    }

    @Override
    public Logger getById(Integer integer) {
        return null;
    }

    @Override
    public <S extends Logger> Optional<S> findOne(Example<S> example) {
        return Optional.empty();
    }

    @Override
    public <S extends Logger> List<S> findAll(Example<S> example) {
        return null;
    }

    @Override
    public <S extends Logger> List<S> findAll(Example<S> example, Sort sort) {
        return null;
    }

    @Override
    public <S extends Logger> Page<S> findAll(Example<S> example, Pageable pageable) {
        return null;
    }

    @Override
    public <S extends Logger> long count(Example<S> example) {
        return 0;
    }

    @Override
    public <S extends Logger> boolean exists(Example<S> example) {
        return false;
    }

    @Override
    public <S extends Logger, R> R findBy(Example<S> example, Function<FluentQuery.FetchableFluentQuery<S>, R> queryFunction) {
        return null;
    }
}
