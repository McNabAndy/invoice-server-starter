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
package cz.itnetwork.entity;

import cz.itnetwork.constant.Countries;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;
/**
 * Entita reprezentující osobu v databázi.
 * Tato třída mapuje data osoby na databázovou tabulku s názvem 'person'.
 * Obsahuje osobní a kontaktní informace, stejně jako vztahy k fakturám, kde osoba vystupuje jako kupující nebo prodávající.
 */
@Entity(name = "person")
@Getter
@Setter
public class PersonEntity {
    /**
     * Jedinečný identifikátor osoby. Generován databází.
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    /**
     * Jméno osoby. Toto pole nesmí být prázdné.
     */
    @Column(nullable = false)
    private String name;

    /**
     * IČO osoby. Toto pole nesmí být prázdné.
     */
    @Column(nullable = false)
    private String identificationNumber;

    /**
     * DIČ osoby. Toto pole nesmí být prázdné.
     */
    @Column(nullable = false)
    private String taxNumber;

    /**
     * Číslo účtu osoby. Toto pole nesmí být prázdné.
     */
    @Column(nullable = false)
    private String accountNumber;


    /**
     * Kód banky, ke které účet náleží. Toto pole nesmí být prázdné.
     */
    @Column(nullable = false)
    private String bankCode;

    /**
     * Mezinárodní bankovní číslo účtu (IBAN). Toto pole nesmí být prázdné.
     */
    @Column(nullable = false)
    private String iban;

    /**
     * Telefonní číslo osoby. Toto pole nesmí být prázdné.
     */
    @Column(nullable = false)
    private String telephone;

    /**
     * E-mailová adresa osoby. Toto pole nesmí být prázdné.
     */
    @Column(nullable = false)
    private String mail;

    /**
     * Ulice a číslo popisné, kde osoba bydlí. Toto pole nesmí být prázdné.
     */
    @Column(nullable = false)
    private String street;

    /**
     * Poštovní směrovací číslo. Toto pole nesmí být prázdné.
     */
    @Column(nullable = false)
    private String zip;

    /**
     * Město, kde osoba bydlí. Toto pole nesmí být prázdné.
     */
    @Column(nullable = false)
    private String city;

    /**
     * Země, ve které osoba bydlí. Toto pole nesmí být prázdné a je reprezentováno jako Enum.
     */
    @Column(nullable = false)
    @Enumerated(EnumType.STRING)
    private Countries country;

    /**
     * Volitelné poznámky k osobě.
     */
    private String note;

    /**
     * Indikátor, zda je osoba skrytá v aplikaci. Výchozí hodnota je 'false'.
     */
    private boolean hidden = false;

    /**
     * Seznam faktur, kde osoba vystupuje jako odběratel.
     */
    @OneToMany(fetch = FetchType.LAZY, mappedBy = "buyer")
    private List<InvoiceEntity> purchases;

    /**
     * Seznam faktur, kde osoba vystupuje jako dodavatel.
     */
    @OneToMany(fetch = FetchType.LAZY, mappedBy = "seller")
    private List<InvoiceEntity> sales;
}
