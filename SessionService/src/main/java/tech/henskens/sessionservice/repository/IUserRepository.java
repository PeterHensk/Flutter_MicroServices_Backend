package tech.henskens.sessionservice.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import tech.henskens.sessionservice.model.User;

public interface IUserRepository extends JpaRepository<User, Long> {
    User findByEmailAddress(String emailAddress);

    Page<User> findAllByOrderByUpdatedDesc(Pageable pageable);
}
