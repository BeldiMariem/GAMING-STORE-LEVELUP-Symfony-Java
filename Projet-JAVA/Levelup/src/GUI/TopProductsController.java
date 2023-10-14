/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package GUI;

import interfaces.ICommande_elem;
import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.animation.RotateTransition;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.geometry.Insets;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ScrollPane;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Region;
import javafx.scene.paint.Color;
import javafx.scene.paint.ImagePattern;
import javafx.scene.shape.Circle;
import javafx.stage.Stage;
import javafx.util.Duration;
import models.TopProduits;
import services.ServiceCommande_elem;
import utils.Session;

/**
 * FXML Controller class
 *
 * @author ASUS
 */
public class TopProductsController implements Initializable {
private Stage stage;
    private Scene scene;
    private Parent root;
    @FXML
    private Circle circle1;
    @FXML
    private Circle circle2;
    @FXML
    private Circle circle3;
    @FXML
    private ScrollPane scroll;
    @FXML
    private GridPane grid;

    
    ICommande_elem sp2 = new ServiceCommande_elem();
    @FXML
    private Circle myCircle1;
    @FXML
    private ImageView top;
    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
         File file = new File(Session.getImage());
        Image im = new Image(file.toURI().toString());
        myCircle1.setStroke(Color.SEAGREEN);
        myCircle1.setFill(new ImagePattern(im));
        // TODO
        this.setRotate(circle1, true, 360, 10);
         this.setRotate(circle2, true, 180, 18);
          this.setRotate(circle3, true, 145, 24);
          
           List<TopProduits> p1 = new ArrayList<TopProduits>();
           p1 = sp2.TopProduits();
            int column = 0 ;
             int row = 1;
            try {
                for(TopProduits p2 : p1){
         
               FXMLLoader fxmlloader = new FXMLLoader();
               fxmlloader.setLocation(getClass().getResource("TopItem.fxml"));
                AnchorPane anchorpane = fxmlloader.load();
                 TopItemController panieritem = fxmlloader.getController();
                    panieritem.setData(p2);
                    
                    if(column == 1){
                    column = 0;
                    row++;
                }
                grid.add(anchorpane, column++, row);
                
                grid.setMinWidth(Region.USE_COMPUTED_SIZE);
                grid.setPrefWidth(Region.USE_COMPUTED_SIZE);
                grid.setMaxWidth(Region.USE_PREF_SIZE);
                
                grid.setMinHeight(Region.USE_COMPUTED_SIZE);
                grid.setPrefHeight(Region.USE_COMPUTED_SIZE);
                grid.setMaxHeight(Region.USE_PREF_SIZE);
                
                GridPane.setMargin(anchorpane, new Insets(10));
            } 
           } catch (IOException ex) {
            Logger.getLogger(TopProductsController.class.getName()).log(Level.SEVERE, null, ex);
        }
               
    }    
    
    public void setRotate(Circle c, boolean reverse, int angle, int duration){
        RotateTransition rt = new RotateTransition(Duration.seconds(duration),c);
        rt.setAutoReverse(reverse);
        rt.setByAngle(angle);
        rt.setDelay(Duration.seconds(0));
        rt.setRate(3);
        rt.setCycleCount(18);
        rt.play();
    }

   @FXML
    private void switchToProfil(ActionEvent event) {
        try {
           root = FXMLLoader.load(getClass().getResource("./Compte.fxml"));
        stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        scene = new Scene(root);
        stage.setScene(scene);
        stage.show();
        } catch (IOException ex) {
            Logger.getLogger(MainController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    @FXML
    private void switchToPassword(ActionEvent event) {
        try {
            
             root = FXMLLoader.load(getClass().getResource("./PasswordUpdate.fxml"));
        stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        scene = new Scene(root);
        stage.setScene(scene);
        stage.show();
          
        } catch (IOException ex) {
            Logger.getLogger(MainController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    @FXML
    private void switchToUsers(ActionEvent event) {
        try {
           
              root = FXMLLoader.load(getClass().getResource("./List.fxml"));
        stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        scene = new Scene(root);
        stage.setScene(scene);
        stage.show();
        } catch (IOException ex) {
            Logger.getLogger(MainController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    @FXML
    private void switchToDashboard(ActionEvent event) {
        try {
             root = FXMLLoader.load(getClass().getResource("./Dashboard.fxml"));
        stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        scene = new Scene(root);
        stage.setScene(scene);
        stage.show();
            //rid.add(anchorPane, 0, 1);
        } catch (IOException ex) {
            Logger.getLogger(MainController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    @FXML
    private void switchToProduct(ActionEvent event) {
                       try {
          root = FXMLLoader.load(getClass().getResource("./Magasin.fxml"));
        stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        scene = new Scene(root);
        stage.setScene(scene);
        stage.show();
            //rid.add(anchorPane, 0, 1);
        } catch (IOException ex) {
            Logger.getLogger(MainController.class.getName()).log(Level.SEVERE, null, ex);
        }

    }

    @FXML
    private void switchToPanier(ActionEvent event) {
        try {
       root = FXMLLoader.load(getClass().getResource("./AffichagePanier.fxml"));
        stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        scene = new Scene(root);
        stage.setScene(scene);
        stage.show();

            
            //rid.add(anchorPane, 0, 1);
        } catch (IOException ex) {
            Logger.getLogger(MainController.class.getName()).log(Level.SEVERE, null, ex);
        }

    }

    @FXML
    private void switchToCommande(ActionEvent event) {
         try {
        root = FXMLLoader.load(getClass().getResource("./ClientCommandes.fxml"));
        stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        scene = new Scene(root);
        stage.setScene(scene);
        stage.show();
            //rid.add(anchorPane, 0, 1);
        } catch (IOException ex) {
            Logger.getLogger(MainController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    @FXML
    private void switchToGestProd(ActionEvent event) {
             try {
    root = FXMLLoader.load(getClass().getResource("./AddProductInterface.fxml"));
        stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        scene = new Scene(root);
        stage.setScene(scene);
        stage.show();
            //rid.add(anchorPane, 0, 1);
        } catch (IOException ex) {
            Logger.getLogger(MainController.class.getName()).log(Level.SEVERE, null, ex);
        }
        
    }

    @FXML
    private void switchToReclamation(ActionEvent event) {
                 try {
         root = FXMLLoader.load(getClass().getResource("./ClientReclamation.fxml"));
        stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        scene = new Scene(root);
        stage.setScene(scene);
        stage.show();
            //rid.add(anchorPane, 0, 1);
        } catch (IOException ex) {
            Logger.getLogger(MainController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    @FXML
    private void switchToStock(ActionEvent event) {
                    try {
           root = FXMLLoader.load(getClass().getResource("./DocFXML.fxml"));
        stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        scene = new Scene(root);
        stage.setScene(scene);
        stage.show();
            //rid.add(anchorPane, 0, 1);
        } catch (IOException ex) {
            Logger.getLogger(MainController.class.getName()).log(Level.SEVERE, null, ex);
        }
        
    }

    @FXML
    private void switchToFacture(ActionEvent event) {
                try {
          root = FXMLLoader.load(getClass().getResource("./FacFXML.fxml"));
        stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        scene = new Scene(root);
        stage.setScene(scene);
        stage.show();
            //rid.add(anchorPane, 0, 1);
        } catch (IOException ex) {
            Logger.getLogger(MainController.class.getName()).log(Level.SEVERE, null, ex);
        }
        

    }

    @FXML
    private void switchToForum(ActionEvent event) {
        try {
         root = FXMLLoader.load(getClass().getResource("../postgrid.fxml"));
        stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        scene = new Scene(root);
        stage.setScene(scene);
        stage.show();
            //rid.add(anchorPane, 0, 1);
        } catch (IOException ex) {
            Logger.getLogger(MainController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    @FXML
    private void Deconnexion(ActionEvent event) throws IOException {
       root = FXMLLoader.load(getClass().getResource("./FX_Login.fxml"));
        stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        scene = new Scene(root);
        stage.setScene(scene);
        stage.show();

        Session.setId(0);
        Session.setPrenom(null);
        Session.setNom(null);
        Session.setEmail(null);
        Session.setAdresse(null);
        Session.setPassword(null);
        Session.setRole(null);
        Session.setTel(null);
        Session.setDns(null);
        Session.setImage(null);

    }

    @FXML
    private void switchToCategorie(ActionEvent event) {
                  try {
   root = FXMLLoader.load(getClass().getResource("./AddCategoryInterface.fxml"));
        stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        scene = new Scene(root);
        stage.setScene(scene);
        stage.show();
            //rid.add(anchorPane, 0, 1);
        } catch (IOException ex) {
            Logger.getLogger(MainController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    @FXML
    private void switchToTop(ActionEvent event) {
        try {
      root = FXMLLoader.load(getClass().getResource("./TopProducts.fxml"));
        stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        scene = new Scene(root);
        stage.setScene(scene);
        stage.show();
            //rid.add(anchorPane, 0, 1);
        } catch (IOException ex) {
            Logger.getLogger(MainController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    @FXML
    private void switchToCommandesAdmin(ActionEvent event) {
        try {
       root = FXMLLoader.load(getClass().getResource("./AdminCommandes.fxml"));
        stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        scene = new Scene(root);
        stage.setScene(scene);
        stage.show();
        } catch (IOException ex) {
            Logger.getLogger(MainController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    @FXML
    private void switchToZoneLivreur(ActionEvent event) {
                 try {
      root = FXMLLoader.load(getClass().getResource("./LivreurReclamationZONE.fxml"));
        stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        scene = new Scene(root);
        stage.setScene(scene);
        stage.show();
        } catch (IOException ex) {
            Logger.getLogger(MainController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
}
