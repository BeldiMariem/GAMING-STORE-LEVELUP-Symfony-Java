/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package GUI;

import interfaces.Iuser;
import java.io.IOException;
import java.net.URL;
import java.util.List;
import java.util.Optional;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.collections.FXCollections;
import javafx.collections.ListChangeListener;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;
import models.Livraison;
import models.Reclamation;
import models.User;
import services.ServiceLivraison;
import services.ServiceLivreur;
import services.ServiceReclamation;
import services.ServiceUser;
import GUI.Emailer;
import java.io.File;
import java.util.Properties;
import javafx.scene.Node;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.paint.Color;
import javafx.scene.paint.ImagePattern;
import javafx.scene.shape.Circle;
import javax.mail.Authenticator;
import javax.mail.Message;
import javax.mail.PasswordAuthentication;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import utils.Session;


/**
 * FXML Controller class
 *
 * @author User
 */
public class GestionReclmationController implements Initializable {

    @FXML
    private TableView<Object> tableReclamation;
    @FXML
    private TableColumn<Reclamation, String> Description;
    @FXML
    private Button Retour;
    @FXML
    private Button Supprimer;
    @FXML
    private Button warn;
    @FXML
    private Button SMS;
    @FXML
    private Circle myCircle1;
    @FXML
    private ImageView top;
     private Stage stage;
    private Scene scene;
    private Parent root;
    

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
         File file = new File(Session.getImage());
        Image im = new Image(file.toURI().toString());
        myCircle1.setStroke(Color.SEAGREEN);
        myCircle1.setFill(new ImagePattern(im));
        Supprimer.setDisable(true);
        warn.setDisable(true);
        
        ServiceReclamation sr = new ServiceReclamation();
        List<Reclamation> livRec= sr.afficherReclamation();
        ObservableList<Object> ListRecData = FXCollections.observableArrayList();
        ListRecData.addAll(livRec);
        Description.setCellValueFactory(new PropertyValueFactory<>("Description"));

tableReclamation.setItems(ListRecData);
ObservableList selectedCells = tableReclamation.getSelectionModel().getSelectedCells();
  
  selectedCells.addListener(new ListChangeListener() {
            @Override
            public void onChanged(Change c) {
               Reclamation ReclamationSelected = (Reclamation) tableReclamation.getSelectionModel().getSelectedItem();
                if(ReclamationSelected!=null){
                    Supprimer.setDisable(false);
                    if(ReclamationSelected.isWarn()){
                                        warn.setDisable(true);

                    }else{
                    warn.setDisable(false);}
                }else{ Supprimer.setDisable(true);
                    warn.setDisable(true);

                }
            }      
        });

        
       
    }    

    @FXML
    private void Retour(ActionEvent event) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("livadmin.fxml"));
            Parent root = loader.load();
            Retour.getScene().setRoot(root);
        } catch (IOException ex) {
            Logger.getLogger(LivadminController.class.getName()).log(Level.SEVERE, null, ex);

        }
    }

    @FXML
    private void Supprimer(ActionEvent event) {
                       Reclamation ReclamationSelected = (Reclamation) tableReclamation.getSelectionModel().getSelectedItem();
        ServiceReclamation sr = new ServiceReclamation();
sr.supprimerReclamation(ReclamationSelected);
miseajour();
    }

    @FXML
    private void warnLiv(ActionEvent event) {
                               Reclamation ReclamationSelected = (Reclamation) tableReclamation.getSelectionModel().getSelectedItem();
        ServiceReclamation sr = new ServiceReclamation();
    
        sr.warn(ReclamationSelected);
ServiceLivraison SL= new ServiceLivraison();
        Optional<Livraison> L = SL.afficherLivraison().stream().filter(l->l.getId_livraison()==ReclamationSelected.getLivraison().getId_livraison()).findFirst();
      System.out.print("hey"+L.get().getUser());
      ServiceUser us = new ServiceUser();
     String email = us.getById(L.get().getUser().getId());
      User UserToSent = us.afficherPersonnes().stream().filter(e->e.getId()==L.get().getUser().getId()).findFirst().get();
       
        try {
            
                Properties properties = new Properties();
                properties.put("mail.smtp.auth", "true");
                properties.put("mail.smtp.starttls.enable", "true");
                properties.put("mail.smtp.host", "smtp.gmail.com");
                properties.put("mail.smtp.port", "587");

                String em = "hazembayoudh886@gmail.com";
                String password = "hazem+19";

                javax.mail.Session session = javax.mail.Session.getInstance(properties, new Authenticator() {
                    @Override
                    protected PasswordAuthentication getPasswordAuthentication() {
                        return new PasswordAuthentication(em, password);
                    }
                });

                Message msg = new MimeMessage(session);
                msg.setFrom(new InternetAddress(em));
                msg.setRecipient(Message.RecipientType.TO, new InternetAddress("amalnouira26@gmail.com"));
                msg.setSubject("Warn");
                msg.setText("");

                String htmlCode =  "Warning   "+ReclamationSelected.getDescription();
                msg.setContent(htmlCode, "text/html");
                Transport.send(msg);
                System.out.println("Message sent successfully !!!!!");
         
          //  JavamailUtil.sendMailaide( email, "WARNING", "SERVICE RECLAMATION",ReclamationSelected.getDescription() );
        } catch (Exception ex) {
            Logger.getLogger(GestionReclmationController.class.getName()).log(Level.SEVERE, null, ex);
        }

        
    }
    void miseajour(){
     ServiceReclamation sr = new ServiceReclamation();
        List<Reclamation> livRec= sr.afficherReclamation();
        ObservableList<Object> ListRecData = FXCollections.observableArrayList();
        ListRecData.addAll(livRec);
        Description.setCellValueFactory(new PropertyValueFactory<>("description"));

tableReclamation.setItems(ListRecData);}

    @FXML
    private void sms(ActionEvent event) throws IOException 
         {
            SMS.getScene().getWindow().hide();
            Stage location = new Stage();
            Parent root = FXMLLoader.load(getClass().getResource("sendSms.fxml"));
            Scene scene = new Scene(root);
            location.setScene(scene);
            location.show();
            location.setResizable(false);
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
    

