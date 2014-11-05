package br.com.uaijug.kairos.repository;

import br.com.uaijug.kairos.domain.Event;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * Spring Data JPA repository for the Event entity.
 */
public interface EventRepository extends JpaRepository<Event, Long> {

}
