package com.example.buyingcarv2.model;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Entity
@Table(name ="client")
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class Client {

    @Id
    @GeneratedValue
    @Column(name = "client_id")
    private Long id;

    @Column(name = "first_name")
    @NotBlank(message = "Client must specify the name")
    private String firstName;

    @Column(name = "last_name")
    @NotBlank(message = "Client must specify surname")
    private String lastName;

    @Column(name = "phone_number")
    @Pattern(regexp = "(\\+380|0)(\\d{9})")
    private String phoneNumber;

    @OneToOne(mappedBy = "client", cascade = CascadeType.ALL)
    private Address address;

    @OneToMany(
            mappedBy = "client",
            cascade = {CascadeType.PERSIST, CascadeType.MERGE},
            orphanRemoval = true
    )
    private List<Order> orders;
}
