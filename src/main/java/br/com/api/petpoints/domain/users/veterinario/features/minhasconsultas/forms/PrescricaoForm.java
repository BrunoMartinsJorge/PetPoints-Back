package br.com.api.petpoints.domain.users.veterinario.features.minhasconsultas.forms;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class PrescricaoForm {

    private Long idConsulta;
    private String observacoes;
    private String diagnostico;

    /** Produtos receitados ao pet, com dose, via, intervalo e duração. */
    private List<ItemPrescricaoForm> itens = new ArrayList<>();

    private LocalDateTime retorno;
}
