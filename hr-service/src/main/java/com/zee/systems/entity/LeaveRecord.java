package com.zee.systems.entity;


import com.zee.systems.datatype.LeaveStatus;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDate;
import java.time.LocalDateTime;

/**
 * @dev : Ezekiel Eromosei
 * @date : 14 Oct, 2026
 */

@Entity
@Table(name = "leave_record")
@Getter
@Setter
@ToString
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class LeaveRecord {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(unique = true, nullable = false)
    private String username;

    private LocalDate leaveDate;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private LeaveStatus status;

   @Column(nullable = false)
    private LocalDateTime createdAt;
}
