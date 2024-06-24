describe('Login Page Tests', () => {
  beforeEach(() => {
    // Visits the login page before each test
    cy.visit('/login');
  });

  it('should load the login page', () => {
    // Ensure the login form exists
    cy.get('form[name="loginForm"]').should('exist');
  });

  it('should display error if username and password are empty', () => {
    // Click the submit button without filling the form
    cy.get('input[type="submit"]').click();

    // Validate the error message
    cy.on('window:alert', (str) => {
      expect(str).to.equal('Username and Password must be filled out');
    });
  });

  it('should display error if only username is filled', () => {
    // Fill the username and leave the password empty
    cy.get('input[name="username"]').type('testuser');
    cy.get('input[type="submit"]').click();

    // Validate the error message
    cy.on('window:alert', (str) => {
      expect(str).to.equal('Username and Password must be filled out');
    });
  });

  it('should display error if only password is filled', () => {
    // Fill the password and leave the username empty
    cy.get('input[name="password"]').type('testpassword');
    cy.get('input[type="submit"]').click();

    // Validate the error message
    cy.on('window:alert', (str) => {
      expect(str).to.equal('Username and Password must be filled out');
    });
  });

  it('should display error message on invalid login', () => {
    // Fill in invalid credentials
    cy.get('input[name="username"]').type('validuser@gmail.com');
    cy.get('input[name="password"]').type('password123');
    cy.get('input[type="submit"]').click();

    // Check for error message in the page
    cy.get('.error-messages').should('contain', 'Invalid username or password');
  });

  it('should login successfully with valid credentials', () => {
    // Fill in valid credentials
    cy.get('input[name="username"]').type('validuser');
    cy.get('input[name="password"]').type('validpassword');
    cy.get('input[type="submit"]').click();
  });

});
