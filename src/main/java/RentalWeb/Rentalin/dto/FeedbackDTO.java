package RentalWeb.Rentalin.dto;

import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

import java.time.LocalDateTime;

public class FeedbackDTO {

    private String id;

    @NotNull(message = "User ID tidak boleh null")
    private String userId;

    private String transaksiId; // Optional

    @Min(value = 1, message = "Rating minimal 1")
    @Max(value = 5, message = "Rating maksimal 5")
    private int rating;

    @NotBlank(message = "Komentar tidak boleh kosong")
    private String komentar;

    private LocalDateTime tanggalFeedback;

    // Getter & Setter
    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getTransaksiId() {
        return transaksiId;
    }

    public void setTransaksiId(String transaksiId) {
        this.transaksiId = transaksiId;
    }

    public int getRating() {
        return rating;
    }

    public void setRating(int rating) {
        this.rating = rating;
    }

    public String getKomentar() {
        return komentar;
    }

    public void setKomentar(String komentar) {
        this.komentar = komentar;
    }

    public LocalDateTime getTanggalFeedback() {
        return tanggalFeedback;
    }

    public void setTanggalFeedback(LocalDateTime tanggalFeedback) {
        this.tanggalFeedback = tanggalFeedback;
    }
}
