package com.ron.test;

import java.text.SimpleDateFormat;
import java.util.List;

import com.ron.dao.DAOFactory;
import com.ron.dao.UserDAO;

import com.ron.model.User;

/**
 * Test harness for the com.example.dao package. This require the following preconditions:
 * <ol>
 * <li>A MySQL server running at localhost:3306 with a database named 'javabase'.
 * <li>A 'user' table in the 'javabase' database which is created as follows:
 * <pre>
 * CREATE TABLE javabase.user (
 *     id BIGINT UNSIGNED NOT NULL AUTO_INCREMENT,
 *     email VARCHAR(60) NOT NULL,
 *     password VARCHAR(32) NOT NULL,
 *     firstname VARCHAR(40) NULL,
 *     lastname VARCHAR(40) NULL,
 *     birthdate DATE NULL,
 *
 *     PRIMARY KEY (id),
 *     UNIQUE (email)
 * )
 * </pre>
 * <li>A MySQL user with the name 'java' and password 'd$7hF_r!9Y' which has sufficient rights on
 * the javabase.user table.
 * <li>A MySQL JDBC Driver JAR file in the classpath.
 * <li>A properties file 'dao.properties' in the classpath with the following entries:
 * <pre>
 * javabase.jdbc.driver = com.mysql.jdbc.Driver
 * javabase.jdbc.url = jdbc:mysql://localhost:3306/javabase
 * javabase.jdbc.username = java
 * javabase.jdbc.password = d$7hF_r!9Y
 * </pre>
 * </ol>
 *
 * @author BalusC
 * @link http://balusc.blogspot.com/2008/07/dao-tutorial-data-layer.html
 */
public class DAOTest {

    public static void mainx() throws Exception {
        // Obtain DAOFactory.
//        DAOFactory.init("javabase.jndi");
//        System.out.println("DAOFactory successfully obtained: " + javabase);

        // Obtain UserDAO.
//        UserDAO userDAO = javabase.getUserDAO();
        UserDAO userDAO = DAOFactory.getInstance().getDAOImpl(UserDAO.class);
        System.out.println("UserDAO successfully obtained: " + userDAO);

        // Create user.
        User user = new User();
        user.setName("foo@bar.com");
        user.setPassword("password");
//        userDAO.create(user);
        System.out.println("User successfully created: " + user);

        // Create another user.
        User anotherUser = new User();
        anotherUser.setName("bar@foo.com");
        anotherUser.setPassword("anotherPassword");
        anotherUser.setDepid("Bar");
        anotherUser.setRight("Foo");
        anotherUser.setInuse("1");
//        userDAO.create(anotherUser);
        System.out.println("Another user successfully created: " + anotherUser);
//
//        // Update user.
//        user.setFirstname("Foo");
//        user.setLastname("Bar");
//        userDAO.update(user);
//        System.out.println("User successfully updated: " + user);
//
//        // Update user.
//        user.setFirstname("Foo");
//        user.setLastname("Bar");
//        userDAO.update(user);
//        System.out.println("User successfully updated: " + user);
//
//        // List all users.
//        List<User> users = userDAO.list();
//        System.out.println("List of users successfully queried: " + users);
//        System.out.println("Thus, amount of users in database is: " + users.size());
//
//        // Delete user.
//        userDAO.delete(user);
//        System.out.println("User successfully deleted: " + user);
//
//        // Check if email exists.
//        boolean exist = userDAO.existEmail("foo@bar.com");
//        System.out.println("This email should not exist anymore, so this should print false: " + exist);
//
//        // Change password.
//        anotherUser.setPassword("newAnotherPassword");
//        userDAO.changePassword(anotherUser);
//        System.out.println("Another user's password successfully changed: " + anotherUser);
//
//        // Get another user by email and password.
//        User foundAnotherUser = userDAO.find("bar@foo.com", "newAnotherPassword");
//        System.out.println("Another user successfully queried with new password: " + foundAnotherUser);
//
//        // Delete another user.
//        userDAO.delete(foundAnotherUser);
//        System.out.println("Another user successfully deleted: " + foundAnotherUser);
//
//        // List all users again.
//        users = userDAO.list();
//        System.out.println("List of users successfully queried: " + users);
//        System.out.println("Thus, amount of users in database is: " + users.size());
    }

    
    public static void main(String[] args) throws Exception {
        // Obtain DAOFactory.
        DAOFactory.init("javabase.jdbc");
//        System.out.println("DAOFactory successfully obtained: " + javabase);

        // Obtain UserDAO.
//        UserDAO userDAO = javabase.getUserDAO();
        UserDAO userDAO = DAOFactory.getInstance().getDAOImpl(UserDAO.class);
        System.out.println("UserDAO successfully obtained: " + userDAO);

        // Create user.
        User user = new User();
        user.setName("foo@bar.com");
        user.setPassword("password");
//        userDAO.create(user);
        System.out.println("User successfully created: " + user);

        // Create another user.
        User anotherUser = new User();
        anotherUser.setName("bar@foo.com");
        anotherUser.setPassword("anotherPassword");
        anotherUser.setDepid("Bar");
        anotherUser.setRight("Foo");
        anotherUser.setInuse("1");
//        userDAO.create(anotherUser);
        System.out.println("Another user successfully created: " + anotherUser);

        // Update user.
        user.setDepid("Foo");
        user.setRight("Bar");
        userDAO.update(user);
        System.out.println("User successfully updated: " + user);

        // Update user.
        user.setDepid("Foo");
        user.setRight("Bar");
        userDAO.update(user);
        System.out.println("User successfully updated: " + user);

        // List all users.
        List<User> users = userDAO.list();
        System.out.println("List of users successfully queried: " + users);
        System.out.println("Thus, amount of users in database is: " + users.size());

        // Delete user.
        userDAO.delete(user);
        System.out.println("User successfully deleted: " + user);

        // Check if email exists.
        boolean exist = userDAO.existName("foo@bar.com");
        System.out.println("This email should not exist anymore, so this should print false: " + exist);

        // Change password.
        anotherUser.setPassword("newAnotherPassword");
        userDAO.changePassword(anotherUser);
        System.out.println("Another user's password successfully changed: " + anotherUser);

        // Get another user by email and password.
        User foundAnotherUser = userDAO.find("bar@foo.com", "newAnotherPassword");
        System.out.println("Another user successfully queried with new password: " + foundAnotherUser);

        // Delete another user.
        userDAO.delete(foundAnotherUser);
        System.out.println("Another user successfully deleted: " + foundAnotherUser);

        // List all users again.
        users = userDAO.list();
        System.out.println("List of users successfully queried: " + users);
        System.out.println("Thus, amount of users in database is: " + users.size());
    }
    
}