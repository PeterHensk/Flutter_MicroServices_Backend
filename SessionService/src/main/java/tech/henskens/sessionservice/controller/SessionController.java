package tech.henskens.sessionservice.controller;

import java.time.LocalDateTime;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import tech.henskens.sessionservice.dto.session.DateRangeDto;
import tech.henskens.sessionservice.dto.session.SessionDto;
import tech.henskens.sessionservice.manager.session.ISessionManager;

@RestController
@RequestMapping({"/session"})
public class SessionController {
    private final ISessionManager sessionManager;

    public SessionController(ISessionManager sessionManager) {
        this.sessionManager = sessionManager;
    }

    @PostMapping
    public ResponseEntity<SessionDto> createSession(@RequestBody SessionDto sessionDto) {
        SessionDto createdSession = this.sessionManager.createSession(sessionDto);
        return new ResponseEntity<>(createdSession, HttpStatus.CREATED);
    }

    @PutMapping({"/{id}"})
    public ResponseEntity<SessionDto> updateSession(@PathVariable Long id, @RequestBody SessionDto sessionDto) {
        SessionDto updatedSession = this.sessionManager.updateSession(id, sessionDto);
        return new ResponseEntity<>(updatedSession, HttpStatus.OK);
    }

    @GetMapping({"/count"})
    public ResponseEntity<Integer> countSessions(@RequestParam("startDate") String startDateStr, @RequestParam("endDate") String endDateStr, @RequestParam("stationIdentifier") String stationIdentifier) {
        LocalDateTime startDate = LocalDateTime.parse(startDateStr);
        LocalDateTime endDate = LocalDateTime.parse(endDateStr);
        DateRangeDto dateRangeDto = new DateRangeDto(startDate, endDate, stationIdentifier);
        int count = this.sessionManager.countSessionsBetweenDates(dateRangeDto);
        return new ResponseEntity<>(count, HttpStatus.OK);
    }

    @GetMapping
    public ResponseEntity<Page<SessionDto>> getAllSessions(Pageable pageable) {
        return ResponseEntity.ok(sessionManager.getAllSessions(pageable));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteSession(@PathVariable Long id) {
        sessionManager.deleteById(id);
        return ResponseEntity.noContent().build();
    }
}

