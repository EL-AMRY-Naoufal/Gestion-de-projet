import { User } from "./user.type";


export interface EnseignantDto {
    id?: number;
    user?: User;
    categorieEnseignant: CategorieEnseignant;
    nbHeureCategorie: number;
    maxHeuresService: number;
    heuresAssignees: number;
}


export enum CategorieEnseignant {
    PROFESSEUR = 'PROFESSEUR',
    DOCTORANT = 'DOCTORANT',
    MAITRE_CONFERENCES = 'MAITRE_CONFERENCES'
}
