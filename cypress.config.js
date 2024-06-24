const { defineConfig } = require("cypress");

module.exports = defineConfig({
  e2e: {
    baseUrl: "http://localhost:9097/UserManagement", // Set the base URL to match your local environment
    setupNodeEvents(on, config) {
      // Implement node event listeners here if needed
    },
    // Additional configuration options
    specPattern: "cypress/e2e/**/*.cy.js", // Location of your test files
    supportFile: "cypress/support/e2e.js", // Support file for custom commands and setup
    fixturesFolder: "cypress/fixtures", // Folder for fixture files
    screenshotsFolder: "cypress/screenshots", // Folder for screenshots
    videosFolder: "cypress/videos", // Folder for video recordings of tests
  },
});
