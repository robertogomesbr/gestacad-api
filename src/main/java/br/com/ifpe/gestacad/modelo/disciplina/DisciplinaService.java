package br.com.ifpe.gestacad.modelo.disciplina;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import br.com.ifpe.gestacad.modelo.horario.Horario;
import br.com.ifpe.gestacad.modelo.horario.HorarioRepository;
import jakarta.transaction.Transactional;

@Service
public class DisciplinaService {

    @Autowired
    private DisciplinaRepository repository;

    @Autowired
    private HorarioRepository horarioRepository;

    @Transactional
    public Disciplina save(Disciplina disciplina) {

        disciplina.setHabilitado(Boolean.TRUE);
        return repository.save(disciplina);
    }

    public List<Disciplina> listarTodos() {

        return repository.findAll();
    }

    public Disciplina obterPorID(Long id) {

        return repository.findById(id).get();
    }

    @Transactional
    public void update(Long id, Disciplina disciplinaAlterada) {

        Disciplina disciplina = repository.findById(id).get();
        disciplina.setProfessor(disciplinaAlterada.getProfessor());
        disciplina.setTurma(disciplinaAlterada.getTurma());
        disciplina.setNome(disciplinaAlterada.getNome());
        disciplina.setArea(disciplinaAlterada.getArea());
        disciplina.setTurno(disciplinaAlterada.getTurno());
        repository.save(disciplina);
    }

    @Transactional
    public void delete(Long id) {

        Disciplina disciplina = repository.findById(id).get();
        disciplina.setHabilitado(Boolean.FALSE);

        repository.save(disciplina);
    }

    @Transactional
    public Horario adicionarHorario(Long disciplinaId, Horario horario) {

        Disciplina disciplina = this.obterPorID(disciplinaId);

        // Primeiro salva o Horario:

        horario.setDisciplina(disciplina);
        horario.setHabilitado(Boolean.TRUE);
        horarioRepository.save(horario);

        // Depois acrescenta o endereço criado ao disciplina e atualiza o disciplina:

        List<Horario> listaHorario = disciplina.getHorarios();

        if (listaHorario == null) {
            listaHorario = new ArrayList<>();
        }

        listaHorario.add(horario);
        disciplina.setHorarios(listaHorario);
        repository.save(disciplina);

        return horario;
    }

    @Transactional
    public Horario atualizarHorario(Long id, Horario horarioAlterado) {

        Horario horario = horarioRepository.findById(id).get();
        horario.setHorario(horarioAlterado.getHorario());

        return horarioRepository.save(horario);
    }

    @Transactional
    public void removerHorario(Long idHorario) {

        Horario horario = horarioRepository.findById(idHorario).get();
        horario.setHabilitado(Boolean.FALSE);
        horarioRepository.save(horario);

        Disciplina disciplina = this.obterPorID(horario.getDisciplina().getId());
        disciplina.getHorarios().remove(horario);
        repository.save(disciplina);
    }

}