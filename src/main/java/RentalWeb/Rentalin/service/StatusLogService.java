package RentalWeb.Rentalin.service;

import RentalWeb.Rentalin.model.StatusLog;
import RentalWeb.Rentalin.model.Transaksi;
import RentalWeb.Rentalin.repository.StatusLogRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class StatusLogService {

    private final StatusLogRepository statusLogRepository;

    public List<StatusLog> getByTransaksi(String transaksiId) {
        return statusLogRepository.findByTransaksi_IdOrderByWaktuAsc(transaksiId);
    }

    public StatusLog logStatus(Transaksi transaksi, Transaksi.Status status) {
        StatusLog log = new StatusLog();
        log.setId(UUID.randomUUID().toString());
        log.setTransaksi(transaksi);
        log.setStatus(status);
        log.setWaktu(LocalDateTime.now());
        return statusLogRepository.save(log);
    }
}
