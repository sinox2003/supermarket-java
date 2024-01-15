package Backend.Produit;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class ProduitDaoImplTest {


    @Test
    void add() {
        // Créer une instance de la classe de DAO et une instance de produit à ajouter
        ProduitDaoImpl produitDao = new ProduitDaoImpl();
        Produit produitToAdd = new Produit(1, "TestProduit", 10, 19.99, LocalDate.now(), LocalDate.now().plusDays(30));

        // Appeler la méthode d'ajout
        produitDao.add(produitToAdd);

        // Récupérer le produit depuis la base de données en utilisant l'ID généré
        Produit retrievedProduit = produitDao.getById(produitToAdd.getId());

        // Vérifier si le produit ajouté est équivalent à celui récupéré
        assertEquals(produitToAdd, retrievedProduit);

        // Nettoyer les données après le test
        produitDao.delete(produitToAdd.getId());
    }

    @Test
    void delete() {
        // Créer une instance de la classe de DAO et une instance de produit à supprimer
        ProduitDaoImpl produitDao = new ProduitDaoImpl();
        Produit produitToDelete = new Produit(1, "ProduitToDelete", 10, 19.99, LocalDate.now(), LocalDate.now().plusDays(30));

        // Ajouter le produit pour s'assurer qu'il existe
        produitDao.add(produitToDelete);

        // Appeler la méthode de suppression
        produitDao.delete(produitToDelete.getId());

        // Récupérer le produit après la suppression
        Produit deletedProduit = produitDao.getById(produitToDelete.getId());

        // Vérifier si le produit supprimé est null, indiquant qu'il n'existe plus dans la base de données
        assertNull(deletedProduit);
    }

    @Test
    void getAll() {
        // Créer une instance de la classe de DAO
        ProduitDaoImpl produitDao = new ProduitDaoImpl();

        // Appeler la méthode pour récupérer tous les produits
        List<Produit> allProduits = produitDao.getAll();

        // Vérifier si la liste n'est pas nulle et si elle contient au moins un produit
        assertNotNull(allProduits);
        assertFalse(allProduits.isEmpty());
    }

    // Ajoutez d'autres méthodes de test selon les besoins de votre application

}
