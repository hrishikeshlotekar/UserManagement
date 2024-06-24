describe('User Listing Page Tests', () => {
    // Before each test, we navigate to the user listing page
    beforeEach(() => {
        cy.visit('/listUsers');
    });

    it('should display the user listing table with users', () => {
        // Check if the table is present
        cy.get('table').should('be.visible');

        // Check if the table has the correct headers
        cy.get('thead tr th').should('have.length', 4);
        cy.get('thead tr th').eq(0).should('have.text', 'User ID');
        cy.get('thead tr th').eq(1).should('have.text', 'Name');
        cy.get('thead tr th').eq(2).should('have.text', 'Email');
        cy.get('thead tr th').eq(3).should('have.text', 'Actions');
    });

    it('should navigate to the next and previous page', () => {
            // Initial page assertions
            cy.get('.pagination').should('be.visible');
            cy.get('.pagination a').contains('Next').should('not.have.class', 'active');

            // Click on the "Next" link and verify the page navigation
            cy.get('.pagination a').contains('Next').click();
            cy.url().should('include', 'page=1'); // Assuming pagination starts from page 0
            cy.get('.pagination a').contains('Previous').should('not.have.class', 'active');

            // Click on the "Previous" link and verify the page navigation
            cy.get('.pagination a').contains('Previous').click();
            cy.url().should('include', 'page=0');
    });

    it('should delete a user successfully', () => {
            // Assuming the user list is not empty and we have at least one user to delete

            // Find the first user row and get the user ID
            cy.get('tbody tr').first().as('firstUserRow');
            cy.get('@firstUserRow').find('td').eq(0).invoke('text').then((userId) => {
                // Click on the delete button of the first user
                cy.get('@firstUserRow').find('form').submit();

                // Confirm the deletion in the dialog
                cy.on('window:confirm', () => true);

                // Verify that the user is no longer in the list
                cy.get('tbody tr').each(($el) => {
                    cy.wrap($el).find('td').eq(0).should('not.have.text', userId);
                });
            });
        });

});
