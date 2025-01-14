import { FormationType } from "./formation.type";

export type DepartementType = {
    id : string;
    nom: string;
    formations : FormationType[] | null;
    responsableDeDepartement : string;
}
