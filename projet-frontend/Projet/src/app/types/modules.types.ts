export interface Groupe {
  id?: string;
  nom: string;
  heures: number;
}

export interface Module {
  label?: string;
  groupes: Groupe[];
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
  label?: string;
  niveaux: Niveau[];
}

export interface Departement {
  label?: string;
  formations: Formation[];
}

export interface Annee {
  label?: string;
  departements: Departement[];
}
