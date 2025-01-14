import { AbstractControl, FormGroup, ValidationErrors } from '@angular/forms';

export class UserCustomValidators {
    /**
     * Validates if the password is strong enough
     * Password should have at least:
     * - one lowercase letter
     * - one uppercase letter
     * - one number
     * - one special character
     * - at least 6 characters in length
     */
    static strongPassword(control: AbstractControl): ValidationErrors | null {
      if (!control.value) {
        return null; // Pas de validation si le champ est vide
      }

      const passwordPattern = /^(?=.*[a-z])(?=.*[A-Z])(?=.*\d)(?=.*[@$!%*?&])[A-Za-z\d@$!%*?&]{6,}$/;
      return passwordPattern.test(control.value) ? null : { weakPassword: true };
    }

    /**
     * Custom validator to ensure that the email format is correct
     * (e.g., name.lastname@etu.univ-lorraine.fr)
     */
    static googleEmail(control: AbstractControl): ValidationErrors | null {
        const emailPattern = /^[a-zA-Z0-9._%+-]+\.[a-zA-Z0-9._%+-]+@[etu.univ\-lorraine]+\.[fr]{2,}$/;
        return emailPattern.test(control.value) ? null : { googleEmail: true };
    }

    /**
     * Validator to check if the password matches the confirmed password
     */
    static matchPasswords(control: AbstractControl): ValidationErrors | null {
      if (control instanceof FormGroup) {
        const password = control.get('password');
        const confirmPassword = control.get('confirmPassword');

        if (password && confirmPassword && password.value !== confirmPassword.value) {
          return { passwordMismatch: true };
        }
      }
      return null;
    }



}
