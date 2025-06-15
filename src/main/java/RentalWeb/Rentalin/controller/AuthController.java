package RentalWeb.Rentalin.controller;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import RentalWeb.Rentalin.dto.UserDTO;
import RentalWeb.Rentalin.model.User;
import RentalWeb.Rentalin.repository.UserRepository;
import RentalWeb.Rentalin.service.UserService;
import jakarta.validation.Valid;

@Controller
@RequestMapping("/auth")
public class AuthController {

    @Autowired
    private UserService userService;

    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private UserRepository userRepository;

    @GetMapping("/login")
    public String loginPage() {
        return "auth/login";
    }

    @GetMapping("/register")
    public String registerPage(Model model) {
        model.addAttribute("userDTO", new UserDTO());
        return "auth/register";
    }

    @PostMapping("/register")
    public String register(@ModelAttribute("userDTO") @Valid UserDTO userDTO, BindingResult result, Model model) {
        if (result.hasErrors()) {
            return "auth/register";
        }

        Optional<User> existing = userRepository.findByEmail(userDTO.getEmail());
        if (existing.isPresent()) {
            model.addAttribute("error", "Email sudah digunakan.");
            return "auth/register";
        }
        
        // Set default role sebagai PELANGGAN
        userDTO.setRole(User.Role.PELANGGAN);

        userService.save(userDTO);
        return "redirect:/auth/login?registerSuccess";
    }
}
