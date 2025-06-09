package RentalWeb.Rentalin.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import RentalWeb.Rentalin.dto.LayananDTO;
import RentalWeb.Rentalin.service.LayananService;

@Controller
@RequestMapping("/pelanggan/layanan")
public class PelangganLayananController {
    @Autowired
    private LayananService layananService;

    @GetMapping
    public String listLayanan(Model model) {
        List<LayananDTO> listLayanan = layananService.findAll();
        model.addAttribute("listLayanan", listLayanan);
        return "pelanggan/layanan/list";
    }
}
