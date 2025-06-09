package RentalWeb.Rentalin.controller;

import RentalWeb.Rentalin.dto.UserDTO;
import RentalWeb.Rentalin.service.UserService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import java.util.List;
import jakarta.validation.Valid;

@RestController
@RequestMapping("/api/users")
@Validated
public class UserController {

    @Autowired
    private UserService userService;

    @PostMapping
    public ResponseEntity<UserDTO> createUser(@Valid @RequestBody UserDTO userDto) {
        UserDTO savedUser = userService.save(userDto);
        return ResponseEntity.ok(savedUser);
    }

    @GetMapping("/{id}")
    public ResponseEntity<UserDTO> getUser(@PathVariable String id) {
        UserDTO user = userService.findById(id);
        if (user == null) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(user);
    }

    @GetMapping
    public ResponseEntity<List<UserDTO>> getAllUsers() {
        List<UserDTO> users = userService.findAll();
        return ResponseEntity.ok(users);
    }

    @PutMapping("/{id}")
    public ResponseEntity<UserDTO> updateUser(
            @PathVariable String id,
            @Valid @RequestBody UserDTO userDto) {
        try {
            UserDTO updatedUser = userService.update(id, userDto);
            return ResponseEntity.ok(updatedUser);
        } catch (RuntimeException e) {
            return ResponseEntity.notFound().build();
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteUser(@PathVariable String id) {
        userService.deleteById(id);
        return ResponseEntity.noContent().build();
    }
}
