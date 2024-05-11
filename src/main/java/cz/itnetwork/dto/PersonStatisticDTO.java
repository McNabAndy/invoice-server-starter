package cz.itnetwork.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
/**
 * Data Transfer Object (DTO) pro předávání statistických údajů o osobách.
 * Tento objekt obsahuje agregovaná data o osobách id osoby, jmeono osoby a součet výnosů danné osoby.
 */
@Data
@AllArgsConstructor
public class PersonStatisticDTO {

    /**
     * Jedinečný identifikátor osoby, firmy v databázy
     */
    private Long personId;

    /**
     * Jméno osoby nebo název firmy
     */
    private String personName;

    /**
     * Celkový součet výnosů osoby.
     * Reprezentuje sumu všech příjmů nebo transakcí, které byly připsány této osobě, obvykle v určitém časovém období.
     */
    private Long revenue;


}
