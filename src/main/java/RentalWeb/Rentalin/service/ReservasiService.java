package RentalWeb.Rentalin.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import RentalWeb.Rentalin.dto.ReservasiDTO;
import RentalWeb.Rentalin.model.Layanan;
import RentalWeb.Rentalin.model.Reservasi;
import RentalWeb.Rentalin.model.User;
import RentalWeb.Rentalin.repository.LayananRepository;
import RentalWeb.Rentalin.repository.ReservasiRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class ReservasiService {

    @Autowired
    private ReservasiRepository reservasiRepository;

    @Autowired
    private LayananRepository layananRepository;

    // Mapping Entity ke DTO
    private ReservasiDTO toDTO(Reservasi reservasi) {
        ReservasiDTO dto = new ReservasiDTO();
        dto.setId(reservasi.getId());
        dto.setUserId(reservasi.getUser().getId());
        dto.setAlamatPenjemputan(reservasi.getAlamatPenjemputan());
        dto.setWaktuPenjemputan(reservasi.getWaktuPenjemputan());
        dto.setCatatan(reservasi.getCatatan());
        dto.setStatus(reservasi.getStatus().name());

        // Tambahan informasi user (jika dibutuhkan di admin)
        dto.setNama(reservasi.getUser().getFullName());

        if (reservasi.getLayanan() != null) {
            dto.setLayananId(reservasi.getLayanan().getId());
            dto.setNamaLayanan(reservasi.getLayanan().getNamaLayanan());
            dto.setHargaPerKg(reservasi.getLayanan().getHargaPerKg());
        }

        return dto;
    }

    // Mapping DTO ke Entity, harus sudah punya User
    private Reservasi toEntity(ReservasiDTO dto, User user) {
        Reservasi reservasi = new Reservasi();
        reservasi.setUser(user);
        reservasi.setAlamatPenjemputan(dto.getAlamatPenjemputan());
        reservasi.setWaktuPenjemputan(dto.getWaktuPenjemputan());
        reservasi.setCatatan(dto.getCatatan());
        reservasi.setStatus(Reservasi.StatusReservasi.MENUNGGU); // default status

        // Set layanan
        if (dto.getLayananId() != null && !dto.getLayananId().isEmpty()) {
            Layanan layanan = layananRepository.findById(dto.getLayananId())
                    .orElseThrow(() -> new RuntimeException("Layanan tidak ditemukan"));
            reservasi.setLayanan(layanan);
        }

        return reservasi;
    }

    // Simpan reservasi baru
    public ReservasiDTO buatReservasi(ReservasiDTO dto, User user) {
        Reservasi reservasi = toEntity(dto, user);
        Reservasi saved = reservasiRepository.save(reservasi);
        return toDTO(saved);
    }

    // Ambil semua reservasi (bisa ditambah filter user nanti)
    public List<ReservasiDTO> getAllReservasi() {
        return reservasiRepository.findAll().stream()
                .map(this::toDTO)
                .collect(Collectors.toList());
    }

    // Ambil reservasi by id
    public ReservasiDTO getReservasiById(String id) {
        return reservasiRepository.findById(id)
                .map(this::toDTO)
                .orElse(null);
    }

    // Update status reservasi (misal untuk ubah dari MENUNGGU ke DIJEMPUT)
    // public ReservasiDTO updateStatus(String id, Reservasi.StatusReservasi status)
    // {
    // return reservasiRepository.findById(id).map(reservasi -> {
    // reservasi.setStatus(status);
    // Reservasi updated = reservasiRepository.save(reservasi);
    // return toDTO(updated);
    // }).orElse(null);
    // }

    public ReservasiDTO updateStatus(String reservasiId, String statusBaru) {
        return reservasiRepository.findById(reservasiId).map(reservasi -> {
            try {
                Reservasi.StatusReservasi status = Reservasi.StatusReservasi.valueOf(statusBaru.toUpperCase());
                reservasi.setStatus(status);
                Reservasi updated = reservasiRepository.save(reservasi);
                return toDTO(updated);
            } catch (IllegalArgumentException e) {
                throw new RuntimeException("Status tidak valid: " + statusBaru);
            }
        }).orElseThrow(() -> new RuntimeException("Reservasi tidak ditemukan dengan ID: " + reservasiId));
    }

    public Page<ReservasiDTO> getReservasiPage(Pageable pageable) {
        return reservasiRepository.findAll(pageable)
                .map(this::toDTO); // langsung map ke DTO
    }

    public Page<ReservasiDTO> searchReservasi(String keyword, Pageable pageable) {
        return reservasiRepository.searchByKeyword(keyword, pageable)
                .map(this::toDTO);
    }

}