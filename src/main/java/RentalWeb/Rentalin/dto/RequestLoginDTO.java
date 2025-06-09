package RentalWeb.Rentalin.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import lombok.Data;

@Data
public class RequestLoginDTO {
    @Email(message = "Email tidak valid")
    @NotBlank(message = "Email wajib diisi")
    private String email;

    @NotBlank(message = "Password wajib diisi")
    private String password;
}
