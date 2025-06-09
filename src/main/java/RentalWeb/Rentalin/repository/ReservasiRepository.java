package RentalWeb.Rentalin.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import RentalWeb.Rentalin.model.Reservasi;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

@Repository
public interface ReservasiRepository extends JpaRepository<Reservasi, String> {
    Page<Reservasi> findAll(Pageable pageable);

    @Query("SELECT r FROM Reservasi r " +
            "WHERE LOWER(r.user.fullName) LIKE LOWER(CONCAT('%', :keyword, '%')) " +
            "OR LOWER(r.alamatPenjemputan) LIKE LOWER(CONCAT('%', :keyword, '%')) " +
            "OR LOWER(r.catatan) LIKE LOWER(CONCAT('%', :keyword, '%'))")
    Page<Reservasi> searchByKeyword(@Param("keyword") String keyword, Pageable pageable);

}