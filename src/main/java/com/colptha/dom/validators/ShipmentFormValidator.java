package com.colptha.dom.validators;

import com.colptha.dom.command.ShipmentForm;
import com.colptha.dom.enums.ProductId;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

/**
 * Created by Colptha on 4/19/17.
 */
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

        // somehow i need to check for the negative inventory problem
    }
}
