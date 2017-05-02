package com.colptha.dom.validators;

import com.colptha.dom.command.ShipmentForm;
import com.colptha.dom.entities.ProductLot;
import com.colptha.dom.enums.ProductId;
import com.colptha.dom.validators.interfaces.ShipmentFormValidator;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;

/**
 * Created by Colptha on 4/19/17.
 */
@Component
public class ShipmentFormValidatorImpl implements ShipmentFormValidator {
    @Override
    public boolean supports(Class<?> aClass) {
        return ShipmentForm.class.equals(aClass);
    }

    @Override
    public void validate(Object o, Errors errors) {
        ShipmentForm shipmentForm = (ShipmentForm) o;
        if (shipmentForm.getShipmentType() == null) {
            errors.rejectValue("shipmentType", "ShipmentTypeNull", "Shipment Type required");
        }
        if (shipmentForm.getPossibleProductLots().size() != ProductId.values().length) {
            errors.rejectValue("possibleProductLots",
                    "PossibleProductLotsWrongSize",
                    "Possible Product Lots includes more or less than possible products");
        }

        boolean lotsAllEmpty = true;
        for (ProductLot lot : shipmentForm.getPossibleProductLots()) {
            if (lot.getQuantity() > 0) lotsAllEmpty = false;
            if (lot.getQuantity() < 0) {
                errors.rejectValue("possibleProductLots[" + lot.getProductId().ordinal() + "].quantity",
                        "PossibleProductLotsNegativeValue",
                        "Quantity cannot be negative");
            }

        }
        if (lotsAllEmpty) {
            errors.rejectValue("possibleProductLots",
                    "PossibleProductLotsEmpty",
                    "Shipment must contain inventories");
        }



    }
}
