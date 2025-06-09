package RentalWeb.Rentalin.controller;

import RentalWeb.Rentalin.model.TransaksiLog;
import RentalWeb.Rentalin.service.TransaksiLogService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("/api/transaksi-log")
@RequiredArgsConstructor
public class TransaksiLogController {

    private final TransaksiLogService transaksiLogService;

    @GetMapping("/transaksi/{transaksiId}")
    public List<TransaksiLog> getByTransaksi(@PathVariable String transaksiId) {
        return transaksiLogService.getByTransaksi(transaksiId);
    }
}
