/*  _____ _______         _                      _
 * |_   _|__   __|       | |                    | |
 *   | |    | |_ __   ___| |___      _____  _ __| | __  ___ ____
 *   | |    | | '_ \ / _ \ __\ \ /\ / / _ \| '__| |/ / / __|_  /
 *  _| |_   | | | | |  __/ |_ \ V  V / (_) | |  |   < | (__ / /
 * |_____|  |_|_| |_|\___|\__| \_/\_/ \___/|_|  |_|\_(_)___/___|
 *                                _
 *              ___ ___ ___ _____|_|_ _ _____
 *             | . |  _| -_|     | | | |     |  LICENCE
 *             |  _|_| |___|_|_|_|_|___|_|_|_|
 *             |_|
 *
 *   PROGRAMOVÁNÍ  <>  DESIGN  <>  PRÁCE/PODNIKÁNÍ  <>  HW A SW
 *
 * Tento zdrojový kód je součástí výukových seriálů na
 * IT sociální síti WWW.ITNETWORK.CZ
 *
 * Kód spadá pod licenci prémiového obsahu a vznikl díky podpoře
 * našich členů. Je určen pouze pro osobní užití a nesmí být šířen.
 * Více informací na http://www.itnetwork.cz/licence
 */
package cz.itnetwork.dto.mapper;

import cz.itnetwork.dto.InvoiceDTO;
import cz.itnetwork.dto.PersonDTO;
import cz.itnetwork.entity.InvoiceEntity;
import cz.itnetwork.entity.PersonEntity;
import org.mapstruct.Mapper;

/**
 * Mapper rozhraní pro konverzi mezi entitami {@link PersonEntity} a přenosovými objekty {@link PersonDTO}.
 * Toto rozhraní je používáno pro mapování datových struktur mezi databázovými entitami a daty předávanými na frontend.
 */
@Mapper(componentModel = "spring")
public interface PersonMapper {


    /**
     * Převede {@link PersonDTO} na {@link PersonEntity}.
     *
     * @param source DTO reprezentující osobu, která má být převedena na entitu.
     * @return Entita osoby
     */
    PersonEntity toEntity(PersonDTO source);


    /**
     * Převede {@link PersonDTO} na {@link PersonEntity}.
     *
     * @param source Entita osoby, která má být převedena na DTO.
     * @return DTO reprezentující osobu
     */
    PersonDTO toDTO(PersonEntity source);
}
