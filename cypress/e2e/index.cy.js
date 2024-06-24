describe('Index Page Tests', () => {
  beforeEach(() => {
    // Visits the index page before each test
    cy.visit('/');
  });

  it('should load the index page', () => {
    // Check for the presence of the Welcome header
    cy.get('h1').contains('Welcome to User Management Application');

    // Ensure the navigation links exist
    cy.get('a[href="login"]').should('exist');
    cy.get('a[href="signup"]').should('exist');
  });

  it('should navigate to login page when clicking the Login link', () => {
    // Click the Login link
    cy.get('a[href="login"]').click();

    // Check that the user is redirected to the login page
    cy.url().should('include', '/login');

    cy.get('form[name="loginForm"]').should('exist');

  });

  it('should navigate to sign up page when clicking the Sign Up link', () => {
    // Click the Sign Up link
    cy.get('a[href="signup"]').click();

    // Check that the user is redirected to the signup page
    cy.url().should('include', '/signup');

     cy.get('form[name="signUpForm"]').should('exist');

  });
});
