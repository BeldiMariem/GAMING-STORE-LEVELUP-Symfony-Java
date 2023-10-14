/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package GUI;

import interfaces.Iuser;
import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.animation.Animation;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.Event;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.chart.PieChart;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.paint.Color;
import javafx.scene.paint.ImagePattern;
import javafx.scene.shape.Circle;
import javafx.stage.Stage;
import javafx.util.Duration;
import services.ServiceUser;
import utils.Session;

/**
 * FXML Controller class
 *
 * @author beldi
 */
public class DashboardController implements Initializable {

    @FXML
    private PieChart clientSexe;
    private Stage stage;
    private Scene scene;
    private Parent root;
    @FXML
    private Label numClient;
    @FXML
    private Label numFournisseur;
    @FXML
    private Label numLivreur;
    @FXML
    private Label numAdmin;
    @FXML
    private Label nonactive;
    @FXML
    private Label actif;
    @FXML
    private Label clock;
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
        final DateFormat format = DateFormat.getInstance();
        final Timeline timeline = new Timeline(new KeyFrame(Duration.seconds(1),
                new EventHandler() {

            @Override
            public void handle(Event event) {
                final Calendar cal = Calendar.getInstance();
                clock.setText(format.format(cal.getTime()));
            }
        }));

        timeline.setCycleCount(Animation.INDEFINITE);
        timeline.play();

        Iuser sp = new ServiceUser();
        numAdmin.setText(String.valueOf(sp.CalculerUser("administrateur")));
        numClient.setText(String.valueOf(sp.CalculerUser("client")));
        numFournisseur.setText(String.valueOf(sp.CalculerUser("fournisseur")));
        numLivreur.setText(String.valueOf(sp.CalculerUser("livreur")));
        ObservableList<PieChart.Data> pieChartData
                = FXCollections.observableArrayList(
                        new PieChart.Data("Homme", 13),
                        new PieChart.Data("Femme", 25)
                );
        clientSexe.getData().addAll(pieChartData);
//        clientSexe.setTitle("Pourcentage");
        actif.setText(String.valueOf(sp.CalculerActive(false)));
        nonactive.setText(String.valueOf(sp.CalculerActive(true)));
        final Label caption = new Label("");
        caption.setTextFill(Color.DARKORANGE);
        caption.setStyle("-fx-font: 24 arial;");

        for (final PieChart.Data data : clientSexe.getData()) {
            data.getNode().addEventHandler(MouseEvent.MOUSE_PRESSED, new EventHandler<MouseEvent>() {
                @Override
                public void handle(MouseEvent e) {
                    double total = 0;
                    for (PieChart.Data d : clientSexe.getData()) {
                        total += d.getPieValue();
                    }
                    caption.setTranslateX(e.getSceneX());
                    caption.setTranslateY(e.getSceneY());
                    String text = String.format("%.1f%%", 100 * data.getPieValue() / total);
                    caption.setText(text);

                }
            });
        }

        for (final PieChart.Data data : clientSexe.getData()) {
            data.getNode().addEventHandler(MouseEvent.MOUSE_PRESSED,
                    e -> {

                    }
            );
        }

        // TODO
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
