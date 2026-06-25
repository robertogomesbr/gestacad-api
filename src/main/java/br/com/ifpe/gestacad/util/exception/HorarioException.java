package br.com.ifpe.gestacad.util.exception;

public class HorarioException extends RuntimeException {

    public static final String MSG_PROFESSOR_OCUPADO =
        "Professor já possui aula neste horário.";

    public static final String MSG_SALA_OCUPADA =
        "Sala já está ocupada neste horário.";

    public static final String MSG_TURMA_OCUPADA =
        "Turma já possui aula neste horário.";

    public static final String MSG_HORARIO_DUPLICADO =
        "Este horário já foi cadastrado para esta alocação.";

    public HorarioException(String msg) {
        super(msg);
    }
}