package br.com.api.petpoints.domain.users.gerente.features.pets.dto;

import br.com.api.petpoints.shared.enums.GeneroEnum;
import br.com.api.petpoints.shared.enums.TipoAnimalEnum;
import br.com.api.petpoints.shared.models.PetModel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class PetsDto {

    private Long id;
    private String nome;
    private TipoAnimalEnum tipo;
    private GeneroEnum genero;
    private LocalDateTime registradoEm;
    private TutorPetsDto tutor;
    private UUID imagem;

    public PetsDto(PetModel pet) {
        this.id = pet.getId();
        this.nome = pet.getNome();
        this.tipo = pet.getTipo();
        this.genero = pet.getGenero();
        this.registradoEm = pet.getRegistradoEm();
        this.tutor = new TutorPetsDto(pet.getTutor());
        this.imagem = pet.getImagem();
    }

    public static List<PetsDto> convert(List<PetModel> pets) {
        return pets.stream().map(PetsDto::new).toList();
    }
}
