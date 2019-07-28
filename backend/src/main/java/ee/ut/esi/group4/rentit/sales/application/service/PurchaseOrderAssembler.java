package ee.ut.esi.group4.rentit.sales.application.service;

import ee.ut.esi.group4.rentit.common.application.dto.BusinessPeriodDTO;
import ee.ut.esi.group4.rentit.inventory.application.service.PlantInventoryEntryAssembler;
import ee.ut.esi.group4.rentit.inventory.domain.model.PlantReservation;
import ee.ut.esi.group4.rentit.sales.application.dto.PurchaseOrderDTO;
import ee.ut.esi.group4.rentit.sales.domain.PlantLocation;
import ee.ut.esi.group4.rentit.sales.domain.PurchaseOrder;
import ee.ut.esi.group4.rentit.sales.rest.SalesRestController;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.server.mvc.ControllerLinkBuilder;
import org.springframework.hateoas.server.mvc.RepresentationModelAssemblerSupport;
import org.springframework.http.HttpMethod;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;


@Service
public class PurchaseOrderAssembler extends RepresentationModelAssemblerSupport<PurchaseOrder, PurchaseOrderDTO> {

    @Autowired
    PlantInventoryEntryAssembler plantInventoryEntryAssembler;

    @Autowired
    PlantInventoryItemAssembler plantInventoryItemAssembler;

    public PurchaseOrderAssembler() {
        super(SalesRestController.class, PurchaseOrderDTO.class);
    }

    @Override
    public PurchaseOrderDTO toModel(PurchaseOrder po) {

        PurchaseOrderDTO dto = createModelWithId(po.getId(), po);
        dto.setStatus(po.getStatus());
        dto.set_id(po.getId());
        dto.setCreatedby(po.getCreatedby());
        dto.setTotal(po.getTotal());
        dto.getReservations().addAll(plantInventoryItemAssembler.toModels(po.getReservations().stream().map(item -> item.getPlant()).collect(Collectors.toList())));
        dto.setRentalPeriod(BusinessPeriodDTO.of(po.getRentalPeriod().getStartDate(), po.getRentalPeriod().getEndDate()));
        dto.setPlant(plantInventoryEntryAssembler.toModel(po.getPlant()));

        dto.add(ControllerLinkBuilder.linkTo(ControllerLinkBuilder.methodOn(SalesRestController.class)
                .fetchPurchaseOrder(dto.get_id())).withRel("fetch"));
        try {
            switch (po.getStatus()) {
                case PENDING:
                    dto.add(ControllerLinkBuilder.linkTo(ControllerLinkBuilder.methodOn(SalesRestController.class)
                            .acceptPurchaseOrder(dto.get_id())).withRel("accept")
                            .withType(HttpMethod.POST.toString()));
                    dto.add(ControllerLinkBuilder.linkTo(ControllerLinkBuilder.methodOn(SalesRestController.class)
                            .rejectPurchaseOrder(dto.get_id())).withRel("reject")
                            .withType(HttpMethod.DELETE.toString()));


                    break;
                case PENDING_EXTENSION:
                    dto.add(ControllerLinkBuilder.linkTo(ControllerLinkBuilder.methodOn(SalesRestController.class)
                            .acceptPurchaseOrderExtension(dto.get_id())).withRel("accept-Ext")
                            .withType(HttpMethod.POST.toString()));
                    dto.add(ControllerLinkBuilder.linkTo(ControllerLinkBuilder.methodOn(SalesRestController.class)
                            .rejectPurchaseOrderExtension(dto.get_id())).withRel("reject-Ext")
                            .withType(HttpMethod.DELETE.toString()));
                    break;
                case OPEN:
                case REJECT_EXTENSION:
                    boolean isReturned = true;
                    for(PlantReservation res : po.getReservations()){
                        isReturned = isReturned && (res.getPlant().getLocation() == PlantLocation.RETURNED);
                    }
                    if(isReturned) {
                        dto.add(ControllerLinkBuilder.linkTo(ControllerLinkBuilder.methodOn(SalesRestController.class)
                                .submitInvoiceforReturnedPO(dto.get_id())).withRel("Invoice-PO")
                                .withType(HttpMethod.POST.toString()));
                    }


//                    switch (po.getLocation()) {
//                        case DISPATCHED:
//
//                        dto.add(ControllerLinkBuilder.linkTo(ControllerLinkBuilder.methodOn(SalesRestController.class)
//                                .returnPlant(dto.get_id())).withRel("return-Plants")
//                                .withType(HttpMethod.POST.toString()));
//                        case RETURNED:
//                            dto.add(ControllerLinkBuilder.linkTo(ControllerLinkBuilder.methodOn(SalesRestController.class)
//                                    .dispatchPlant(dto.get_id())).withRel("return-Plants")
//                                    .withType(HttpMethod.POST.toString()));
//                            break;
//                        default:
//                            break;
//                    }
                    break;
                default:
                    break;
            }
        } catch (Exception e) {}

        return dto;
    }

    public List<PurchaseOrderDTO> toModels(List<PurchaseOrder> pos) {

        return pos.stream().map(po -> toModel(po)).collect(Collectors.toList());
    }
}
