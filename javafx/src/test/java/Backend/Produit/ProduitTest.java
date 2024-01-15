package Backend.Produit;

import org.junit.jupiter.api.Test;

import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.assertEquals;

class ProduitTest {

    @Test
    void constructorWithId() {
        int id = 1;
        int idCategorie = 2;
        String designation = "TestProduit";
        int qte = 10;
        double prix = 19.99;
        LocalDate date = LocalDate.now();
        LocalDate peremption = LocalDate.now().plusDays(30);

        Produit produit = new Produit(id, idCategorie, designation, qte, prix, date, peremption);

        assertEquals(id, produit.getId());
        assertEquals(idCategorie, produit.getIdCategorie());
        assertEquals(designation, produit.getDesignation());
        assertEquals(qte, produit.getQte());
        assertEquals(prix, produit.getPrix());
        assertEquals(date, produit.getDate());
        assertEquals(peremption, produit.getPeremption());
    }

    @Test
    void constructorWithoutId() {
        int idCategorie = 2;
        String designation = "TestProduit";
        int qte = 10;
        double prix = 19.99;
        LocalDate date = LocalDate.now();
        LocalDate peremption = LocalDate.now().plusDays(30);

        Produit produit = new Produit(idCategorie, designation, qte, prix, date, peremption);

        assertEquals(-1, produit.getId());
        assertEquals(idCategorie, produit.getIdCategorie());
        assertEquals(designation, produit.getDesignation());
        assertEquals(qte, produit.getQte());
        assertEquals(prix, produit.getPrix());
        assertEquals(date, produit.getDate());
        assertEquals(peremption, produit.getPeremption());
    }

}
