package kth.bookdesk.repository;

import kth.bookdesk.model.Booker;
import org.springframework.data.jpa.repository.JpaRepository;

//JpaRepository: get all methods from CrudReposiory and PaginAndSortingRespository
//CrudRepository would be enough for this implementation.
public interface BookerRepository extends JpaRepository<Booker, Integer> {

    public Booker getById(int id);
    Booker save(Booker booker);
    public void removeBookedDesk(int deskId);
    public void deleteById(Integer id);
}
