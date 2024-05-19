package cz.itnetwork.controller;
import cz.itnetwork.dto.InvoiceDTO;
import cz.itnetwork.dto.InvoiceFilter;
import cz.itnetwork.dto.InvoiceStatisticDTO;
import cz.itnetwork.service.InvoiceService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * Třída {@code InvoiceController} spravuje HTTP požadavky pro operace s fakturami.
 * Tento kontroler poskytuje API endpointy pro vytváření, aktualizaci, mazání a získávání informací o fakturách.
 */

@RestController
@RequestMapping("/api")
public class InvoiceController {

    /**
     * DI Field Injection InvoiceService
     */
    @Autowired
    private InvoiceService invoiceService;




    /**
     * Požadavek na vytvoření faktury
     * @param invoiceDTO Data předaná ve formuláři k uložení do databáze
     * @return Nově vytvořená faktura
     */
    @PostMapping("/invoices")
    public InvoiceDTO addInvoice(@RequestBody InvoiceDTO invoiceDTO){
        return invoiceService.addInvoice(invoiceDTO);
    }


   /*
   anotaci @ModelAttribute nemusím použít ale pro čitelnost je to lepší, spring používá totiž tzv
   koncept tzv. "data binding" pro automatické mapování parametrů z URL na objekty v metodách kontrolerů
   Pokud máte v metodě kontroleru parametr typu InvoiceFilter a tento typ obsahuje settery odpovídající parametrům z URL dotazu,
   Spring automaticky vytvoří instanci InvoiceFilter, načte parametry z URL (například product=myš), a použije příslušné settery
   pro nastavení hodnot do objektu InvoiceFilter. Tento proces se někdy nazývá "model attribute binding".

   Pokud máte třídu InvoiceFilter s definovaným setterem pro product, Spring zjistí, že existuje parametr product v URL
   a automaticky nastaví jeho hodnotu na "myš" skrze setter setProduct(String product).

   Pokud chcete být výslovnější o tom, že parametry mají být vázány na modelový objekt, můžete použít anotaci
    @ModelAttribute před parametrem InvoiceFilter ve vaší metodě kontroleru. To je užitečné pro čitelnost a explicitnost,
    ale není nutné, pokud jsou naming a datové typy v souladu.

    Ani anotace @PathVariable není v tomto případě potřeba, protože @PathVariable se používá pro parametry, které jsou
    součástí cesty URL (např. /invoices/{invoiceId}), nikoli pro parametry z query stringu (?product=myš).

    akže důvod, proč Spring dokáže automaticky přiřadit parametry URL do objektu InvoiceFilter, je kombinace inteligentního "data binding"
    mechanismu a správné definice třídy InvoiceFilter s odpovídajícími settery pro parametry, které očekáváte z URL.
    Tento přístup je velmi mocný a usnadňuje práci s daty z HTTP požadavků.
    */

    /**
     * Vyfitruje faktury
     * @param invoiceFilter Data dle kterých se má filtrovat
     * @return Vyfiltrované faktury
     */
    @GetMapping("/invoices")
    public List<InvoiceDTO> getInvoices(@ModelAttribute InvoiceFilter invoiceFilter){  //
        return invoiceService.getAll(invoiceFilter);
    }


    /**
     * Vyhledá konrétní fakturu v databázi dle ID
     * @param invoiceId ID konkrítní faktury
     * @return Vyhledaná faktura
     */
    @GetMapping("/invoices/{invoiceId}")
    public InvoiceDTO getInvoiceById(@PathVariable Long invoiceId){
        return invoiceService.getInvoice(invoiceId);
    }


    /**
     * Vymaže fakturu
     * @param invoiceId
     */
    @DeleteMapping("/invoices/{invoiceId}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteInvoice( @PathVariable Long invoiceId) {
        invoiceService.removeInvoice(invoiceId);
    }


    /**
     * Upraví požadovanou fakturu
     * @param invoiceId ID fakturu, kterou chci upravit
     * @param invoiceDTO Data předaná ve formuláři k uložení do databáze
     * @return Upravená faktura
     */
    @PutMapping("/invoices/{invoiceId}")
    public InvoiceDTO updateinvoice(@PathVariable Long invoiceId, @RequestBody InvoiceDTO invoiceDTO){
        return invoiceService.updateInvoice(invoiceId, invoiceDTO);
    }


    /**
     * Získá statistiku faktur
     * @return Statistika faktur
     */
    @GetMapping("invoices/statistics")
    public InvoiceStatisticDTO getInvoiceStatistic(){
        return invoiceService.getInvoiceStatistics();
    }

}
