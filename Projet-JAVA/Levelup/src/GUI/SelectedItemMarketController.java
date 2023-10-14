/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package GUI;

import interfaces.IPanier_elem;
import interfaces.Iproduit;
import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.Spinner;
import javafx.scene.control.SpinnerValueFactory;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.shape.Circle;
import javafx.stage.Stage;
import models.Panier_elem;
import models.Produit;
import services.ServicePanier_elem;
import services.ServiceProduit;
import utils.Session;

/**
 * FXML Controller class
 *
 * @author Iskander
 */
public class SelectedItemMarketController implements Initializable {

    Produit p;
    Iproduit sp = new ServiceProduit();
    IPanier_elem sElem = new ServicePanier_elem();
    FX_LoginController fx = new FX_LoginController();
    
    private Stage stage;
    private Scene scene;
    private Parent root;
    @FXML
    private ImageView itemIMG;
    @FXML
    private Label prodName;
    @FXML
    private Label prodRef;
    @FXML
    private Label prodPrix;
    @FXML
    private Label prodPromo;
    @FXML
    private Label prodDescription;
    @FXML
    private Spinner<Integer> SpinnerProd;
    @FXML
    private Button btnRetour;
    @FXML
    private Label presenceStockLabel;
    @FXML
    private Button butt;
    @FXML
    private Label id_prod;
    @FXML
    private Circle myCircle1;
    @FXML
    private ImageView top;


    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
     
    }

    public void setData(Produit p) {
        this.p = sp.retrieveProductById(p.getId_produit());
        id_prod.setText(String.valueOf(p.getId_produit()));
        prodName.setText(p.getNom());
        prodRef.setText("Référence :\n" + p.getReference());
        prodPrix.setText(String.valueOf(p.getPrix()));
        prodPromo.setText(String.valueOf(p.getPromotion()));
        String urlImage= "C:/Users/beldi/Desktop/Projet-WEB/LevelUp/public/uploads/images/products/";
        Image imn = new Image("file:/"+urlImage + p.getImage());
        prodDescription.setText("Détails :\n" + p.getDescription());
       itemIMG.setImage(imn);
        SpinnerValueFactory<Integer> valueFactory = new SpinnerValueFactory.IntegerSpinnerValueFactory(1, 50);
        valueFactory.setValue(1);
        SpinnerProd.setValueFactory(valueFactory);
//       pt.setQuantite(this.SpinnerProd.getValue());
    }

    @FXML
    private void switchBackToMarket(ActionEvent event) throws IOException {

        FXMLLoader loader = new FXMLLoader(getClass().getResource("./Magasin.fxml"));
        root = loader.load();
        stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        scene = new Scene(root);
        stage.setScene(scene);
        stage.show();
    }
   public Produit getData() {
        Produit prod = new Produit();
        prod.setId_produit(Integer.parseInt(this.id_prod.getText()));
        prod.setNom(prodName.getText());
        prod.setReference(prodRef.getText());
        prod.setPrix(Double.parseDouble(prodPrix.getText()));
        prod.setPromotion(Double.parseDouble(prodPromo.getText()));
        //prod.setImage(itemIMG);
//       pt.setQuantite(this.SpinnerProd.getValue());
        return p;
    }
      @FXML
    private void ajouter(ActionEvent event) throws IOException {
         
        Produit p = new Produit();
        p = getData();
        Panier_elem p1 = new Panier_elem(fx.p,p,this.SpinnerProd.getValue());
        sElem.AjouterElementPanierQ(p1);
        System.out.println(p1);
        root = FXMLLoader.load(getClass().getResource("./Magasin.fxml"));
        stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        scene = new Scene(root);
        stage.setScene(scene);
        stage.show();
    }

    @FXML
    private void Retour(ActionEvent event) throws IOException {
          root = FXMLLoader.load(getClass().getResource("./Magasin.fxml"));
        stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        scene = new Scene(root);
        stage.setScene(scene);
        stage.show();
       
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

//        public Produit getData() {
//        Produit prod = new Produit();
//        prod.setId_produit(Integer.parseInt(this.id_prod.getText()));
//        prod.setNom(prodName.getText());
//        prod.setReference(prodRef.getText());
//        prod.setPrix(Double.valueOf(prodPrix.getText()));
//        prod.setPromotion(Double.valueOf(prodPromo.getText()));
//        //prod.setImage(this.itemIMG);
////       pt.setQuantite(this.SpinnerProd.getValue());
//        return p;
//    }


    

