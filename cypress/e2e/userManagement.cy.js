describe('User Management Page', () => {

    beforeEach(() => {
        // Assuming the user is already logged in and visiting the user management page
        cy.visit('/userManagement');
    });

    it('should display the user management page with navigation links', () => {
        cy.get('h1').should('contain', 'User Management');
        cy.get('.navigation a').eq(0).should('contain', 'User Listing');
        cy.get('.navigation a').eq(1).should('contain', 'Create User');
    });

});
