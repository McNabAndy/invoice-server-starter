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
package cz.itnetwork.controller;

import cz.itnetwork.dto.InvoiceDTO;
import cz.itnetwork.dto.PersonDTO;
import cz.itnetwork.dto.PersonStatisticDTO;
import cz.itnetwork.service.PersonService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * Třída {@code PersonController} spravuje HTTP požadavky pro operace s fakturami.
 * Tento kontroler poskytuje API endpointy pro vytváření, aktualizaci, mazání a získávání informací o fakturách.
 */

@RestController
@RequestMapping("/api")
public class PersonController {

    /**
     * DI Field Injection Person Service
     */
    @Autowired
    private PersonService personService;


    /**
     * Vytvoří osobu v databázy
     * @param personDTO Data předávaná ve formuláři
     * @return vytvořenou osobu
     */
    @PostMapping("/persons")
    public PersonDTO addPerson(@RequestBody PersonDTO personDTO) {
        return  personService.addPerson(personDTO);
    }

    /**
     * Upraví osobu dle ID
     * @param personId ID osoby kterou upravuji
     * @param personDTO Data k upravení, předávaná ve formuláři
     * @return Aktualizovaná osoba po úpravě
     */
    @PutMapping ("/persons/{personId}")
    public PersonDTO editPerson(@PathVariable Long personId, @RequestBody PersonDTO personDTO){
        return personService.updatePerson(personId, personDTO);
    }


    /**
     * Získá seznam všech osob
     * @return Seznam všech osob (neskrytých v databázy)
     */
    @GetMapping("/persons")
    public List<PersonDTO> getPersons() {
        return personService.getAll();
    }


    /**
     * Odstraní osobu (nastaví atribut HIDDEN na "1") v databázy dle ID
     * @param personId ID osoby
     */
    @DeleteMapping("/persons/{personId}")
    public void deletePerson(@PathVariable  Long personId) {
        personService.removePerson(personId);
    }

    /**
     * Získá osobu dle ID z databáze
     * @param personId ID osoby
     * @return Nalezená osoba
     */
    @GetMapping("/persons/{personId}")
    public PersonDTO getPersonById(@PathVariable Long personId){
        return personService.getPersonById(personId);
    }


    /**
     * Získá seznam faktur dodavatele dle IČO
     * @param identificationNumber IČO osoby
     * @return Seznam faktur kde figiruje dodavetel s daným IČO
     */
   @GetMapping("identification/{identificationNumber}/sales")
    public List<InvoiceDTO> getSalesInvoices(@PathVariable String identificationNumber){
        return personService.getAllSales(identificationNumber);
    }


    /**
     * Získá seznam faktur odběratele dle IČO
     * @param identificationNumber IČO osoby
     * @return Seznam faktur kde figiruje odběratel s daným IČO
     */
    @GetMapping("identification/{identificationNumber}/purchases")
    public List<InvoiceDTO> getBuyerInvoices(@PathVariable String identificationNumber){
        return personService.getAllBuyers(identificationNumber);
    }

    /**
     * Získá statistiku faktur
     * @return Statistika faktur
     */
    @GetMapping("/persons/statistics")
    public List<PersonStatisticDTO> getAllPersonStatistic(){
        return personService.getPersonStatistic();
    }
}



