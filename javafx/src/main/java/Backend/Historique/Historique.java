package Backend.Historique;

import lombok.Data;

import java.time.LocalDate;
@Data
public class Historique {
    private int id;
    private int idCategorie;
    private String designation;
    private int qte;
    private double prix;
    private int type;//1:entrant | -1:sortant
    private LocalDate date;

    public Historique(int id, int idCategorie, String designation, int qte, double prix,int type, LocalDate date) {
        this.id = id;
        this.idCategorie=idCategorie;
        this.designation = designation;
        this.qte = qte;
        this.prix = prix;
        this.type = type;
        this.date = date;
    }
    public Historique(int idCategorie, String designation, int qte, double prix, int type, LocalDate date) {
        this.id = -1;
        this.idCategorie=idCategorie;
        this.designation = designation;
        this.qte = qte;
        this.prix = prix;
        this.type = type;
        this.date = date;

    }
}
