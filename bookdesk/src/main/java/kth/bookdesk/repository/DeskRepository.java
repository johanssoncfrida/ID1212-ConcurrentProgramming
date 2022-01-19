package kth.bookdesk.repository;

import kth.bookdesk.model.Desk;
import org.springframework.data.jpa.repository.JpaRepository;

import java.sql.Timestamp;
import java.util.List;

//JpaRepository: get all methods from CrudReposiory and PaginAndSortingRespository
//CrudRepository would be enough for this implementation.
public interface DeskRepository extends JpaRepository<Desk, Integer> {

    Desk save(Desk desk);
    List<Desk> findAll();
    public void update(Desk desk, Timestamp start, Timestamp end);
    public void removeDesk(Desk desk);
    public Desk getById(Integer id);
    public void deleteById(Integer integer);
    public void delete(Desk desk);
    public Integer getDeskId(Integer id);
}
