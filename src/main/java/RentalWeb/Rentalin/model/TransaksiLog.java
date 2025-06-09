package RentalWeb.Rentalin.model;

import java.time.LocalDateTime;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;

@Entity
@Table(name = "transaksi_log")
public class TransaksiLog {

    @Id
    @Column(length = 36)
    private String id;

    @ManyToOne
    @JoinColumn(name = "transaksi_id", nullable = false)
    private Transaksi transaksi;

    private String aksi;

    private String deskripsi;

    private LocalDateTime tanggalLog;

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

    public String getAksi() {
        return aksi;
    }

    public void setAksi(String aksi) {
        this.aksi = aksi;
    }

    public String getDeskripsi() {
        return deskripsi;
    }

    public void setDeskripsi(String deskripsi) {
        this.deskripsi = deskripsi;
    }

    public LocalDateTime getTanggalLog() {
        return tanggalLog;
    }

    public void setTanggalLog(LocalDateTime tanggalLog) {
        this.tanggalLog = tanggalLog;
    }
}
