package pi2025_semestre6.metro_obras.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.beans.factory.annotation.Autowired;
import pi2025_semestre6.metro_obras.exception.CustomAuthenticationEntryPoint;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration
@EnableWebSecurity
public class SecurityConfig {

    @Autowired
    private SecurityFilter securityFilter;

    @Autowired
    private CustomAuthenticationEntryPoint customAuthenticationEntryPoint;

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        return http
                .csrf(csrf -> csrf.disable()) // Desabilita CSRF, pois usaremos tokens (stateless)
                .sessionManagement(session -> session.sessionCreationPolicy(SessionCreationPolicy.STATELESS)) // Define a política de sessão como stateless
                .authorizeHttpRequests(authorize -> authorize
                        .requestMatchers(HttpMethod.POST, "/auth/login").permitAll()
                        .requestMatchers(HttpMethod.POST, "/auth/register").permitAll()
                        // Permite acesso ao H2 Console em ambiente de desenvolvimento
                        .requestMatchers("/h2-console/**").permitAll()

                        //obras
                        // somente um admin pode criar uma obra
                        .requestMatchers(HttpMethod.POST, "/obras").hasRole("ADMIN_GLOBAL")

                        // qualquer usuario autenticado pode fazer GET
                        .requestMatchers(HttpMethod.GET, "/obras", "/obras/**").authenticated()

                        //usuarios
                        // somente pode ver se estiver autenticado
                        .requestMatchers(HttpMethod.GET, "/usuarios").authenticated()

                        // imagens
                        // qualquer usuario autenticado pode criar (adicionar) ou deletar imagens
                        .requestMatchers("/imagens", "/imagens/**").authenticated()

                        //relatorio
                        .requestMatchers("/relatorios", "/relatorios/**").authenticated()

                        .anyRequest().authenticated() // Todas as outras requisições exigem autenticação
                )
                // Adiciona nosso filtro customizado antes do filtro padrão do Spring
                .addFilterBefore(securityFilter, UsernamePasswordAuthenticationFilter.class)

                .exceptionHandling(ex -> ex
                        .authenticationEntryPoint(customAuthenticationEntryPoint)
                )

                .headers(headers -> headers.frameOptions(frameOptions -> frameOptions.disable()))
                .build();
    }

    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration authenticationConfiguration) throws Exception {
        return authenticationConfiguration.getAuthenticationManager();
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        // Bean para criptografar as senhas
        return new BCryptPasswordEncoder();
    }
}