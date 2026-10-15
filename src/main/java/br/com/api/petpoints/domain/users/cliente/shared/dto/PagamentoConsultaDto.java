package br.com.api.petpoints.domain.users.cliente.shared.dto;

import br.com.api.petpoints.shared.enums.StatusPagamentoEnum;
import br.com.api.petpoints.shared.enums.TipoPagamentoEnum;
import br.com.api.petpoints.shared.features.payment.dto.PagamentoDto;
import br.com.api.petpoints.shared.models.ConsultaModel;
import br.com.api.petpoints.shared.models.PagamentoModel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class PagamentoConsultaDto {

    private Long id;
    private Long idConsulta;
    private StatusPagamentoEnum statusPagamento;
    private BigDecimal valor;
    private TipoPagamentoEnum tipoPagamento;
    private PagamentoPixDto pixPagamento;
    private String tipoConsulta;
    private Long idVeterinario;
    private String petConsulta;
    private String veterinarioConsulta;
    private LocalDateTime dataHoraConsulta;
    private boolean atrasado;
    private String motivoIndeferimento;

    public record PagamentoPixDto(
            String ordemId,
            Long pagamentoId,
            String statusPagamentoOnline,
            String urlPagamento,
            String qrCodeBase,
            String qrCodeCopiaECola
    ) {
    }

    public PagamentoConsultaDto(PagamentoModel pagamento, ConsultaModel consulta) {
        this.id = pagamento.getId();
        this.statusPagamento = pagamento.getStatusPagamento();
        this.valor = pagamento.getValorPagamento();
        this.tipoPagamento = pagamento.getTipoPagamento();
        this.tipoConsulta = consulta.getTipoConsulta().getNome();
        this.idVeterinario = consulta.getVeterinario().getId();
        this.petConsulta = consulta.getPet().getNome();
        this.veterinarioConsulta = consulta.getVeterinario().getNome();
        this.dataHoraConsulta = consulta.getDataConsulta();
        this.atrasado = pagamento.getDataLimitePagamento() != null && pagamento.getDataLimitePagamento().isAfter(LocalDateTime.now());
        this.motivoIndeferimento = pagamento.getMotivoIndeferimento();
        this.idConsulta = consulta.getId();
    }

    public PagamentoConsultaDto(PagamentoModel pagamento,
                                PagamentoDto.PagamentoPixResponse apiResponse, ConsultaModel consulta) {

        this.id = pagamento.getId();
        this.statusPagamento = pagamento.getStatusPagamento();
        this.valor = pagamento.getValorPagamento();
        this.tipoPagamento = pagamento.getTipoPagamento();

        this.pixPagamento = new PagamentoPixDto(
                apiResponse.orderId(),
                apiResponse.pagamentoId(),
                apiResponse.status(),
                apiResponse.ticketUrl(),
                apiResponse.qrCodeBase64(),
                apiResponse.qrCode()
        );
        this.idConsulta = consulta.getId();
        this.tipoConsulta = consulta.getTipoConsulta().getNome();
        this.idVeterinario = consulta.getVeterinario().getId();
        this.petConsulta = consulta.getPet().getNome();
        this.veterinarioConsulta = consulta.getVeterinario().getNome();
        this.dataHoraConsulta = consulta.getDataConsulta();
        this.atrasado = pagamento.getDataLimitePagamento() != null && pagamento.getDataLimitePagamento().isAfter(LocalDateTime.now());
        this.motivoIndeferimento = pagamento.getMotivoIndeferimento();
    }
}
