package RentalWeb.Rentalin.dto;

import java.math.BigDecimal;
import java.time.LocalDateTime;

import RentalWeb.Rentalin.util.MapperUtil;

public class ReservasiDTO {

    private String id;
    private String userId;
    private String nama;
    private String alamatPenjemputan;
    private LocalDateTime waktuPenjemputan;
    private String catatan;
    private String status;
    private String layananId; // untuk form submit pilih layanan
    private String namaLayanan; // untuk menampilkan nama layanan di detail/list
    // private String HargaLayanan;
    private BigDecimal hargaPerKg;

    private String reservasiId;

    public ReservasiDTO() {
    }

    // Getters dan Setters

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

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getTanggalReservasiFormatted() {
        return MapperUtil.formatTanggal(this.waktuPenjemputan);
    }

    public String getLayananId() {
        return layananId;
    }

    public void setLayananId(String layananId) {
        this.layananId = layananId;
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

    public String getNama() {
        return nama;
    }

    public void setNama(String nama) {
        this.nama = nama;
    }

    public String getReservasiId() {
        return reservasiId;
    }

    public void setReservasiId(String reservasiId) {
        this.reservasiId = reservasiId;
    }
}
