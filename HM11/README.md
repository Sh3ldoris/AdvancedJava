# HW 11 - JDBC Report generating application

The goal of the assignment is to print out a report based on the data stored in a SQL database.

The database schema contains two tables that keep track of sale agents and their customers. In particular, 
the AGENTS table contains columns `AGENT_CODE, AGENT_NAME`, and `WORKING_AREA` and the table `CUSTOMER` 
contains columns `CUST_NAME, PHONE_NO`, and `AGENT_CODE` which is a foreign key referencing the sales 
agent who is making business with the particular customer.

The application prints out the report for the given area provided as the third argument. The report starts 
with a summary and continues with a list of agents operating within the given area. For each agent, all customers
are listed with their phone numbers. Sort both agents and customers lexicographically by name.

### Example

Given the input arguments
``schema.sql data.sql Bangalore ``
the program will produce the following output
```
2 agents in Bangalore, 3 customers
Agent: Ramasundar
  Customer: Srinivas, Phone: AAAAAAB
  Customer: Venkatpati, Phone: JRTVFDD
Agent: Subbarao
  Customer: Rangarappa, Phone: AAAATGF
  ```
