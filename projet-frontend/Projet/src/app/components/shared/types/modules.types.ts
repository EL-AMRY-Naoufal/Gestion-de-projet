import {EnseignantDto} from "./enseignant.type";
import { User } from "./user.type";

export enum TypeHeure {
  CM = "CM",
  TD = "TD",
  TP = "TP",
  EI = "EI",
  TPL = "TPL",
}

export type CoAffectation = {
  id: number;
  enseignantName: string;
  enseignantFirstName: string;
  groupeName: string;
  heuresAssignees: number;
  dateAffectation: string;
}

export type Affectation = {
  id?: number;
  heuresAssignees: number;
  enseignantId: number;
  groupeId: number;
  moduleId?: number;
  commentaire: string;
  dateAffectation: string;
};

export interface Groupe {
  id?: number;
  nom: string;
  type: TypeHeure;
  heuresAffectees: number;
  totalHeuresDuGroupe: number;
  moduleId: number;
}

export interface Module {
  id?: number;
  nom: string;
  heuresParType: Map<string, number>;
  semestreId: number;
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
