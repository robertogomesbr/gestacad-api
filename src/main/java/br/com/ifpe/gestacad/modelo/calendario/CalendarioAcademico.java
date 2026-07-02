package br.com.ifpe.gestacad.modelo.calendario;

import java.time.LocalDate;

public class CalendarioAcademico {

    public static LocalDate getInicio(String semestre){

        switch (semestre){

            case "2026.1":
                return LocalDate.of(2026,2,3);

            case "2026.2":
                return LocalDate.of(2026,8,4);

            default:
                throw new RuntimeException("Semestre inválido");
        }
    }

    public static LocalDate getFim(String semestre){

        switch (semestre){

            case "2026.1":
                return LocalDate.of(2026,6,3);

            case "2026.2":
                return LocalDate.of(2026,12,18);

            default:
                throw new RuntimeException("Semestre inválido");
        }
    }

}