package kth.bookdesk.repository;

import kth.bookdesk.model.Logger;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;

//JpaRepository: get all methods from CrudReposiory and PaginAndSortingRespository
//CrudRepository would be enough for this implementation.
public interface LoggerRepository  extends JpaRepository<Logger, Integer> {
    Logger save(Logger logger);
    public List<Logger> findAll();

}
