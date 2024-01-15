package com.example.javafx;


import Backend.Historique.Historique;
import Backend.Historique.HistoriqueDaoImpl;
import Backend.Produit.Produit;
import Backend.Produit.ProduitDaoImpl;

import io.github.palexdev.materialfx.controls.MFXButton;
import io.github.palexdev.materialfx.effects.DepthLevel;
import io.github.palexdev.materialfx.enums.ButtonType;
import io.github.palexdev.materialfx.factories.InsetsFactory;
import io.github.palexdev.materialfx.font.MFXFontIcon;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.control.Label;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.FontPosture;
import javafx.scene.text.FontWeight;

import java.net.URL;
import java.time.LocalDate;
import java.util.List;
import java.util.ResourceBundle;
import java.util.stream.Collectors;

public class NotificationsController implements Initializable {
    @FXML
    VBox notification_vbox;
    @FXML
    private AnchorPane topBar;
    private final ProduitDaoImpl produitsDao = new ProduitDaoImpl();
    private final HistoriqueDaoImpl hdao = new HistoriqueDaoImpl();
//    File1Controller file1Controller=new File1Controller();
    public double x,y;
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

        Initialize_notifications();


        topBar.setOnMousePressed(Event->{
            x=Event.getSceneX();
            y=Event.getSceneY();
        });

        topBar.setOnMouseDragged(event ->{
            ApplicationController.notification_stage.setX(event.getScreenX()-x);
            ApplicationController.notification_stage.setY(event.getScreenY()-y );
        });

    }

    public void Initialize_notifications( ){
//        try {
            List<Produit> produits_expire=produitsDao.getAll().stream().filter(p ->p.getPeremption()!=null && p.getPeremption().isBefore(LocalDate.now())).collect(Collectors.toList());
////
//            FXMLLoader loader = new FXMLLoader(getClass().getResource("file1.fxml"));
//            Parent root = loader.load();
//            File1Controller file1Controller = loader.getController();
//            file1Controller.notification_nbr.setText(Double.toString(produits_expire.stream().count()));

            notification_vbox.getChildren().clear();


            for(Produit produit:produits_expire){

                notification_vbox.getChildren().add(createNotification(produit));
            }
//
            notification_vbox.setSpacing(1);
//        }
//        catch (IOException e) {
//            throw new RuntimeException(e);
//        }


    }
    public Node createNotification(Produit produit){



        MFXFontIcon mfxFontIcon2=new MFXFontIcon("mfx-exclamation-circle-filled",21,Color.web("#FFA808"));


        Label label = new Label("Produit a expiré");
        label.setFont(Font.font("Roboto",FontWeight.BOLD, FontPosture.REGULAR,17));


        HBox header = new HBox();
        header.getChildren().addAll(mfxFontIcon2,label);
        header.setSpacing(10);
        header.setAlignment(Pos.CENTER_LEFT);
        header.setPadding(InsetsFactory.of(5, 0, 5, 2));

        /*   ////////////////////////////////////////  \\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\*/



        Label contentLabel = new Label("Le produit "+produit.getDesignation()+" d'ID "+produit.getId()+" a expiré le "+produit.getPeremption()+"\nIl est recommader de supprimer ce produit de la BD");
        contentLabel.setFont(Font.font("Roboto",FontWeight.SEMI_BOLD, FontPosture.REGULAR,14));
//        Label recommander = new Label("Il est recommande de supprimer ce produit ");
//
        contentLabel.setWrapText(true);


        contentLabel.setMaxSize(Double.MAX_VALUE, Double.MAX_VALUE);
        contentLabel.setPadding(InsetsFactory.left(24));



        /*   ////////////////////////////////////////  \\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\*/



        MFXFontIcon mfxFontIcon3=new MFXFontIcon("mfx-delete-alt",22,Color.web("#780CD8"));

        MFXButton action2 = new MFXButton("Produit",mfxFontIcon3);
        action2.setDefaultButton(false);

        action2.getStyleClass().add("outline-button");

        action2.setFocusTraversable(false);

        action2.setButtonType(ButtonType.RAISED);
        action2.setDepthLevel(DepthLevel.LEVEL5);
        action2.setOnAction(event -> {
            supprimerProduit(produit);

            Initialize_notifications();



        });
        HBox actionsBar = new HBox(15, action2);
        actionsBar.setSpacing(20);
        actionsBar.setAlignment(Pos.CENTER_RIGHT);
        actionsBar.setPadding(InsetsFactory.all(5));

        /*   ////////////////////////////////////////  \\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\*/



        BorderPane container = new BorderPane();


        container.setTop(header);
        container.setCenter(contentLabel);

        container.setBottom(actionsBar);
        container.setBorder(new Border(new BorderStroke(
                Color.web("#CCCCCC"), BorderStrokeStyle.SOLID, new CornerRadii(5), new BorderWidths(0.5))));
        container.setBackground(Background.fill(Color.WHITE));

        container.setPadding(InsetsFactory.all(7));
        container.setMinHeight(200);


        return container;
    }
    public void supprimerProduit(Produit produit) {
        produitsDao.delete(produit.getId());
        Historique h = new Historique(produit.getId(),produit.getIdCategorie(), produit.getDesignation(), produit.getQte(),
                produit.getPrix(), -1, LocalDate.now());

        hdao.add(h);

    }


}
