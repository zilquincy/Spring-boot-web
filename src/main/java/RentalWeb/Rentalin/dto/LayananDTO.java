package RentalWeb.Rentalin.dto;

import java.math.BigDecimal;

import jakarta.validation.constraints.NotBlank;
import lombok.Data;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;

@Data
public class LayananDTO {

    private String id;

    @NotBlank(message = "Nama layanan tidak boleh kosong")
    private String namaLayanan;

    @NotNull(message = "Harga wajib diisi")
    @Positive(message = "Harga harus positif")
    private BigDecimal hargaPerKg;
}
