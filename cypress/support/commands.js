Cypress.Commands.add('login', (username, password) => {
  cy.visit('/login');
  cy.get('input[name="username"]').type(username);
  cy.get('input[name="password"]').type(password);
  cy.get('input[type="submit"]').click();
  cy.url().should('include', '/userManagement').then((url) => {
    cy.log('Successfully logged in and redirected to:', url);
  });
});
