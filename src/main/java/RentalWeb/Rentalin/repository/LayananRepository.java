package RentalWeb.Rentalin.repository;

import RentalWeb.Rentalin.model.Layanan;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface LayananRepository extends JpaRepository<Layanan, String> {
    List<Layanan> findAllByDeletedAtIsNull();

    List<Layanan> findByDeletedAtIsNull();

    Page<Layanan> findAllByDeletedAtIsNull(Pageable pageable);

    Page<Layanan> findByNamaLayananContainingIgnoreCaseAndDeletedAtIsNull(String nama, Pageable pageable);

}
