package org.nightingaale.userservice.event;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class KafkaPaymentTransactionEvent {
    private String aggregateId;
    private String transactionId;
    private String userId;
    private String amount;
    private String currency;
}
