package RentalWeb.Rentalin.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class AdminDashboardController {

    @GetMapping("/admin/dashboard")
    public String showDashboardAdmin() {
        // Add attributes to the model as needed
        return "admin/dashboard"; // Return the view name for the admin dashboard
    }
}
