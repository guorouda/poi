package com.ron.dao;

import java.sql.Connection;
import java.sql.SQLException;

import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.sql.DataSource;

import com.ron.exceptions.DAOConfigurationException;

/**
 * This class represents a DAO factory for a SQL database. You can use {@link #getInstance(String)}
 * to obtain a new instance for the given database name. The specific instance returned depends on
 * the properties file configuration. You can obtain DAO's for the DAO factory instance using the 
 * DAO getters.
 * <p>
 * This class requires a properties file named 'dao.properties' in the classpath with among others
 * the following properties:
 * <pre>
 * name.url *
 * name.driver
 * name.username
 * name.password
 * </pre>
 * Those marked with * are required, others are optional and can be left away or empty. Only the
 * username is required when any password is specified.
 * <ul>
 * <li>The 'name' must represent the database name in {@link #getInstance(String)}.</li>
 * <li>The 'name.url' must represent either the JDBC URL or JNDI name of the database.</li>
 * <li>The 'name.driver' must represent the full qualified class name of the JDBC driver.</li>
 * <li>The 'name.username' must represent the username of the database login.</li>
 * <li>The 'name.password' must represent the password of the database login.</li>
 * </ul>
 * If you specify the driver property, then the url property will be assumed as JDBC URL. If you
 * omit the driver property, then the url property will be assumed as JNDI name. When using JNDI
 * with username/password preconfigured, you can omit the username and password properties as well.
 * <p>
 * Here are basic examples of valid properties for a database with the name 'javabase':
 * <pre>
 * javabase.jdbc.url = jdbc:mysql://localhost:3306/javabase
 * javabase.jdbc.driver = com.mysql.jdbc.Driver
 * javabase.jdbc.username = java
 * javabase.jdbc.password = d$7hF_r!9Y
 * </pre>
 * <pre>
 * javabase.jndi.url = jdbc/javabase
 * </pre>
 * Here is a basic use example:
 * <pre>
 * DAOFactory javabase = DAOFactory.getInstance("javabase.jdbc");
 * UserDAO userDAO = javabase.getUserDAO();
 * </pre>
 *
 * @author BalusC
 * @link http://balusc.blogspot.com/2008/07/dao-tutorial-data-layer.html
 */
public abstract class DAOFactory {

    // Constants ----------------------------------------------------------------------------------

    private static final String PROPERTY_URL = "url";
    private static final String PROPERTY_DRIVER = "driver";
    private static final String PROPERTY_USERNAME = "username";
    private static final String PROPERTY_PASSWORD = "password";
    
    private static DAOFactory instance;

    // Actions ------------------------------------------------------------------------------------
    public static DAOFactory getInstance() {
    	return instance;
    }

    /**
     * Returns a new DAOFactory instance for the given database name.
     * @param name The database name to return a new DAOFactory instance for.
     * @return A new DAOFactory instance for the given database name.
     * @throws DAOConfigurationException If the database name is null, or if the properties file is
     * missing in the classpath or cannot be loaded, or if a required property is missing in the
     * properties file, or if either the driver cannot be loaded or the datasource cannot be found.
     */
    public static DAOFactory init(String name) throws DAOConfigurationException {
        if (name == null) {
            throw new DAOConfigurationException("Database name is null.");
        }
        System.out.println("\"DAOFactory getInstance\" is starting...");

        DAOProperties properties = new DAOProperties(name);
        String url = properties.getProperty(PROPERTY_URL, true);
        String driverClassName = properties.getProperty(PROPERTY_DRIVER, false);
        String password = properties.getProperty(PROPERTY_PASSWORD, false);
        String username = properties.getProperty(PROPERTY_USERNAME, password != null);
//        DAOFactory instance;

        // If driver is specified, then load it to let it register itself with DriverManager.
        if (driverClassName != null) {
            try {
                Class.forName(driverClassName);
            } catch (ClassNotFoundException e) {
                throw new DAOConfigurationException(
                    "Driver class '" + driverClassName + "' is missing in classpath.", e);
            }
            instance = new DriverManagerDAOFactory(url, username, password);
        }

        // Else assume URL as DataSource URL and lookup it in the JNDI.
        else {
            DataSource dataSource;
            try {
                System.out.println(url);
                dataSource = (DataSource) new InitialContext().lookup(url);
            } catch (NamingException e) {
                throw new DAOConfigurationException(
                    "DataSource '" + url + "' is missing in JNDI.", e);
            }
            if (username != null) {
                instance = new DataSourceWithLoginDAOFactory(dataSource, username, password);
            } else {
                instance = new DataSourceDAOFactory(dataSource);
            }
        }

        return instance;
    }

    /**
     * Returns a connection to the database. Package private so that it can be used inside the DAO
     * package only.
     * @return A connection to the database.
     * @throws SQLException If acquiring the connection fails.
     */
    abstract Connection getConnection() throws SQLException;

    // DAO implementation getters -----------------------------------------------------------------

    /**
     * Returns the User DAO associated with the current DAOFactory.
     * @return The User DAO associated with the current DAOFactory.
     */
//    public UserDAO getUserDAO() {
//        return new UserDAOJDBC(this);
//    }

    // You can add more DAO implementation getters here.
    
    public <DAO extends BaseDAO> DAO getDAOImpl(Class<DAO> daoInterface) throws DAOConfigurationException {
		String daoInterfaceName = daoInterface.getName();
//		System.out.println("daoInterfaceName: " + daoInterfaceName);
		
		if (!daoInterface.isInterface()) {
		    throw new DAOConfigurationException("Class '" + daoInterfaceName + "'"
		        + " is actually not an Interface.");
		}
		
		String daoClassName = daoInterfaceName + "JDBC";//String daoClassName = daoProperties.getProperty(daoInterfaceName, true);
		DAO daoImplementation;
		
		try {
		    daoImplementation = daoInterface.cast(Class.forName(daoClassName).newInstance());
		} catch (ClassNotFoundException e) {
		    throw new DAOConfigurationException("DAO class '" + daoClassName
		        + "' is missing in classpath. Verify the class or the '" + daoInterfaceName
		        + "' property.", e);
		} catch (IllegalAccessException e) {
		    throw new DAOConfigurationException("DAO class '" + daoClassName
		        + "' cannot be accessed. Verify the class or the '" + daoInterfaceName
		        + "' property.", e);
		} catch (InstantiationException e) {
		    throw new DAOConfigurationException("DAO class '" + daoClassName
		        + "' cannot be instantiated. Verify the class or the '" + daoInterfaceName
		        + "' property.", e);
		} catch (ClassCastException e) {
		    throw new DAOConfigurationException("DAO class '" + daoClassName
		        + "' does not implement '" + daoInterfaceName + "'. Verify the class or the '"
		        + daoInterfaceName + "' property.", e);
		}
		
		daoImplementation.setDAOFactory(this);
		
		return daoImplementation;
    }

}