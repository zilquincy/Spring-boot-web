package RentalWeb.Rentalin.controller;

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

import RentalWeb.Rentalin.dto.UserDTO;
import RentalWeb.Rentalin.service.UserService;
import jakarta.validation.Valid;

@Controller
@RequestMapping("/admin/users")
public class AdminUserViewController {

    @Autowired
    private UserService userService;

    @GetMapping
    public String list(@RequestParam(defaultValue = "0") int page,
            @RequestParam(required = false) String keyword,
            Model model) {
        int pageSize = 5;
        Page<UserDTO> userPage;

        if (keyword != null && !keyword.isBlank()) {
            userPage = userService.searchUsers(keyword, page, pageSize);
        } else {
            userPage = userService.findPaginated(page, pageSize);
        }

        model.addAttribute("users", userPage.getContent());
        model.addAttribute("currentPage", page);
        model.addAttribute("totalPages", userPage.getTotalPages());
        model.addAttribute("keyword", keyword);

        return "admin/users/list";
    }

    @GetMapping("/create")
    public String showCreateForm(Model model) {
        model.addAttribute("user", new UserDTO());
        return "admin/users/create";
    }

    @PostMapping("/save")
    public String saveUser(@ModelAttribute("user") @Valid UserDTO userDTO,
            BindingResult result,
            Model model) {
        if (result.hasErrors()) {
            return "admin/users/form";
        }
        userService.save(userDTO);
        return "redirect:/admin/users";
    }

    @GetMapping("/edit/{id}")
    public String editForm(@PathVariable String id, Model model) {
        UserDTO userDTO = userService.findById(id);
        model.addAttribute("user", userDTO);
        return "admin/users/edit";
    }

    @GetMapping("/delete/{id}")
    public String delete(@PathVariable String id) {
        userService.deleteById(id);
        return "redirect:/admin/users";
    }
}
