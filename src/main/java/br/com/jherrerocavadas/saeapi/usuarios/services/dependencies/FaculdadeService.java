package br.com.jherrerocavadas.saeapi.usuarios.services.dependencies;

import br.com.jherrerocavadas.saeapi.usuarios.dto.dependenciesDTO.FaculdadeDTO;
import br.com.jherrerocavadas.saeapi.usuarios.entity.dependencies.Faculdade;
import org.springframework.stereotype.Service;

@Service
public class FaculdadeService {


    //Converter DTO para entidade do banco de dados
    public Faculdade faculdadeDtoToFaculdade(FaculdadeDTO faculdadeDTO){
        Faculdade faculdade = new Faculdade();

        faculdade.setId(faculdadeDTO.getId());
        faculdade.setCodFaculdade(faculdadeDTO.getCodFaculdade());
        faculdade.setNomeFaculdade(faculdadeDTO.getNomeFaculdade());
        faculdade.setSiglaFaculdade(faculdadeDTO.getSiglaFaculdade());
        faculdade.setCidade(faculdadeDTO.getCidade());
        faculdade.setEndereco(faculdadeDTO.getEndereco());

        return faculdade;
    }


    // Converter entidade do banco de dados para DTO
    public FaculdadeDTO faculdadeToFaculdadeDto(Faculdade faculdade){
        FaculdadeDTO faculdadeDTO = new FaculdadeDTO();

        faculdadeDTO.setId(faculdade.getId());
        faculdadeDTO.setCodFaculdade(faculdade.getCodFaculdade());
        faculdadeDTO.setNomeFaculdade(faculdade.getNomeFaculdade());
        faculdadeDTO.setSiglaFaculdade(faculdade.getSiglaFaculdade());
        faculdadeDTO.setCidade(faculdade.getCidade());
        faculdadeDTO.setEndereco(faculdade.getEndereco());

        return faculdadeDTO;
    }
}
