package RentalWeb.Rentalin.dto;

import java.math.BigDecimal;
import java.text.DecimalFormat;
import java.time.LocalDateTime;

import RentalWeb.Rentalin.util.MapperUtil;
import jakarta.validation.constraints.NotBlank;
import lombok.Data;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;

@Data
public class PembayaranDTO {

    private String id;

    @NotBlank(message = "ID transaksi tidak boleh kosong")
    private String transaksiId;

    @NotNull(message = "Jumlah pembayaran wajib diisi")
    @Positive(message = "Jumlah harus positif")
    private Double jumlah;

    private String metodePembayaran;
    private String status;
    private LocalDateTime waktuBayar;

    // Tambahan data transaksi
    private String namaKasir;
    private String namaPelanggan;
    private BigDecimal berat;
    private LocalDateTime tanggalTransaksi;

    public String getWaktuBayarFormatted() {
        return MapperUtil.formatTanggal(this.waktuBayar);
    }

    public String getJamBayarFormatted() {
        return MapperUtil.formatJam(this.waktuBayar);
    }

    public String getTotalFormatted() {
        if (jumlah == null)
            return "Rp 0";
        DecimalFormat formatter = new DecimalFormat("#,##0");
        return "Rp " + formatter.format(jumlah).replace(',', '.').replace('.', ',');
    }

}
