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
package cz.itnetwork.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import cz.itnetwork.constant.Countries;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
/**
 * Data Transfer Object (DTO) reprezentující informace o osobě nebo firmě.
 * Tento objekt obsahuje kontaktní údaje a identifikační informace nutné pro obchodní transakce.
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class PersonDTO {

    /**
     * Jedinečný identifikátor faktury.
     */
    @JsonProperty("_id")
    private Long id;

    /**
     * Jméno, název firmy
     */
    private String name;

    /**
     * IČO
     */
    private String identificationNumber;

    /**
     * DIČ
     */
    private String taxNumber;

    /**
     * číslo bankovního účtu bez kodu banky
     */
    private String accountNumber;

    /**
     * Kód banky
     */
    private String bankCode;

    /**
     * IBAN bankovního účtu
     */
    private String iban;

    /**
     * Telefon
     */
    private String telephone;

    /**
     * Email
     */
    private String mail;

    /**
     * Ulice
     */
    private String street;

    /**
     * PSČ
     */
    private String zip;

    /**
     * Město
     */

    private String city;

    /**
     * země
     */
    private Countries country;

    /**
     * Poznámka
     */
    private String note;
}
