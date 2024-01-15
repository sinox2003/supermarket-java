package Backend.Historique;

import org.junit.jupiter.api.Test;

import java.time.LocalDate;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class HistoriqueDaoImplTest {

    @Test
    void addAndGetAll() {
        HistoriqueDaoImpl historiqueDao = new HistoriqueDaoImpl();
        Historique historiqueToAdd = new Historique(1, 1, "TestProduit", 10, 25.0, 1, LocalDate.now());

        historiqueDao.add(historiqueToAdd);

        List<Historique> allHistoriques = historiqueDao.getAll();

        assertNotNull(allHistoriques);
        assertFalse(allHistoriques.isEmpty());
        assertTrue(allHistoriques.contains(historiqueToAdd));

        // Nettoyer les données après le test
        historiqueDao.delete(historiqueToAdd.getId());
    }

    @Test
    void getAll() {
        HistoriqueDaoImpl historiqueDao = new HistoriqueDaoImpl();

        List<Historique> allHistoriques = historiqueDao.getAll();

        assertNotNull(allHistoriques);
        assertFalse(allHistoriques.isEmpty());
    }

    @Test
    void add() {
        HistoriqueDaoImpl historiqueDao = new HistoriqueDaoImpl();
        Historique historiqueToAdd = new Historique(1, 1, "TestProduit", 10, 25.0, 1, LocalDate.now());

        historiqueDao.add(historiqueToAdd);

        List<Historique> allHistoriques = historiqueDao.getAll();

        assertTrue(allHistoriques.contains(historiqueToAdd));

        // Nettoyer les données après le test
        historiqueDao.delete(historiqueToAdd.getId());
    }

    @Test
    void delete() {
        HistoriqueDaoImpl historiqueDao = new HistoriqueDaoImpl();
        Historique historiqueToAdd = new Historique(1, 1, "TestProduit", 10, 25.0, 1, LocalDate.now());

        historiqueDao.add(historiqueToAdd);
        historiqueDao.delete(historiqueToAdd.getId());

        List<Historique> allHistoriques = historiqueDao.getAll();

        assertFalse(allHistoriques.contains(historiqueToAdd));
    }

}
