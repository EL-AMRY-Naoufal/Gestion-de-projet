import { CategorieEnseignant } from "./enseignant.type";

export interface User {
    id?: number;
    username: string;
    firstname: string;
    name: string;
    password: string;
    email: string;
    roles: UserRoleDto[];
    categorieEnseignant?: CategorieEnseignant;
    nbHeureCategorie?: number;
    maxHeuresService?: number;
    hasProfile?: boolean;
}


export interface UserRoleDto {
  year: number; // Correspond au champ `Long yearId` dans le Java
  role: Roles;  // Utilisation du type `Role` défini précédemment
}

export type Roles = 'CHEF_DE_DEPARTEMENT' | 'RESPONSABLE_DE_FORMATION' | 'SECRETARIAT_PEDAGOGIQUE' | 'ENSEIGNANT';


