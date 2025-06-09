package RentalWeb.Rentalin.model;

import java.math.BigDecimal;
import java.time.LocalDateTime;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@Entity
@Table(name = "layanan")
public class Layanan {

    @Id
    @Column(length = 36)
    private String id;

    private String namaLayanan;

    private BigDecimal hargaPerKg;

    private LocalDateTime deletedAt;

    // getters & setters
    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getNamaLayanan() {
        return namaLayanan;
    }

    public void setNamaLayanan(String namaLayanan) {
        this.namaLayanan = namaLayanan;
    }

    public BigDecimal getHargaPerKg() {
        return hargaPerKg;
    }

    public void setHargaPerKg(BigDecimal hargaPerKg) {
        this.hargaPerKg = hargaPerKg;
    }

    public LocalDateTime getDeletedAt() {
        return deletedAt;
    }

    public void setDeletedAt(LocalDateTime deletedAt) {
        this.deletedAt = deletedAt;
    }
}
