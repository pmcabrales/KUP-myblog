package es.kairosds.user;

import org.springframework.data.jpa.repository.JpaRepository;

public interface BlogUserRepository extends JpaRepository<BlogUser, Long> {

	BlogUser findByUsername(String username);
}
