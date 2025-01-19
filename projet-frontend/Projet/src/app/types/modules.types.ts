import { EnseignantDto } from "./enseignant.type";

export type Affectation = {
  id?: number;
  enseignant?: EnseignantDto;
  nomEnseignant?: string;
  heuresAssignees: number;
  dateAffectation?: string;
};

export interface Groupe {
  id?: number;
  nom: string;
  heures: number;
  affectations: Affectation[];
}

export interface Module {
  id?: number;
  nom: string;
  totalHeuresRequises: number;
  groupes: Groupe[];
  heuresParType:Map<string, number>;
}

export interface Semestre {
  id?: number;
  nom?: string;
  modules: Module[];
}

export interface Orientation {
  id?: number;
  nom?: string;
  semestres: Semestre[];
}

export interface Niveau {
  id?: number;
  nom?: string;
  orientations: Orientation[];
}

export interface Formation {
  id?: number;
  nom: string;
  totalHeures: number;
  responsableFormation: string;
  niveaux: Niveau[];
}

export interface Departement {
  id? : number;
  nom: string;
  formations : Formation[];
  responsableDeDepartement : string;
}

export interface Annee {
  id?: number;
  debut: string;
  departements: Departement[];
}