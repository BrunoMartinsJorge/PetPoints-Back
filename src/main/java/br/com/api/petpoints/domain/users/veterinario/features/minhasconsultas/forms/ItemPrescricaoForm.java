package br.com.api.petpoints.domain.users.veterinario.features.minhasconsultas.forms;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * Produto receitado pelo veterinário, junto do modo de utilização informado
 * no momento da geração da prescrição.
 */
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class ItemPrescricaoForm {

    /** Identificador do produto (medicamento, vacina, etc.) receitado. */
    private Long id;
    private String dose;
    private String via;
    private String intervalo;
    private String duracao;
}
