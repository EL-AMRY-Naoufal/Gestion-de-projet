export const environment = {
  production: true,
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
      allmodules: '/api/modules',
      annees: '/api/annees',
      departements: '/api/departements',
      niveaux: '/api/niveaux',
      semestres: '/api/semestres',
      groupes: '/api/groupes',
    },
  },
};
