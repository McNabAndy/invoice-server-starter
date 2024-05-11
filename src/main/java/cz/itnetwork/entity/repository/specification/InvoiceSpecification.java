package cz.itnetwork.entity.repository.specification;

import cz.itnetwork.dto.InvoiceFilter;
import cz.itnetwork.entity.InvoiceEntity;
import cz.itnetwork.entity.InvoiceEntity_;
import cz.itnetwork.entity.PersonEntity;
import cz.itnetwork.entity.PersonEntity_;
import jakarta.persistence.criteria.*;
import lombok.RequiredArgsConstructor;
import org.springframework.data.jpa.domain.Specification;

import java.util.ArrayList;
import java.util.List;

/**
 * Třída {@code InvoiceSpecification} implementuje rozhraní {@link Specification} pro entitu {@link InvoiceEntity}.
 * Umožňuje vytvářet dynamické dotazy na databázi na základě různých filtrů definovaných v {@link InvoiceFilter}.
 */
@RequiredArgsConstructor
public class InvoiceSpecification implements Specification<InvoiceEntity> {

    /**
     * Filtr pro definování kritérií dotazu.
     */
    private final InvoiceFilter invoiceFilter;


    /**
     * Vytváří {@link Predicate} na základě kritérií definovaných v {@link InvoiceFilter}.
     * Tato metoda se používá pro dynamické vytváření částí dotazů, které filtrují faktury podle různých atributů.
     *
     * @param root Základ dotazu, typicky tabulka faktur.
     * @param query Dotaz, který se kontroluje
     * @param criteriaBuilder Nástroj pro sestavení dotazu
     * @return složený {@link Predicate} obsahující všechny filtrační podmínky
     */
    @Override
    public Predicate toPredicate(Root<InvoiceEntity> root, CriteriaQuery<?> query, CriteriaBuilder criteriaBuilder) {
        List<Predicate> predicates = new ArrayList<>();

        if (invoiceFilter.getProduct() != null){
            String likePattern = "%" + invoiceFilter.getProduct() + "%";
            predicates.add(criteriaBuilder.like(root.get(InvoiceEntity_.PRODUCT), likePattern));
        }

        if (invoiceFilter.getMinPrice() != null){
            predicates.add(criteriaBuilder.greaterThanOrEqualTo(root.get(InvoiceEntity_.PRICE), invoiceFilter.getMinPrice()));
        }

        if (invoiceFilter.getMaxPrice() != null){
            predicates.add(criteriaBuilder.lessThanOrEqualTo(root.get(InvoiceEntity_.PRICE), invoiceFilter.getMaxPrice()));
        }

        if (invoiceFilter.getBuyerId() != null){
            Join<InvoiceEntity, PersonEntity> buyerJoin = root.join(InvoiceEntity_.BUYER);
            predicates.add(criteriaBuilder.equal(buyerJoin.get(PersonEntity_.ID), invoiceFilter.getBuyerId()));
        }

        if (invoiceFilter.getSellerId() != null){
            Join<InvoiceEntity, PersonEntity> sellerJoin = root.join(InvoiceEntity_.SELLER);
            predicates.add(criteriaBuilder.equal(sellerJoin.get(PersonEntity_.ID), invoiceFilter.getSellerId()));
        }
        return criteriaBuilder.and(predicates.toArray(new Predicate[0]));
    }
}
