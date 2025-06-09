package RentalWeb.Rentalin.controller;

import RentalWeb.Rentalin.dto.PembayaranDTO;
import RentalWeb.Rentalin.service.PembayaranService;
import jakarta.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("/api/pembayarans")
@Validated
public class PembayaranController {

    @Autowired
    private PembayaranService pembayaranService;

    @PostMapping
    public ResponseEntity<PembayaranDTO> create(@Valid @RequestBody PembayaranDTO dto) {
        PembayaranDTO saved = pembayaranService.save(dto);
        return ResponseEntity.ok(saved);
    }

    @GetMapping("/{id}")
    public ResponseEntity<PembayaranDTO> getById(@PathVariable String id) {
        PembayaranDTO dto = pembayaranService.findById(id);
        if (dto == null)
            return ResponseEntity.notFound().build();
        return ResponseEntity.ok(dto);
    }

    @GetMapping
    public ResponseEntity<List<PembayaranDTO>> getAll() {
        List<PembayaranDTO> list = pembayaranService.findAll();
        return ResponseEntity.ok(list);
    }
}
