package RentalWeb.Rentalin.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.data.domain.Page;

import RentalWeb.Rentalin.dto.ReservasiDTO;
import RentalWeb.Rentalin.model.Layanan;
import RentalWeb.Rentalin.model.User;
import RentalWeb.Rentalin.repository.LayananRepository;
import RentalWeb.Rentalin.service.ReservasiService;
import RentalWeb.Rentalin.service.UserService;

@Controller
@RequestMapping("/admin/reservasi")
@PreAuthorize("hasRole('ADMIN')")
public class AdminReservasiController {

    @Autowired
    private ReservasiService reservasiService;

    @Autowired
    private UserService userService;

    @Autowired
    private LayananRepository layananRepository;

    @GetMapping("/list")
    public String listAllReservasi(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(required = false) String keyword,
            Model model) {

        int pageSize = 3;
        Page<ReservasiDTO> reservasiPage;

        if (keyword != null && !keyword.isEmpty()) {
            reservasiPage = reservasiService.searchReservasi(keyword, PageRequest.of(page, pageSize));
        } else {
            reservasiPage = reservasiService.getReservasiPage(PageRequest.of(page, pageSize));
        }

        model.addAttribute("reservasiPage", reservasiPage);
        model.addAttribute("currentPage", page);
        model.addAttribute("totalPages", reservasiPage.getTotalPages());
        model.addAttribute("keyword", keyword); // supaya form pencarian tetap tampil

        return "admin/reservasi/list";
    }

    // Detail reservasi by id
    @GetMapping("/detail/{id}")
    public String detailReservasi(@PathVariable String id, Model model) {
        ReservasiDTO reservasiDTO = reservasiService.getReservasiById(id);
        if (reservasiDTO == null) {
            return "error/404";
        }
        model.addAttribute("reservasi", reservasiDTO);
        return "admin/reservasi/detail";
    }

    // Tampilkan halaman form buat reservasi baru
    @GetMapping("/create")
    public String tampilkanFormReservasi(Model model) {
        model.addAttribute("reservasiDTO", new ReservasiDTO());
        List<Layanan> layananList = layananRepository.findByDeletedAtIsNull();
        model.addAttribute("layananList", layananList);

        List<User> pelangganList = userService.getAllPelanggan(); // method ini harus ada di userService
        model.addAttribute("pelangganList", pelangganList);

        return "admin/reservasi/create";
    }

    // Proses submit form reservasi baru
    @PostMapping("/save")
    public String submitReservasi(
            @ModelAttribute ReservasiDTO reservasiDTO,
            Model model) {

        User user = userService.getById(reservasiDTO.getUserId()); // ambil user dari id yg dipilih di form

        if (user == null) {
            model.addAttribute("error", "Pelanggan tidak ditemukan");
            return "admin/reservasi/create";
        }

        reservasiService.buatReservasi(reservasiDTO, user);
        return "redirect:/admin/reservasi/list";
    }

    // Update status reservasi
    @PostMapping("/{id}/status")
    public String updateStatusReservasiAdmin(
            @PathVariable String id,
            @RequestParam String status) {
        reservasiService.updateStatus(id, status);
        return "redirect:/admin/reservasi/list"; // arahkan kembali ke list reservasi
    }

}
