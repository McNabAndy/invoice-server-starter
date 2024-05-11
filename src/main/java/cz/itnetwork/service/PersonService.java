package cz.itnetwork.service;

import cz.itnetwork.dto.InvoiceDTO;
import cz.itnetwork.dto.PersonDTO;
import cz.itnetwork.dto.PersonStatisticDTO;

import java.util.List;

public interface PersonService {

    /**
     * Vytvoření nové osoby
     *
     * @param personDTO osoba k vytvoření
     * @return nově vytvořená osoba
     */
    PersonDTO addPerson(PersonDTO personDTO);

    /**
     * Nastaví atribut Hidden na "1" u ID osoby kterou chci vymazat
     * <p>Sets hidden flag to true for the person with the matching [id]</p>
     * <p>In case a person with the passed [id] isn't found, the method <b>silently fails</b></p>
     *
     * @param id ID osoby k vymazání
     */
    void removePerson(long id);

    /**
     * Stažení všech NESKRYTÝCH osob
     *
     * @return Seznam všech neskrytých osob
     */
    List<PersonDTO> getAll();

    /**
     * Získá osobu dle ID
     * @param id ID osoby
     * @return DTO vyhledáváné osoby
     */
    PersonDTO getPersonById(long id);

    /**
     * Aktualizace osoby dle ID osoby
     * @param personId ID osoby k úpravě
     * @param personDTO Upravená data k uložení
     * @return DTO aktualizované osoby
     */
    PersonDTO updatePerson(Long personId, PersonDTO personDTO);

    /**
     * Získá všechny faktury kde figuruje osoba dle IČO jako dodavatel
     * @param identificationNumber IČO osoby
     * @return Seznam faktur
     */
    List<InvoiceDTO> getAllSales(String identificationNumber);

    /**
     * Získá všechny faktury kde figuruje osoba dle IČO jako odběratel
     * @param identificationNumber IČO osoby
     * @return Seznam faktur
     */
    List<InvoiceDTO> getAllBuyers(String identificationNumber);

    /**
     * Získá statistiku osob
     * @return Seznam statistik osob
     */
    List <PersonStatisticDTO> getPersonStatistic();



}
