package cz.cuni.mff.java.hw.jdbc;

import java.sql.*;

public class Main {
    private static final String H2_MEM_URL = "jdbc:h2:mem:default";

    /**
     * Instantiate the H2 in-memory database and load it with the schema and data from provided scripts.
     *
     * The database will stay in the memory and keep its content until JVM is terminated.
     *
     * @param schemaScript sql script containing ddl commands to create database schema
     * @param dataScript sql script containing commands to insert data into tables
     * @throws SQLException sql exception
     */
    private static void prepareDatabase(String schemaScript, String dataScript) throws SQLException {
        Connection con = DriverManager.getConnection(
                String.format("%s;DB_CLOSE_DELAY=-1;INIT=runscript from '%s'\\;runscript from '%s'",
                        H2_MEM_URL, schemaScript, dataScript));
        con.close();
    }

    public static void main(String[] args) throws SQLException {

        String schemaScript = args[0];
        String dataScript = args[1];
        String location = args[2];
        prepareDatabase(schemaScript, dataScript);

        Connection conn = DriverManager.getConnection(H2_MEM_URL);

        // Done TODO read data from the database and generate the report
        int agentsCount = getAgentsCount(conn, location);
        if (agentsCount == 0) {
            System.out.printf("No agents in %s\n", location);
            return;
        }

        int customersCount = getCustomersCount(conn, location);
        // Print header of report
        System.out.printf("%d agents in %s, %d customers\n", agentsCount, location, customersCount);
        ResultSet agentsRS = getAgentsResultSet(conn, location);
        while (agentsRS.next()) {
            String agentsName = agentsRS.getString("AGENT_NAME");
            String agentsCode = agentsRS.getString("AGENT_CODE");
            System.out.printf("Agent: %s\n", agentsName);

            ResultSet customersRS = getCustomersResultSetByAgentsCodeAndLocation(conn, agentsCode, location);
            while (customersRS.next()) {
                String customersName = customersRS.getString("CUST_NAME");
                String customersPhone = customersRS.getString("PHONE_NO");
                System.out.printf("  Customer: %s, Phone: %s\n", customersName, customersPhone);
            }
        }
    }

    private static int getAgentsCount(Connection conn, String location) throws SQLException {
        Statement statement = conn.createStatement();
        ResultSet rs = statement.executeQuery("SELECT COUNT(*) AS COUNT FROM AGENTS WHERE WORKING_AREA='" + location + "'");
        int agentsCount = 0;
        if (rs.next()) {
            agentsCount = rs.getInt("COUNT");
        }
        statement.close();
        return agentsCount;
    }

    private static int getCustomersCount(Connection conn, String location) throws SQLException {
        Statement statement = conn.createStatement();
        ResultSet rs = statement.executeQuery("SELECT COUNT(*) AS COUNT FROM Customer WHERE WORKING_AREA='" + location + "'");
        int customersCount = 0;
        if (rs.next()) {
            customersCount = rs.getInt("COUNT");
        }
        statement.close();
        return customersCount;
    }

    private static ResultSet getAgentsResultSet(Connection conn, String location) throws SQLException {
        Statement statement = conn.createStatement();
        return statement.executeQuery("SELECT AGENT_NAME, AGENT_CODE FROM Agents WHERE WORKING_AREA='" + location + "'ORDER BY AGENT_NAME");
    }

    private static ResultSet getCustomersResultSetByAgentsCodeAndLocation(Connection conn, String code, String location) throws SQLException {
        Statement statement = conn.createStatement();
        return statement.executeQuery("SELECT CUST_NAME, PHONE_NO FROM Customer WHERE WORKING_AREA='" + location + "' AND AGENT_CODE='" + code + "' ORDER BY CUST_NAME");
    }
}
