package RentalWeb.Rentalin.controller;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import RentalWeb.Rentalin.dto.ReservasiDTO;
import RentalWeb.Rentalin.dto.TransaksiDTO;
import RentalWeb.Rentalin.model.Layanan;
import RentalWeb.Rentalin.model.User;
import RentalWeb.Rentalin.service.ReservasiService;
import RentalWeb.Rentalin.service.TransaksiService;
import RentalWeb.Rentalin.service.UserService;
import RentalWeb.Rentalin.repository.LayananRepository;
import RentalWeb.Rentalin.repository.UserRepository;
import RentalWeb.Rentalin.model.User.Role;
import RentalWeb.Rentalin.model.Transaksi.Status;

import lombok.RequiredArgsConstructor;

@Controller
@RequestMapping("/kasir/transaksi")
@RequiredArgsConstructor
public class KasirTransaksiController {
    private final TransaksiService transaksiService;
    private final LayananRepository layananRepository;
    private final UserRepository userRepository;
    private final ReservasiService reservasiService;
    private final UserService userService;

    // Menampilkan semua transaksi
    @GetMapping
    public String listTransaksi(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "5") int size,
            @RequestParam(name = "keyword", required = false) String keyword,
            Model model) {

        Page<TransaksiDTO> transaksiPage;

        if (keyword != null && !keyword.isEmpty()) {
            transaksiPage = transaksiService.searchTransaksi(keyword, page, size);
            model.addAttribute("keyword", keyword);
        } else {
            transaksiPage = transaksiService.findPaginated(page, size);
        }

        model.addAttribute("transaksiPage", transaksiPage);
        model.addAttribute("currentPage", page);
        model.addAttribute("totalPages", transaksiPage.getTotalPages());
        model.addAttribute("totalItems", transaksiPage.getTotalElements());

        return "kasir/transaksi/list";
    }

    // Menampilkan detail transaksi tertentu
    @GetMapping("/{id}")
    public String detailTransaksi(@PathVariable String id, Model model) {
        TransaksiDTO transaksi = transaksiService.findById(id);
        if (transaksi == null) {
            return "redirect:/kasir/transaksi?error=notfound";
        }
        model.addAttribute("transaksi", transaksi);
        return "kasir/transaksi/detail";
    }

    // Form tambah transaksi
    @GetMapping("/create")
    public String formTambahTransaksi(Model model) {
        model.addAttribute("transaksiDTO", new TransaksiDTO());

        List<Layanan> layananList = layananRepository.findAll();
        List<User> kasirList = userRepository.findAllByRole(Role.KASIR);

        model.addAttribute("layananList", layananList);
        model.addAttribute("kasirList", kasirList);

        return "kasir/transaksi/create";
    }

    // Proses simpan transaksi
    @PostMapping("/save")
    public String simpanTransaksi(@ModelAttribute("transaksiDTO") TransaksiDTO dto) {
        TransaksiDTO saved = transaksiService.save(dto);
        return "redirect:/kasir/pembayaran/create?transaksiId=" + saved.getId();
    }

    @PostMapping("/save-from-reservasi")
    public String simpanTransaksiReservasi(@ModelAttribute("transaksiDTO") TransaksiDTO dto) {
        transaksiService.save(dto);
        return "redirect:/kasir/transaksi";
    }

    // Form edit transaksi
    @GetMapping("/edit/{id}")
    public String formEditTransaksi(@PathVariable String id, Model model) {
        TransaksiDTO transaksi = transaksiService.findById(id);
        if (transaksi == null) {
            return "redirect:/kasir/transaksi?error=notfound";
        }
        model.addAttribute("transaksiDTO", transaksi);

        List<Layanan> layananList = layananRepository.findAll();
        List<User> kasirList = userRepository.findAllByRole(Role.KASIR);
        List<User> pelangganList = userRepository.findAllByRole(Role.PELANGGAN);
        model.addAttribute("pelangganList", pelangganList);

        model.addAttribute("layananList", layananList);
        model.addAttribute("kasirList", kasirList);
        model.addAttribute("statusList", Status.values());

        return "kasir/transaksi/edit";
    }

    // Proses update transaksi
    @PostMapping("/update")
    public String updateTransaksi(@ModelAttribute("transaksiDTO") TransaksiDTO dto) {
        transaksiService.update(dto);
        return "redirect:/kasir/transaksi";
    }

    // Hapus transaksi (soft delete)
    @GetMapping("/delete/{id}")
    public String delete(@PathVariable String id) {
        transaksiService.deleteById(id);
        return "redirect:/kasir/transaksi";
    }

    @GetMapping("/from-reservasi/{reservasiId}")
    public String buatTransaksiDariReservasi(@PathVariable String reservasiId, Model model) {
        ReservasiDTO reservasiDTO = reservasiService.getReservasiById(reservasiId);
        if (reservasiDTO == null) {
            return "redirect:/admin/reservasi/list";
        }

        TransaksiDTO transaksiDTO = new TransaksiDTO();
        transaksiDTO.setNamaPelanggan(reservasiDTO.getNama());
        transaksiDTO.setPelangganId(reservasiDTO.getUserId());
        transaksiDTO.setReservasiId(reservasiDTO.getId());
        transaksiDTO.setLayananId(reservasiDTO.getLayananId()); // <== Set otomatis dari reservasi

        model.addAttribute("transaksiDTO", transaksiDTO);
        model.addAttribute("layananList", layananRepository.findByDeletedAtIsNull());
        model.addAttribute("kasirList", userService.getAllKasir());
        return "kasir/transaksi/create-from-reservasi";
    }

}
