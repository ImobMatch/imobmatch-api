package br.com.imobmatch.api.repositories;

import br.com.imobmatch.api.models.user.UserVerificationCode;
import org.springframework.data.jpa.repository.JpaRepository;
import br.com.imobmatch.api.models.user.User;
import br.com.imobmatch.api.models.enums.VerificationType;
import java.util.List;
import java.util.UUID;

public interface UserVerificationCodeRepository
                extends JpaRepository<UserVerificationCode, UUID> {

        List<UserVerificationCode> findByUserAndTypeAndVerifiedFalse(
                        User user,
                        VerificationType type);
}
