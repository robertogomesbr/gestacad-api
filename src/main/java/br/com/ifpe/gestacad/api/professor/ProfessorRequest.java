package br.com.ifpe.gestacad.api.professor;

import java.util.Arrays;

import org.hibernate.validator.constraints.Length;
import org.hibernate.validator.constraints.br.CPF;

import br.com.ifpe.gestacad.modelo.acesso.Perfil;
import br.com.ifpe.gestacad.modelo.acesso.Usuario;
import br.com.ifpe.gestacad.modelo.professor.Professor;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ProfessorRequest {

    @NotBlank(message = "O Nome é de preenchimento obrigatório")
    @Length(max = 50, message = "O Nome deverá ter no máximo {max} caracteres")
    private String nome;

    @NotBlank(message = "O CPF é de preenchimento obrigatório")
    @CPF(message = "CPF inválido")
    private String cpf;

    @NotBlank(message = "A senha é de preenchimento obrigatório")
    private String password;

    @NotBlank(message = "O SIAPE é de preenchimento obrigatório")
    @Size(min = 8, max = 8, message = "O SIAPE deve possuir 8 caracteres")
    private String siape;

    @NotBlank(message = "O email é obrigatório")
    @Email(message = "Email inválido")
    private String email;

    @NotNull(message = "O status ativo é obrigatório")
    private Boolean ativo;

    public Usuario buildUsuario() {
        return Usuario.builder()
                .username(email)
                .password(password)
                .roles(Arrays.asList(new Perfil(Perfil.ROLE_PROFESSOR)))
                .build();
    }

    public Professor build() {

        return Professor.builder()
                .usuario(buildUsuario())
                .nome(nome)
                .cpf(cpf)
                .siape(siape)
                .email(email)
                .ativo(ativo)
                .build();
    }
}