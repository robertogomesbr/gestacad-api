package br.com.ifpe.gestacad.util.exception;

public class AutenticacaoException extends RuntimeException {

    public static final String MSG_PROFESSOR_PENDENTE =
        "Seu cadastro ainda está aguardando aprovação da secretaria. Você poderá fazer login assim que for validado.";

    public static final String MSG_ACESSO_ADMIN_NEGADO =
        "Este usuário não possui permissão de administrador. Utilize a tela de login do docente.";

    public static final String MSG_ACESSO_PROFESSOR_NEGADO =
        "Esta conta é de administrador. Utilize a tela de login do administrador.";

    public AutenticacaoException(String msg) {
        super(msg);
    }
}