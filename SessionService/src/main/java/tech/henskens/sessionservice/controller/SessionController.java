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
import tech.henskens.sessionservice.model.Session;
import tech.henskens.sessionservice.model.User;

import javax.servlet.http.HttpServletRequest;

@RestController
@RequestMapping({"/session"})
public class SessionController {
    private final ISessionManager sessionManager;
    private final IUserManager userManager;

    public SessionController(ISessionManager sessionManager, IUserManager userManager) {
        this.sessionManager = sessionManager;
        this.userManager = userManager;
    }

    @PostMapping("/start")
    public ResponseEntity<SessionDto> startSession(@RequestHeader("Authorization") String bearerToken, @RequestBody StartSessionDto startSessionDto) {
        User authenticatedUser = this.userManager.authenticatedUser(bearerToken);
        return this.sessionManager.startSession(bearerToken, authenticatedUser, startSessionDto);
    }

    @PutMapping("/stop/{id}")
    public ResponseEntity<SessionDto> stopSession(@RequestHeader("Authorization") String bearerToken, @PathVariable Long id) {
        this.userManager.authenticatedUser(bearerToken);
        return this.sessionManager.stopSession(bearerToken, id);
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
    public ResponseEntity<Page<SessionDto>> getAllSessions(@RequestHeader("Authorization") String bearerToken,Pageable pageable) {
        this.userManager.authenticatedUser(bearerToken);
        return ResponseEntity.ok(sessionManager.getAllSessions(pageable));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteSession(@PathVariable Long id) {
        sessionManager.deleteById(id);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/running")
    public ResponseEntity<SessionDto> getSession(@RequestHeader("Authorization") String bearerToken) {
        User user = userManager.authenticatedUser(bearerToken);
        return sessionManager.getSessionForAuthenticatedUser(user);
    }
}

