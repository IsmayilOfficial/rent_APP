package ee.ut.esi.group4.rentit.sales.application.service;

import ee.ut.esi.group4.rentit.inventory.application.service.PlantInventoryEntryAssembler;
import ee.ut.esi.group4.rentit.inventory.domain.model.PlantInventoryItem;
import ee.ut.esi.group4.rentit.sales.application.dto.PlantInventoryItemDTO;
import ee.ut.esi.group4.rentit.sales.domain.PlantLocation;
import ee.ut.esi.group4.rentit.sales.rest.SalesRestController;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.server.mvc.ControllerLinkBuilder;
import org.springframework.hateoas.server.mvc.RepresentationModelAssemblerSupport;
import org.springframework.http.HttpMethod;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;


@Service
public class PlantInventoryItemAssembler extends RepresentationModelAssemblerSupport<PlantInventoryItem, PlantInventoryItemDTO> {

    @Autowired
    PlantInventoryEntryAssembler plantInventoryEntryAssembler;

    public PlantInventoryItemAssembler() {
        super(SalesRestController.class, PlantInventoryItemDTO.class);
    }

    @Override
    public PlantInventoryItemDTO toModel(PlantInventoryItem pii) {

        PlantInventoryItemDTO dto = createModelWithId(pii.getId(), pii);
        dto.setLocation(pii.getLocation() == null? PlantLocation.IN_STORE : pii.getLocation());
        dto.setId(pii.getId());
        dto.setSerialNumber(pii.getSerialNumber());
//        dto.setEquipmentCondition(po.getTotal());
        dto.setPlantInfo(plantInventoryEntryAssembler.toModel(pii.getPlantInfo()));


//        dto.add(ControllerLinkBuilder.linkTo(ControllerLinkBuilder.methodOn(SalesRestController.class)
//                .fetchPurchaseOrder(dto.getId())).withRel("fetch"));
        try {
//            if(pii.getLocation() == null){
//                dto.add(ControllerLinkBuilder.linkTo(ControllerLinkBuilder.methodOn(SalesRestController.class)
//                        .dispatchPlant(dto.getId())).withRel("dispatch-Plants")
//                        .withType(HttpMethod.POST.toString()));
//            }
                    switch (pii.getLocation()) {
                        case IN_STORE:
                            dto.add(ControllerLinkBuilder.linkTo(ControllerLinkBuilder.methodOn(SalesRestController.class)
                                    .dispatchPlant(dto.getId())).withRel("dispatch-Plants")
                                    .withType(HttpMethod.POST.toString()));
                            break;
                        case DISPATCHED:

                            dto.add(ControllerLinkBuilder.linkTo(ControllerLinkBuilder.methodOn(SalesRestController.class)
                                    .returnPlant(dto.getId())).withRel("return-Plants")
                                    .withType(HttpMethod.POST.toString()));
                            break;
                        case RETURNED:
                            dto.add(ControllerLinkBuilder.linkTo(ControllerLinkBuilder.methodOn(SalesRestController.class)
                                    .dispatchPlant(dto.getId())).withRel("dispatch-Plants")
                                    .withType(HttpMethod.POST.toString()));
                            break;
                        default:
                            break;
                    }

        } catch (Exception e) {}

        return dto;
    }

    public List<PlantInventoryItemDTO> toModels(List<PlantInventoryItem> pis) {

        return pis.stream().map(pi -> toModel(pi)).collect(Collectors.toList());
    }
}
