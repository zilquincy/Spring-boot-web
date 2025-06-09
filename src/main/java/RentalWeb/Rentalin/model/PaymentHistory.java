package RentalWeb.Rentalin.model;

import java.math.BigDecimal;
import java.time.LocalDateTime;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;

@Entity
@Table(name = "payment_history")
public class PaymentHistory {

    @Id
    @Column(length = 36)
    private String id;

    @ManyToOne
    @JoinColumn(name = "pembayaran_id", nullable = false)
    private Pembayaran pembayaran;

    private BigDecimal jumlahBayar;

    private String metodePembayaran;

    private LocalDateTime tanggalBayar;

    private String keterangan;

    // getters & setters
    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public Pembayaran getPembayaran() {
        return pembayaran;
    }

    public void setPembayaran(Pembayaran pembayaran) {
        this.pembayaran = pembayaran;
    }

    public BigDecimal getJumlahBayar() {
        return jumlahBayar;
    }

    public void setJumlahBayar(BigDecimal jumlahBayar) {
        this.jumlahBayar = jumlahBayar;
    }

    public String getMetodePembayaran() {
        return metodePembayaran;
    }

    public void setMetodePembayaran(String metodePembayaran) {
        this.metodePembayaran = metodePembayaran;
    }

    public LocalDateTime getTanggalBayar() {
        return tanggalBayar;
    }

    public void setTanggalBayar(LocalDateTime tanggalBayar) {
        this.tanggalBayar = tanggalBayar;
    }

    public String getKeterangan() {
        return keterangan;
    }

    public void setKeterangan(String keterangan) {
        this.keterangan = keterangan;
    }
}
