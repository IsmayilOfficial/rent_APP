package ee.ut.esi.group4.rentit.sales.application.service;

import ee.ut.esi.group4.rentit.common.application.service.AutheticationService;
import ee.ut.esi.group4.rentit.common.application.service.BusinessPeriodValidator;
import ee.ut.esi.group4.rentit.common.domain.BusinessPeriod;
import ee.ut.esi.group4.rentit.common.domain.ClientAccount;
import ee.ut.esi.group4.rentit.common.domain.ClientAccountRepository;
import ee.ut.esi.group4.rentit.inventory.domain.model.PlantInventoryEntry;
import ee.ut.esi.group4.rentit.inventory.domain.model.PlantInventoryItem;
import ee.ut.esi.group4.rentit.inventory.domain.model.PlantReservation;
import ee.ut.esi.group4.rentit.inventory.domain.model.StatusMessagingData;
import ee.ut.esi.group4.rentit.inventory.domain.repository.InventoryRepository;
import ee.ut.esi.group4.rentit.inventory.domain.repository.PlantInventoryEntryRepository;
import ee.ut.esi.group4.rentit.inventory.domain.repository.PlantInventoryItemRepository;
import ee.ut.esi.group4.rentit.inventory.domain.repository.PlantReservationRepository;
import ee.ut.esi.group4.rentit.sales.application.dto.POExtensionDTO;
import ee.ut.esi.group4.rentit.sales.application.dto.PlantInventoryItemDTO;
import ee.ut.esi.group4.rentit.sales.application.dto.PurchaseOrderDTO;
import ee.ut.esi.group4.rentit.sales.application.dto.RemittanceDTO;
import ee.ut.esi.group4.rentit.sales.domain.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.validation.DataBinder;
import org.springframework.web.client.RestTemplate;

import java.math.BigDecimal;
import java.time.temporal.ChronoUnit;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class SalesService {

    @Autowired
    PlantInventoryEntryRepository plantInventoryEntryRepository;

    @Autowired
    PurchaseOrderRepository poRepository;

    @Autowired
    InventoryRepository inventoryRepository;

    @Autowired
    PlantReservationRepository plantReservationRepository;

    @Autowired
    PlantInventoryItemRepository plantInventoryItemRepository;

    @Autowired
    PurchaseOrderAssembler purchaseOrderAssembler;

    @Autowired
    PlantInventoryItemAssembler plantInventoryItemAssembler;

    @Autowired
    ClientAccountRepository clientAccountRepository;

    @Autowired
    RestTemplate restTemplate;

    @Autowired
    InvoiceRepository invoiceRepository;

    @Autowired
    AutheticationService autheticationService;

    @Autowired
    ExtensionRepository extensionRepository;

    @Autowired
    POExtensionRepository poExtensionRepository;

    @Autowired
    RemittanceRepository remittanceRepository;

    @Autowired
    ReservationRepository reservationRepository;

    public PurchaseOrderDTO findPO(Long id) {
        PurchaseOrder po = poRepository.findById(id).orElse(null);
        return purchaseOrderAssembler.toModel(po);
    }

    public List<PurchaseOrderDTO> loadPurchaseOrder()  throws Exception {
        List<PurchaseOrder> pos = poRepository.findAll();
        return purchaseOrderAssembler.toModels(pos);
    }

    public List<PurchaseOrderDTO> findPurchaseOrderByCreator(String createdby) {
        List<PurchaseOrder> pos = poRepository.findByCreator(createdby.toLowerCase());
        return purchaseOrderAssembler.toModels(pos);
    }

    public PurchaseOrderDTO createPurchaseOrderForBuiltIt(Long entityId, BusinessPeriod businessPeriod ) throws Exception {

        PlantInventoryItem plant = plantInventoryItemRepository.findById(entityId).orElse(null);

        if(plant == null)
            throw new Exception("Plant NOT Found");

        PurchaseOrder po = PurchaseOrder.of(plant.getPlantInfo(), businessPeriod);
        return saveNewPO(po);

//        poRepository.save(po);
//
//        PlantReservation reservation = new PlantReservation();
//        reservation.setSchedule(businessPeriod);
//        reservation.setPlant(plant);
//
//        plantReservationRepository.save(reservation);
//        po.getReservations().add(reservation);
//
//        po.setTotal(computePOPrice(po));

//        poRepository.save(po);
//        return purchaseOrderAssembler.toModel(po);

    }

    public PurchaseOrderDTO createPO(PurchaseOrderDTO poDTO) throws Exception {

        BusinessPeriod period = BusinessPeriod.of(
                         poDTO.getRentalPeriod().getStartDate(),
                         poDTO.getRentalPeriod().getEndDate());

        DataBinder binder = new DataBinder(period);
        binder.addValidators(new BusinessPeriodValidator());
        binder.validate();

        if (binder.getBindingResult().hasErrors())
            throw new Exception("Invalid PO Period");

        if(poDTO.getPlant() == null)
            throw new Exception("Invalid Input Plant");

        PlantInventoryEntry plant = plantInventoryEntryRepository.findById(poDTO.getPlant().get_id()).orElse(null);

        if(plant == null)
            throw new Exception("Plant NOT Found");

        PurchaseOrder po = PurchaseOrder.of(plant, period);

        return saveNewPO(po);

    }

    private PurchaseOrderDTO saveNewPO(PurchaseOrder po) throws Exception {
        poRepository.save(po);

        List<PlantInventoryItem> availableItems = getPlantInventoryItemForPeriod(po.getRentalPeriod(), po.getPlant().getId());

        if(availableItems.size() == 0) {
            po.setStatus(POStatus.REJECTED);
        }

        PlantReservation reservation = new PlantReservation();
        reservation.setSchedule(po.getRentalPeriod());
        reservation.setPlant(availableItems.get(0));

        plantReservationRepository.save(reservation);
        po.getReservations().add(reservation);
        po.setTotal(ComputePrice(po));

        poRepository.save(po);
        return purchaseOrderAssembler.toModel(po);
    }

    private List<PlantInventoryItem> getPlantInventoryItemForPeriod(BusinessPeriod period, Long id) throws Exception {
        List<PlantInventoryItem> availableItems = inventoryRepository.confirmItemAvailableAtPeriod(
                id,
                period.getStartDate(),
                period.getEndDate());

        if(availableItems.size() == 0) {
            throw new Exception("No available items");
        }
        return availableItems;
    }

    private BigDecimal ComputePrice(PurchaseOrder po) {
        Long noofDay = ChronoUnit.DAYS.between(po.getRentalPeriod().getStartDate(), po.getRentalPeriod().getEndDate());
        double aDayTotal = po.getReservations().stream().mapToDouble(item -> item.getPlant().getPlantInfo().getPrice().doubleValue()).sum();

        double totalInDays = aDayTotal * noofDay;
        return new BigDecimal(totalInDays);
    }

    public PurchaseOrderDTO acceptPO(Long id) throws Exception {
        PurchaseOrder po = getPO(id);
        po.setStatus(POStatus.OPEN);
        poRepository.save(po);

        UpdateReservedPlantLocation(po, PlantLocation.IN_STORE);

        updateBuiltItPOStatus(po, POStatus.OPEN);
        return purchaseOrderAssembler.toModel(po);
    }

    private void UpdateReservedPlantLocation(PurchaseOrder po, PlantLocation loc) {
        List<Long> reserved = po.getReservations().stream().map(item -> item.getPlant().getId()).collect(Collectors.toList());
        List<PlantInventoryItem> reservedPlants = plantInventoryItemRepository.findAllById(reserved);

        for (PlantInventoryItem  item :  reservedPlants){
            item.setLocation(loc);
        }

        plantInventoryItemRepository.saveAll(reservedPlants);
    }

    public PurchaseOrderDTO rejectPO(Long id) throws Exception {
        PurchaseOrder po = getPO(id);
        while (!po.getReservations().isEmpty()) {
            plantReservationRepository.delete(po.getReservations().remove(0));
        }
        po.setStatus(POStatus.CLOSED);
        UpdateReservedPlantLocation(po, null);
        poRepository.save(po);
        updateBuiltItPOStatus(po, POStatus.CLOSED);
        return purchaseOrderAssembler.toModel(po);
    }


    public void updateBuiltItPOStatus(PurchaseOrder po, POStatus status) {

        StatusMessagingData plantHireRequestPostData = StatusMessagingData.of(po.getId(), status, "");

        HttpEntity<StatusMessagingData> request = new HttpEntity<>(plantHireRequestPostData);

        ClientAccount clientAccount = clientAccountRepository.findByUserName(po.getCreatedby().toLowerCase()).orElse(null);

        if(clientAccount != null) {
            restTemplate = new RestTemplate(autheticationService.getClientHttpRequestFactory(clientAccount.getClientSystemAccount(), clientAccount.getPassword()));

            final String baseUrl = clientAccount.getPhrHref();

            ResponseEntity<Object> plants = restTemplate.postForEntity(baseUrl, request, Object.class);
        }

    }

    public PurchaseOrderDTO resubmitPO(Long id) throws Exception {
        return null; // not implemented
    }

    private PurchaseOrder getPO(Long id) throws Exception {
        PurchaseOrder po = poRepository.findById(id).orElse(null);
        if(po == null)
            throw new Exception("PO Not Found");
        if(po.getStatus() != POStatus.PENDING)
            throw new Exception("PO cannot be accepted/rejected due to it is not Pending");
        return po;
    }

    private BigDecimal computePOPrice(PurchaseOrder po) {
        List<PlantReservation> reservations = po.getReservations();

        BigDecimal dayPeriod = new BigDecimal(ChronoUnit.DAYS.between(po.getRentalPeriod().getStartDate(), po.getRentalPeriod().getEndDate()));
        BigDecimal total = new BigDecimal(0);;
        reservations.stream().map(c -> (c.getPlant().getPlantInfo().getPrice().multiply(dayPeriod)).add(total));
        return  total;
    }

    public void updatePOStateRequest(StatusMessagingData data) {

        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String currentPrincipalName = authentication.getName();

        PurchaseOrder po = poRepository.findByOwnerAndPoId(currentPrincipalName, data.getEntityId()).orElse(null);
        po.setStatus(data.getState());

        po.addStatusMessage(data);

        poRepository.save(po);
    }

//    private POStatus ConvertToPOState(PHRStatus state) {
//        switch (state){
////            case OPEN: return PHRStatus.PHR_OPEN;
//
//            case CLOSED: return POStatus.PHR_CLOSED;
//
////            case PENDING: return PHRStatus.PO_CREATED;
////
////            case REJECTED: return PHRStatus.PO_REJECTED;
////
////            case PENDING_EXTENSION: return PHRStatus.PENDING_EXTENSION;
//            default:
//                return POStatus.PHR_UNMAPPED;
//
//        }
//    }

    public void createPOExtension(POExtensionDTO extension) throws Exception {
        PurchaseOrder po = poRepository.findById(extension.getEntityId()).orElse(null);

        POExtension newPOextension  = poExtensionRepository.save(POExtension.of(0l, extension.getOldEndDate(), extension.getEndDate() ));
        po.setPOExtension(newPOextension);

        getPlantInventoryItemForPeriod(BusinessPeriod.of(po.getRentalPeriod().getEndDate().plusDays(1), extension.getOldEndDate()), po.getPlant().getId());

//        po.requestExtension(POExtension.of(0l, extension.getOldEndDate(), extension.getEndDate()));
        BusinessPeriod newPeriod = BusinessPeriod.of(po.getRentalPeriod().getStartDate(), extension.getEndDate());
        po.setRentalPeriod(newPeriod);

        ExtendReservationPeriod(po, newPeriod);


        po.setTotal(ComputePrice(po));
        po.setStatus(POStatus.PENDING_EXTENSION);
        poRepository.save(po);
    }

    public PurchaseOrderDTO acceptPOExtension(Long id) throws Exception {
        PurchaseOrder po = poRepository.findById(id).orElse(null);
        po.setExtension(null);
        po.setStatus(POStatus.OPEN);
//        extensionRepository.deleteById(po.get);

//        updateBuiltItPOStatus(po, POStatus.OPEN);
        updateBuiltItPOStatus(po, po.getStatus());
        poRepository.save(po);
        return purchaseOrderAssembler.toModel(po);
    }

    public PurchaseOrderDTO rejectPOExtension(Long id) throws Exception {
        PurchaseOrder po = poRepository.findById(id).orElse(null);
        BusinessPeriod newPeriod = BusinessPeriod.of(po.getRentalPeriod().getStartDate(), po.getExtension().getOldEndDate());
        po.setRentalPeriod(newPeriod);

        ExtendReservationPeriod(po, newPeriod);

        po.setStatus(POStatus.REJECT_EXTENSION);
        po.setTotal(ComputePrice(po));

        updateBuiltItPOStatus(po, po.getStatus());
        poRepository.save(po);
        return purchaseOrderAssembler.toModel(po);
    }

    private void ExtendReservationPeriod(PurchaseOrder po, BusinessPeriod newPeriod) {
        List<Long> reservationIds = po.getReservations().stream().map(c -> c.getId()).collect(Collectors.toList());
        List<PlantReservation> oldReservations = reservationRepository.findAllById(reservationIds);

        for (PlantReservation reservation : oldReservations) {
            reservation.setSchedule(newPeriod);
        }
        reservationRepository.saveAll(oldReservations);
    }

    public PlantInventoryItemDTO returnPlant(Long id) throws Exception {
        PlantInventoryItem pi = plantInventoryItemRepository.findById(id).orElse(null);
        if(pi.getLocation() == PlantLocation.DISPATCHED) {
            pi.setLocation(PlantLocation.RETURNED);
            plantInventoryItemRepository.save(pi);
        }
        return  plantInventoryItemAssembler.toModel(pi);
    }

    public PlantInventoryItemDTO dispatchPlant(Long id) throws Exception {
        PlantInventoryItem pi = plantInventoryItemRepository.findById(id).orElse(null);
        if(pi.getLocation() == PlantLocation.IN_STORE) {
            pi.setLocation(PlantLocation.DISPATCHED);
            plantInventoryItemRepository.save(pi);
        }
        return  plantInventoryItemAssembler.toModel(pi);
    }

    public List<PlantInventoryItemDTO> loadPlants() {
        List<PlantInventoryItem> entries = plantInventoryItemRepository.findAll();
        return plantInventoryItemAssembler.toModels(entries);
    }

    public void  submitInvoiceRemittance(RemittanceDTO remittanceDTO){

        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String currentPrincipalName = authentication.getName();

        PurchaseOrder po = poRepository.findById(remittanceDTO.getPoId()).orElse(null);

        Remittance rem = remittanceRepository.save(Remittance.of(0l, remittanceDTO.getAmount(), remittanceDTO.getDescription()));

        Remittance remSaved = remittanceRepository.save(rem);

        po.setRemittance(remSaved);
        UpdateReservedPlantLocation(po, null);
        po.setStatus(POStatus.CLOSED);

        poRepository.save(po);
    }

    public PurchaseOrderDTO submitInvoiceforReturnedPO(Long id ) {

        PurchaseOrder po = poRepository.findById(id).orElse(null);

        BigDecimal total = computePOPrice(po);
        Invoice inv = Invoice.of(0l, po.getId(), total);
        Invoice newInvoice = invoiceRepository.save(inv);

        po.setInvoice(newInvoice);
        UpdateReservedPlantLocation(po, null);
        po.setStatus(POStatus.INVOICED);

        ClientAccount clientAccount =  clientAccountRepository.findByUserName(po.getCreatedby()).orElse(null);

        if(clientAccount == null) return null;

        restTemplate = new RestTemplate(autheticationService.getClientHttpRequestFactory(clientAccount.getClientSystemAccount(), clientAccount.getPassword()));

        final String baseUrl = clientAccount.getInvoicingUrl();

        ResponseEntity<Object> plants = restTemplate.postForEntity(baseUrl, newInvoice, Object.class);

        poRepository.save(po);

        return purchaseOrderAssembler.toModel(po);
    }
}
