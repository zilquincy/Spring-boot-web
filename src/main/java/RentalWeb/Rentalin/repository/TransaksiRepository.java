package RentalWeb.Rentalin.repository;

import RentalWeb.Rentalin.model.Transaksi;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Page;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

public interface TransaksiRepository extends JpaRepository<Transaksi, String> {
        List<Transaksi> findAllByDeletedAtIsNull();

        Page<Transaksi> findAllByDeletedAtIsNull(Pageable pageable);

        List<Transaksi> findByPelanggan_IdAndDeletedAtIsNull(String pelangganId);

        int countByTanggalTransaksiBetweenAndDeletedAtIsNull(LocalDateTime start, LocalDateTime end);

        @Query("SELECT SUM(t.total) FROM Transaksi t WHERE t.tanggalTransaksi BETWEEN :start AND :end AND t.deletedAt IS NULL")
        BigDecimal sumTotalByTanggalTransaksiBetweenAndDeletedAtIsNull(
                        @Param("start") LocalDateTime start,
                        @Param("end") LocalDateTime end);

        int countByStatus(Transaksi.Status status);

        @Query(value = "SELECT * FROM transaksi WHERE deleted_at IS NULL ORDER BY tanggal_transaksi DESC", nativeQuery = true)
        List<Transaksi> findTopByDeletedAtIsNull(Pageable pageable);

        // Cari berdasarkan nama pelanggan (menggunakan relasi ke User)
        @Query("SELECT t FROM Transaksi t WHERE t.deletedAt IS NULL AND LOWER(t.pelanggan.fullName) LIKE LOWER(CONCAT('%', :keyword, '%'))")
        Page<Transaksi> searchByPelangganName(@Param("keyword") String keyword, Pageable pageable);

        // Alternatif, jika ingin search berdasarkan status juga:
        @Query("SELECT t FROM Transaksi t WHERE t.deletedAt IS NULL AND " +
                        "(LOWER(t.namaPelanggan) LIKE LOWER(CONCAT('%', :keyword, '%')) OR " +
                        "LOWER(t.status) LIKE LOWER(CONCAT('%', :keyword, '%')))")
        Page<Transaksi> searchByKeyword(@Param("keyword") String keyword, Pageable pageable);

        @Query(value = "SELECT HOUR(t.tanggal_transaksi) as jam, SUM(t.total) as total " +
                        "FROM transaksi t " +
                        "WHERE t.tanggal_transaksi BETWEEN :awal AND :akhir AND t.deleted_at IS NULL " +
                        "GROUP BY jam " +
                        "ORDER BY jam ASC", nativeQuery = true)
        List<Object[]> sumTotalGroupByHour(@Param("awal") LocalDateTime awal, @Param("akhir") LocalDateTime akhir);

}
