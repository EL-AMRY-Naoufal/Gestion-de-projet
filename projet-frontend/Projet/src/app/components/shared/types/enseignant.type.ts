import { User } from "./user.type";


export interface EnseignantDto {
    id?: number;
    user?: User;
    firstname: string;
    name: string;
    hasAccount: boolean;
    categorieEnseignant: CategorieEnseignant;
    nbHeureCategorie: number;
    maxHeuresService: number;
    heuresAssignees: HeuresAssignees[];
}


export enum CategorieEnseignant {
    EnseignantChercheur = 'ENSEIGNANT_CHERCHEUR',
    PRAG = 'PRAG',
    ATER = 'ATER',
    DCCE = 'DCCE',
    VACATAIRE = 'Vacataire',


}
export interface HeuresAssignees {
    annee: number;
    heures: number;
}