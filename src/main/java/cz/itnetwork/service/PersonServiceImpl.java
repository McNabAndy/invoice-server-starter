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
package cz.itnetwork.service;

import cz.itnetwork.dto.InvoiceDTO;
import cz.itnetwork.dto.PersonDTO;
import cz.itnetwork.dto.PersonStatisticDTO;
import cz.itnetwork.dto.mapper.InvoiceMapper;
import cz.itnetwork.dto.mapper.PersonMapper;
import cz.itnetwork.entity.InvoiceEntity;
import cz.itnetwork.entity.PersonEntity;
import cz.itnetwork.entity.repository.InvoiceRepository;
import cz.itnetwork.entity.repository.PersonRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.webjars.NotFoundException;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Služba pro správu faktur.
 * Obsahuje metody pro přidání, získání, aktualizaci, odstranění faktur a získání statistik faktur.
 */
@Service
public class PersonServiceImpl implements PersonService {

    /**
     * DI Field Injection PersonMapper
     */
    @Autowired
    private PersonMapper personMapper;

    /**
     * DI Field Injection PersonRepository
     */
    @Autowired
    private PersonRepository personRepository;

    /**
     * DI Field Injection InvoiceRepository
     */
    @Autowired
    private InvoiceRepository invoiceRepository;

    /**
     * DI Field Injection InvoiceMapper
     */
    @Autowired
    private InvoiceMapper invoiceMapper;


    /**
     * Creates a new person
     *
     * @param personDTO Person to create
     * @return new person DTO
     */
    public PersonDTO addPerson(PersonDTO personDTO) {
        PersonEntity entity = personMapper.toEntity(personDTO);
        entity = personRepository.save(entity);
        return personMapper.toDTO(entity);
    }

    /**
     * Aktualizuje a upraví osobu na základě ID a dat která obdrží
     *
     * @param personId ID osoby
     * @param personDTO Uravená data osoby
     * @return nové upravené DTO osoby
     */
    public PersonDTO updatePerson(Long personId, PersonDTO personDTO){
        PersonEntity oldPersonEntity = fetchPersonById(personId);
        oldPersonEntity.setHidden(true); // JPA pokud si stáhne  z databaze entitu, tak na konci transakce (na konci metody) ji automaticky přeuloží,
        PersonEntity newPersonEntity = personMapper.toEntity(personDTO);
        newPersonEntity = personRepository.save(newPersonEntity);
        return personMapper.toDTO(newPersonEntity);
    }

    /**
     * Získa seznam všech faktur kde figuruje osoba dle IČO jako Dodavatel
     *
     * @param identificationNumber IČO osoby
     * @return seznam faktur
     */
    @Override
    public List<InvoiceDTO> getAllSales(String identificationNumber) {
        List<PersonEntity> sellerEntity = personRepository.findByIdentificationNumber(identificationNumber);
        List<InvoiceDTO> invoicesDTO = new ArrayList<>();
        List<InvoiceEntity> invoiceEntities = new ArrayList<>();
        for (PersonEntity e : sellerEntity){
            invoiceEntities.addAll(e.getSales());
        }
        for (InvoiceEntity e : invoiceEntities){
            InvoiceDTO invoiceDTO = invoiceMapper.toDTO(e);  // deklaruji si pomocnou proměnou invoiceDTO
            invoicesDTO.add(invoiceDTO); // ukládám do kolekce invoicesDTO pomocnou proměnou invoiceDTO
        }
        return invoicesDTO;
    }

    /**
     * Získa seznam všech faktur kde figuruje osoba dle IČO jako Odběratel
     *
     * @param identificationNumber IČO osoby
     * @return seznam faktur
     */
    @Override
    public List<InvoiceDTO> getAllBuyers(String identificationNumber) {
        List<PersonEntity> sellerEntity = personRepository.findByIdentificationNumber(identificationNumber);
        List<InvoiceDTO> invoicesDTO = new ArrayList<>();
        List<InvoiceEntity> invoiceEntities = new ArrayList<>();
        for (PersonEntity e : sellerEntity){
            invoiceEntities.addAll(e.getPurchases());
        }
        for (InvoiceEntity e : invoiceEntities){
            InvoiceDTO invoiceDTO = invoiceMapper.toDTO(e);  // deklaruji si pomocnou proměnou invoiceDTO
            invoicesDTO.add(invoiceDTO); // ukládám do kolekce invoicesDTO pomocnou proměnou invoiceDTO
        }
        return invoicesDTO;
    }

    /**
     * Získa osobu del ID
     *
     * @param id ID osoby
     * @return DTO vyhledané osoby
     */

    @Override
    public PersonDTO getPersonById(long id) {
        PersonEntity personEntity = fetchPersonById(id);
        return personMapper.toDTO(personEntity);
    }

    /**
     * Odstranění osoby dle ID (nastaví atribut HIDDEN na "1") v databázi
     * @param personId Person to delete
     */
    @Override
    public void removePerson(long personId) {
        try {
            PersonEntity person = fetchPersonById(personId);
            person.setHidden(true);

            personRepository.save(person);
        } catch (NotFoundException ignored) {
            // The contract in the interface states, that no exception is thrown, if the entity is not found.
        }
    }

    /**
     * Získá seznam všech neskrytých osob
     * @return seznam všech osob
     */
    @Override
    public List<PersonDTO> getAll() {  // tady mi to vrací pouze nezkryté osoby, tzn pokud mám fakturu a osoba je skrytá tak ji na klientovi nemohu vidět nebot m ji Server nebrací- vyřešit
        return personRepository.findByHidden(false)
                .stream()
                .map(i -> personMapper.toDTO(i))
                .collect(Collectors.toList());
    }

    /**
     * Získá všechny statistky
     * @return DTO s daty statistik
     */
    @Override
    public List <PersonStatisticDTO> getPersonStatistic(){
        List<PersonEntity> allPersonEntity = personRepository.findAll();
        List<PersonStatisticDTO> allPersonStatistic = new ArrayList<>();

        for (PersonEntity personEntity : allPersonEntity){
            long revenue = 0;
            for (InvoiceEntity invoiceEntity : personEntity.getSales()){   // sahám si na kolekci vystavenych faktur v PersonEntitě, díky tomu mužu sečíst revenue
                revenue = revenue + invoiceEntity.getPrice();
            }
            allPersonStatistic.add(new PersonStatisticDTO(personEntity.getId(),personEntity.getName(), revenue)); // vytvořím si výstupní přepravku a inicalizuji konstrukotrem
        }
        return allPersonStatistic;
    }



    // region Private methods
    /**
     * <p>Attempts to fetch a person.</p>
     * <p>In case a person with the passed [id] doesn't exist a [{@link org.webjars.NotFoundException}] is thrown.</p>
     *
     * @param id Person to fetch
     * @return Fetched entity
     * @throws org.webjars.NotFoundException In case a person with the passed [id] isn't found
     */
    private PersonEntity fetchPersonById(long id) {
        return personRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("Person with id " + id + " wasn't found in the database."));
    }

    // endregion
}
