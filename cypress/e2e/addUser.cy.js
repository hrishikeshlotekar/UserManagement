describe('Add User Page Tests', () => {
  beforeEach(() => {
    // Visits the add user page before each test
    cy.visit('/addUser');
  });

  it('should load the addUser page', () => {
    // Check for the presence of the addUser form elements
    cy.get('input[name="name"]').should('exist');
    cy.get('input[name="email"]').should('exist');
    cy.get('input[name="password"]').should('exist');
    cy.get('input[name="confirmPassword"]').should('exist');
    cy.get('input[type="submit"]').should('exist');
  });

  it('should validate form fields', () => {
    // Try to submit the form without filling it out
    cy.get('input[type="submit"]').click();
    // Expect to see validation errors
    cy.on('window:alert', (txt) => {
      expect(txt).to.contains('All fields must be filled out');
    });
  });

  it('should display error for password mismatch', () => {
    // Fill out the form with mismatched passwords
    cy.get('input[name="name"]').type('John Doe');
    cy.get('input[name="email"]').type('john.doe@example.com');
    cy.get('input[name="password"]').type('password123');
    cy.get('input[name="confirmPassword"]').type('password456');
    cy.get('input[type="submit"]').click();

    // Expect to see a password mismatch error
    cy.on('window:alert', (txt) => {
      expect(txt).to.contains('Passwords do not match');
    });
  });

  it('should display error for duplicate email', () => {
    // Fill out the form with an existing email
    cy.get('input[name="name"]').type('John Doe');
    cy.get('input[name="email"]').type('existing.email@example.com'); // Use an email that already exists
    cy.get('input[name="password"]').type('password123');
    cy.get('input[name="confirmPassword"]').type('password123');
    cy.get('input[type="submit"]').click();

    // Expect to see a password mismatch error
    cy.on('window:alert', (txt) => {
      expect(txt).to.contains('Email already exists');
    });
  });

  it('should sign up successfully with valid data', () => {
    // Fill out the form with valid data
    cy.get('input[name="name"]').type('Jane Doe');
    cy.get('input[name="email"]').type('jane.doe@example.com');
    cy.get('input[name="password"]').type('password123');
    cy.get('input[name="confirmPassword"]').type('password123');
    cy.get('input[type="submit"]').click();
  });
});
