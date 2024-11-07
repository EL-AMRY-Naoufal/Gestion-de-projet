export const environment = {
  production: true,
  backend: {
    protocol: 'http',
    host: 'localhost',
    port: '8080',
    endpoints: {
      allUsers: '/api/users',
      oneUser: '/api/users/:id',
    },
  },
};
