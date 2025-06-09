package RentalWeb.Rentalin.model;

import jakarta.persistence.*;
import java.time.LocalDateTime;
import java.util.UUID;

@Entity
@Table(name = "reservasi") // Sesuai tabel database
public class Reservasi {

    @Id
    @Column(length = 36)
    private String id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", nullable = false)
    private User user; // Asumsi sudah ada Entity User

    @Column(name = "alamat_penjemputan", columnDefinition = "TEXT", nullable = false)
    private String alamatPenjemputan;

    @Column(name = "waktu_penjemputan", nullable = false)
    private LocalDateTime waktuPenjemputan;

    @Column(name = "catatan", columnDefinition = "TEXT")
    private String catatan;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private StatusReservasi status = StatusReservasi.MENUNGGU;

    @Column(name = "created_at", nullable = false, updatable = false)
    private LocalDateTime createdAt;

    @Column(name = "updated_at")
    private LocalDateTime updatedAt;

    // Enum Status
    public enum StatusReservasi {
        MENUNGGU,
        DIJEMPUT,
        DIPROSES,
        SELESAI,
        DIBATALKAN
    }

    public Reservasi() {
        this.id = UUID.randomUUID().toString();
        this.createdAt = LocalDateTime.now();
    }

    @ManyToOne
    @JoinColumn(name = "layanan_id")
    private Layanan layanan;

    // getter & setter
    public Layanan getLayanan() {
        return layanan;
    }

    public void setLayanan(Layanan layanan) {
        this.layanan = layanan;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public String getAlamatPenjemputan() {
        return alamatPenjemputan;
    }

    public void setAlamatPenjemputan(String alamatPenjemputan) {
        this.alamatPenjemputan = alamatPenjemputan;
    }

    public LocalDateTime getWaktuPenjemputan() {
        return waktuPenjemputan;
    }

    public void setWaktuPenjemputan(LocalDateTime waktuPenjemputan) {
        this.waktuPenjemputan = waktuPenjemputan;
    }

    public String getCatatan() {
        return catatan;
    }

    public void setCatatan(String catatan) {
        this.catatan = catatan;
    }

    public StatusReservasi getStatus() {
        return status;
    }

    public void setStatus(StatusReservasi status) {
        this.status = status;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    public LocalDateTime getUpdatedAt() {
        return updatedAt;
    }

    public void setUpdatedAt(LocalDateTime updatedAt) {
        this.updatedAt = updatedAt;
    }
}
