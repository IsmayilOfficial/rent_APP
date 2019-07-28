package ee.ut.esi.group4.rentit.inventory.rest;

import ee.ut.esi.group4.rentit.inventory.application.dto.PlantInventoryEntryDTO;
import ee.ut.esi.group4.rentit.inventory.application.service.InventoryService;
import ee.ut.esi.group4.rentit.inventory.domain.model.PurchaseOrderPostData;
import ee.ut.esi.group4.rentit.inventory.domain.model.StatusMessagingData;
import ee.ut.esi.group4.rentit.sales.application.dto.POExtensionDTO;
import ee.ut.esi.group4.rentit.sales.application.dto.PurchaseOrderDTO;
import ee.ut.esi.group4.rentit.sales.application.dto.RemittanceDTO;
import ee.ut.esi.group4.rentit.sales.application.service.SalesService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;

@RestController
@CrossOrigin(origins = "*", allowedHeaders = "*")
@RequestMapping("/api/inventory")
public class InventoryRestController {

    @Autowired
    InventoryService inventoryService;

    @Autowired
    SalesService salesService;

    @GetMapping("/plants")
    public List<PlantInventoryEntryDTO> findAvailablePlants(
            @RequestParam(name = "name") String plantName,
            @RequestParam(name = "startDate") @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate startDate,
            @RequestParam(name = "endDate") @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate endDate
    ) {
        return inventoryService.findAvailablePlants(plantName.toLowerCase(), startDate, endDate);
    }

    @GetMapping("/orders")
    @ResponseStatus(HttpStatus.OK)
    public List<PurchaseOrderDTO> fetchPurchaseOrders() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String currentPrincipalName = authentication.getName();

        return salesService.findPurchaseOrderByCreator(currentPrincipalName);
    }

    @GetMapping("{id}")
    public PlantInventoryEntryDTO getInventoryById(@PathVariable long id) throws Exception {
        return inventoryService.findPlantById(id);
    }

    @PostMapping("/createpo")
    public ResponseEntity<PurchaseOrderDTO> createPurchaseOrder(@RequestBody PurchaseOrderPostData data) throws Exception {

        PurchaseOrderDTO newPODTO = salesService.createPurchaseOrderForBuiltIt(data.getPlantItemId(), data.getPeriod());

        return new ResponseEntity<PurchaseOrderDTO>(newPODTO, HttpStatus.CREATED);
    }

    @PostMapping("/orders/statusupdate")
    public HttpStatus updatePORequestState(@RequestBody StatusMessagingData data) throws Exception {

        salesService.updatePOStateRequest(data);

        return  HttpStatus.ACCEPTED;
    }

    @PostMapping("/orders/extension")
    public HttpStatus requestPurchaseOrderExtension(@RequestBody POExtensionDTO extension) throws Exception {

        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        extension.setClient(authentication.getName());

        salesService.createPOExtension(extension);

        return  HttpStatus.CREATED;
    }

    @PostMapping("/orders/invoice/remittance")
    public HttpStatus submitInvoiceRemittance(@RequestBody RemittanceDTO remittanceDTO){

        salesService.submitInvoiceRemittance(remittanceDTO);
        return  HttpStatus.ACCEPTED;
    }

}
