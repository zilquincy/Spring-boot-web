package RentalWeb.Rentalin.util;

import RentalWeb.Rentalin.dto.*;
import RentalWeb.Rentalin.model.*;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Locale;
import java.util.UUID;

public class MapperUtil {

    // User
    public static User toEntity(UserDTO dto) {
        if (dto == null)
            return null;
        User user = new User();
        user.setId(dto.getId() != null ? dto.getId() : UUID.randomUUID().toString());
        user.setFullName(dto.getFullName());
        user.setEmail(dto.getEmail());
        user.setUsername(dto.getUsername());
        user.setPassword(dto.getPassword());
        user.setRole(dto.getRole());
        return user;
    }

    public static UserDTO toDTO(User user) {
        if (user == null)
            return null;
        UserDTO dto = new UserDTO();
        dto.setId(user.getId());
        dto.setFullName(user.getFullName());
        dto.setEmail(user.getEmail());
        dto.setUsername(user.getUsername());
        dto.setPassword(user.getPassword());
        dto.setRole(user.getRole());
        return dto;
    }

    // Layanan
    public static Layanan toEntity(LayananDTO dto) {
        if (dto == null)
            return null;

        Layanan layanan = new Layanan();
        layanan.setId(dto.getId() != null ? dto.getId() : UUID.randomUUID().toString());
        layanan.setNamaLayanan(dto.getNamaLayanan());
        layanan.setHargaPerKg(dto.getHargaPerKg());
        layanan.setDeletedAt(null);

        return layanan;
    }

    public static LayananDTO toDTO(Layanan entity) {
        if (entity == null)
            return null;

        LayananDTO dto = new LayananDTO();
        dto.setId(entity.getId());
        dto.setNamaLayanan(entity.getNamaLayanan());
        dto.setHargaPerKg(entity.getHargaPerKg());

        return dto;
    }

    // transaksi
    public static Transaksi toEntity(TransaksiDTO dto, User pelanggan, User kasir, Layanan layanan) {
        if (dto == null)
            return null;
        Transaksi t = new Transaksi();

        // ID
        if (dto.getId() == null || dto.getId().isEmpty()) {
            t.setId(UUID.randomUUID().toString());
        } else {
            t.setId(dto.getId());
        }

        // Pelanggan
        t.setPelanggan(pelanggan);

        // Hindari NullPointerException
        if (pelanggan != null) {
            t.setNamaPelanggan(pelanggan.getFullName());
        } else {
            t.setNamaPelanggan(dto.getNamaPelanggan()); // untuk guest / tanpa akun
        }

        // Lainnya
        t.setKasir(kasir);
        t.setLayanan(layanan);
        t.setBeratKg(dto.getBeratKg());
        t.setTotal(dto.getTotal());

        if (dto.getStatus() != null) {
            t.setStatus(Transaksi.Status.valueOf(dto.getStatus()));
        }

        t.setTanggalTransaksi(dto.getTanggalTransaksi());

        if (dto.getReservasiId() != null && !dto.getReservasiId().isEmpty()) {
            Reservasi reservasi = new Reservasi();
            reservasi.setId(dto.getReservasiId());
            t.setReservasi(reservasi);
        } else {
            t.setReservasi(null);
        }

        return t;
    }

    public static TransaksiDTO toDTO(Transaksi t) {
        if (t == null)
            return null;

        TransaksiDTO dto = new TransaksiDTO();
        dto.setId(t.getId());

        if (t.getPelanggan() != null) {
            dto.setPelangganId(t.getPelanggan().getId());
            dto.setNamaPelanggan(t.getPelanggan().getFullName());
        } else {
            dto.setPelangganId(null);
            dto.setNamaPelanggan(t.getNamaPelanggan()); // fallback ke nama manual
        }

        dto.setKasirId(t.getKasir() != null ? t.getKasir().getId() : null);
        dto.setLayananId(t.getLayanan() != null ? t.getLayanan().getId() : null);
        dto.setBeratKg(t.getBeratKg());
        dto.setTotal(t.getTotal());
        dto.setStatus(t.getStatus() != null ? t.getStatus().name() : null);
        dto.setTanggalTransaksi(t.getTanggalTransaksi());
        dto.setLayananNama(t.getLayanan() != null ? t.getLayanan().getNamaLayanan() : null);
        dto.setKasirNama(t.getKasir() != null ? t.getKasir().getFullName() : null);

        return dto;
    }

    // Pembayaran
    public static Pembayaran toEntity(PembayaranDTO dto, Transaksi transaksi) {
        if (dto == null)
            return null;
        Pembayaran p = new Pembayaran();
        p.setId(dto.getId() != null ? dto.getId() : UUID.randomUUID().toString());
        p.setTransaksi(transaksi);
        p.setWaktuBayar(LocalDateTime.now());
        if (dto.getJumlah() != null) {
            p.setTotalBayar(BigDecimal.valueOf(dto.getJumlah()));
        }
        if (dto.getMetodePembayaran() != null) {
            p.setMetodePembayaran(Pembayaran.MetodePembayaran.valueOf(dto.getMetodePembayaran()));
        }
        if (dto.getStatus() != null) {
            p.setStatus(Pembayaran.StatusPembayaran.valueOf(dto.getStatus()));
        }
        return p;
    }

    public static PembayaranDTO toDTO(Pembayaran entity) {
        if (entity == null)
            return null;

        PembayaranDTO dto = new PembayaranDTO();
        dto.setId(entity.getId());

        if (entity.getTransaksi() != null) {
            Transaksi trx = entity.getTransaksi();
            dto.setTransaksiId(trx.getId());

            // Tambahan data dari transaksi
            if (trx.getKasir() != null) {
                dto.setNamaKasir(trx.getKasir().getFullName());
            }

            if (trx.getNamaPelanggan() != null && !trx.getNamaPelanggan().isEmpty()) {
                dto.setNamaPelanggan(trx.getNamaPelanggan());
            } else {
                dto.setNamaPelanggan("Unknown");
            }

            dto.setBerat(trx.getBeratKg());
            dto.setTanggalTransaksi(trx.getTanggalTransaksi());
        }

        if (entity.getTotalBayar() != null) {
            dto.setJumlah(entity.getTotalBayar().doubleValue());
        }

        if (entity.getMetodePembayaran() != null) {
            dto.setMetodePembayaran(entity.getMetodePembayaran().name());
        }

        if (entity.getStatus() != null) {
            dto.setStatus(entity.getStatus().name());
        }

        dto.setWaktuBayar(entity.getWaktuBayar());

        return dto;
    }

    // PaymentHistory
    public static PaymentHistory toEntity(PaymentHistoryDTO dto, Pembayaran pembayaran) {
        if (dto == null)
            return null;
        PaymentHistory ph = new PaymentHistory();
        ph.setId(dto.getId() != null ? dto.getId() : UUID.randomUUID().toString());
        ph.setPembayaran(pembayaran);
        ph.setJumlahBayar(dto.getJumlah() != null ? BigDecimal.valueOf(dto.getJumlah()) : null);
        ph.setTanggalBayar(LocalDateTime.now());
        // Jika ingin set metodePembayaran dan keterangan, bisa ditambahkan di sini
        return ph;
    }

    public static PaymentHistoryDTO toDTO(PaymentHistory entity) {
        if (entity == null)
            return null;
        PaymentHistoryDTO dto = new PaymentHistoryDTO();
        dto.setId(entity.getId());
        dto.setPembayaranId(entity.getPembayaran() != null ? entity.getPembayaran().getId() : null);
        dto.setJumlah(entity.getJumlahBayar() != null ? entity.getJumlahBayar().doubleValue() : null);
        return dto;
    }

    // Feedback
    public static Feedback toFeedback(FeedbackDTO dto, User user, Transaksi transaksi) {
        Feedback feedback = new Feedback();
        feedback.setId(dto.getId());
        feedback.setUser(user);
        feedback.setTransaksi(transaksi);
        feedback.setRating(dto.getRating());
        feedback.setKomentar(dto.getKomentar());
        feedback.setTanggalFeedback(dto.getTanggalFeedback());
        return feedback;
    }

    public static FeedbackDTO toFeedbackDTO(Feedback feedback) {
        FeedbackDTO dto = new FeedbackDTO();
        dto.setId(feedback.getId());
        dto.setUserId(feedback.getUser().getId());
        dto.setTransaksiId(
                feedback.getTransaksi() != null ? feedback.getTransaksi().getId() : null);
        dto.setRating(feedback.getRating());
        dto.setKomentar(feedback.getKomentar());
        dto.setTanggalFeedback(feedback.getTanggalFeedback());
        return dto;
    }

    // Resevasi
    public ReservasiDTO toDTO(Reservasi reservasi) {
        ReservasiDTO dto = new ReservasiDTO();
        dto.setId(reservasi.getId());
        dto.setUserId(reservasi.getUser().getId());
        dto.setAlamatPenjemputan(reservasi.getAlamatPenjemputan());
        dto.setWaktuPenjemputan(reservasi.getWaktuPenjemputan());
        dto.setCatatan(reservasi.getCatatan());
        dto.setStatus(reservasi.getStatus().name());
        return dto;
    }

    public Reservasi toEntity(ReservasiDTO dto, User user) {
        Reservasi reservasi = new Reservasi();
        reservasi.setUser(user);
        reservasi.setAlamatPenjemputan(dto.getAlamatPenjemputan());
        reservasi.setWaktuPenjemputan(dto.getWaktuPenjemputan());
        reservasi.setCatatan(dto.getCatatan());
        // status default = MENUNGGU saat baru dibuat
        return reservasi;
    }

    public static String formatTanggal(LocalDateTime tanggal) {
        if (tanggal == null)
            return "-";
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd MMM yyyy", new Locale("id", "ID"));
        return tanggal.format(formatter);
    }

    public static String formatJam(LocalDateTime waktu) {
        if (waktu == null)
            return "";
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("EEEE, HH:mm:ss", new Locale("id", "ID"));
        return waktu.format(formatter);
    }

    public static String formatRupiah(BigDecimal amount) {
        if (amount == null)
            return "Rp 0";
        return String.format("Rp %,d", amount).replace(',', '.');
    }

}
