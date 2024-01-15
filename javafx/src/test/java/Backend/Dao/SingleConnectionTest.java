package Backend.Dao;

import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import java.sql.Connection;
import java.sql.SQLException;

import static org.junit.jupiter.api.Assertions.*;

class SingleConnectionTest {

    private static Connection connection;

    @BeforeAll
    static void setUp() {
        connection = SingleConnection.getConnection();
    }

    @AfterAll
    static void tearDown() {
        try {
            if (connection != null && !connection.isClosed()) {
                connection.close();
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Test
    void testConnectionNotNull() {
        assertNotNull(connection);
    }

    @Test
    void testConnectionIsOpen() {
        try {
            assertFalse(connection.isClosed());
        } catch (SQLException e) {
            e.printStackTrace();
            fail("Exception while checking if the connection is open");
        }
    }

    @Test
    void testGetConnectionReturnsSameInstance() {
        Connection secondConnection = SingleConnection.getConnection();
        assertEquals(connection, secondConnection);
    }
}
