package cz.itnetwork.dto;

import lombok.Data;
/**
 * Třída {@code InvoiceFilter} slouží jako filtr pro vyhledávání faktur.
 * Umožňuje specifikovat kritéria pro vyhledávání faktur na základě různých atributů, jako jsou ID odběratele a dodavatele,
 * hledaný produkt, rozsah cen a limit počtu výsledků.
 */
@Data
public class InvoiceFilter {
    /**
     * ID odběratele, podle kterého filtruje faktury.
     * Slouží k identifikaci faktur, které byly vystaveny specifickému odběrateli.
     */
    private Long buyerId;

    /**
     * ID dodavatele, podle kterého filtruje faktury.
     * Umožňuje najít faktury, které byly vystaveny určitým dodavatelem.
     */
    private Long sellerId;

    /**
     * Text hledaný v názvu produktu na faktuře.
     * Umožňuje vyhledávat faktury obsahující specifický produkt.
     */
    private String product;

    /**
     * Minimální částka faktury, která má být zahrnuta ve výsledcích.
     * Faktury s celkovou částkou nižší než tato hodnota nebudou zahrnuty.
     */
    private Long minPrice;

    /**
     * Maximální částka faktury, která má být zahrnuta ve výsledcích.
     * Faktury s celkovou částkou vyšší než tato hodnota nebudou zahrnuty.
     */
    private Long maxPrice;

    /**
     * Omezení počtu faktur, které mají být vráceny.
     * Toto číslo definuje maximální počet faktur, které vyhledávání vrátí.
     */
    private Long limit;


}
