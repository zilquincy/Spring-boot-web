package RentalWeb.Rentalin.model;

import java.math.BigDecimal;
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
@Table(name = "pembayaran")
public class Pembayaran {

    @Id
    @Column(length = 36)
    private String id;

    @ManyToOne
    @JoinColumn(name = "transaksi_id", nullable = false)
    private Transaksi transaksi;

    private BigDecimal totalBayar;

    private LocalDateTime waktuBayar;

    @Enumerated(EnumType.STRING)
    @Column(name = "metode_pembayaran", nullable = false, length = 20)
    private MetodePembayaran metodePembayaran;

    @Enumerated(EnumType.STRING)
    @Column(length = 20)
    private StatusPembayaran status;

    public enum StatusPembayaran {
        LUNAS, BELUM_LUNAS
    }

    public enum MetodePembayaran {
        CASH,
        TRANSFER,
        E_WALLET
    }

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

    public BigDecimal getTotalBayar() {
        return totalBayar;
    }

    public void setTotalBayar(BigDecimal totalBayar) {
        this.totalBayar = totalBayar;
    }

    public LocalDateTime getWaktuBayar() {
        return waktuBayar;
    }

    public void setWaktuBayar(LocalDateTime waktuBayar) {
        this.waktuBayar = waktuBayar;
    }

    public MetodePembayaran getMetodePembayaran() {
        return metodePembayaran;
    }

    public void setMetodePembayaran(MetodePembayaran metodePembayaran) {
        this.metodePembayaran = metodePembayaran;
    }

    public StatusPembayaran getStatus() {
        return status;
    }

    public void setStatus(StatusPembayaran status) {
        this.status = status;
    }
}
