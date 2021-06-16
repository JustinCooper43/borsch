package com.nayax.borsch.model.dto.user.response;

import com.nayax.borsch.model.dto.user.response.nested.CreditCardDto;

public class RespCashierDto {
    //TODO unmock values
    private Boolean cashPaymentAllowed = true;
    private CreditCardDto creditCard = new CreditCardDto();
    private String qrCode = "data:image/png;base64,XDg5UE5HCgpcMDBcMDBcMDAKSUhEUlwwMFwwMFwwMFxDOFwwMFwwMFwwMFxDOFwwMFwwMFwwMFw5N1w5NjxcRERcMDBcMDBcMDBQTFRFXEZGXEZGXEZGXDAwXDAwXDAwVVxDMlxEM35cMDBcMDBcMDAJcEhZc1wwMFwwMFxDNFwwMFwwMFxDNFw5NStcMDBcMDBcMDBcQTVJREFUWFw4NVxFRFw5NVFcODAwQ1xCOVxGRlxBNWsyClxDQ1xFOHxcOEIkXDkyXEMxXEUzXEE3XEFCfDxcOTRcOTFcODdcQTlcQjhcQTRcQjIyXEREekhcQjJcQkFcRUMKT3x955CZXDgwXDkzXENBXEM5X3ZcOTRFXEVDClxBRFxGNlxFMSEsXEQyXEYxXEVDIFxDOSo1ZjFcOThJIlvaplxEMGRcRjNcQURuXyBcOTRcRjhhSTNAJmFcRkZcQkZcQTFJClxFNFxGN2IkLFxEMk5cREXsvok4XEUyXEVDXEJGUlw5OSBcOThcRjhcOUNKN1xBOXxcQkVcQzE4XDgxXEQ0TlxCNlxGN1FcQzlcQzZzWylcOTJYWylWCkNcQzlcOEVcQTJcQjA5KjNcQzdCUlwwMFwwMFwwMFwwMElFTkRcQUVCYFw4Mg";

    public Boolean getCashPaymentAllowed() {
        return cashPaymentAllowed;
    }

    public void setCashPaymentAllowed(Boolean cashPaymentAllowed) {
        this.cashPaymentAllowed = cashPaymentAllowed;
    }

    public CreditCardDto getCreditCard() {
        return creditCard;
    }

    public void setCreditCard(CreditCardDto creditCard) {
        this.creditCard = creditCard;
    }

    public String getQrCode() {
        return qrCode;
    }

    public void setQrCode(String qrCode) {
        this.qrCode = qrCode;
    }
}
