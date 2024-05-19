package cz.itnetwork.service;

import cz.itnetwork.dto.InvoiceDTO;
import cz.itnetwork.dto.InvoiceFilter;
import cz.itnetwork.dto.InvoiceStatisticDTO;
import cz.itnetwork.dto.mapper.InvoiceMapper;
import cz.itnetwork.entity.InvoiceEntity;
import cz.itnetwork.entity.PersonEntity;
import cz.itnetwork.entity.repository.InvoiceRepository;
import cz.itnetwork.entity.repository.PersonRepository;
import cz.itnetwork.entity.repository.specification.InvoiceSpecification;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.webjars.NotFoundException;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
/**
 * Služba pro správu faktur.
 * Obsahuje metody pro přidání, získání, aktualizaci, odstranění faktur a získání statistik faktur.
 */
@Service
public class InvoiceServiceImpl implements InvoiceService{

    /**
     * DI Field Injection InvoiceRepository
     */
    @Autowired
    private InvoiceRepository invoiceRepository;

    /**
     * DI Field Injection PersonRepository
     */
    @Autowired
    private PersonRepository personRepository;

    /**
     * DI Field Injection InvoiceMapper
     */
    @Autowired
    private InvoiceMapper invoiceMapper;


    /**
     * Přidává novou fakturu na základě přijatých dat ve formátu DTO.
     * Převádí DTO na entitu, ukládá entitu a nastavuje odkazy na prodávajícího a kupujícího.
     *
     * @param invoiceDTO DTO obsahující data nové faktury.
     * @return DTO uložené faktury
     */
    @Override
    public InvoiceDTO addInvoice(InvoiceDTO invoiceDTO) {
        InvoiceEntity invoiceEntity = invoiceMapper.toEntity(invoiceDTO);
        InvoiceEntity saveInvoiceEntity = invoiceRepository.save(invoiceEntity);
        saveInvoiceEntity.setSeller(personRepository.getReferenceById(invoiceDTO.getSeller().getId()));
        saveInvoiceEntity.setBuyer(personRepository.getReferenceById(invoiceDTO.getBuyer().getId()));
        return invoiceMapper.toDTO(saveInvoiceEntity);
    }


    /**
     * Vrátí seznam všech faktur bez filtru.
     * @return DTO seznam faktur
     */
    @Override  // tuto metodu pořešit abych ji nemusel přetěžovat - protože get all používám k výpisu statistik....možná by bylo lepší to udělat přes to query, protože jinak metoda vyžaduje filter
    public List<InvoiceDTO> getAll() {
        List <InvoiceEntity> allInvoices = invoiceRepository.findAll();
        List<InvoiceDTO> invoicesDTO = new ArrayList<>();
        for (InvoiceEntity e : allInvoices){
            InvoiceDTO invoiceDTO = invoiceMapper.toDTO(e);
            invoicesDTO.add(invoiceDTO);
        }
        return invoicesDTO;
    }

    /**
     * Vrátí seznam faktur odpovídajících zadanému filtru. Podporuje stránkování.
     * @param invoiceFilter Filtr pro specifikaci kritérií vyhledávání.
     * @return DTO seznam faktur
     */
    @Override
    public List<InvoiceDTO> getAll(InvoiceFilter invoiceFilter) {
        InvoiceSpecification invoiceSpecification = new InvoiceSpecification(invoiceFilter);
        Pageable pageable = Pageable.unpaged(); // default bez stránkování
        if (invoiceFilter.getLimit() != null) {  // limit mám jako Long proto může mít hodnotu null
            pageable = PageRequest.of(0, invoiceFilter.getLimit().intValue()); // protože je atribut limit Long - a metoda PegerRequest vyžaduje int
        }
        Page<InvoiceEntity> invoicesPage = invoiceRepository.findAll(invoiceSpecification, pageable);
        List <InvoiceEntity> allInvoices = invoicesPage.getContent();
        List<InvoiceDTO> invoicesDTO = new ArrayList<>();
        for (InvoiceEntity e : allInvoices){
            InvoiceDTO invoiceDTO = invoiceMapper.toDTO(e);
            invoicesDTO.add(invoiceDTO);
        }
        return invoicesDTO;
    }

    /**
     * Získá fakturu podle jejího ID. Používá metodu getReferenceById pro rychlejší načítání z databáze.
     * @param invoiceId ID faktury, kterou chceme získat.
     * @return DTO vyhledávané faktury
     */
    @Override
    public InvoiceDTO getInvoice(Long invoiceId) {
        InvoiceEntity invoiceEntity = invoiceRepository.getReferenceById(invoiceId);
        return invoiceMapper.toDTO(invoiceEntity);
    }

    /**
     * Odstraní fakturu podle ID. Zachycuje výjimky, pokud faktura neexistuje.
     * @param id ID faktury, která má být odstraněna.
     */
    @Override
    public void removeInvoice(Long id) {
        try {
            InvoiceEntity invoiceEntity = invoiceRepository.getReferenceById(id);
            invoiceRepository.delete(invoiceEntity);
        } catch (NotFoundException ignored) {
            // The contract in the interface states, that no exception is thrown, if the entity is not found.
        }
    }

    /**
     * Aktualizuje fakturu. Zahrnuje logiku pro nastavení prodávajícího a kupujícího, jelikož mapper ignoruje tyto atributy.
     *
     * @param id ID faktury, která má být aktualizována.
     * @param invoiceDTO  DTO s aktualizovanými daty faktury.
     * @return DTO upravené faktury
     */
    @Override
    public InvoiceDTO updateInvoice(Long id, InvoiceDTO invoiceDTO) {
        InvoiceEntity fetchInvoiceEntity = invoiceRepository.findById(id)
        .orElseThrow(() -> new NotFoundException("Invoice with id " + id + " wasn't found in the database."));
        System.out.println("ID před mappingem: " + fetchInvoiceEntity.getId());

        /**
         *POZOR Mapper nastavuje všechny hodnoty které má entita z DTO, jeho nastavení ja takové že pokud dělám tzv.
         * Mapingtarget z poskytnuté DTO na existující Entitu tak pokud mám DTO nějaký atribut null a Entita obsahuje u
         * tohoto atributu hodnotu z databáze -> přepíše se na hodnotu z DTO tzn na NULL. To je i duvod proč musím zde
         * NATVRDO jasně nastavit ještě jednou ID , protože DTO ho nemá a mapper u Entity ID přepíše na NULL - viz
         * kontrolní vypisi do konzole
         */

        InvoiceEntity newEntity = invoiceMapper.updateEntity(invoiceDTO,fetchInvoiceEntity); // Dát si bacha Mapper mi nastavuje při převodu DTO na Entitu hodnoty z DTO
        System.out.println("ID po mappingu: " + newEntity.getId());

        newEntity.setId(id);
        System.out.println("ID po opětovém nastvaní díky metode setId(id): " + newEntity.getId());

        /**
         * V Json požadavku mi přijde IDecka buyera a sellera - jenže Mapper s tímto v DTO nepočítá a má zde odkaz na celého buyera a sellera
         * takže namapuje pouze jeho IDcka a ostatní je NULL => na Mapperu dám u update  metody ignorovat buyera a sellear a pro tuto
         * update metodu je specificky implmentuju přímo přes dotaz přes Person repozitář.
         * -------------------------------------------------------------------------------------------------------------------------------
         * Jinými slový řeknu ignoruj buyera sellera, a pak pošlu dotaz do databáze
         * -> Vyhledej PersonEntitu podle ID -> ID předám z přeravky InvoiceDTO která obsahuje u buyera a sellera pouze IDecka ->
         * takže na invoiceDTO zavolám -> dej mi Buyera -> a na Buyerovi zavola dej mi ID (které jako jediné mám u něj v přepravce)
         */

        newEntity.setBuyer(personRepository.getReferenceById(invoiceDTO.getBuyer().getId())); // nastvý Entitě Buyera dle id  databáze
        newEntity.setSeller(personRepository.getReferenceById(invoiceDTO.getSeller().getId()));
        invoiceRepository.saveAndFlush(newEntity);
        return invoiceMapper.toDTO(newEntity);
    }

    /**
     * vytvoří statistiku faktur
     * @return DTO se statistikou faktur
     */
    @Override
    public InvoiceStatisticDTO getInvoiceStatistics() {
        List<InvoiceDTO> allInvoice = getAll();
        Date currentYear = new Date();
        long allTimeSum = 0;
        long currentYearSum = 0;
        long count = 0;
        for (InvoiceDTO invoiceDTO : allInvoice){
            allTimeSum = allTimeSum + invoiceDTO.getPrice();
            int datum= invoiceDTO.getDueDate().getYear();
            System.out.println(datum);
            if (invoiceDTO.getDueDate().getYear() == currentYear.getYear()){
                currentYearSum = currentYearSum + invoiceDTO.getPrice();
            }
            count = count + 1;
        }
        InvoiceStatisticDTO invoiceStatisticDTO = new InvoiceStatisticDTO(currentYearSum,allTimeSum,count);
        return invoiceStatisticDTO;
    }


}
