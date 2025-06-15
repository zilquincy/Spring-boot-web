package RentalWeb.Rentalin.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Data;
import RentalWeb.Rentalin.model.User;

@Data
public class UserDTO {

    private String id;

    @Email(message = "Email tidak valid")
    @NotBlank(message = "Email wajib diisi")
    private String email;

    @NotBlank(message = "Username wajib diisi")
    private String username;

    // @NotBlank(message = "Password wajib diisi")
    @Size(min = 6, message = "Password minimal 6 karakter")
    private String password;

    @NotBlank(message = "Full name tidak boleh kosong")
    private String fullName;

    private User.Role role;

}
