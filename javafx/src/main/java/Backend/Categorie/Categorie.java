package Backend.Categorie;

import lombok.Data;

@Data
public class Categorie {
    private int id;
    private String nom;
    public Categorie(int id, String nom){
        this.id = id;
        this.nom = nom;
    }
    public Categorie(String nom){
        this.nom = nom;
    }

}
