package cz.itnetwork.dto.mapper;

import cz.itnetwork.dto.InvoiceDTO;
import cz.itnetwork.dto.PersonDTO;
import cz.itnetwork.entity.InvoiceEntity;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;

/**
 * Mapper rozhraní pro konverzi mezi entitami {@link InvoiceEntity} a přenosovými objekty {@link InvoiceDTO}.
 * Toto rozhraní je používáno pro mapování datových struktur mezi databázovými entitami a daty předávanými na frontend.
 */
@Mapper(componentModel = "spring")
public interface InvoiceMapper {

    /**
     * Převede {@link InvoiceDTO} na {@link InvoiceEntity}.
     *
     * @param source DTO reprezentující fakturu, která má být převedena na entitu.
     * @return Entita faktury
     */
    InvoiceEntity toEntity(InvoiceDTO source);


    /**
     * Převede {@link InvoiceEntity} na {@link InvoiceDTO}
     *
     * @param source Entita faktury, která má být převedena na DTO.
     * @return DTO reprezentující fakturu
     */
    InvoiceDTO toDTO(InvoiceEntity source);

    /**
     * Aktualizuje existující {@link InvoiceEntity} s novými hodnotami z {@link InvoiceDTO}.
     * Některé cílové atributy, jako jsou 'buyer' a 'seller', jsou ignorovány při aktualizaci.
     *
     * @param source DTO s novými hodnotami pro aktualizaci.
     * @param target Existující entita faktury, která má být aktualizována.
     * @return Aktualizovaná entita faktury
     */
    @Mapping(target = "buyer", ignore = true)
    @Mapping(target = "seller", ignore = true)
    InvoiceEntity updateEntity(InvoiceDTO source, @MappingTarget InvoiceEntity target);
}
