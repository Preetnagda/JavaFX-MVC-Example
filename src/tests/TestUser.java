package src.tests;

import static org.junit.Assert.assertEquals;

import org.junit.Test;

import src.model.User;

public class TestUser {

    @Test
    public void testUserConstructorWithIsVip() {
        String firstname = "John";
        String lastname = "Doe";
        String username = "johndoe";
        String password = "password123";
        Integer isVip = 1;

        User user = new User(firstname, lastname, username, password, isVip);

        assertEquals(firstname, user.getFirstname());
        assertEquals(lastname, user.getLastname());
        assertEquals(username, user.getUsername());
        assertEquals(password, user.getPassword());
        assertEquals(isVip, user.getIsVip());
    }

    @Test
    public void testUserConstructorWithoutIsVip() {
        String firstname = "Alice";
        String lastname = "Smith";
        String username = "alicesmith";
        String password = "securePassword";

        User user = new User(firstname, lastname, username, password);

        assertEquals(firstname, user.getFirstname());
        assertEquals(lastname, user.getLastname());
        assertEquals(username, user.getUsername());
        assertEquals(password, user.getPassword());
        assertEquals(Integer.valueOf(0), user.getIsVip()); // Assuming the default value is 0
    }
}
