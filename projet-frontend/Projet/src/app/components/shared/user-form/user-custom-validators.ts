import { AbstractControl, FormGroup, ValidationErrors } from '@angular/forms';
import { User } from '../types/user.type';
import { UserService } from '../../../services/user.service';
import { OnInit } from '@angular/core';

export class UserCustomValidators{
  private static _listUsers: User[] = [];


    /**
   * Met à jour la liste des utilisateurs avant validation
   */
    static setUsersList(users: User[]) {
      this._listUsers = users;
      console.log("setUsersList",this._listUsers)
    }

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


/**
 * Vérifie si le surname est déjà utilisé
 */
static utiliseUsername(control: AbstractControl): ValidationErrors | null {
  if (!control.value) {
    return null; // Pas de validation si le champ est vide
  }


  const usernameExists = UserCustomValidators._listUsers.some((user) => user.username === control.value);

  return usernameExists ? { utiliseUsername: true } : null;
}

/**
 * Vérifie si l'email est déjà utilisé
 */
static utiliseEmail(control: AbstractControl): ValidationErrors | null {
  if (!control.value) {
    return null; // Pas de validation si le champ est vide
  }

  const emailExists = UserCustomValidators._listUsers.some((user) => user.email === control.value);

  return emailExists ? { utiliseEmail: true } : null;
}







}
