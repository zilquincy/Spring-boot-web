package RentalWeb.Rentalin.controller;

import jakarta.servlet.http.HttpServletRequest;
import org.springframework.boot.web.servlet.error.ErrorController;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class CustomErrorController implements ErrorController {

    @RequestMapping("/error")
    public String handleError(HttpServletRequest request) {
        Object status = request.getAttribute("jakarta.servlet.error.status_code");

        if (status != null) {
            int statusCode = Integer.parseInt(status.toString());

            if (statusCode == 404) {
                return "error/404"; // file di templates/error/404.html
            } else if (statusCode == 403) {
                return "error/403";
            }
        }

        return "error/error"; // fallback default
    }
}
