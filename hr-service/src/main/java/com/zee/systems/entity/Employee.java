package com.zee.systems.entity;


import jakarta.persistence.*;
import lombok.*;

/**
 * @dev : Ezekiel Eromosei
 * @date : 14 Oct, 2026
 */

@Entity
@Table(name = "employee")
@Getter
@Setter
@ToString
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Employee {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(unique = true, nullable = false)
    private String username;

    @Column(name = "full_name")
    private String fullname;

    @Column
    private String team;
}
