export type ModuleType = {
    id: string;
    nom: string;
    totalHeuresRequises: number;
    groupes: number;
    heuresParType:Map<string, number>;
  };