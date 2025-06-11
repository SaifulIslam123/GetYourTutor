package com.getyourtutor.domain.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import java.time.LocalDate;


@Entity
@Table(name = "profiles")
@Getter
@Setter
public class Profile extends BaseEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long profileId;

    @Column(nullable = false)
    private String firstName;

    @Column(nullable = false)
    private String lastName;

    @Column
    private String phone;


    @Embedded
    private Address address;

    @Column
    private LocalDate dateOfBirth;

    @Column(columnDefinition = "TEXT")
    private String bio;

    @Column(columnDefinition = "TEXT")
    private String qualifications;


    @Column(columnDefinition = "TEXT")
    private String experience;

    @Column
    private String photoUrl;



    @Column
    private String taxId;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", nullable = false)
    private User user;


}
