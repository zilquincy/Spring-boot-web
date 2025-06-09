package RentalWeb.Rentalin.controller;

import java.security.Principal;
import java.util.List;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import RentalWeb.Rentalin.dto.TransaksiDTO;
import RentalWeb.Rentalin.model.User;
import RentalWeb.Rentalin.repository.LayananRepository;
import RentalWeb.Rentalin.repository.UserRepository;
import RentalWeb.Rentalin.service.ReservasiService;
import RentalWeb.Rentalin.service.TransaksiService;
import RentalWeb.Rentalin.service.UserService;
import lombok.RequiredArgsConstructor;

@Controller
@RequestMapping("/pelanggan/transaksi")
@RequiredArgsConstructor
public class PelangganTransaksiController {

    private final TransaksiService transaksiService;
    private final UserRepository userRepository;

    @GetMapping
    public String listTransaksiUser(Model model, Principal principal) {
        User user = userRepository.findByEmail(principal.getName())
                .orElseThrow(() -> new RuntimeException("User tidak ditemukan"));

        List<TransaksiDTO> listTransaksi = transaksiService.findByPelangganId(user.getId());
        model.addAttribute("listTransaksi", listTransaksi);
        return "pelanggan/transaksi/list"; // arahkan ke file HTML/Thymeleaf yang kamu buat
    }
}
