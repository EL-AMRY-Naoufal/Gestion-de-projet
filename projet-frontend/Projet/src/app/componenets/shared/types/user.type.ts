export interface User {
    id?: number;
    username: string;
    email: string;
    roles: Array<'CHEF_DE_DEPARTEMENT' | 'RESPONSABLE_DE_FORMATION' | 'SECRETARIAT_PEDAGOGIQUE' | 'ENSEIGNANT'>;
    password: string;
  }
