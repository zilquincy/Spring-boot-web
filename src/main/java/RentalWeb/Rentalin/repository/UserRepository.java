package RentalWeb.Rentalin.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import RentalWeb.Rentalin.model.User;
import RentalWeb.Rentalin.model.User.Role;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;
import java.util.Optional;

public interface UserRepository extends JpaRepository<User, String> {
    Optional<User> findByEmailAndDeletedAtIsNull(String email);

    Optional<User> findByEmail(String email);

    List<User> findAllByDeletedAtIsNull();

    List<User> findAllByRole(Role kasir);

    List<User> findByRole(Role role);

    Page<User> findAllByDeletedAtIsNull(Pageable pageable);

    @Query("SELECT u FROM User u WHERE u.deletedAt IS NULL AND " +
            "(LOWER(u.username) LIKE LOWER(CONCAT('%', :keyword, '%')) OR " +
            "LOWER(u.fullName) LIKE LOWER(CONCAT('%', :keyword, '%')) OR " +
            "LOWER(u.email) LIKE LOWER(CONCAT('%', :keyword, '%')))")
    Page<User> searchByKeyword(@Param("keyword") String keyword, Pageable pageable);

}
