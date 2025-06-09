package RentalWeb.Rentalin.controller;

import java.math.BigDecimal;
import java.security.Principal;
import java.util.List;
import java.util.Objects;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import RentalWeb.Rentalin.dto.PembayaranDTO;
import RentalWeb.Rentalin.dto.TransaksiDTO;
import RentalWeb.Rentalin.model.User;
import RentalWeb.Rentalin.repository.UserRepository;
import RentalWeb.Rentalin.service.PembayaranService;
import RentalWeb.Rentalin.service.TransaksiService;

@Controller
public class PelangganDashboardController {
    @Autowired
    private TransaksiService transaksiService;

    @Autowired
    private PembayaranService pembayaranService;

    @Autowired
    private UserRepository userRepository;

    // Misal userId dari session / principal
    @GetMapping("/pelanggan/dashboard")
    public String showDashboardUser(Model model, Principal principal) {
        // Ambil user login dari username (email)
        String username = principal.getName();

        User user = userRepository.findByEmail(username)
                .orElseThrow(() -> new RuntimeException("User tidak ditemukan"));

        // Ambil transaksi dan pembayaran user
        List<TransaksiDTO> transaksiList = transaksiService.findByPelangganId(user.getId());
        List<PembayaranDTO> pembayaranList = pembayaranService.findByPelangganId(user.getId());

        BigDecimal totalTransaksi = transaksiList.stream()
                .map(TransaksiDTO::getTotal)
                .filter(Objects::nonNull)
                .reduce(BigDecimal.ZERO, BigDecimal::add);

        BigDecimal totalPembayaran = pembayaranList.stream()
                .map(p -> BigDecimal.valueOf(p.getJumlah()))
                .filter(Objects::nonNull)
                .reduce(BigDecimal.ZERO, BigDecimal::add);

        // Masukkan data ke model untuk dikirim ke view
        model.addAttribute("user", user);
        model.addAttribute("totalTransaksi", totalTransaksi);
        model.addAttribute("totalPembayaran", totalPembayaran);
        model.addAttribute("transaksiList", transaksiList);
        model.addAttribute("pembayaranList", pembayaranList);

        // Return nama template Thymeleaf
        return "pelanggan/dashboard"; // ganti dengan nama template yang sesuai
    }
}
