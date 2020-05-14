package bookrentalcheon;

import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface ReservationMirrorRepository extends CrudRepository<ReservationMirror, Long> {
    Optional<ReservationMirror> findByuserid(String UserId);
}