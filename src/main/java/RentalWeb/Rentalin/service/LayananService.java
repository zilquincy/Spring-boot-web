package RentalWeb.Rentalin.service;

import RentalWeb.Rentalin.dto.LayananDTO;
import RentalWeb.Rentalin.model.Layanan;
import RentalWeb.Rentalin.repository.LayananRepository;
import RentalWeb.Rentalin.util.MapperUtil;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class LayananService {

    @Autowired
    private LayananRepository layananRepository;

    public LayananDTO save(LayananDTO dto) {
        Layanan layanan = MapperUtil.toEntity(dto);
        Layanan saved = layananRepository.save(layanan);
        return MapperUtil.toDTO(saved);
    }

    public LayananDTO findById(String id) {
        return layananRepository.findById(id)
                .map(MapperUtil::toDTO)
                .orElse(null);
    }

    public List<LayananDTO> findAll() {
        return layananRepository.findAllByDeletedAtIsNull()
                .stream()
                .map(MapperUtil::toDTO)
                .collect(Collectors.toList());
    }

    public void deleteById(String id) {
        layananRepository.findById(id).ifPresent(layanan -> {
            layanan.setDeletedAt(LocalDateTime.now());
            layananRepository.save(layanan);
        });
    }

    public void update(LayananDTO dto) {
        Layanan existing = layananRepository.findById(dto.getId())
                .orElseThrow(() -> new RuntimeException("Layanan tidak ditemukan"));

        existing.setNamaLayanan(dto.getNamaLayanan());
        existing.setHargaPerKg(dto.getHargaPerKg());

        layananRepository.save(existing);
    }

    public Page<LayananDTO> findPaginated(int page, int size) {
        Pageable pageable = PageRequest.of(page, size);
        Page<Layanan> layananPage = layananRepository.findAllByDeletedAtIsNull(pageable);
        return layananPage.map(MapperUtil::toDTO);
    }

    public Page<LayananDTO> searchByName(String keyword, int page, int size) {
        Pageable pageable = PageRequest.of(page, size);
        Page<Layanan> result = layananRepository.findByNamaLayananContainingIgnoreCaseAndDeletedAtIsNull(keyword,
                pageable);
        return result.map(MapperUtil::toDTO);
    }

}
