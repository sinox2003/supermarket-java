package Backend.Categorie;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

class CategorieTest {

    @Test
    void getId() {
        Categorie categorie = new Categorie(1, "Test");
        assertEquals(1, categorie.getId());
    }

    @Test
    void getNom() {
        Categorie categorie = new Categorie(1, "Test");
        assertEquals("Test", categorie.getNom());
    }

    @Test
    void setId() {
        Categorie categorie = new Categorie(1, "Test");
        categorie.setId(2);
        assertEquals(2, categorie.getId());
    }

    @Test
    void setNom() {
        Categorie categorie = new Categorie(1, "Test");
        categorie.setNom("NewTest");
        assertEquals("NewTest", categorie.getNom());
    }

    @Test
    void testEquals() {
        Categorie categorie1 = new Categorie(1, "Test");
        Categorie categorie2 = new Categorie(1, "Test");
        assertTrue(categorie1.equals(categorie2));
    }

    @Test
    void canEqual() {
        Categorie categorie1 = new Categorie(1, "Test");
        Categorie categorie2 = new Categorie(1, "Test");
        assertTrue(categorie1.canEqual(categorie2));
    }

    @Test
    void testHashCode() {
        Categorie categorie1 = new Categorie(1, "Test");
        Categorie categorie2 = new Categorie(1, "Test");
        assertEquals(categorie1.hashCode(), categorie2.hashCode());
    }

    @Test
    void testToString() {
        Categorie categorie = new Categorie(1, "Test");
        assertEquals("Categorie(id=1, nom=Test)", categorie.toString());
    }
}
