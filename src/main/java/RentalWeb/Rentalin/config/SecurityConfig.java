package RentalWeb.Rentalin.config;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

import RentalWeb.Rentalin.model.User;
import RentalWeb.Rentalin.repository.UserRepository;

@Configuration
@EnableWebSecurity
public class SecurityConfig {

        @Bean
        public PasswordEncoder passwordEncoder() {
                return new BCryptPasswordEncoder();
        }

        @Autowired
        private UserRepository userRepository;

        @Bean
        public UserDetailsService userDetailsService() {
                return email -> {
                        User user = userRepository.findByEmail(email)
                                        .orElseThrow(() -> new UsernameNotFoundException("User not found"));
                        return new org.springframework.security.core.userdetails.User(
                                        user.getEmail(),
                                        user.getPassword(),
                                        List.of(new SimpleGrantedAuthority("ROLE_" + user.getRole().name())));
                };
        }

        @Bean
        public AuthenticationManager authenticationManager(HttpSecurity http, PasswordEncoder encoder,
                        UserDetailsService uds) throws Exception {
                return http.getSharedObject(AuthenticationManagerBuilder.class)
                                .userDetailsService(uds)
                                .passwordEncoder(encoder)
                                .and().build();
        }

        @Bean
        public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
                http
                                .csrf(csrf -> csrf.disable())
                                .authorizeHttpRequests(auth -> auth
                                                .requestMatchers("/auth/**", "/css/**", "/js/**", "/img/**",
                                                                "/style/**", "/")
                                                .permitAll()
                                                // Khusus ADMIN
                                                .requestMatchers("/admin/**").hasRole("ADMIN")

                                                // Khusus KASIR
                                                .requestMatchers("/kasir/**").hasRole("KASIR")

                                                // Khusus PELANGGAN
                                                .requestMatchers("/pelanggan/**").hasRole("PELANGGAN")
                                                .anyRequest().authenticated())
                                .formLogin(form -> form
                                                .loginPage("/auth/login")
                                                .loginProcessingUrl("/login")
                                                .defaultSuccessUrl("/dashboard", true)
                                                .failureUrl("/auth/login?error=true")
                                                .permitAll())
                                .logout(logout -> logout
                                                .logoutUrl("/logout")
                                                .logoutSuccessUrl("/auth/login?logout=true")
                                                .invalidateHttpSession(true)
                                                .deleteCookies("JSESSIONID")
                                                .permitAll())
                                .exceptionHandling(exception -> exception
                                                .accessDeniedPage("/error/403") // ⬅️ Tambahkan baris ini
                                );
                return http.build();
        }
}
