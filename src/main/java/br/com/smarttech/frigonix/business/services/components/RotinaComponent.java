package br.com.smarttech.frigonix.business.services.components;

import br.com.smarttech.frigonix.business.models.entities.RegistroEntity;
import br.com.smarttech.frigonix.business.models.repositories.IManutencaoJpaRepository;
import br.com.smarttech.frigonix.business.models.repositories.IMaquinaJpaRepository;
import br.com.smarttech.frigonix.business.models.repositories.IRegistroJpaRepository;
import br.com.smarttech.frigonix.business.models.repositories.IUserJpaRepository;
import br.com.smarttech.frigonix.infrastructures.enums.TipoConclusao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

@Component
public class RotinaComponent {
    private final IMaquinaJpaRepository maquinaRepository;
    private final IManutencaoJpaRepository manutencaoRepository;
    private final IUserJpaRepository userRepository;
    private final IRegistroJpaRepository registroRepository;

    @Autowired
    public RotinaComponent(IMaquinaJpaRepository maquinaRepository, IManutencaoJpaRepository manutencaoRepository, IUserJpaRepository userRepository, IRegistroJpaRepository registroRepository) {
        this.maquinaRepository = maquinaRepository;
        this.manutencaoRepository = manutencaoRepository;
        this.userRepository = userRepository;
        this.registroRepository = registroRepository;
    }

    @Scheduled(cron = "0 0 0 * * *")
    public void runOnLastDayOfMonth() {
        LocalDate today = LocalDate.now();
        int lastDay = today.lengthOfMonth();

        if (today.getDayOfMonth() == lastDay) {

            RegistroEntity entity = new RegistroEntity();
            entity.setDataRegistro(LocalDateTime.now());
            entity.setQuantidadeManutencoesAbertas(manutencaoRepository.countByAtivoTrue());

            LocalDateTime startOfMonth = LocalDate.now().withDayOfMonth(1).atStartOfDay();
            LocalDateTime endOfMonth = LocalDate.now().withDayOfMonth(LocalDate.now().lengthOfMonth()).atTime(23, 59, 59);

            List<Object[]> results = manutencaoRepository.countManutencoesByConclusaoInPeriod(startOfMonth, endOfMonth);

            for(Object[] row : results) {
                TipoConclusao conclusao = (TipoConclusao) row[0];
                Long quantidade = (Long) row[1];
                switch (conclusao) {
                    case SUCESSO:
                        entity.setQuantidadeSucesso(quantidade);
                        break;
                    case FALHA:
                        entity.setQuantidadeFalha(quantidade);
                        break;
                    case ADIAMENTO:
                        entity.setQuantidadeAdiamento(quantidade);
                        break;
                }
            }

            entity.setQuantidadeManutencoesFechadas(entity.getQuantidadeSucesso() + entity.getQuantidadeFalha() + entity.getQuantidadeAdiamento());
            entity.setQuantidadePessoas(userRepository.countByActiveTrue());
            entity.setQuantidadeMaquinas(maquinaRepository.countByAtivoTrue());

            registroRepository.save(entity);
        }
    }
}
