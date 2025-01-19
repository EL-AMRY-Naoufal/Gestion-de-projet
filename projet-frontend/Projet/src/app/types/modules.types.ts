
export type Affectation = {
  id?: string;
  enseignant?: string;
  heuresAssignees: number;
  dateAffectation?: string;
};

export interface Groupe {
  id?: string;
  nom: string;
  heures: number;
  affectations: Affectation[];
}

export interface Module {
  id?: string;
  nom: string;
  totalHeuresRequises: number;
  groupes: Groupe[];
  heuresParType:Map<string, number>;
}

export interface Semestre {
  nom?: string;
  modules: Module[];
}

export interface Orientation {
  nom?: string;
  semestres: Semestre[];
}

export interface Niveau {
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
