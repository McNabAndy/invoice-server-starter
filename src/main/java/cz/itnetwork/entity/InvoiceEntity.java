package cz.itnetwork.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.util.Date;
/**
 * Entita reprezentující fakturu v databázi.
 * Tato třída obsahuje všechny potřebné atributy pro fakturu a je mapována na tabulku 'invoice' v databázi.
 * Využívá Lombok anotace {@link Setter} a {@link Getter} pro automatické generování setterů a getterů pro všechny atributy.
 */
@Entity(name = "invoice")
@Setter
@Getter

public class InvoiceEntity {
    /**
     * Jedinečný identifikátor faktury. Generován databází.
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    /**
     * Číslo faktury, které musí být nenulové.
     */
    @Column(nullable = false)
    private int invoiceNumber;

    /**
     * Datum vystavení faktury, které musí být nenulové.
     */
    @Column(nullable = false)
    private Date issued;

    /**
     * Datum splatnosti faktury, které musí být nenulové.
     */
    @Column(nullable = false)
    private Date dueDate;

    /**
     * Název produktu nebo služby fakturované, musí být zadán.
     */
    @Column(nullable = false)
    private String product;

    /**
     * Cena produktu, musí být zadána
     */
    @Column(nullable = false)
    private Long price;

    /**
     * DPH produktu, musí být zadána
     */
    @Column(nullable = false)
    private int vat;

    /**
     * Poznámka k produktu, musí být zadána
     */
    @Column(nullable = false)
    private String note;

    /**
     * Ddběratel, k němuž je faktura přiřazena. Tento vztah je načítán pomocí  - (LAZY fetching).
     */
    @ManyToOne(fetch = FetchType.LAZY)
    private PersonEntity buyer;

    /**
     * Dodavatel, který fakturu vystavil. Tento vztah je načítán pomocí - (LAZY fetching).
     */
    @ManyToOne(fetch = FetchType.LAZY)
    private PersonEntity seller;

}
