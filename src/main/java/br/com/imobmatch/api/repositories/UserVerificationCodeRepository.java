package br.com.imobmatch.api.repositories;

import br.com.imobmatch.api.models.user.UserVerificationCode;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface UserVerificationCodeRepository extends JpaRepository<UserVerificationCode, UUID> {
}
