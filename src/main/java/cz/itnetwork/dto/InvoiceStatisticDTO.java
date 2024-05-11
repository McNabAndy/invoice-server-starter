package cz.itnetwork.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

/**
 * Data Transfer Object (DTO) pro předávání statistických údajů o fakturách.
 * Tento objekt obsahuje agregovaná data o fakturách za aktuální rok, celková data za všechny roky a počet faktur.
 */
@Data
@AllArgsConstructor
public class InvoiceStatisticDTO {

    /**
     * Součet hodnot všech faktur za aktuální rok.
     * Toto číslo reprezentuje celkovou sumu, která byla fakturována v průběhu aktuálního roku.
     */
    private Long currentYearSum;

    /**
     * Součet hodnot všech faktur od zahájení podnikání.
     * Toto číslo představuje kumulativní celkovou sumu, která byla fakturována za celou dobu existence podniku.
     */
    private Long allTimeSum;


    /**
     * Celkový počet faktur, které byly vystaveny.
     * Toto číslo zahrnuje všechny faktury vytvořené v databázy
     */
    private Long invoicesCount;


}
