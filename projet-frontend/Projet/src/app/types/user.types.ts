export interface User {
    id: number;
    username: string;
    password: string;
    email: string;
    role: UserRoleDto[];
}


export interface UserRoleDto {
  yearId: number; // Correspond au champ `Long yearId` dans le Java
  role: 'CHEF_DE_DEPARTEMENT' | 'RESPONSABLE_DE_FORMATION' | 'SECRETARIAT_PEDAGOGIQUE' | 'ENSEIGNANT';  // Utilisation du type `Role` défini précédemment
}



