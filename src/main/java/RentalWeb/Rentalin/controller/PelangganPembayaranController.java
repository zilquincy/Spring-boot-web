package RentalWeb.Rentalin.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;

import RentalWeb.Rentalin.dto.PembayaranDTO;
import RentalWeb.Rentalin.model.Transaksi;
import RentalWeb.Rentalin.model.User;
import RentalWeb.Rentalin.repository.TransaksiRepository;
import RentalWeb.Rentalin.service.PembayaranService;
import RentalWeb.Rentalin.service.UserService;

@Controller
@RequestMapping("/pelanggan/pembayaran")
public class PelangganPembayaranController {

    @Autowired
    private PembayaranService pembayaranService;

    @Autowired
    private TransaksiRepository transaksiRepository;

    @Autowired
    private UserService userService;

    @GetMapping
    public String listPembayaran(Model model) {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        String username = auth.getName(); // biasanya username/email login

        User userLogin = userService.findByEmail(username); // asumsi username = email
        if (userLogin == null) {
            // error handling jika user tidak ditemukan
            return "error/403"; // contoh halaman akses ditolak
        }

        String pelangganId = userLogin.getId();

        List<PembayaranDTO> listPembayaran = pembayaranService.findByPelangganId(pelangganId);
        model.addAttribute("listPembayaran", listPembayaran);

        return "pelanggan/pembayaran/list";
    }

    // Tampilkan form pembayaran
    @GetMapping("/create")
    public String formPembayaran(@RequestParam("transaksiId") String transaksiId, Model model) {
        Transaksi transaksi = transaksiRepository.findById(transaksiId)
                .orElseThrow(() -> new RuntimeException("Transaksi tidak ditemukan"));

        PembayaranDTO dto = new PembayaranDTO();
        dto.setTransaksiId(transaksiId);
        dto.setNamaPelanggan(transaksi.getNamaPelanggan());
        dto.setBerat(transaksi.getBeratKg());
        dto.setTanggalTransaksi(transaksi.getTanggalTransaksi());
        dto.setJumlah(transaksi.getTotal() != null ? transaksi.getTotal().doubleValue() : 0.0);

        model.addAttribute("pembayaran", dto);
        return "pelanggan/pembayaran/create"; // pastikan ini sesuai dengan nama file HTML
    }

    // Proses simpan pembayaran
    @PostMapping("/simpan")
    public String simpanPembayaran(@ModelAttribute("pembayaran") PembayaranDTO dto,
            RedirectAttributes redirectAttributes) {
        try {
            pembayaranService.save(dto);
            redirectAttributes.addFlashAttribute("success", "Pembayaran berhasil disimpan.");
            return "redirect:/pelanggan/pembayaran"; // redirect ke halaman daftar transaksi
        } catch (Exception e) {
            redirectAttributes.addFlashAttribute("error", "Gagal menyimpan pembayaran: " + e.getMessage());
            return "redirect:/pelanggan/pembayaran/create?transaksiId=" + dto.getTransaksiId();
        }
    }

    @GetMapping("/{id}/detail")
    public String detailPembayaran(@PathVariable String id, Model model) {
        PembayaranDTO pembayaran = pembayaranService.findById(id);
        if (pembayaran == null) {
            return "redirect:/pelanggan/pembayaran?error=notfound";
        }
        model.addAttribute("pembayaran", pembayaran);
        return "pelanggan/pembayaran/detail";
    }

    @GetMapping("/{id}/struk")
    public String cetakStruk(@PathVariable String id, Model model) {
        PembayaranDTO pembayaran = pembayaranService.findById(id);
        if (pembayaran == null) {
            return "redirect:/pelanggan/pembayaran?error=notfound";
        }
        model.addAttribute("pembayaran", pembayaran);
        return "pelanggan/pembayaran/struk";
    }
}
