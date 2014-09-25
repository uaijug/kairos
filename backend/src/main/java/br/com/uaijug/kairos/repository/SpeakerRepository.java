package br.com.uaijug.kairos.repository;

import br.com.uaijug.kairos.domain.Speaker;
        import org.springframework.data.jpa.repository.JpaRepository;

/**
 * Spring Data JPA repository for the Speaker entity.
 */
public interface SpeakerRepository extends JpaRepository<Speaker, Long> {

}
