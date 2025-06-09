package RentalWeb.Rentalin.controller;

import RentalWeb.Rentalin.dto.LayananDTO;
import RentalWeb.Rentalin.service.LayananService;
import jakarta.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("/api/layanans")
@Validated
public class LayananController {

    @Autowired
    private LayananService layananService;

    @PostMapping
    public ResponseEntity<LayananDTO> create(@Valid @RequestBody LayananDTO dto) {
        LayananDTO saved = layananService.save(dto);
        return ResponseEntity.ok(saved);
    }

    @GetMapping("/{id}")
    public ResponseEntity<LayananDTO> getById(@PathVariable String id) {
        LayananDTO dto = layananService.findById(id);
        if (dto == null)
            return ResponseEntity.notFound().build();
        return ResponseEntity.ok(dto);
    }

    @GetMapping
    public ResponseEntity<List<LayananDTO>> getAll() {
        List<LayananDTO> list = layananService.findAll();
        return ResponseEntity.ok(list);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable String id) {
        layananService.deleteById(id);
        return ResponseEntity.noContent().build();
    }
}
