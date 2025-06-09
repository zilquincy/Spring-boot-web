package RentalWeb.Rentalin.dto;

import java.math.BigDecimal;
import java.text.DecimalFormat;
import java.time.LocalDateTime;

import RentalWeb.Rentalin.util.MapperUtil;
import lombok.Data;
import jakarta.validation.constraints.NotNull;

@Data
public class TransaksiDTO {

    private String id;

    private String namaPelanggan;

    private String pelangganId;

    @NotNull(message = "ID layanan tidak boleh kosong")
    private String layananId;

    @NotNull(message = "ID kasir tidak boleh kosong")
    private String kasirId;

    @NotNull(message = "Berat cucian wajib diisi")
    private BigDecimal beratKg;

    private BigDecimal total; // bisa dihitung otomatis di service

    private String status; // enum: DITERIMA, DICUCI, dll (bisa di-set default di backend)

    private LocalDateTime tanggalTransaksi;

    // private transient String tanggalTransaksiFormatted;

    private String layananNama;
    private String kasirNama;

    private String reservasiId; // untuk transaksi dari reservasi

    public String getTanggalTransaksiFormatted() {
        return MapperUtil.formatTanggal(this.tanggalTransaksi);
    }

    public String getTotalFormatted() {
        if (total == null)
            return "Rp 0";
        DecimalFormat formatter = new DecimalFormat("#,##0");
        return "Rp " + formatter.format(total).replace(',', '.').replace('.', ',');
    }

    public String getReservasiId() {
        return reservasiId;
    }

    public void setReservasiId(String reservasiId) {
        this.reservasiId = reservasiId;
    }

}
