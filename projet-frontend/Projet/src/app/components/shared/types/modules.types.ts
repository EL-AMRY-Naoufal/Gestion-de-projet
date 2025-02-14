import {EnseignantDto} from "./enseignant.type";
import { User } from "./user.type";

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
  type: TypeHeure;
  heuresAffectees: number;
  totalHeuresDuGroupe: number;
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
  niveauId: number;
}

export interface Niveau {
  id?: number;
  nom?: string;
  formationId: number;
}

export interface Formation {
  id?: number;
  nom: string;
  responsableFormationId: number;
  departementId: number;
}

export interface Departement {
  id?: number;
  nom: string;
  responsableDepartementId: number;
  anneeId: number;
}

export interface Annee {
  id?: number;
  debut: number;
}

export interface ResponsableDepartement {
  id?: number;
  user?: User;
}