package RentalWeb.Rentalin.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.data.domain.Page;

import RentalWeb.Rentalin.dto.LayananDTO;
import RentalWeb.Rentalin.service.LayananService;

@Controller
@RequestMapping("/kasir/layanan")
public class KasirLayananController {
    @Autowired
    private LayananService layananService;

    @GetMapping
    public String listLayanan(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "5") int size,
            @RequestParam(required = false) String keyword,
            Model model) {

        Page<LayananDTO> layananPage;

        if (keyword != null && !keyword.isEmpty()) {
            layananPage = layananService.searchByName(keyword, page, size);
        } else {
            layananPage = layananService.findPaginated(page, size);
        }

        model.addAttribute("layananPage", layananPage);
        model.addAttribute("currentPage", page);
        model.addAttribute("totalPages", layananPage.getTotalPages());
        model.addAttribute("keyword", keyword);

        return "kasir/layanan/list";
    }
}
