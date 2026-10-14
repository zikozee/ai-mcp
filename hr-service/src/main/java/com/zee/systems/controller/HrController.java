package com.zee.systems.controller;


import com.zee.systems.entity.LeaveRecord;
import com.zee.systems.service.HrService;
import lombok.RequiredArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;

/**
 * @dev : Ezekiel Eromosei
 * @date : 14 Oct, 2026
 */


@RestController
@RequiredArgsConstructor
@RequestMapping(path= "hr")
public class HrController {

    private final HrService hrService;

    @PostMapping(path = "leave")
    public ResponseEntity<LeaveRecord> applyLeave(@RequestParam("username") String username,
                                                  @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE)LocalDate date){

        return ResponseEntity.ok(hrService.applyLeave(username, date));
    }

    @GetMapping(path = "leave/{username}")
    public  ResponseEntity<Boolean> isOnLeave(@PathVariable String username,
                                              @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE)LocalDate date){

        return  ResponseEntity.ok(hrService.isOnLeave(username, date));
    }

    @GetMapping(path = "replacement/{username}")
    public  ResponseEntity<String> findReplacement(@PathVariable String username,
                                              @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE)LocalDate date){

        return ResponseEntity.ok(hrService.findReplacement(username, date));
    }
}
