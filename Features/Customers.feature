Feature: Customers

 Background: Below are the common steps for each scenario
	Given User Launch chrome browser
	When User opens URL "http://admin-demo.nopcommerce.com/login"
	And User enters Email as "admin@yourstore.com" and Password as "admin"
	And Click on Login
	Then User can view Dashboard

@Sanity	
Scenario: Add a new Customer
	
	When User click on customers Menu
	And click on customers Menu Item
	And click on Add new button
	Then user can view Add new customer page
	When User enter customer info
	And click on save button
	Then User can view confirmation message "The new customer has been added successfully"
	And close browser
	

@Regression
Scenario: Search Customer by EmailID
	
	When User click on customers Menu
	And click on customers Menu Item
	And Enter customer Email
	When Click on search button
	Then User should found Email in the Search table
	And close browser
	

@Regression
Scenario: Search Customer by Name
	
	When User click on customers Menu
	And click on customers Menu Item
	And Enter customer FirstName
	And Enter customer LastName
	When Click on search button
	Then User should found Name in the Search table
	And close browser
	
	
	