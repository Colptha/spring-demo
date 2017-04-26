package com.colptha.services.security;

/**
 * Created by Colptha on 4/26/17.
 */
public interface EncryptionService {
    String encryptPassword(String password);
    boolean matches(String password, String encryptedPassword);
}
