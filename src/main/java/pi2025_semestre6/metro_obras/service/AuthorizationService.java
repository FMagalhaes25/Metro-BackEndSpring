package pi2025_semestre6.metro_obras.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import pi2025_semestre6.metro_obras.repository.UsuarioRepository;

@Service
public class AuthorizationService implements UserDetailsService {

    @Autowired
    private UsuarioRepository repository;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        UserDetails user = repository.findByEmail(username);
        if (user == null) {
            throw new UsernameNotFoundException("Usuário não encontrado!");
        }
        return user;
    }
}