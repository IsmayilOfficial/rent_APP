package ee.ut.esi.group4.rentit.sales.application.dto;

import lombok.Data;

import java.math.BigDecimal;

@Data
public class RemittanceDTO {
    Long Id;
    Long poId;
    BigDecimal amount;
    String description;

    public RemittanceDTO(Long poId, BigDecimal amount, String description){
        this.poId = poId;
        this.amount = amount;
        this.description = description;
    }
}