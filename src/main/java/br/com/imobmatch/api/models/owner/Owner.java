package br.com.imobmatch.api.models.owner;

import br.com.imobmatch.api.models.user.User;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.UUID;

/**
 * defines an owner model based on composition with the parent class user.
 */
@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
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

    @Column(name = "phone_ddd", nullable = false, length = 3)
    private String phoneDdd;

    @Column(name = "phone_number", nullable = false, length=9)
    private String phoneNumber;
}
