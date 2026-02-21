package br.com.imobmatch.api.repositories;

import br.com.imobmatch.api.models.user.User;
import br.com.imobmatch.api.models.enums.UserRole;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface UserRepository extends JpaRepository<User, UUID> {
    Optional<User> findById(UUID id);

    List<User> findAllByRole(UserRole userRole);

    @Query("SELECT u FROM users u WHERE u.email = :email")
    UserDetails findByUsername(@Param("email") String email);

    boolean existsByEmail(String email);

    Optional<User> findByEmail(String email);
}
