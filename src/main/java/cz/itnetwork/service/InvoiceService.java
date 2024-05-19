package cz.itnetwork.service;

import cz.itnetwork.dto.InvoiceDTO;
import cz.itnetwork.dto.InvoiceFilter;
import cz.itnetwork.dto.InvoiceStatisticDTO;

import java.util.List;
/**
 * Rozhraní pro služby související s fakturami.
 * Poskytuje metody pro přidání, aktualizaci, získání a odstranění faktur, stejně jako pro získání statistik.
 */
public interface InvoiceService {

    /**
     * Přidá novou fakturu do systému.
     * @param invoiceDTO DTO obsahující data nové faktury.
     * @return DTO nově vytvořené faktury.
     */
    InvoiceDTO addInvoice(InvoiceDTO invoiceDTO);

    /**
     * Získá seznam všech faktur, které odpovídají zadanému filtru.
     * @param invoiceFilter Filtr pro specifikaci kritérií vyhledávání.
     * @return Seznam faktur vyhovujících filtru ve formě DTO.
     */
    List<InvoiceDTO> getAll(InvoiceFilter invoiceFilter);


    /**
     * Získá seznam všech faktur v systému.
     * @return Seznam všech faktur ve formě DTO.
     */List<InvoiceDTO> getAll(); // přetížená metoda

    /**
     * Získá konkrétní fakturu podle jejího ID.
     * @param invoiceId ID faktury, kterou chceme získat.
     * @return DTO požadované faktury.
     */
    InvoiceDTO getInvoice(Long invoiceId);


    /**
     * Odstraní fakturu podle jejího ID.
     * @param id ID faktury, která má být odstraněna.
     */
    void removeInvoice(Long id);

    /**
     * Aktualizuje data existující faktury.
     * @param id ID faktury, která má být aktualizována.
     * @param invoiceDTO  DTO s aktualizovanými daty faktury.
     * @return DTO aktualizované faktury.
     */
    InvoiceDTO updateInvoice(Long id, InvoiceDTO invoiceDTO);

    /**
     * Získá statistické údaje o fakturách.
     * @return Objekt DTO s agregovanými statistickými údaji o fakturách.
     */
    InvoiceStatisticDTO getInvoiceStatistics();


}
