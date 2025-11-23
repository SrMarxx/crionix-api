package br.com.smarttech.frigonix.business.services;

import br.com.smarttech.frigonix.business.models.entities.ManutencaoEntity;
import br.com.smarttech.frigonix.business.models.entities.MaquinaEntity;
import br.com.smarttech.frigonix.business.models.entities.UserEntity;
import br.com.smarttech.frigonix.business.models.repositories.IManutencaoJpaRepository;
import br.com.smarttech.frigonix.business.models.repositories.IMaquinaJpaRepository;
import br.com.smarttech.frigonix.business.models.repositories.IUserJpaRepository;
import br.com.smarttech.frigonix.controllers.dtos.ManutencaoRequestRecordDTO;
import br.com.smarttech.frigonix.controllers.dtos.ManutencaoResponseRecordDTO;
import br.com.smarttech.frigonix.controllers.mappers.ManutencaoMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class ManutencaoService {
    private final IManutencaoJpaRepository manutencaoRepository;
    private final IMaquinaJpaRepository maquinaRepository;
    private final IUserJpaRepository userRepository;

    @Autowired
    public ManutencaoService(IManutencaoJpaRepository manutencaoRepository, IMaquinaJpaRepository maquinaRepository, IUserJpaRepository userRepository) {
        this.manutencaoRepository = manutencaoRepository;
        this.maquinaRepository = maquinaRepository;
        this.userRepository = userRepository;
    }

    public ManutencaoResponseRecordDTO createManutencao(ManutencaoRequestRecordDTO manutencaoRequestDTO){
        MaquinaEntity maquina = maquinaRepository.findById(manutencaoRequestDTO.maquina()).orElseThrow(()-> new ResponseStatusException(HttpStatus.NOT_FOUND, "Máquina não encontrada."));
        UserEntity colaborador = userRepository.findById(manutencaoRequestDTO.colaborador()).orElseThrow(()-> new ResponseStatusException(HttpStatus.NOT_FOUND, "Usuário não encontrado."));

        ManutencaoEntity manutencao = new ManutencaoEntity();
        manutencao.setAtivo(true);
        manutencao.setDataCriacao(LocalDateTime.now());
        manutencao.setMaquina(maquina);
        manutencao.setColaborador(colaborador);

        manutencao.setTipoManutencao(manutencaoRequestDTO.tipo());
        manutencao.setDescricao(manutencaoRequestDTO.descricao());
        manutencao.setPrioridade(manutencaoRequestDTO.prioridade());
        manutencao.setDataLimite(manutencaoRequestDTO.dataLimite());

        ManutencaoEntity saved = manutencaoRepository.save(manutencao);
        return ManutencaoMapper.toResponseDTO(saved);
    }

    public ManutencaoResponseRecordDTO getManutencao(Long manutencaoId) {
        ManutencaoEntity manutencao = manutencaoRepository.findById(manutencaoId).orElseThrow(()-> new ResponseStatusException(HttpStatus.NOT_FOUND, "Manutenção não encontrada."));
        return ManutencaoMapper.toResponseDTO(manutencao);
    }

    public List<ManutencaoResponseRecordDTO> getManutencoes() {
        List<ManutencaoEntity> list = manutencaoRepository.findAll();
        return list.stream().map(ManutencaoMapper::toResponseDTO).collect(Collectors.toList());
    }

    public List<ManutencaoResponseRecordDTO> getManutencoesAtivas() {
        List<ManutencaoEntity> list = manutencaoRepository.findByAtivoTrue();
        return list.stream().map(ManutencaoMapper::toResponseDTO).collect(Collectors.toList());
    }

    public ManutencaoResponseRecordDTO updateManutencao(Long manutencaoId, ManutencaoRequestRecordDTO manutencaoRequestDTO) {
        ManutencaoEntity manutencao = manutencaoRepository.findById(manutencaoId).orElseThrow(()-> new ResponseStatusException(HttpStatus.NOT_FOUND, "Manutenção não encontrada."));
        if(manutencaoRequestDTO.colaborador() != null) {
            UserEntity colaborador = userRepository.findById(manutencaoRequestDTO.colaborador()).orElseThrow(()-> new ResponseStatusException(HttpStatus.NOT_FOUND, "Usuário não encontrado."));
            manutencao.setColaborador(colaborador);
        }
        if(manutencaoRequestDTO.maquina() != null) {
            MaquinaEntity maquina = maquinaRepository.findById(manutencaoRequestDTO.maquina()).orElseThrow(()-> new ResponseStatusException(HttpStatus.NOT_FOUND, "Maquina inexistente."));
            manutencao.setMaquina(maquina);
        }
        if(manutencaoRequestDTO.descricao() != null || !manutencaoRequestDTO.descricao().isBlank() || !manutencaoRequestDTO.descricao().isEmpty()) {
            manutencao.setDescricao(manutencaoRequestDTO.descricao());
        }
        if(manutencaoRequestDTO.prioridade() != null) {
            manutencao.setPrioridade(manutencaoRequestDTO.prioridade());
        }
        if(manutencaoRequestDTO.dataLimite() != null) {
            manutencao.setDataLimite(manutencaoRequestDTO.dataLimite());
        }
        if(manutencaoRequestDTO.tipo() != null) {
            manutencao.setTipoManutencao(manutencaoRequestDTO.tipo());
        }

        ManutencaoEntity saved = manutencaoRepository.save(manutencao);
        return ManutencaoMapper.toResponseDTO(saved);
    }

    public void concluirManutencao(Long manutencaoId) {
        ManutencaoEntity manutencao = manutencaoRepository.findById(manutencaoId).orElseThrow(()-> new ResponseStatusException(HttpStatus.NOT_FOUND, "Manutenção não encontrada."));

        manutencao.setDataConclusao(LocalDateTime.now());
        manutencao.setAtivo(false);

        manutencaoRepository.save(manutencao);
    }
}
