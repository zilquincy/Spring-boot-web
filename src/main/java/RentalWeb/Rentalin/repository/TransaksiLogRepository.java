package RentalWeb.Rentalin.repository;

import RentalWeb.Rentalin.model.TransaksiLog;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;

public interface TransaksiLogRepository extends JpaRepository<TransaksiLog, String> {
    List<TransaksiLog> findByTransaksi_IdOrderByTanggalLogDesc(String transaksiId);
}
