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
    EnseignantChercheur = 'Enseignant_Chercheur',
    PRAG = 'PRAG',
    ATER = 'ATER',
    DCCE = 'DCCE',
    VACATAIRE = 'Vacataire',


}
