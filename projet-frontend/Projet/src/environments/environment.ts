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
      allUsers: '/api/responsableDepartement',
      oneUser: '/api/responsableDepartement/:id',
      allEnseignants: '/api/enseignants',
      authenticate: '/api/users/authenticate',
      categories: '/api/categories',
      role: '/api/responsableDepartement/users/by-role-and-year',
      allYears: '/api/annees',
      logout: '/api/users/user/logout',
      me: '/api/users/user/me'
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
