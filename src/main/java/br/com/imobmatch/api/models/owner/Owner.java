package br.com.imobmatch.api.models.owner;

import br.com.imobmatch.api.models.user.User;
import jakarta.persistence.*;
import jakarta.validation.constraints.Past;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.util.UUID;

;

/**
 * defines an owner model based on composition with the parent class user.
 */
@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Table(name = "owners")
public class Owner {

    @Id
    @Column(name = "id")
    private UUID id;

    @Column(name = "name", nullable = false, length = 255)
    private String name;

    @OneToOne
    @MapsId //enable shared primary keys in JPA
    @JoinColumn(name = "id", nullable = false, unique = true)
    private User user;

    @Column(name = "cpf", nullable = false, length = 11,  unique = true)
    private String cpf;

    @Past
    @Column(name ="birth_date", nullable = false)
    private LocalDate birthDate;

    @Column(name="whats_app_phone_number",  nullable = false, length = 20)
    private String whatsAppPhoneNumber;

    @Column(name="personal_phone_number", length = 20)
    private String personalPhoneNumber;

}
