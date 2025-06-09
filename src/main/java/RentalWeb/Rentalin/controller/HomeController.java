package RentalWeb.Rentalin.controller;

import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.security.core.Authentication;

@Controller
public class HomeController {

    @GetMapping("/dashboard")
    public String dashboard(Authentication auth) {
        UserDetails userDetails = (UserDetails) auth.getPrincipal();
        return "redirect:/" + userDetails.getAuthorities().stream().findFirst().get().getAuthority().toLowerCase()
                .replace("role_", "") + "/dashboard";
    }
}
