package RentalWeb.Rentalin.dto;

import jakarta.validation.constraints.NotBlank;
import lombok.Data;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;

@Data
public class PaymentHistoryDTO {

    private String id;

    @NotBlank(message = "ID pembayaran tidak boleh kosong")
    private String pembayaranId;

    @NotNull(message = "Jumlah wajib diisi")
    @Positive(message = "Jumlah harus lebih dari 0")
    private Double jumlah;
}
