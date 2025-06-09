package RentalWeb.Rentalin.repository;

import RentalWeb.Rentalin.model.PaymentHistory;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;

public interface PaymentHistoryRepository extends JpaRepository<PaymentHistory, String> {
    List<PaymentHistory> findByPembayaran_Id(String pembayaranId);
}
