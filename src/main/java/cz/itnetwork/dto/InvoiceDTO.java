package cz.itnetwork.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonProperty;
import cz.itnetwork.entity.PersonEntity;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

/**
 * Data Transfer Object (DTO) reprezentující fakturu.
 * Tento objekt obsahuje všechny relevantní údaje o faktuře, které mohou být přenášeny mezi různými částmi aplikace.
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class InvoiceDTO {

    /**
     * Jedinečný identifikátor faktury.
     */
    @JsonProperty("_id")
    private Long id;

    /**
     * Čislo faktury
     */
    private int invoiceNumber;

    /**
     * Datum vystavení faktury.
     * Formát data: yyyy-MM-dd
     */
    @JsonFormat(pattern = "yyyy-MM-dd")
    private Date issued;


    /**
     * Datum splatnosti faktury.
     * Formát data: yyyy-MM-dd
     */
    @JsonFormat(pattern = "yyyy-MM-dd")
    private Date dueDate;

    /**
     * Fakturovaný produkt
     */
    private String product;

    /**
     * Cena fakturovaného produktu v KČ
     */
    private Long price;

    /**
     * DPH v %
     */
    private int vat;


    /**
     * Poznámka k faktuře
     */
    private String note;


    /**
     * Informace o kupujícím - Odběratel
     */
    private PersonDTO buyer;

    /**
     * Informace o prodávajícím - Dodavatel
     */
    private PersonDTO seller;
}
