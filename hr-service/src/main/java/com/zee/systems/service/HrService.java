package com.zee.systems.service;


import com.zee.systems.datatype.LeaveStatus;
import com.zee.systems.entity.Employee;
import com.zee.systems.entity.LeaveRecord;
import com.zee.systems.repository.EmployeeRepository;
import com.zee.systems.repository.LeaveRecordRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

/**
 * @dev : Ezekiel Eromosei
 * @date : 14 Oct, 2026
 */

@Service
@RequiredArgsConstructor
public class HrService {

    private final EmployeeRepository employeeRepository;
    private final LeaveRecordRepository leaveRecordRepository;


    @Transactional
    public LeaveRecord applyLeave(String username, LocalDate date) {
        return leaveRecordRepository
                .findByUsernameAndLeaveDate(username, date)
                .orElseGet(() -> {
                    LeaveRecord leaveRecord = LeaveRecord.builder()
                            .username(username)
                            .leaveDate(date)
                            .status(LeaveStatus.APPROVED)
                            .createdAt(LocalDateTime.now())
                            .build();

                    return leaveRecordRepository.save(leaveRecord);
                });
    }

    @Transactional(readOnly = true)
    public boolean isOnLeave(String username, LocalDate date){
        return leaveRecordRepository
                .findByUsernameAndLeaveDate(username, date)
                .isPresent();
    }

    @Transactional(readOnly = true)
    public String findReplacement(String username, LocalDate date){
        Employee current = employeeRepository.findByUsername(username)
                .orElseThrow(() ->new RuntimeException("Employee not founde"));


        List<Employee> teamMembers = employeeRepository.findByTeam(current.getTeam());

        for(Employee member : teamMembers){
            if(member.getUsername().equals(username)){
                continue;
            }
            boolean onLeave = leaveRecordRepository.findByUsernameAndLeaveDate(member.getUsername(), date)
                    .isPresent();

            if(!onLeave){
                return member.getUsername();
            }
        }
        throw new RuntimeException("No replacement available");
    }
}
