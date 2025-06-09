package RentalWeb.Rentalin.repository;

import RentalWeb.Rentalin.model.Pembayaran;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;

public interface PembayaranRepository extends JpaRepository<Pembayaran, String> {
    List<Pembayaran> findByTransaksi_Id(String transaksiId);

    Page<Pembayaran> findAll(Pageable pageable);

    @Query("SELECT SUM(p.totalBayar) FROM Pembayaran p WHERE p.transaksi.id = :transaksiId")
    BigDecimal sumTotalBayarByTransaksiId(@Param("transaksiId") String transaksiId);

    Optional<Pembayaran> findByTransaksiIdAndStatus(String transaksiId, Pembayaran.StatusPembayaran status);

    List<Pembayaran> findByTransaksiPelangganEmail(String email);

    List<Pembayaran> findAllByTransaksiPelangganId(String pelangganId);

    @Query("SELECT p FROM Pembayaran p " +
            "WHERE LOWER(p.transaksi.namaPelanggan) LIKE LOWER(CONCAT('%', :keyword, '%')) " +
            "   OR LOWER(p.status) LIKE LOWER(CONCAT('%', :keyword, '%')) "
            + "   OR LOWER(p.transaksi.id) LIKE LOWER(CONCAT('%', :keyword, '%')) " +
            "   OR LOWER(p.metodePembayaran) LIKE LOWER(CONCAT('%', :keyword, '%'))")
    Page<Pembayaran> search(@Param("keyword") String keyword, Pageable pageable);

}
