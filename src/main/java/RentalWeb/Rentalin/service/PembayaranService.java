package RentalWeb.Rentalin.service;

import RentalWeb.Rentalin.dto.PembayaranDTO;
import RentalWeb.Rentalin.model.Pembayaran;
import RentalWeb.Rentalin.model.Transaksi;
import RentalWeb.Rentalin.repository.PembayaranRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import RentalWeb.Rentalin.repository.TransaksiRepository;
import RentalWeb.Rentalin.util.MapperUtil;

@Service
public class PembayaranService {

    @Autowired
    private PembayaranRepository pembayaranRepository;

    @Autowired
    private TransaksiRepository transaksiRepository;

    public PembayaranDTO save(PembayaranDTO dto) {
        Transaksi transaksi = transaksiRepository.findById(dto.getTransaksiId())
                .orElseThrow(() -> new RuntimeException("Transaksi tidak ditemukan"));

        BigDecimal jumlahBayarSekarang = dto.getJumlah() != null ? BigDecimal.valueOf(dto.getJumlah())
                : BigDecimal.ZERO;

        // Cek apakah ada pembayaran BELUM_LUNAS sebelumnya untuk transaksi ini
        Optional<Pembayaran> existingPembayaranOpt = pembayaranRepository
                .findByTransaksiIdAndStatus(dto.getTransaksiId(), Pembayaran.StatusPembayaran.BELUM_LUNAS);

        Pembayaran pembayaran;

        if (existingPembayaranOpt.isPresent()) {
            // Update pembayaran yang BELUM LUNAS
            pembayaran = existingPembayaranOpt.get();

            BigDecimal totalSebelumnya = pembayaran.getTotalBayar() != null ? pembayaran.getTotalBayar()
                    : BigDecimal.ZERO;
            BigDecimal totalSetelahBayar = totalSebelumnya.add(jumlahBayarSekarang);

            pembayaran.setTotalBayar(totalSetelahBayar);
            pembayaran.setWaktuBayar(LocalDateTime.now());

            // Update metode pembayaran jika ada
            if (dto.getMetodePembayaran() != null) {
                pembayaran.setMetodePembayaran(Pembayaran.MetodePembayaran.valueOf(dto.getMetodePembayaran()));
            }

            // Update status jika sudah lunas
            if (totalSetelahBayar.compareTo(transaksi.getTotal()) >= 0) {
                pembayaran.setStatus(Pembayaran.StatusPembayaran.LUNAS);
            }

        } else {
            // Buat pembayaran baru
            pembayaran = MapperUtil.toEntity(dto, transaksi);

            BigDecimal totalBayarSebelumnya = pembayaranRepository.sumTotalBayarByTransaksiId(transaksi.getId());
            if (totalBayarSebelumnya == null) {
                totalBayarSebelumnya = BigDecimal.ZERO;
            }

            BigDecimal totalBayarAkhir = totalBayarSebelumnya.add(jumlahBayarSekarang);
            pembayaran.setWaktuBayar(LocalDateTime.now());

            if (dto.getMetodePembayaran() != null) {
                pembayaran.setMetodePembayaran(Pembayaran.MetodePembayaran.valueOf(dto.getMetodePembayaran()));
            }

            if (totalBayarAkhir.compareTo(transaksi.getTotal()) >= 0) {
                pembayaran.setStatus(Pembayaran.StatusPembayaran.LUNAS);
            } else {
                pembayaran.setStatus(Pembayaran.StatusPembayaran.BELUM_LUNAS);
            }
        }

        Pembayaran saved = pembayaranRepository.save(pembayaran);
        return MapperUtil.toDTO(saved);
    }

    public PembayaranDTO findById(String id) {
        return pembayaranRepository.findById(id)
                .map(MapperUtil::toDTO)
                .orElse(null);
    }

    public List<PembayaranDTO> findAll() {
        return pembayaranRepository.findAll()
                .stream()
                .map(MapperUtil::toDTO)
                .collect(Collectors.toList());
    }

    public List<PembayaranDTO> findByPelangganId(String pelangganId) {
        return pembayaranRepository.findAllByTransaksiPelangganId(pelangganId)
                .stream()
                .map(MapperUtil::toDTO)
                .collect(Collectors.toList());
    }

    // Method paging
    public Page<PembayaranDTO> findAllPaged(int page, int size) {
        Pageable pageable = PageRequest.of(page, size);
        Page<Pembayaran> pageResult = pembayaranRepository.findAll(pageable);
        return pageResult.map(MapperUtil::toDTO);
    }

    public Page<PembayaranDTO> search(String keyword, int page, int size) {
        Pageable pageable = PageRequest.of(page, size);
        Page<Pembayaran> result = pembayaranRepository.search(keyword, pageable);
        return result.map(MapperUtil::toDTO);
    }

}
