import { CategorieEnseignant } from "../componenets/shared/types/enseignant.type";

export interface User {
    id?: number;
    username: string;
    password: string;
    email: string;
    roles: UserRoleDto[];
    categorieEnseignant?: CategorieEnseignant;
    nbHeureCategorie?: number;
    maxHeuresService?: number;
}


export interface UserRoleDto {
  yearId: number; // Correspond au champ `Long yearId` dans le Java
  role: 'CHEF_DE_DEPARTEMENT' | 'RESPONSABLE_DE_FORMATION' | 'SECRETARIAT_PEDAGOGIQUE' | 'ENSEIGNANT';  // Utilisation du type `Role` défini précédemment
}



