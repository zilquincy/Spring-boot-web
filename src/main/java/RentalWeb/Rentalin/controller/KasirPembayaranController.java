package RentalWeb.Rentalin.controller;

import java.util.Arrays;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import RentalWeb.Rentalin.dto.PembayaranDTO;
import RentalWeb.Rentalin.model.Pembayaran;
import RentalWeb.Rentalin.service.PembayaranService;
import RentalWeb.Rentalin.service.TransaksiService;

@Controller
@RequestMapping("/kasir/pembayaran")
public class KasirPembayaranController {
    @Autowired
    private PembayaranService pembayaranService;

    @Autowired
    private TransaksiService transaksiService;

    @GetMapping
    public String listPembayaran(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "") String keyword,
            Model model) {

        int pageSize = 5;

        Page<PembayaranDTO> pageResult;

        if (keyword != null && !keyword.isEmpty()) {
            pageResult = pembayaranService.search(keyword, page, pageSize);
        } else {
            pageResult = pembayaranService.findAllPaged(page, pageSize);
        }

        model.addAttribute("listPembayaran", pageResult.getContent());
        model.addAttribute("currentPage", page);
        model.addAttribute("totalPages", pageResult.getTotalPages());
        model.addAttribute("keyword", keyword);

        return "kasir/pembayaran/list";
    }

    @GetMapping("/create")
    public String showCreateForm(
            @RequestParam(value = "transaksiId", required = false) String transaksiId,
            Model model) {

        PembayaranDTO pembayaranDTO = new PembayaranDTO();

        if (transaksiId != null) {
            pembayaranDTO.setTransaksiId(transaksiId); // Isi otomatis field transaksi
        }

        model.addAttribute("pembayaranDTO", pembayaranDTO);
        model.addAttribute("listTransaksi", transaksiService.findAll());
        model.addAttribute("metodePembayaranOptions", Arrays.asList(Pembayaran.MetodePembayaran.values()));
        model.addAttribute("statusPembayaranOptions", Arrays.asList(Pembayaran.StatusPembayaran.values()));
        return "kasir/pembayaran/create";
    }

    @PostMapping("/save")
    public String savePembayaran(
            @ModelAttribute PembayaranDTO dto, BindingResult result, Model model) {
        if (result.hasErrors()) {
            model.addAttribute("listTransaksi", transaksiService.findAll());
            model.addAttribute("metodePembayaranOptions", Arrays.asList(Pembayaran.MetodePembayaran.values()));
            model.addAttribute("statusPembayaranOptions", Arrays.asList(Pembayaran.StatusPembayaran.values()));
            return "kasir/pembayaran/create";
        }

        PembayaranDTO saved = pembayaranService.save(dto); // Menyimpan dan mendapatkan ID
        return "redirect:/kasir/pembayaran/" + saved.getId() + "/struk";
    }

    @GetMapping("/{id}/detail")
    public String detailPembayaran(@PathVariable String id, Model model) {
        PembayaranDTO pembayaran = pembayaranService.findById(id);
        if (pembayaran == null) {
            return "redirect:/kasir/pembayaran?error=notfound";
        }
        model.addAttribute("pembayaran", pembayaran);
        return "kasir/pembayaran/detail";
    }

    @GetMapping("/{id}/struk")
    public String cetakStruk(@PathVariable String id, Model model) {
        PembayaranDTO pembayaran = pembayaranService.findById(id);
        if (pembayaran == null) {
            return "redirect:/kasir/pembayaran?error=notfound";
        }
        model.addAttribute("pembayaran", pembayaran);
        return "kasir/pembayaran/struk";
    }
}
