package Backend.Produit;

import lombok.Data;

import java.time.LocalDate;

@Data
public class Produit {

    private int id;
    private int idCategorie;
    private String designation;
    private int qte;
    private double prix;
    private LocalDate date;
    private LocalDate peremption;

    public Produit(int id, int idCategorie, String designation, int qte, double prix, LocalDate date, LocalDate peremption) {
        this.id = id;
        this.idCategorie=idCategorie;
        this.designation = designation;
        this.qte = qte;
        this.prix = prix;
        this.date = date;
        this.peremption=peremption;
    }
   public Produit(int idCategorie, String designation, int qte, double prix, LocalDate date, LocalDate peremption) {
        this.id = -1;
        this.idCategorie=idCategorie;
        this.designation = designation;
        this.qte = qte;
        this.prix = prix;
        this.date = date;
        this.peremption=peremption;
    }

    public int getId() {
        return id;
    }

    public int getIdCategorie() {
        return idCategorie;
    }

    public String getDesignation() {
        return designation;
    }

    public int getQte() {
        return qte;
    }

    public double getPrix() {
        return prix;
    }

    public LocalDate getDate() {
        return date;
    }

    public LocalDate getPeremption() {
        return peremption;
    }
}
