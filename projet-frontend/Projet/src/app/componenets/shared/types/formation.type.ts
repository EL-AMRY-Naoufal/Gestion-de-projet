import { ModuleType } from "../../../types/module.type";

export type FormationType = {
    id: string;
    nom: string;
    totalHeures: number;
    responsableFormation: string;
    modules: ModuleType[] | null;
    niveaux: string | null;   
}