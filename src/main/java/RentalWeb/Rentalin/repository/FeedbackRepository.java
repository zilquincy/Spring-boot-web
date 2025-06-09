package RentalWeb.Rentalin.repository;

import RentalWeb.Rentalin.model.Feedback;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;

public interface FeedbackRepository extends JpaRepository<Feedback, String> {
    List<Feedback> findAllByDeletedAtIsNull();

    List<Feedback> findByDeletedAtIsNull();

    List<Feedback> findByUser_IdAndDeletedAtIsNull(String userId);
}
