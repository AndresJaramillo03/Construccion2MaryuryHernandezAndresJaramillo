package app.dao.repository;

import app.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepository extends JpaRepository<User,Long> {

    public User findBy();

    public User findByUserName(String userName);

    public boolean existsByUserName(String userName); 
    
    public User findById (long id);
}