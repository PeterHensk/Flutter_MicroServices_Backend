package tech.henskens.sessionservice.controller;

import java.time.LocalDateTime;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import tech.henskens.sessionservice.dto.session.CreateSessionDto;
import tech.henskens.sessionservice.dto.session.DateRangeDto;
import tech.henskens.sessionservice.dto.session.SessionDto;
import tech.henskens.sessionservice.dto.session.StartSessionDto;
import tech.henskens.sessionservice.manager.session.ISessionManager;
import tech.henskens.sessionservice.manager.user.IUserManager;
import tech.henskens.sessionservice.model.User;

@RestController
@RequestMapping({"/session"})
public class SessionController {
    private final ISessionManager sessionManager;
    private final IUserManager userManager;

    public SessionController(ISessionManager sessionManager, IUserManager userManager) {
        this.sessionManager = sessionManager;
        this.userManager = userManager;
    }

    @PostMapping
    public ResponseEntity<SessionDto> createSession(@RequestBody CreateSessionDto createSessionDto) {
        SessionDto createdSession = this.sessionManager.createSession(createSessionDto);
        return new ResponseEntity<>(createdSession, HttpStatus.CREATED);
    }

    @PostMapping("/start")
    public ResponseEntity<SessionDto> startSession(@RequestHeader("Authorization") String bearerToken, @RequestBody StartSessionDto startSessionDto) {
        User authenticatedUser = this.userManager.authenticatedUser(bearerToken);
        SessionDto createdSession = this.sessionManager.startSession(authenticatedUser, startSessionDto);
        return new ResponseEntity<>(createdSession, HttpStatus.CREATED);
    }

    @PutMapping("/stop/{id}")
    public ResponseEntity<SessionDto> stopSession(@PathVariable Long id) {
        SessionDto stoppedSession = this.sessionManager.stopSession(id);
        return new ResponseEntity<>(stoppedSession, HttpStatus.OK);
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

