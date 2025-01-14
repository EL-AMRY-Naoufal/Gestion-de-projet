import { DepartementType } from "./departement.type";

export type AnneeType = {
    id: number;
    debut: string;
    departements: DepartementType[] | null;   
}