describe('Sign Up Page Tests', () => {
  beforeEach(() => {
    // Visits the signup page before each test
    cy.visit('/signup');
  });

  it('should load the signup page', () => {
    // Check for the presence of the sign up form elements
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

    it('should display error for invalid email format', () => {
      // Fill out the form with an invalid email
      cy.get('input[name="name"]').type('John Doe');
      cy.get('input[name="email"]').type('john.doe');
      cy.get('input[name="password"]').type('password123');
      cy.get('input[name="confirmPassword"]').type('password123');
      cy.get('input[type="submit"]').click();

      // Expect to see an invalid email format error
      cy.on('window:alert', (txt) => {
        expect(txt).to.contains('Invalid email format');
      });
    });

    it('should display error for short password', () => {
      // Fill out the form with a short password
      cy.get('input[name="name"]').type('John Doe');
      cy.get('input[name="email"]').type('john.doe@example.com');
      cy.get('input[name="password"]').type('short');
      cy.get('input[name="confirmPassword"]').type('short');
      cy.get('input[type="submit"]').click();

      // Expect to see a short password error
      cy.on('window:alert', (txt) => {
        expect(txt).to.contains('Password must be at least 6 characters');
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
