package br.com.ifpe.gestacad.config;

import java.util.Arrays;
import java.util.List;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;

import br.com.ifpe.gestacad.modelo.acesso.Perfil;
import br.com.ifpe.gestacad.modelo.segurança.JwtAuthenticationFilter;

@Configuration
@EnableWebSecurity
public class SecurityConfiguration {

        private final AuthenticationProvider authenticationProvider;
        private final JwtAuthenticationFilter jwtAuthenticationFilter;

        public SecurityConfiguration(JwtAuthenticationFilter jwtAuthenticationFilter,
                        AuthenticationProvider authenticationProvider) {
                this.authenticationProvider = authenticationProvider;
                this.jwtAuthenticationFilter = jwtAuthenticationFilter;
        }

        @Bean
        public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {

                http
                                .cors(cors -> cors.configurationSource(corsConfigurationSource()))
                                .csrf(c -> c.disable())
                                .authorizeHttpRequests(authorize -> authorize

                                                // EndPoints Públicos
                                                .requestMatchers(HttpMethod.POST, "/api/professor").permitAll()
                                                .requestMatchers(HttpMethod.POST, "/api/auth").permitAll()

                                                // Curso, Turma, Disciplina, Sala — apenas Admin
                                                .requestMatchers(HttpMethod.POST, "/api/curso", "/api/turma",
                                                                "/api/disciplina", "/api/sala")
                                                .hasAnyAuthority(Perfil.ROLE_ADMIN)
                                                .requestMatchers(HttpMethod.PUT, "/api/curso/**", "/api/turma/**",
                                                                "/api/disciplina/**", "/api/sala/**")
                                                .hasAnyAuthority(Perfil.ROLE_ADMIN)
                                                .requestMatchers(HttpMethod.DELETE, "/api/curso/**", "/api/turma/**",
                                                                "/api/disciplina/**", "/api/sala/**")
                                                .hasAnyAuthority(Perfil.ROLE_ADMIN)
                                                .requestMatchers(HttpMethod.GET, "/api/curso/**", "/api/turma/**",
                                                                "/api/disciplina/**", "/api/sala/**")
                                                .hasAnyAuthority(Perfil.ROLE_ADMIN, Perfil.ROLE_PROFESSOR)

                                                // Alocação de Aula — Admin gerencia, Professor consulta
                                                .requestMatchers(HttpMethod.POST, "/api/alocacao-aula")
                                                .hasAnyAuthority(Perfil.ROLE_ADMIN)
                                                .requestMatchers(HttpMethod.PUT, "/api/alocacao-aula/**")
                                                .hasAnyAuthority(Perfil.ROLE_ADMIN)
                                                .requestMatchers(HttpMethod.DELETE, "/api/alocacao-aula/**")
                                                .hasAnyAuthority(Perfil.ROLE_ADMIN)
                                                .requestMatchers(HttpMethod.GET, "/api/alocacao-aula/**")
                                                .hasAnyAuthority(Perfil.ROLE_ADMIN, Perfil.ROLE_PROFESSOR)

                                                // Reposição de Aula — Professor e Admin
                                                .requestMatchers(HttpMethod.POST, "/api/reposicao")
                                                .hasAnyAuthority(Perfil.ROLE_ADMIN, Perfil.ROLE_PROFESSOR)
                                                .requestMatchers(HttpMethod.GET, "/api/reposicao/**")
                                                .hasAnyAuthority(Perfil.ROLE_ADMIN, Perfil.ROLE_PROFESSOR)
                                                .requestMatchers(HttpMethod.PUT, "/api/reposicao/**")
                                                .hasAnyAuthority(Perfil.ROLE_ADMIN, Perfil.ROLE_PROFESSOR)
                                                .requestMatchers(HttpMethod.DELETE, "/api/reposicao/**")
                                                .hasAnyAuthority(Perfil.ROLE_ADMIN, Perfil.ROLE_PROFESSOR)

                                                .requestMatchers(HttpMethod.GET, "/api-docs/*").permitAll()
                                                .requestMatchers(HttpMethod.GET, "/swagger-ui/*").permitAll()

                                                .anyRequest().authenticated()

                                )
                                .sessionManagement((session) -> session
                                                .sessionCreationPolicy(SessionCreationPolicy.STATELESS))
                                .authenticationProvider(authenticationProvider)
                                .addFilterBefore(jwtAuthenticationFilter, UsernamePasswordAuthenticationFilter.class);

                return http.build();
        }

        public CorsConfigurationSource corsConfigurationSource() {

                CorsConfiguration configuration = new CorsConfiguration();

                configuration.setAllowedOrigins(Arrays.asList("http://localhost:3000"));
                configuration.setAllowedMethods(Arrays.asList("GET", "POST", "PUT", "DELETE"));
                configuration.setAllowedHeaders(List.of("Authorization", "Content-Type"));
                configuration.setAllowCredentials(true);

                UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
                source.registerCorsConfiguration("/**", configuration);
                return source;
        }
}
