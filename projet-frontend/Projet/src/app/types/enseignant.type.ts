export interface EnseignantDto {
    id?: number; 
    categorie: CategorieEnseignant;
    maxHeuresService: number;
    heuresAssignees: number;
}


export enum CategorieEnseignant {
    PROFESSEUR = 'PROFESSEUR',
    DOCTORANT = 'DOCTORANT',
    MAITRE_CONFERENCES = 'MAITRE_CONFERENCES'
}