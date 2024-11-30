export interface User {
    id: number;
    username: string;
    password: string; 
    email: string;
    role: 'CHEF_DE_DEPARTEMENT' | 'RESPONSABLE_DE_FORMATION' | 'SECRETARIAT_PEDAGOGIQUE' | 'ENSEIGNANT';
}

