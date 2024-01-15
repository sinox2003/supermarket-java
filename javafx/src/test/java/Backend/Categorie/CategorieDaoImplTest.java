package Backend.Categorie;

import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class CategorieDaoImplTest {

    @Test
    void add() {
        // Créer une instance de la classe de DAO et une instance de la catégorie à ajouter
        CategorieDaoImpl categorieDao = new CategorieDaoImpl();
        Categorie categorieToAdd = new Categorie("NouvelleCatégorie");

        // Appeler la méthode d'ajout
        categorieDao.add(categorieToAdd);

        // Récupérer la catégorie depuis la base de données en utilisant l'ID généré
        Categorie retrievedCategorie = categorieDao.getById(categorieToAdd.getId());

        // Vérifier si la catégorie ajoutée est équivalente à celle récupérée
        assertEquals(categorieToAdd, retrievedCategorie);

        // Nettoyer les données après le test
        categorieDao.delete(categorieToAdd.getId());
    }

    @Test
    void delete() {
        // Créer une instance de la classe de DAO et une instance de catégorie à supprimer
        CategorieDaoImpl categorieDao = new CategorieDaoImpl();
        Categorie categorieToDelete = new Categorie("CatégorieToDelete");

        // Ajouter la catégorie pour s'assurer qu'elle existe
        categorieDao.add(categorieToDelete);

        // Appeler la méthode de suppression
        categorieDao.delete(categorieToDelete.getId());

        // Récupérer la catégorie après la suppression
        Categorie deletedCategorie = categorieDao.getById(categorieToDelete.getId());

        // Vérifier si la catégorie supprimée est null, indiquant qu'elle n'existe plus dans la base de données
        assertNull(deletedCategorie);
    }

    @Test
    void getAll() {
        // Créer une instance de la classe de DAO
        CategorieDaoImpl categorieDao = new CategorieDaoImpl();

        // Appeler la méthode pour récupérer toutes les catégories
        List<Categorie> allCategories = categorieDao.getAll();

        // Vérifier si la liste n'est pas nulle et si elle contient au moins une catégorie
        assertNotNull(allCategories);
        assertFalse(allCategories.isEmpty());
    }
}
