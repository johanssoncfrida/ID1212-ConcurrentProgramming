package kth.bookdesk.repository;

import kth.bookdesk.model.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

//JpaRepository: get all methods from CrudReposiory and PaginAndSortingRespository
//CrudRepository would be enough for this implementation.
public interface UserRepository extends JpaRepository<User, Integer> {

    User save(User user);
    User getById(Integer id);
    List<User> findAll();
}
