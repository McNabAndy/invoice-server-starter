package cz.itnetwork.service;

import cz.itnetwork.dto.InvoiceDTO;
import cz.itnetwork.dto.mapper.InvoiceMapper;
import cz.itnetwork.entity.InvoiceEntity;
import cz.itnetwork.entity.repository.InvoiceRepository;
import cz.itnetwork.entity.repository.PersonRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class InvoiceServiceImpl implements InvoiceService{
    @Autowired
    private InvoiceRepository invoiceRepository;

    @Autowired
    private PersonRepository personRepository;

    @Autowired
    private InvoiceMapper invoiceMapper;

    @Override
    public InvoiceDTO addInvoice(InvoiceDTO invoiceDTO) {
        InvoiceEntity invoiceEntity = invoiceMapper.toEntity(invoiceDTO);
        InvoiceEntity saveInvoiceEntity = invoiceRepository.save(invoiceEntity);
        saveInvoiceEntity.setSeller(personRepository.getReferenceById(invoiceDTO.getSeller().getId())); // toto musím víc do hloubky prostudovat
        saveInvoiceEntity.setBuyer(personRepository.getReferenceById(invoiceDTO.getBuyer().getId()));   // toto musím víc do hloubky prostudovat
        return invoiceMapper.toDTO(saveInvoiceEntity);
    }

    @Override
    public List<InvoiceDTO> getAll() {
        List <InvoiceEntity> allInvoices = invoiceRepository.findAll();
        List<InvoiceDTO> invoicesDTO = new ArrayList<>();
        for (InvoiceEntity e : allInvoices){
            InvoiceDTO invoiceDTO = invoiceMapper.toDTO(e);
            invoicesDTO.add(invoiceDTO);
        }
        return invoicesDTO;
    }



}
