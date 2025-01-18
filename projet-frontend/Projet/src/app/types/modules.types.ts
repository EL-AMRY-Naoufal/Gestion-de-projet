export interface Groupe {
  id?: string;
  nom: string;
  heures: number;
}

export interface Module {
  id?: string;
  nom: string;
  totalHeuresRequises: number;
  groupes: Groupe[];
  heuresParType:Map<string, number>;
}

export interface Semestre {
  label?: string;
  modules: Module[];
}

export interface Orientation {
  label?: string;
  semestres: Semestre[];
}

export interface Niveau {
  label?: string;
  orientations: Orientation[];
}

export interface Formation {
  id?: number;
  nom: string;
  totalHeures: number;
  responsableFormation: string;
  modules: Module[];
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