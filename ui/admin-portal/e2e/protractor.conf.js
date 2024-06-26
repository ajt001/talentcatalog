/*
 * Copyright (c) 2021 Talent Beyond Boundaries.
 *
 * This program is free software: you can redistribute it and/or modify it under
 * the terms of the GNU Affero General Public License as published by the Free
 * Software Foundation, either version 3 of the License, or any later version.
 *
 * This program is distributed in the hope that it will be useful, but WITHOUT
 * ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or
 * FITNESS FOR A PARTICULAR PURPOSE. See the GNU Affero General Public License
 * for more details.
 *
 * You should have received a copy of the GNU Affero General Public License
 * along with this program. If not, see https://www.gnu.org/licenses/.
 */

// Protractor configuration file, see link for more information
// https://github.com/angular/protractor/blob/master/lib/config.ts
const { SpecReporter } = require('jasmine-spec-reporter');
const httpMock = require('protractor-http-mock/lib/httpMock');
  exports.config = {
    allScriptsTimeout: 11000,
    specs: [
    './src/components/account/login/login.e2e.spec.ts',
    './src/**/*.e2e-spec.ts',
    './src/components/account/reset-password/reset-password.e2e.spec.ts',
    './src/components/settings/users/create-update-user.e2e.spec.ts',
    './src/components/account/change-password/change-password.e2e.spec.ts',
    './src/components/settings/users/cleanup.e2e.spec.ts',
  ],
  capabilities: {
    'browserName': 'chrome'
  },
  directConnect: true,
  baseUrl: 'http://localhost:4201',
  framework: 'jasmine',
  jasmineNodeOpts: {
    showColors: true,
    defaultTimeoutInterval: 30000,
    print: function() {}
  },
  onPrepare() {
    require('ts-node').register({
      project: require('path').join(__dirname, './tsconfig.e2e.json')
    });
   jasmine.getEnv().addReporter(new SpecReporter({ spec: { displayStacktrace: true } }));

    // Initialize protractor-http-mock
    const mocks = []; // Define your mocks here if needed
    const plugins = []; // Define your plugins here if needed
    const skipDefaults = false; // Set to true to skip default mocks and plugins

    httpMock(mocks, plugins, skipDefaults);
  }
};
