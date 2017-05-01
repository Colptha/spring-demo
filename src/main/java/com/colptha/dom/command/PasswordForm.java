package com.colptha.dom.command;

/**
 * Created by Colptha on 4/27/17.
 */
public class PasswordForm {
    private String currentPassword;
    private String newPassword;
    private String confirmPassword;

    public String getCurrentPassword() {
        return currentPassword;
    }

    public void setCurrentPassword(String currentPassword) {
        this.currentPassword = currentPassword;
    }

    public String getNewPassword() {
        return newPassword;
    }

    void setNewPassword(String newPassword) {
        this.newPassword = newPassword;
    }

    public String getConfirmPassword() {
        return confirmPassword;
    }

    void setConfirmPassword(String confirmPassword) {
        this.confirmPassword = confirmPassword;
    }
}
