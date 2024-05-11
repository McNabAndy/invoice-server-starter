package cz.itnetwork.entity.repository;

import cz.itnetwork.dto.InvoiceStatisticDTO;
import cz.itnetwork.entity.InvoiceEntity;
import cz.itnetwork.entity.PersonEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
/**
 * Repozitářové rozhraní pro entitu {@link InvoiceEntity}, které poskytuje základní CRUD (Create, Read, Update, Delete)
 * operace a rozšířené schopnosti pro provádění dotazů pomocí specifikací.
 * Dědí z {@link JpaRepository}, což umožňuje využívat bohatou nabídku metody pro práci s databází.
 * Rozhraní {@link JpaSpecificationExecutor} přidává možnost provádět složité dotazy na databázi pomocí
 * Specification objektů, což umožňuje dynamické sestavení SQL dotazů podle potřeb aplikace.
 * Toto rozhraní je automaticky implementováno Spring Data JPA, což zjednodušuje vývoj aplikace tím, že eliminuje potřebu
 * manuálně psát implementaci repozitáře. S použitím tohoto rozhraní se múže nadno přistupovat k datům
 * a provádět složité dotazy bez přímého psaní SQL kódu.
 */
public interface InvoiceRepository extends JpaRepository <InvoiceEntity, Long>, JpaSpecificationExecutor<InvoiceEntity> {

}
