package Backend.User;

import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class UserDaoImplTest {

    @Test
    void addAndGetById() {
        UserDaoImpl userDao = new UserDaoImpl();
        User userToAdd = new User("testUser", "testPassword", "testType");

        userDao.add(userToAdd);

        User retrievedUser = userDao.getById(userToAdd.getId());

        assertEquals(userToAdd, retrievedUser);

        userDao.delete(userToAdd.getId());
    }

    @Test
    void delete() {
        UserDaoImpl userDao = new UserDaoImpl();
        User userToAdd = new User("testUser", "testPassword", "testType");

        userDao.add(userToAdd);
        userDao.delete(userToAdd.getId());

        User deletedUser = userDao.getById(userToAdd.getId());

        assertNull(deletedUser);
    }

    @Test
    void getAll() {
        UserDaoImpl userDao = new UserDaoImpl();

        List<User> allUsers = userDao.getAll();

        assertNotNull(allUsers);
        assertFalse(allUsers.isEmpty());
    }

    @Test
    void getUsersByInfo() {
        UserDaoImpl userDao = new UserDaoImpl();
        User userToAdd = new User("testUser", "testPassword", "testType");

        userDao.add(userToAdd);

        List<User> usersByInfo = userDao.getUsersByInfo("testUser", "testPassword");

        assertNotNull(usersByInfo);
        assertFalse(usersByInfo.isEmpty());

        userDao.delete(userToAdd.getId());
    }

    @Test
    void getByType() {
        UserDaoImpl userDao = new UserDaoImpl();
        User userToAdd = new User("testUser", "testPassword", "testType");

        userDao.add(userToAdd);

        List<User> usersByType = userDao.getByType("testType");

        assertNotNull(usersByType);
        assertFalse(usersByType.isEmpty());

        userDao.delete(userToAdd.getId());
    }

    @Test
    void update() {
        UserDaoImpl userDao = new UserDaoImpl();
        User userToAdd = new User("testUser", "testPassword", "testType");

        userDao.add(userToAdd);

        userToAdd.setUsername("updatedUsername");
        userToAdd.setPassword("updatedPassword");
        userToAdd.setType("updatedType");

        userDao.update(userToAdd);

        User updatedUser = userDao.getById(userToAdd.getId());

        assertEquals(userToAdd, updatedUser);

        userDao.delete(userToAdd.getId());
    }
}
