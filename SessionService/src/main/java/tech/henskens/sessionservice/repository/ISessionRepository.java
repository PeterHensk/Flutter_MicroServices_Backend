package tech.henskens.sessionservice.repository;

import java.time.LocalDateTime;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import tech.henskens.sessionservice.dto.session.SessionDto;
import tech.henskens.sessionservice.model.Session;

public interface ISessionRepository extends JpaRepository<Session, Long> {
    int countByStartedBetweenAndStationIdentifier(LocalDateTime startDate, LocalDateTime endDate, String stationIdentifier);
    Page<Session> findAll(Pageable pageable);
    void deleteById(Long id);
}
