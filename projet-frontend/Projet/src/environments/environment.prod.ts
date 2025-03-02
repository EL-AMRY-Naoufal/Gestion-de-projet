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
      role : '/api/responsableDepartement/role',
      modules : '/api/modules',
      annees : '/api/annees',
      departements : '/api/departements',
      niveaux: '/api/niveaux',
      semestres: '/api/semestres',
      groupes: '/api/groupes',
      responsableDepartement: '/api/responsableDepartement',
      formations:'/api/formations',
      affectations:'/api/affectations',
      allAffectation: '/api/affectations',
      allCoAffectations: '/api/affectations/coAffectations',
      logout: '/api/users/user/logout',
      me: '/api/users/user/me'
    },
  },
};
