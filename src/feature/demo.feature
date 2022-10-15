Feature: Automated Testing Demo for MyObservatory Mobile App
  Description: Tests 9-Day Forecast Page

  @Demo @9DayForecast
  Scenario: 9-Day Forecast
    Given User is on homepage
    Then User checks 'Current Date'
    And User taps 'Menu' button
    And User taps '9-Day Forecast' button
    Then User checks '9-Day Forecast Page'

  @Demo @LocalForecast
  Scenario: Local Forecast
    Given User is on homepage
    Then User checks 'Current Date'
    And User taps 'Menu' button
    And User taps '9-Day Forecast' button
    Then User checks '9-Day Forecast Page'
    And User taps 'Local Forecast' button
    Then User checks 'Local Forecast Page'

  @Demo @ExtendedOutlook
  Scenario: Extended Outlook
    Given User is on homepage
    Then User checks 'Current Date'
    And User taps 'Menu' button
    And User taps '9-Day Forecast' button
    Then User checks '9-Day Forecast Page'
    And User taps 'Extended Outlook' button
    Then User checks 'Extended Outlook Page'

