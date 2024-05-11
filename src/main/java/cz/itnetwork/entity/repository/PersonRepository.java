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
package cz.itnetwork.entity.repository;

import cz.itnetwork.entity.InvoiceEntity;
import cz.itnetwork.entity.PersonEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
/**
 * Rozhraní repozitáře pro entitu {@link PersonEntity} využívající Spring Data JPA.
 * Toto rozhraní poskytuje standardní CRUD operace a umožňuje provádět specifické vyhledávání osob podle různých kritérií.
 * Rozšiřuje {@link JpaRepository}, což zajišťuje základní databázové operace, jako jsou ukládání, načítání, mazání a aktualizace entit.
 */
public interface PersonRepository extends JpaRepository<PersonEntity, Long> {

    /**
     * Vyhledá všechny osoby na základě hodnoty atributu 'hidden'.
     *
     * @param hidden Booleovská hodnota označující, zda má být osoba skrytá.
     * @return Seznam osob, které odpovídají zadanému kritériu 'hidden'.
     */
    List<PersonEntity> findByHidden(boolean hidden);

    /**
     * Vyhledá osoby podle jejich identifikačního čísla.
     * Tato metoda je užitečná pro vyhledání konkrétní osoby na základě IČO
     *
     * @param identificationNumber IČO osoby
     * @return Seznam osob, které mají zadané IČO
     */

    List<PersonEntity> findByIdentificationNumber (String identificationNumber);

}
