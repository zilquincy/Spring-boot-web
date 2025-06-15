package RentalWeb.Rentalin.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import RentalWeb.Rentalin.dto.TransaksiDTO;
import RentalWeb.Rentalin.model.Transaksi;
import RentalWeb.Rentalin.model.User;
import RentalWeb.Rentalin.service.LayananService;
import RentalWeb.Rentalin.service.TransaksiService;
import RentalWeb.Rentalin.service.UserService;

import java.util.List;
import java.util.Map;

@Controller
public class AdminDashboardController {

    @Autowired
    private TransaksiService transaksiService;

    @Autowired
    private UserService userService;

    @Autowired
    private LayananService layananService;

    @GetMapping("/admin/dashboard")
    public String showDashboardAdmin(Model model) {
        // Statistik transaksi
        int jumlahTransaksi = transaksiService.countTransaksiHariIni();
        int totalPemasukan = transaksiService.getTotalPemasukanHariIni();

        // Statistik status transaksi
        int menunggu = transaksiService.countByStatus(Transaksi.Status.DITERIMA);
        int proses = transaksiService.countByStatus(Transaksi.Status.DICUCI);
        int selesai = transaksiService.countByStatus(Transaksi.Status.SELESAI);

        // Jumlah pengguna berdasarkan role
        int jumlahAdmin = userService.countByRole(User.Role.ADMIN);
        int jumlahKasir = userService.countByRole(User.Role.KASIR);
        int jumlahPelanggan = userService.countByRole(User.Role.PELANGGAN);

        // Jumlah layanan
        int jumlahLayanan = layananService.countAll();

        // Transaksi terbaru
        List<TransaksiDTO> transaksiTerbaru = transaksiService.getTransaksiTerbaru(5);

        // Data pemasukan per jam
        Map<Integer, Integer> pemasukanPerJam = transaksiService.getPemasukanPerJamHariIni();

        // Tambahkan semua data ke model
        model.addAttribute("jumlahTransaksi", jumlahTransaksi);
        model.addAttribute("totalPemasukan", totalPemasukan);
        model.addAttribute("menunggu", menunggu);
        model.addAttribute("proses", proses);
        model.addAttribute("selesai", selesai);
        model.addAttribute("jumlahAdmin", jumlahAdmin);
        model.addAttribute("jumlahKasir", jumlahKasir);
        model.addAttribute("jumlahPelanggan", jumlahPelanggan);
        model.addAttribute("jumlahLayanan", jumlahLayanan);
        model.addAttribute("transaksiTerbaru", transaksiTerbaru);
        model.addAttribute("pemasukanPerJam", pemasukanPerJam);

        return "admin/dashboard";
    }
}
