/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package GUI;

import interfaces.Icategorie;
import interfaces.Iproduit;
import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.util.List;
import java.util.Optional;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.beans.property.ReadOnlyObjectWrapper;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
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
import javafx.scene.control.ButtonType;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableRow;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.paint.Color;
import javafx.scene.paint.ImagePattern;
import javafx.scene.shape.Circle;
import javafx.stage.Stage;
import javafx.stage.Window;
import static jdk.nashorn.internal.objects.NativeRegExp.source;
import models.Categorie;
import models.Produit;
import services.ServiceCategorie;
import services.ServiceProduit;
import services.ServiceUser;
import utils.Session;

/**
 * FXML Controller class
 *
 * @author Iskander
 */
public class AddCategoryInterfaceController implements Initializable {

    private Stage stage;
    private Scene scene;
    private Parent root;

    Icategorie sc = new ServiceCategorie();

    @FXML
    private TableView<Categorie> tv_categories;
    @FXML
    private TableColumn<Categorie, Integer> col_idCat;
    @FXML
    private TableColumn<Categorie, String> col_nomCategorie;
    @FXML
    private Button btn_addCategorie;
    @FXML
    private Button btn_deleteCategorie;
    @FXML
    private Button btn_updateCategorie;
    @FXML
    private Button btn_retour;

    @FXML
    private TextField nomCategorieTF;
    @FXML
    private Label label_insererNom;
    @FXML
    private Label label_InsererID;
    @FXML
    private TextField IdCategorieTF;
    @FXML
    private Circle myCircle1;
    @FXML
    private ImageView top;

    /**
     * Initializes the controller class.
     */
    // Crud Affichage des catégories.
    // permet la séléction des items selon le clique de la souris
    @Override
    public void initialize(URL url, ResourceBundle rb) {
         File file = new File(Session.getImage());
        Image im = new Image(file.toURI().toString());
        myCircle1.setStroke(Color.SEAGREEN);
        myCircle1.setFill(new ImagePattern(im));
        tv_categories.getSelectionModel().selectedItemProperty().addListener(new ChangeListener() {
            @Override
            public void changed(ObservableValue observable, Object oldValue, Object newValue) {
                if (tv_categories.getSelectionModel().getSelectedItem() != null) {
                    Categorie selectedCategorie = tv_categories.getSelectionModel().getSelectedItem();
                    IdCategorieTF.setText(String.valueOf(selectedCategorie.getId_categorie()));
                    nomCategorieTF.setText(String.valueOf(selectedCategorie.getNom_categorie()));
                } else {
                    tv_categories.getSelectionModel().clearSelection();
                }
            }
        });

        // Old AFFICHAGE + without selection here
        List<Categorie> listCat = sc.afficherCategorie();
        col_idCat.setCellValueFactory(new PropertyValueFactory<>("id_categorie"));
        col_nomCategorie.setCellValueFactory(new PropertyValueFactory<>("nom_categorie"));
        listCat.forEach((c) -> {
            tv_categories.getItems().add(new Categorie(c.getId_categorie(), c.getNom_categorie()));
        });
    }

    // Passage vers l'écran d'accueil par un bouton de retour
    @FXML

    private void switchTo(ActionEvent event) throws IOException {

        root = FXMLLoader.load(getClass().getResource("./SelectionInterface.fxml"));
        stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        scene = new Scene(root);
        stage.setScene(scene);
        stage.show();

    }
    // Création d'une alerte lors du crud

    private void showAlert(Alert.AlertType alertType, Window owner, String title, String message) {
        Alert alert = new Alert(alertType);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.initOwner(owner);
        alert.show();
    }

    // Crud ajout d'une catégorie
    @FXML
    private void addCategorie(ActionEvent event) throws IOException {
        Categorie c = new Categorie();

        // Alerte de confirmation de l'ajout d'une catégorie
        Alert alert1 = new Alert(Alert.AlertType.CONFIRMATION);
        alert1.setTitle("Confirmation d'ajout");
        alert1.setHeaderText(null);
        alert1.setContentText(" Etes-vous sure de vouloir ajouter cette catégorie ? ");
        Optional<ButtonType> action = alert1.showAndWait();

        if (action.get() == ButtonType.OK) {
            if (nomCategorieTF.getText().isEmpty()) {
                System.out.println("valeur null");
                showAlert(Alert.AlertType.ERROR, ((Node) event.getSource()).getScene().getWindow(),
                        " Erreur d'ajout catégorie ! ", " Erreur d'ajout de la catégorie ! \n Veuillez remplir touts les champs! ");
                // Alerte de confirmation

            } else {
                if (sc.validerCategorie(nomCategorieTF.getText()) == 0) {
                    sc.ajouterCategorie(new Categorie(nomCategorieTF.getText()));
                    root = FXMLLoader.load(getClass().getResource("./AddCategoryInterface.fxml"));
                    stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
                    scene = new Scene(root);
                    stage.setScene(scene);
                    stage.show();
                    
                    showAlert(Alert.AlertType.INFORMATION, ((Node) event.getSource()).getScene().getWindow(),
                            " Succés! ", " Ajout de la catégorie établie avec succés! ");
                } else {
                    System.out.println("Catégorie existante");
                    showAlert(Alert.AlertType.ERROR, ((Node) event.getSource()).getScene().getWindow(),
                            " Erreur d'ajout catégorie ! ", " Erreur d'ajout! \n Catégorie déja existante ! ");

                }
            }
        } else {
            alert1.close();
        }
    }

// Crud suppression d'une catégorie
    @FXML
    private void deleteCategorie(ActionEvent event) throws IOException {
        Alert alert1 = new Alert(Alert.AlertType.CONFIRMATION);
        alert1.setTitle("Confirmation de suppression");
        alert1.setHeaderText(null);
        alert1.setContentText(" Etes-vous sure de vouloir supprimer cette catégorie ? ");
        Optional<ButtonType> action = alert1.showAndWait();
        if (IdCategorieTF.getText().isEmpty()) {
            System.out.println(" Aucun champ n'est selectionné ");
            showAlert(Alert.AlertType.ERROR, ((Node) event.getSource()).getScene().getWindow(),
                    " Echec de suppression !  ", " Aucun champ n'a été selectionné! ");
        } else {
            if (action.get() == ButtonType.OK) {

                // alerte confirmation suppression
                sc.supprimerCategorie(new Categorie(Integer.parseInt(IdCategorieTF.getText())));

                root = FXMLLoader.load(getClass().getResource("./AddCategoryInterface.fxml"));
                stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
                scene = new Scene(root);
                stage.setScene(scene);
                stage.show();
                showAlert(Alert.AlertType.INFORMATION, ((Node) event.getSource()).getScene().getWindow(),
                        " Succés de suppression! ", " Suppression de la catégorie établie avec succés! ");

            } else {
                alert1.close();
            }
        }

    }

    @FXML
    private void updateCategorie(ActionEvent event) throws IOException {
        Alert alert1 = new Alert(Alert.AlertType.CONFIRMATION);
        alert1.setTitle("Confirmation de modification");
        alert1.setHeaderText(null);
        alert1.setContentText(" Etes-vous sure de vouloir modifier cette catégorie ? ");
        Optional<ButtonType> action = alert1.showAndWait();
        if (action.get() == ButtonType.OK) {
            if (nomCategorieTF.getText().isEmpty()) {
                System.out.println("valeur null");
                showAlert(Alert.AlertType.ERROR, ((Node) event.getSource()).getScene().getWindow(),
                        " Erreur de modification catégorie ! ", " Erreur de modification de la catégorie ! \n Veuillez remplir touts les champs! ");
                tv_categories.getSelectionModel().clearSelection();
                // Alerte de confirmation
            } else {
                sc.modifierCategorie(new Categorie(Integer.parseInt(IdCategorieTF.getText()), nomCategorieTF.getText()));
                root = FXMLLoader.load(getClass().getResource("./AddCategoryInterface.fxml"));
                stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
                scene = new Scene(root);
                stage.setScene(scene);
                stage.show();
                showAlert(Alert.AlertType.INFORMATION, ((Node) event.getSource()).getScene().getWindow(),
                        " Succés de modification! ", " Modification de la catégorie établie avec succés! ");
            }
        } else {
            alert1.close();
        }
    }

    @FXML
    private void switchTo(MouseEvent event) throws IOException {

        root = FXMLLoader.load(getClass().getResource("SelectionInterface.fxml"));
        stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        scene = new Scene(root);
        stage.setScene(scene);
        stage.show();
    }

    // Clear white space on clicking on the AnchorPane
    @FXML
    private void clearWhiteSpace(MouseEvent event
    ) {
        IdCategorieTF.setText("");
        nomCategorieTF.setText("");
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
