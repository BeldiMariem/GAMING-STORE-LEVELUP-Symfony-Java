/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package services;

import GUI.MD5;
import interfaces.Iuser;
import java.sql.Array;

import java.sql.Connection;
import java.sql.Date;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.time.LocalDateTime;
import java.time.OffsetDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.stream.Collectors;
import javafx.util.converter.LocalDateTimeStringConverter;
import models.Administrateur;
import models.Client;
import models.User;
import utils.MaConnexion;
import java.sql.Date;
import java.time.LocalDate;
import models.Categorie;
import models.Produit;
/**
 *
 * @author 21694
 */
public class ServiceUser implements Iuser {

    //var
    Connection cnx = MaConnexion.getInstance().getCnx();

    @Override
    public long ajouterPersonne(User p) {
        String request;
   
       if (p.getRole().equals("administrateur"))
       {  request = "INSERT INTO `user`(`email`, `password`, `role`, `nom`, `prenom`, `adresse`, `tel`, `dns`, `roles`, `tel`) VALUES ('" + p.getEmail() + "','" + p.getPassword() + "','" + p.getRole() + "','" + p.getNom() + "','" + p.getPrenom() + "','tunis','" + p.getDns() + "', '[\"ROLE_ADMIN\"]','00000000')";
        try {
            Statement st = cnx.createStatement();
            if (st.executeUpdate(request, Statement.RETURN_GENERATED_KEYS) == 1) {
                ResultSet rs = st.getGeneratedKeys();
                if (rs.next()) {
                    return rs.getLong(1);
                }
            }
            return 0;
        } catch (SQLException ex) {
            ex.printStackTrace();
            return 0;
        }
       }
       
       else if (p.getRole().equals("client"))
       {
             request = "INSERT INTO `user`(`email`, `password`, `role`, `nom`, `prenom`, `adresse`, `dns`, `roles`, `tel`) VALUES ('" + p.getEmail() + "','" + p.getPassword() + "','" + p.getRole() + "','" + p.getNom() + "','" + p.getPrenom() + "','tunis','" + p.getDns() + "', '[\"ROLE_CLIENT\"]','00000000')";
        try {
            Statement st = cnx.createStatement();
            if (st.executeUpdate(request, Statement.RETURN_GENERATED_KEYS) == 1) {
                ResultSet rs = st.getGeneratedKeys();
                if (rs.next()) {
                    return rs.getLong(1);
                }
            }
            return 0;
        } catch (SQLException ex) {
            ex.printStackTrace();
            return 0;
        }
       
       }
       else if (p.getRole().equals("livreur"))
       {
         request = "INSERT INTO `user`(`email`, `password`, `role`, `nom`, `prenom`, `adresse`, `dns`, `roles`, `tel`) VALUES ('" + p.getEmail() + "','" + p.getPassword() + "','" + p.getRole() + "','" + p.getNom() + "','" + p.getPrenom() + "','tunis','" + p.getDns() + "', '[\"ROLE_LIVREUR\"]','00000000')";
        try {
            Statement st = cnx.createStatement();
            if (st.executeUpdate(request, Statement.RETURN_GENERATED_KEYS) == 1) {
                ResultSet rs = st.getGeneratedKeys();
                if (rs.next()) {
                    return rs.getLong(1);
                }
            }
            return 0;
        } catch (SQLException ex) {
            ex.printStackTrace();
            return 0;
        }
       }
       else if (p.getRole().equals("fournisseur"))
       {
         request = "INSERT INTO `user`(`email`, `password`, `role`, `nom`, `prenom`, `adresse`, `dns`, `roles`, `tel`) VALUES ('" + p.getEmail() + "','" + p.getPassword() + "','" + p.getRole() + "','" + p.getNom() + "','" + p.getPrenom() + "','tunis','" + p.getDns() + "', '[\"ROLE_FOURNISSEUR\"]','00000000')";
        try {
            Statement st = cnx.createStatement();
            if (st.executeUpdate(request, Statement.RETURN_GENERATED_KEYS) == 1) {
                ResultSet rs = st.getGeneratedKeys();
                if (rs.next()) {
                    return rs.getLong(1);
                }
            }
            return 0;
        } catch (SQLException ex) {
            ex.printStackTrace();
            return 0;
        }
       }
       
       
         return 0;
       
    }

//    @Override
//    public List<User> afficherPersonnes() {
//        List<User> personnes = new ArrayList<User>();
//        String req = "SELECT * from user";
//        Statement st = null;
//        try {
//            st = cnx.createStatement();
//            ResultSet rs = st.executeQuery(req);
//            while (rs.next()) {
//                personnes.add(new User(rs.getInt("id_user"), rs.getString("email"), rs.getString("password"),
//                        rs.getString("role"), rs.getString("nom"), rs.getString("prenom"), rs.getString("adresse"), rs.getString("tel"), rs.getDate("dns")));
//            }
//        } catch (SQLException e) {
//            e.printStackTrace();
//        }
//        return personnes;
//    }
    
        @Override
    public List<User> afficherPersonnes() {
        List<User> personnes = new ArrayList<User>();
        String req = "SELECT * from user";
        Statement st = null;
        try {
            st = cnx.createStatement();
            ResultSet rs = st.executeQuery(req);
            while (rs.next()) {
                personnes.add(new User(rs.getInt("id_user"), rs.getString("email"), rs.getString("password"),
                        rs.getString("role"), rs.getString("nom"), rs.getString("prenom"), rs.getString("adresse"), rs.getString("tel"), rs.getDate("dns")
                        ,rs.getString("image")));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return personnes;
    }
    @Override
    public List<User> afficherParLettre(String lettre) {
      List<User> u = afficherPersonnes().stream()
                         .filter(x->x.getPrenom().startsWith(lettre.toUpperCase()))
                         .collect(Collectors.toList());
      return u;
       
    }
     @Override
    public List<User> afficherParPrenom(String prenom) {
      List<User> u = afficherPersonnes().stream()
                        .filter(x->x.getPrenom().toUpperCase().equals(prenom.toUpperCase()))
                         .collect(Collectors.toList());
      return u;
       
    }
    @Override
    public List<User> afficherParRole(String role) {
      List<User> u = afficherPersonnes().stream()
                        .filter(x->x.getRole().equals(role))
                         .collect(Collectors.toList());
      return u;
    }
    
    
    @Override
    public String getById(int email) {
        String req = "SELECT * from user WHERE  user.id_user= '" + email + "' ";
        User ad = new User();
        Statement st = null;
        try {
            st = cnx.createStatement();
            ResultSet rs = st.executeQuery(req);
            while (rs.next()) {
//                String[] adr= rs.getString("adresse").split("/"); ;
//                Adresse adresse=new Adresse(adr[0], adr[1], adr[2], adr[3]);
                ad = new User(rs.getInt("id_user"), rs.getString("email"), rs.getString("password"),
                        rs.getString("role"), rs.getString("nom"), rs.getString("prenom"), rs.getString("adresse"), rs.getString("tel"), rs.getDate("dns"));
                
                Date d =Date.valueOf(LocalDate.now());
                System.out.println((d.getYear())-(rs.getDate("dns").getYear()));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return ad.getEmail();
    }

    
    @Override
    public User getByEmail(String email) {
        String req = "SELECT * from user WHERE  user.email= '" + email + "' ";
        User ad = new User();
        Statement st = null;
        try {
            st = cnx.createStatement();
            ResultSet rs = st.executeQuery(req);
            while (rs.next()) {
                ad = new User(rs.getInt("id_user"), rs.getString("email"), rs.getString("password"),
                        rs.getString("role"), rs.getString("nom"), rs.getString("prenom"), rs.getString("adresse"), rs.getString("tel"), rs.getDate("dns"),rs.getString("image"));
                      }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return ad;

    }

    @Override
    public String authentification(String email, String password) {
        String req = "SELECT * from user WHERE  user.email= '" + email + "' ";

        try {
            Statement st = cnx.createStatement();
            ResultSet rs = st.executeQuery(req);
            //email correcte
            LocalDateTime time = LocalDateTime.now();
            if (rs.next()) {
                
                //System.out.println( rs.getTimestamp("limite").getHours()-time.getHour()  );
                //compte locked
                if (rs.getBoolean("locked")) {                    
                    if (Math.abs(time.getHour() - rs.getTimestamp("limite").getHours()) < 1) {
                        return "Votre compte est bloqué" ;
                    } else {

                        if (rs.getString("password").equals(password)) {
                            st.executeUpdate("UPDATE `user` SET `tentative`=0, `locked`=false WHERE `email`= '" + email + "' ;");
                            return rs.getString("role");
                        } else {
                            st.executeUpdate("UPDATE `user` SET `tentative`=`tentative`+1 , `locked`=false WHERE `email`= '" + email + "' ;");
                            return "Mot de passe incorrecte";
                        }
                    }
                } 
                
                else {

                    if (MD5.matches(rs.getString("password"), password)) {
                        return rs.getString("role");
                    } else {
                        //modification de l'attribut tentative
                        if (rs.getInt("tentative") == 2) {
                            st.executeUpdate("UPDATE `user` SET `tentative`=0, `locked`=true ,`limite`= '" + LocalDateTime.now() + "' WHERE `email`= '" + email + "' ;");
                            return "Vous avez depassez le nombre de tentatives , votre compte est bloqué";
                        } else {
                            st.executeUpdate("UPDATE `user` SET `tentative`=`tentative`+1  WHERE `email`= '" + email + "' ;");
                            return "Mot de passe incorrecte";
                        }
                    }
                }
            } else {//email inccorrecte
                return "email incorrecte";
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return "true";

    }

    @Override
    public boolean modifierPersonne(User p) {

        String req = "UPDATE `user` SET `email`= '" + p.getEmail() + "' , `password`='" + p.getPassword() + "' ,`role`='" + p.getRole() + "', `nom`='" + p.getNom() + "', `prenom`='" + p.getPrenom() + "',`adresse`='" + p.getAdresse() + "',`tel`='" + p.getTel() + "',`dns`='" + p.getDns() + "',`image`='" + p.getImage()+ "' WHERE `id_user` = " + p.getId() + " ";

        try {
            Statement st = cnx.createStatement();
            if (st.executeUpdate(req) == 1) {
                return true;
            }
            return false;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }

    }

    @Override
    public boolean supprimerPersonne(User p) {
        String req = "DELETE FROM `user` WHERE `id_user` = " + p.getId() + " ";
        try {
            Statement st = cnx.createStatement();
            if (st.executeUpdate(req) == 1) {
                return true;
            }
            return false;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }

    }
    
    public int CalculerUser(String role ){
         String req = "SELECT count(*) as nbr from user WHERE  user.role= '" + role + "' ";
         int i=0;
           Statement st = null;
            try {
            st = cnx.createStatement();
            ResultSet rs = st.executeQuery(req);
             while (rs.next()) {
               i=rs.getInt("nbr");
        }
           
           
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return i ;
        
    }
    public int CalculerActive(Boolean role ){
         String req = "SELECT count(*) as nbr from user WHERE  user.locked= " + role +"";
         int i=0;
           Statement st = null;
            try {
            st = cnx.createStatement();
            ResultSet rs = st.executeQuery(req);
             while (rs.next()) {
               i=rs.getInt("nbr");
        }
           
           
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return i ;
        
    }
//     public List<User> chercherProduitDynamiquement(String s, List<User> p) {
//        List<Produit> strList = l.stream()
//                .map(Produit::concat)
//                .filter(pt -> pt.contains(s)) //starts with (only searches for a person's id)
//                .map(pt -> new Produit(
//                Integer.parseInt(pt.split("-")[0]),
//                pt.split("-")[1],
//                pt.split("-")[2],
//                new Categorie(Integer.parseInt(pt.split("-")[3]), pt.split("-")[4]),
//                Double.parseDouble(pt.split("-")[5]),
//                pt.split("-")[6],
//                new User(Integer.parseInt(pt.split("-")[7])),
//                Double.parseDouble(pt.split("-")[8])
//        ))
//                //Dans le cas ou je veut limiter le nombre des produits affichés         .limit(5)
//                .collect(Collectors.toList());
//        return strList;
//    }
}
