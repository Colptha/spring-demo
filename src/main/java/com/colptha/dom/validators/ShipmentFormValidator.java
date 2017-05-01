package com.colptha.dom.validators;

import com.colptha.dom.command.ShipmentForm;
import com.colptha.dom.entities.ProductLot;
import com.colptha.dom.enums.ProductId;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

/**
 * Created by Colptha on 4/19/17.
 */
@Component
public class ShipmentFormValidator implements Validator {
    @Override
    public boolean supports(Class<?> aClass) {
        return ShipmentForm.class.equals(aClass);
    }

    @Override
    public void validate(Object o, Errors errors) {
        ShipmentForm shipmentForm = (ShipmentForm) o;

        if (shipmentForm.getPossibleProductLots().size() != ProductId.values().length) {
            errors.rejectValue("possibleProductLots",
                    "PossibleProductLotsWrongSize",
                    "Possible Product Lots includes more or less than possible products");
        }

        for (ProductLot lot : shipmentForm.getPossibleProductLots()) {
            if (lot.getQuantity() < 0) {
                errors.rejectValue("possibleProductLots",
                        "PossibleProductLotsNegativeValue",
                        "Quantity cannot be negative");
            }
        }

    }
}
