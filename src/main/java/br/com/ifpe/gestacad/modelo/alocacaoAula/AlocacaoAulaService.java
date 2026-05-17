package br.com.ifpe.gestacad.modelo.alocacaoAula;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import br.com.ifpe.gestacad.modelo.horario.Horario;
import br.com.ifpe.gestacad.modelo.horario.HorarioRepository;
import jakarta.transaction.Transactional;

@Service
public class AlocacaoAulaService {
    
    @Autowired
    private AlocacaoAulaRepository repository;

    @Autowired
    private HorarioRepository horarioRepository;

    @Transactional
    public AlocacaoAula save(AlocacaoAula alocacaoAula) {

        alocacaoAula.setHabilitado(Boolean.TRUE);
        return repository.save(alocacaoAula);
    }
    
    public List<AlocacaoAula> listarTodos() {

        return repository.findAll();
    }

    public AlocacaoAula obterPorID(Long id) {
        
        return repository.findById(id).get();
    }

    @Transactional
    public void update(Long id, AlocacaoAula alocacaoAulaAlterado) {

        AlocacaoAula alocacaoAula = repository.findById(id).get();
        alocacaoAula.setTurma(alocacaoAulaAlterado.getTurma());
        alocacaoAula.setDisciplina(alocacaoAulaAlterado.getDisciplina());
        alocacaoAula.setSala(alocacaoAulaAlterado.getSala());
        alocacaoAula.setProfessor(alocacaoAulaAlterado.getProfessor());
        alocacaoAula.setSemestreLetivo(alocacaoAulaAlterado.getSemestreLetivo());

        repository.save(alocacaoAula);
    }
    
    @Transactional
    public void delete(Long id) {

        AlocacaoAula alocacaoAula = repository.findById(id).get();
        alocacaoAula.setHabilitado(Boolean.FALSE);

        repository.save(alocacaoAula);
    }

    @Transactional
    public Horario adicionarHorario(Long alocacaoAulaId, Horario horario) {

        AlocacaoAula alocacaoAula = this.obterPorID(alocacaoAulaId);

        // Primeiro salva o Horario:

        horario.setAlocacaoAula(alocacaoAula);
        horario.setHabilitado(Boolean.TRUE);
        horarioRepository.save(horario);

        // Depois acrescenta o horario criado ao AlocacaoAula e atualiza o AlocacaoAula:

        List<Horario> listaHorario = alocacaoAula.getHorarios();

        if (listaHorario == null) {
            listaHorario = new ArrayList<Horario>();
        }

        listaHorario.add(horario);
        alocacaoAula.setHorarios(listaHorario);
        repository.save(alocacaoAula);

        return horario;
    }

    @Transactional
    public Horario atualizarHorario(Long id, Horario horarioAlterado) {

        Horario horario = horarioRepository.findById(id).get();
        horario.setHorarioInicio(horarioAlterado.getHorarioInicio());
        horario.setHorarioFim(horarioAlterado.getHorarioFim());
        horario.setDiaSemana(horarioAlterado.getDiaSemana());

        if (horarioAlterado.getAlocacaoAula() != null) {
            horario.setAlocacaoAula(horarioAlterado.getAlocacaoAula());
        }

        return horarioRepository.save(horario);
    }

    @Transactional
    public void removerHorario(Long idhorario) {

        Horario horario = horarioRepository.findById(idhorario).get();
        horario.setHabilitado(Boolean.FALSE);
        horarioRepository.save(horario);

        AlocacaoAula alocacaoAula = this.obterPorID(horario.getAlocacaoAula().getId());
        alocacaoAula.getHorarios().remove(horario);
        repository.save(alocacaoAula);
    }

}
