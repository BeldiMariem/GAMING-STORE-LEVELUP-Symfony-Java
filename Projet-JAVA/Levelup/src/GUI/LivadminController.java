/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package GUI;

import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Date;
import java.util.List;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.stream.Collectors;
import javafx.collections.FXCollections;
import javafx.collections.ListChangeListener;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.paint.Color;
import javafx.scene.paint.ImagePattern;
import javafx.scene.shape.Circle;
import javafx.stage.Stage;
import javafx.stage.Window;
import models.Commande;
import models.Livraison;
import models.Livreur;
import models.User;
import services.ServiceCommande;
import services.ServiceLivraison;
import services.ServiceLivreur;
import utils.Session;

/**
 * FXML Controller class
 *
 * @author User
 */
public class LivadminController implements Initializable {

    String action = "";
    @FXML
    private Button affecterBtn;
    @FXML
    private Button supprimerbtn;
    @FXML
    private TextField recherche;
    @FXML
    private TableColumn<Livraison, String> Date_liv;
    @FXML
    private TableColumn<Livraison, String> etat_livraison;
    @FXML
    private TableView<Object> livTableau;
    @FXML
    private ComboBox<Object> liste_livreur;
    @FXML
    private Button ajouterbtn;
    @FXML
    private AnchorPane formLivraison;
    @FXML
    private Button enregistrer;
    @FXML
    private Button modifierbtn;
   
    @FXML
    private ComboBox<Object> idcommandeform;
    @FXML
    private ComboBox<Object> idlivreurform;
    @FXML
    private ComboBox<String> etatLivraison;
    @FXML
    private Label etatLivraisonlabel;
    @FXML
    private Button gestionRecBTN;
    @FXML
    private Button Retour;
        private Stage stage;
    private Scene scene;
    private Parent root;
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
        affecterBtn.setDisable(true);
        supprimerbtn.setDisable(true);
        modifierbtn.setDisable(true);
        formLivraison.setOpacity(0);
        ServiceLivraison sl = new ServiceLivraison();
        ServiceCommande sC = new ServiceCommande();

        ServiceLivreur serviceLivreur = new ServiceLivreur();
        List<Livraison> livFromdatabase = sl.afficherLivraison();
        List<Livreur> LivreurFromDatabase = serviceLivreur.afficherPersonnes();
        List<Commande> CommandeFromDataBase = sC.AfficherCommande();

        ObservableList<Object> ListeLivreurData = FXCollections.observableArrayList();
        ListeLivreurData.addAll(LivreurFromDatabase);

        ObservableList<Object> CommandeData = FXCollections.observableArrayList();
        CommandeData.addAll(CommandeFromDataBase);

        liste_livreur.setItems(ListeLivreurData);
        idlivreurform.setItems(ListeLivreurData);
        liste_livreur.setDisable(true);
        idcommandeform.setItems(CommandeData);

        ObservableList<Object> listLivData = FXCollections.observableArrayList();

        Date_liv.setCellValueFactory(new PropertyValueFactory<>("date"));
        etat_livraison.setCellValueFactory(new PropertyValueFactory<>("etat_livraison"));
        listLivData.addAll(livFromdatabase);
        livTableau.setItems(listLivData);
        ObservableList selectedCells = livTableau.getSelectionModel().getSelectedCells();

        selectedCells.addListener(new ListChangeListener() {
            @Override
            public void onChanged(Change c) {
                Livraison livSelected = (Livraison) livTableau.getSelectionModel().getSelectedItem();
                System.out.println("selected value " + livSelected);
                if (livSelected != null) {
                    if (livSelected.getUser().getId() != 0 || livSelected.getUser() == null) {
                        affecterBtn.setText("Annuler l'affectation");
                    } else {
                        affecterBtn.setText("Affecter");

                    }
                    liste_livreur.setDisable(true);
                    modifierbtn.setDisable(false);
                    affecterBtn.setDisable(false);
                    supprimerbtn.setDisable(false);

                } else {

                    affecterBtn.setDisable(false);
                    supprimerbtn.setDisable(false);
                }
            }
        });

    }

    void mise_a_jourbase() {
        ServiceLivraison sl = new ServiceLivraison();
        List<Livraison> livFromdatabase = sl.afficherLivraison();
        ObservableList<Object> listLivData = FXCollections.observableArrayList();

        Date_liv.setCellValueFactory(new PropertyValueFactory<>("date"));
        etat_livraison.setCellValueFactory(new PropertyValueFactory<>("etat_livraison"));
        listLivData.addAll(livFromdatabase);
        livTableau.setItems(listLivData);

    }

    @FXML
    private void SupprimerLiv(ActionEvent event) {
        Livraison livSelected = (Livraison) livTableau.getSelectionModel().getSelectedItem();
        ServiceLivraison sl = new ServiceLivraison();
        sl.supprimerLivraison(livSelected);
        mise_a_jourbase();

    }

    @FXML
    private void Chercher(ActionEvent event) {

        ServiceLivraison sl = new ServiceLivraison();
        ObservableList<Object> list = FXCollections.observableArrayList();

        list.addAll(sl.afficherLivraison().stream().
                filter(l -> l.getEtat_livraison().contains(recherche.getText())).collect(Collectors.toList())
        );
        livTableau.setItems(list);

    }

    @FXML
    private void affecterAction(ActionEvent event) {
        Livraison livSelected = (Livraison) livTableau.getSelectionModel().getSelectedItem();
        if (livSelected.getUser().getId() != 0 || livSelected.getUser() == null) {
            livSelected.setUser(null);
            ServiceLivraison sl = new ServiceLivraison();
            sl.annulerAffectation(livSelected);
            mise_a_jourbase();

        } else {
            liste_livreur.setDisable(false);

        }
    }

    @FXML
    private void AffecterLivreurLivraison(ActionEvent event) {
        Livraison livSelected = (Livraison) livTableau.getSelectionModel().getSelectedItem();
        Livreur livreurSelected = (Livreur) liste_livreur.getSelectionModel().getSelectedItem();
        ServiceLivraison sl = new ServiceLivraison();
        sl.Affectation(livSelected, livreurSelected);
        mise_a_jourbase();
        liste_livreur.setDisable(true);

    }

    @FXML
    private void ajouter(ActionEvent event) {

        action = "ajouter";
        formLivraison.setOpacity(1);
  
        etatLivraisonlabel.setOpacity(0);
        etatLivraison.setOpacity(0);
    }

    @FXML
    private void Modifier(ActionEvent event) {
        action = "modifier";
        formLivraison.setOpacity(1);
        Livraison livSelected = (Livraison) livTableau.getSelectionModel().getSelectedItem();
       
        etatLivraisonlabel.setOpacity(1);
        etatLivraison.setOpacity(1);
        etatLivraison.getItems().add(0, "En cours");
        etatLivraison.getItems().add(1, "Confirmee");
        etatLivraison.getItems().add(2, "Livree");

    }

    private void showAlert(Alert.AlertType alertType, Window owner, String title, String message) {
        Alert alert = new Alert(alertType);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.initOwner(owner);
        alert.show();
    }

    @FXML
    private void enregistrer(ActionEvent event) {

       if (idcommandeform.getValue() == null) {
             showAlert(Alert.AlertType.ERROR, ((Node) event.getSource()).getScene().getWindow(),
                    "Error!", "id commande  vide ");

        } else if (idlivreurform.getValue() == null) {
             showAlert(Alert.AlertType.ERROR, ((Node) event.getSource()).getScene().getWindow(),
                    "Error!", "mail livreur  vide ");
       
        }
        else{
        if (action.compareTo("ajouter") == 0) {
            Commande CommandeSelected = (Commande) idcommandeform.getSelectionModel().getSelectedItem();
            User LivreurSelected = (Livreur) idlivreurform.getSelectionModel().getSelectedItem();
            Date d = new Date();

            String EtatLiv = (String) etatLivraison.getSelectionModel().getSelectedItem();
            Livraison l = new Livraison(CommandeSelected, LivreurSelected, d.toString(), "En cours");
            ServiceLivraison sl = new ServiceLivraison();
            sl.ajouterLivraison(l);
            mise_a_jourbase();

        } else {

            Livraison livSelected = (Livraison) livTableau.getSelectionModel().getSelectedItem();
            Commande CommandeSelected = (Commande) idcommandeform.getSelectionModel().getSelectedItem();
            User LivreurSelected = (Livreur) idlivreurform.getSelectionModel().getSelectedItem();

            livSelected.setCommande(CommandeSelected);
            livSelected.setUser(LivreurSelected);
            String EtatLiv = (String) etatLivraison.getSelectionModel().getSelectedItem();
            livSelected.setEtat_livraison(EtatLiv);
            ServiceLivraison sl = new ServiceLivraison();
            sl.modifierLivraison(livSelected);
            mise_a_jourbase();
        }
        formLivraison.setOpacity(0);
  
        etatLivraisonlabel.setOpacity(0);
        etatLivraison.setOpacity(0);
        }
    }

    @FXML
    private void gotToREC(ActionEvent event) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("GestionReclmation.fxml"));
            Parent root = loader.load();
            gestionRecBTN.getScene().setRoot(root);
        } catch (IOException ex) {
            Logger.getLogger(GestionReclmationController.class.getName()).log(Level.SEVERE, null, ex);

        }
    }

    @FXML
    private void Retour(ActionEvent event) throws IOException {
         root = FXMLLoader.load(getClass().getResource("./ClientReclamation.fxml"));
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
