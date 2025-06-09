package RentalWeb.Rentalin.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.data.domain.Page;

import RentalWeb.Rentalin.dto.LayananDTO;
import RentalWeb.Rentalin.service.LayananService;
import jakarta.validation.Valid;

@Controller
@RequestMapping("/admin/layanan")
public class AdminLayananController {
    @Autowired
    private LayananService layananService;

    @GetMapping
    public String listLayanan(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(required = false) String search,
            Model model) {

        int pageSize = 5;
        Page<LayananDTO> layananPage;

        if (search != null && !search.isEmpty()) {
            layananPage = layananService.searchByName(search, page, pageSize);
            model.addAttribute("search", search); // agar form tetap menampilkan kata kunci pencarian
        } else {
            layananPage = layananService.findPaginated(page, pageSize);
        }

        model.addAttribute("layananPage", layananPage);
        model.addAttribute("currentPage", page);
        model.addAttribute("totalPages", layananPage.getTotalPages());

        return "admin/layanan/list";
    }

    @GetMapping("/create")
    public String showCreateForm(Model model) {
        model.addAttribute("layananDTO", new LayananDTO());
        return "admin/layanan/create";
    }

    @PostMapping("/save")
    public String saveLayanan(@Valid @ModelAttribute LayananDTO dto, BindingResult result, Model model) {
        if (result.hasErrors()) {
            return "admin/layanan/create";
        }
        layananService.save(dto);
        return "redirect:/admin/layanan";
    }

    @GetMapping("/edit/{id}")
    public String showEditForm(@PathVariable("id") String id, Model model) {
        LayananDTO dto = layananService.findById(id);
        if (dto == null) {
            return "redirect:/admin/layanan";
        }
        model.addAttribute("layananDTO", dto);
        return "admin/layanan/edit";
    }

    @PostMapping("/update")
    public String updateLayanan(@ModelAttribute("layananDTO") @Valid LayananDTO dto, BindingResult result,
            Model model) {
        if (result.hasErrors()) {
            return "admin/layanan/edit";
        }

        layananService.update(dto); // pastikan service ada method update
        return "redirect:/admin/layanan";
    }

    @GetMapping("/delete/{id}")
    public String deleteLayanan(@PathVariable("id") String id) {
        layananService.deleteById(id);
        return "redirect:/admin/layanan";
    }

}
