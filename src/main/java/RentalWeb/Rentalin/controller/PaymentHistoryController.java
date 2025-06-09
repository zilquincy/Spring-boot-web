package RentalWeb.Rentalin.controller;

import RentalWeb.Rentalin.dto.PaymentHistoryDTO;
import RentalWeb.Rentalin.service.PaymentHistoryService;
import jakarta.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("/api/payment-histories")
@Validated
public class PaymentHistoryController {

    @Autowired
    private PaymentHistoryService paymentHistoryService;

    @PostMapping
    public ResponseEntity<PaymentHistoryDTO> create(@Valid @RequestBody PaymentHistoryDTO dto) {
        PaymentHistoryDTO saved = paymentHistoryService.save(dto);
        return ResponseEntity.ok(saved);
    }

    @GetMapping("/{id}")
    public ResponseEntity<PaymentHistoryDTO> getById(@PathVariable String id) {
        PaymentHistoryDTO dto = paymentHistoryService.findById(id);
        if (dto == null)
            return ResponseEntity.notFound().build();
        return ResponseEntity.ok(dto);
    }

    @GetMapping
    public ResponseEntity<List<PaymentHistoryDTO>> getAll() {
        List<PaymentHistoryDTO> list = paymentHistoryService.findAll();
        return ResponseEntity.ok(list);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable String id) {
        paymentHistoryService.deleteById(id);
        return ResponseEntity.noContent().build();
    }
}
