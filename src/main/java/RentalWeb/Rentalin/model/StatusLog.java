package RentalWeb.Rentalin.model;

import java.time.LocalDateTime;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;

@Entity
@Table(name = "status_log")
public class StatusLog {

    @Id
    @Column(length = 36)
    private String id;

    @ManyToOne
    @JoinColumn(name = "transaksi_id", nullable = false)
    private Transaksi transaksi;

    @Enumerated(EnumType.STRING)
    private Transaksi.Status status;

    private LocalDateTime waktu;

    // getters & setters
    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public Transaksi getTransaksi() {
        return transaksi;
    }

    public void setTransaksi(Transaksi transaksi) {
        this.transaksi = transaksi;
    }

    public Transaksi.Status getStatus() {
        return status;
    }

    public void setStatus(Transaksi.Status status) {
        this.status = status;
    }

    public LocalDateTime getWaktu() {
        return waktu;
    }

    public void setWaktu(LocalDateTime waktu) {
        this.waktu = waktu;
    }
}
