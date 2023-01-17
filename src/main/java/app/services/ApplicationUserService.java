package app.services;

import app.domain.ApplicationUser;
import app.repositories.ApplicationUserRepository;
import lombok.AllArgsConstructor;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Collections;

@Service
@AllArgsConstructor
public class ApplicationUserService implements UserDetailsService {
    private final ApplicationUserRepository applicationUserRepository;

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {

        ApplicationUser user = applicationUserRepository.findByEmailAddress(email)
                .orElseThrow(() -> new UsernameNotFoundException("Email Address not found"));

        return new User(user.getEmailAddress(), user.getPasswordHash(), Collections.emptyList());
    }
}
