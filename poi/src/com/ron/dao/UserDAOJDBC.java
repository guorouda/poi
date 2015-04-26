package com.ron.dao;


import static com.ron.dao.DAOUtil.*;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import org.apache.log4j.Logger;

import com.ron.exceptions.DAOException;
import com.ron.model.User;


/**
 * This class represents a concrete JDBC implementation of the {@link UserDAO} interface.
 *
 * @author BalusC
 * @link http://balusc.blogspot.com/2008/07/dao-tutorial-data-layer.html
 */
public class UserDAOJDBC extends BaseDAOJDBC implements UserDAO {

    // Constants ----------------------------------------------------------------------------------
    public static Logger log = Logger.getLogger(UserDAOJDBC.class);
	
    private static final String SQL_FIND_BY_ID = "SELECT id, email, firstname, lastname, birthdate FROM Users WHERE id = ?";
    private static final String SQL_FIND_BY_EMAIL_AND_PASSWORD = "SELECT id, email, firstname, lastname, birthdate FROM Users WHERE email = ? AND password = ?";
    private static final String SQL_LIST_ORDER_BY_ID = "SELECT id, email, firstname, lastname, birthdate FROM Users ORDER BY id";
    private static final String SQL_INSERT = "INSERT INTO Users (id, email, password, firstname, lastname, birthdate) VALUES (users_id_seq.nextval, ?, ?, ?, ?, ?)";
    private static final String SQL_UPDATE = "UPDATE Users SET email = ?, firstname = ?, lastname = ?, birthdate = ? WHERE id = ?";
    private static final String SQL_DELETE = "DELETE FROM Users WHERE id = ?";
    private static final String SQL_EXIST_EMAIL = "SELECT id FROM Users WHERE email = ?";
    private static final String SQL_CHANGE_PASSWORD = "UPDATE Users SET password = ? WHERE id = ?";
    private static final String SQL_LOGIN = "select * from xxfb_user where id =  ? and password = ?";

    private static final String SQL_GETVALUE = "select * from xxfb_user where id = ?";

    // Vars ---------------------------------------------------------------------------------------

    // private DAOFactory daoFactory;

    // Constructors -------------------------------------------------------------------------------

    /**
     * Construct an User DAO for the given DAOFactory. Package private so that it can be constructed
     * inside the DAO package only.
     * @param daoFactory The DAOFactory to construct this User DAO for.
     */
    // UserDAOJDBC(DAOFactory daoFactory) {
    // this.daoFactory = daoFactory;
    // }
    
    // @Override
    // public void setDAOFactory(DAOFactory daoFactory) {
    // this.daoFactory = daoFactory;
    // }
    

    // Actions ------------------------------------------------------------------------------------

    @Override
    public User find(String id) throws DAOException {
        return find(SQL_FIND_BY_ID, id);
    }

    @Override
    public User find(String name, String password) throws DAOException {
        return find(SQL_FIND_BY_EMAIL_AND_PASSWORD, name, password);
    }

    /**
     * Returns the user from the database matching the given SQL query with the given values.
     * @param sql The SQL query to be executed in the database.
     * @param values The PreparedStatement values to be set.
     * @return The user from the database matching the given SQL query with the given values.
     * @throws DAOException If something fails at database level.
     */
    private User find(String sql, Object... values) throws DAOException {
        Connection connection = null;
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;
        User user = null;

        try {
            connection = daoFactory.getConnection();
            preparedStatement = prepareStatement(connection, sql, false, values);
            resultSet = preparedStatement.executeQuery();
            if (resultSet.next()) {
                user = map(resultSet);
            }
        } catch (SQLException e) {
            throw new DAOException(e);
        } finally {
            close(connection, preparedStatement, resultSet);
        }

        return user;
    }

    @Override
    public List<User> list() throws DAOException {
        Connection connection = null;
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;
        List<User> users = new ArrayList<User>();

        try {
            connection = daoFactory.getConnection();
            preparedStatement = connection.prepareStatement(SQL_LIST_ORDER_BY_ID);
            resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                users.add(map(resultSet));
            }
        } catch (SQLException e) {
            throw new DAOException(e);
        } finally {
            close(connection, preparedStatement, resultSet);
        }

        return users;
    }

    @Override
    public void create(User user) throws IllegalArgumentException, DAOException {
        if (user.getId() != null) {
            throw new IllegalArgumentException(
                    "User is already created, the user ID is not null.");
        }

        Object[] values = {
            user.getName(), user.getPassword(), user.getDepid(), user.getInuse(),
            user.getRight()
        };

        Connection connection = null;
        PreparedStatement preparedStatement = null;
        ResultSet generatedKeys = null;

        try {
            connection = daoFactory.getConnection();
            preparedStatement = prepareStatement(connection, SQL_INSERT, true,
                    values);
            int affectedRows = preparedStatement.executeUpdate();

            if (affectedRows == 0) {
                throw new DAOException("Creating user failed, no rows affected.");
            }
            generatedKeys = preparedStatement.getGeneratedKeys();
            if (generatedKeys.next()) {
                user.setId(generatedKeys.getLong(1) + "");
            } else {
                throw new DAOException(
                        "Creating user failed, no generated key obtained.");
            }
        } catch (SQLException e) {
            throw new DAOException(e);
        } finally {
            close(connection, preparedStatement, generatedKeys);
        }
    }

    @Override
    public void update(User user) throws DAOException {
        if (user.getId() == null) {
            throw new IllegalArgumentException(
                    "User is not created yet, the user ID is null.");
        }

        Object[] values = {
            user.getName(), user.getDepid(), user.getRight(), user.getInuse(),
            user.getId()
        };

        Connection connection = null;
        PreparedStatement preparedStatement = null;

        try {
            connection = daoFactory.getConnection();
            preparedStatement = prepareStatement(connection, SQL_UPDATE, false,
                    values);
            int affectedRows = preparedStatement.executeUpdate();

            if (affectedRows == 0) {
                throw new DAOException("Updating user failed, no rows affected.");
            }
        } catch (SQLException e) {
            throw new DAOException(e);
        } finally {
            close(connection, preparedStatement);
        }
    }

    @Override
    public void delete(User user) throws DAOException {
        Object[] values = { 
            user.getId()
        };

        Connection connection = null;
        PreparedStatement preparedStatement = null;

        try {
            connection = daoFactory.getConnection();
            preparedStatement = prepareStatement(connection, SQL_DELETE, false,
                    values);
            int affectedRows = preparedStatement.executeUpdate();

            if (affectedRows == 0) {
                throw new DAOException("Deleting user failed, no rows affected.");
            } else {
                user.setId(null);
            }
        } catch (SQLException e) {
            throw new DAOException(e);
        } finally {
            close(connection, preparedStatement);
        }
    }

    @Override
    public boolean existName(String name) throws DAOException {
        Object[] values = { 
            name 
        };

        Connection connection = null;
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;
        boolean exist = false;

        try {
            connection = daoFactory.getConnection();
            preparedStatement = prepareStatement(connection, SQL_EXIST_EMAIL,
                    false, values);
            resultSet = preparedStatement.executeQuery();
            exist = resultSet.next();
        } catch (SQLException e) {
            throw new DAOException(e);
        } finally {
            close(connection, preparedStatement, resultSet);
        }

        return exist;
    }

    @Override
    public void changePassword(User user) throws DAOException {
        if (user.getId() == null) {
            throw new IllegalArgumentException(
                    "User is not created yet, the user ID is null.");
        }

        Object[] values = {
            user.getPassword(), user.getId()
        };

        Connection connection = null;
        PreparedStatement preparedStatement = null;

        try {
            connection = daoFactory.getConnection();
            preparedStatement = prepareStatement(connection, SQL_CHANGE_PASSWORD,
                    false, values);
            int affectedRows = preparedStatement.executeUpdate();

            if (affectedRows == 0) {
                throw new DAOException(
                        "Changing password failed, no rows affected.");
            }
        } catch (SQLException e) {
            throw new DAOException(e);
        } finally {
            close(connection, preparedStatement);
        }
    }

    // Helpers ------------------------------------------------------------------------------------

    /**
     * Map the current row of the given ResultSet to an User.
     * @param resultSet The ResultSet of which the current row is to be mapped to an User.
     * @return The mapped User from the current row of the given ResultSet.
     * @throws SQLException If something fails at database level.
     */
    private static User map(ResultSet resultSet) throws SQLException {
        User user = new User();

        user.setId(resultSet.getString("id"));
        user.setName(resultSet.getString("name"));
        user.setDepid(resultSet.getString("depid"));
        user.setRight(resultSet.getString("right"));
        user.setInuse(resultSet.getString("inuse"));
        return user;
    }
    
    public String login(String username, String password) throws DAOException {   
        Object[] values = { 
            username, password
        };
        
        Connection connection = null;
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;

        try {
            connection = daoFactory.getConnection();
            preparedStatement = prepareStatement(connection, SQL_LOGIN, false,
                    values);
            resultSet = preparedStatement.executeQuery();
            if (resultSet != null && resultSet.next()) {
                String right = resultSet.getString("right");

                if (right != null && !right.isEmpty()) {
                    return "1," + right;
                }
            }
        } catch (Exception ex) {
            ex.printStackTrace();
            return "0,0";
        } finally {
            close(connection, preparedStatement, resultSet);
        }

        return "0,0";
    }
	
    public String getValue(User user, String key) throws DAOException {
        String value = "";
        Object[] values = {
            user.getId()
        };

        Connection connection = null;
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;

        try {
            connection = daoFactory.getConnection();
            preparedStatement = prepareStatement(connection, SQL_GETVALUE, false,
                    values);
            resultSet = preparedStatement.executeQuery();
            if (resultSet.next()) {
                value = resultSet.getString(key);
            }
        } catch (SQLException e) {
            throw new DAOException(e);
        } finally {
            close(connection, preparedStatement, resultSet);
        }
    	
        return value;
    }

}
