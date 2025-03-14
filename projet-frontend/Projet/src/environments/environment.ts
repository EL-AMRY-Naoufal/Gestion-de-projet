// This file can be replaced during build by using the `fileReplacements` array.
// `ng build` replaces `environment.ts` with `environment.prod.ts`.
// The list of file replacements can be found in `angular.json`.

export const environment = {
  production: false,
  backend: {
    protocol: 'http',
    host: 'localhost',
    port: '8080',
    endpoints: {
      affectations: '/api/affectations',
      allCoAffectations: '/api/affectations/coAffectations',
      allUsers: '/api/responsableDepartement',
      oneUser: '/api/responsableDepartement/:id',
      allEnseignants: '/api/enseignants',
      authenticate: '/api/users/authenticate',
      categories: '/api/categories',
      role: '/api/responsableDepartement/users/by-role-and-year',
      justRole: '/api/responsableDepartement/users/by-just-role',
      allYears: '/api/annees',
      logout: '/api/users/user/logout',
      me: '/api/users/user/me',
      modules: '/api/modules',
      annees: '/api/annees',       // Notez la différence de nom avec allYears en dev
      departements: '/api/departements',
      niveaux: '/api/niveaux',
      semestres: '/api/semestres',
      groupes: '/api/groupes',
      responsableDepartement: '/api/responsableDepartement',
      formations: '/api/formations',
      allAffectation: '/api/affectations',  // Doublon avec affectations
      rolesAndYears: '/api/users/user/roles-and-years',

    },
  },
};

/*
 * For easier debugging in development mode, you can import the following file
 * to ignore zone related error stack frames such as `zone.run`, `zoneDelegate.invokeTask`.
 *
 * This import should be commented out in production mode because it will have a negative impact
 * on performance if an error is thrown.
 */
// import 'zone.js/plugins/zone-error';  // Included with Angular CLI.
