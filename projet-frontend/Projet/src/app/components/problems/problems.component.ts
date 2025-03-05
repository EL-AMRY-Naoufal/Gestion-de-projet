import {Component, OnInit} from '@angular/core';
import {ModuleService} from "../../services/module.service";
import {Groupe, Module} from "../shared/types/modules.types";
import {GroupeService} from "../../services/groupe.service";
import {NgForOf} from "@angular/common";

@Component({
  selector: 'app-problems',
  standalone: true,
  imports: [
    NgForOf
  ],
  templateUrl: './problems.component.html',
  styleUrl: './problems.component.scss'
})
export class ProblemsComponent implements OnInit{

  groupeWithLowHours: Groupe[] = [];
  constructor(
    public groupeService: GroupeService,
  ) {
   this.loadModulesWithsLowHours();
  }

  loadModulesWithsLowHours() {
    this.groupeService.getAllGroupes().subscribe((groupes:Groupe[]) => {
      this.groupeWithLowHours =groupes ;
      //filter modules with low hours
      this.groupeWithLowHours = this.groupeWithLowHours.filter(groupes => groupes.heuresAffectees < groupes.totalHeuresDuGroupe);
    });
  }

  ngOnInit(): void {
    console.log('init');
  }

}
