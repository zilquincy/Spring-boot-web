package RentalWeb.Rentalin.controller;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.security.core.annotation.AuthenticationPrincipal;

import RentalWeb.Rentalin.dto.ReservasiDTO;
import RentalWeb.Rentalin.model.Layanan;
import RentalWeb.Rentalin.model.User;
import RentalWeb.Rentalin.repository.LayananRepository;
import RentalWeb.Rentalin.service.ReservasiService;
import RentalWeb.Rentalin.service.UserService;

@Controller
@RequestMapping("/pelanggan/reservasi")
@PreAuthorize("hasRole('PELANGGAN')") // hanya role PELANGGAN bisa akses controller ini
public class ReservasiController {

    @Autowired
    private ReservasiService reservasiService;

    @Autowired
    private UserService userService;

    @Autowired
    private LayananRepository layananRepository;

    // Tampilkan halaman form buat reservasi baru
    @GetMapping("/create")
    public String tampilkanFormReservasi(Model model) {
        model.addAttribute("reservasiDTO", new ReservasiDTO());
        List<Layanan> layananList = layananRepository.findByDeletedAtIsNull();
        model.addAttribute("layananList", layananList);
        return "pelanggan/reservasi/create";
    }

    // Proses submit form reservasi baru
    @PostMapping("/save")
    public String submitReservasi(
            @ModelAttribute ReservasiDTO reservasiDTO,
            @AuthenticationPrincipal org.springframework.security.core.userdetails.User principal,
            Model model) {

        if (principal == null) {
            return "redirect:/login";
        }

        User user = userService.findByEmail(principal.getUsername());
        if (user == null) {
            return "redirect:/login";
        }

        reservasiService.buatReservasi(reservasiDTO, user);
        return "redirect:/pelanggan/reservasi/list"; // setelah submit redirect ke daftar reservasi
    }

    // Tampilkan list reservasi milik pelanggan yang login
    @GetMapping("/list")
    public String listReservasi(
            @AuthenticationPrincipal org.springframework.security.core.userdetails.User principal,
            Model model) {

        if (principal == null) {
            return "redirect:/login";
        }

        User user = userService.findByEmail(principal.getUsername());
        if (user == null) {
            return "redirect:/login";
        }

        List<ReservasiDTO> listUserReservasi = reservasiService.getAllReservasi().stream()
                .filter(r -> r.getUserId().equals(user.getId()))
                .collect(Collectors.toList());

        model.addAttribute("reservasiList", listUserReservasi);
        return "pelanggan/reservasi/list"; // halaman untuk menampilkan list reservasi user
    }

    // Detail reservasi by id, hanya bisa akses reservasi milik user tersebut
    @GetMapping("/detail/{id}")
    public String detailReservasi(
            @PathVariable String id,
            @AuthenticationPrincipal org.springframework.security.core.userdetails.User principal,
            Model model) {

        if (principal == null) {
            return "redirect:/login";
        }

        User user = userService.findByEmail(principal.getUsername());
        if (user == null) {
            return "redirect:/login";
        }

        ReservasiDTO reservasiDTO = reservasiService.getReservasiById(id);
        if (reservasiDTO == null || !reservasiDTO.getUserId().equals(user.getId())) {
            return "error/403"; // akses ditolak jika bukan milik user
        }

        model.addAttribute("reservasi", reservasiDTO);
        return "pelanggan/reservasi/detail"; // halaman detail reservasi
    }
}
