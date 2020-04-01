package es.kairosds.user;

import org.springframework.data.jpa.repository.JpaRepository;

public interface LoggedUserRepository extends JpaRepository<LoggedUser, Long> {

	LoggedUser findByUsername(String username);
}
