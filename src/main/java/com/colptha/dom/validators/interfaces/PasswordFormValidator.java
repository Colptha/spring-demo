package com.colptha.dom.validators.interfaces;

import com.colptha.dom.command.PasswordForm;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.validation.BindingResult;
import org.springframework.validation.Validator;

/**
 * Created by Colptha on 5/1/17.
 */
public interface PasswordFormValidator extends Validator {
    void checkCurrentPassword(PasswordForm passwordForm, BindingResult bindingResult, UserDetails userDetails);
}
