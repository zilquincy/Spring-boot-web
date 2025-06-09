package RentalWeb.Rentalin.controller;

import RentalWeb.Rentalin.model.StatusLog;
import RentalWeb.Rentalin.service.StatusLogService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("/api/status-log")
@RequiredArgsConstructor
public class StatusLogController {

    private final StatusLogService statusLogService;

    @GetMapping("/transaksi/{transaksiId}")
    public List<StatusLog> getByTransaksi(@PathVariable String transaksiId) {
        return statusLogService.getByTransaksi(transaksiId);
    }
}
