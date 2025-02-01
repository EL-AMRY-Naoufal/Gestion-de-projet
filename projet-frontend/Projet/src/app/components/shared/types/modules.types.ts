import {EnseignantDto} from "./enseignant.type";

export enum TypeHeure {
  CM = "CM",
  TD = "TD",
  TP = "TP",
  EI = "EI",
  TPL = "TPL",
}

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
  type: TypeHeure;
  affectations: Affectation[];
}

export interface Module {
  id?: number;
  nom: string;
  groupes: Groupe[];
  heuresParType: Map<string, number>;
  nombreGroupes: number;
}

export interface Semestre {
  id?: number;
  nom?: string;
  modules: Module[];
}

export interface Niveau {
  id?: number;
  nom?: string;
  semestres: Semestre[];
}

export interface Formation {
  id?: number;
  nom: string;
  responsableFormation: string;
  niveaux: Niveau[];
}

export interface Departement {
  id?: number;
  nom: string;
  formations: Formation[];
  responsableDeDepartement: string;
}

export interface Annee {
  id?: number;
  debut: number;
  departements: Departement[];
}
