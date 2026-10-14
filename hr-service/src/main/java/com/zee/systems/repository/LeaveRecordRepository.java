package com.zee.systems.repository;


import com.zee.systems.entity.LeaveRecord;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.Optional;

/**
 * @dev : Ezekiel Eromosei
 * @date : 14 Oct, 2026
 */

@Repository
public interface LeaveRecordRepository extends CrudRepository<LeaveRecord, Long> {

    Optional<LeaveRecord> findByUsernameAndLeaveDate(String username, LocalDate leaveDate);

}
