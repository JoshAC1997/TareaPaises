package sample.Controladores;

import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import sample.Main;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class Controller {
    public TextField txtPais;
    public ListView<String> lvPaises;
    public ListView<String> lvCiudades;
    public Label titulo;
    public Label error;
    public Label Dist;
    public Label Pob;
    public Label codP;
    public Label id;
    public Label nomC;
    public String paisSeleccionado;
    public String ciudadSeleccionada;

    private ObservableList<String> paises = FXCollections.observableArrayList();
    private ObservableList<String> ciudades = FXCollections.observableArrayList();


    @FXML
    public void initialize(){
        lvPaises.setItems(paises);
        lvCiudades.setItems(ciudades);
        lvPaises.getSelectionModel().selectedItemProperty().addListener(new ChangeListener<String>() {
            @Override
            public void changed(ObservableValue<? extends String> observable, String oldValue, String newValue) {
                paisSeleccionado=newValue;
                try {
                    Connection con = Main.getConexion();
                    Statement stmt = con.createStatement();
                    //dos puntitos por hacer la consulta
                    String sql = "SELECT Name FROM city WHERE countryCode=(SELECT code FROM country WHERE Name='"+paisSeleccionado+"')";
                    ResultSet resultado = stmt.executeQuery(sql);
                    while (resultado.next()){
                        ciudades.add(resultado.getString(1));
                    }

                } catch (SQLException e) {
                    error.setText(e.getMessage());
                }
            }
        });
        this.lvCiudades.getSelectionModel().selectedItemProperty().addListener(new ChangeListener<String>() {
            public void changed(ObservableValue<? extends String> observable, String oldValue, String newValue) {
                Controller.this.ciudadSeleccionada = newValue;


                try {
                    Connection con = Main.getConexion();
                    Statement stmt = con.createStatement();
                    String sql = "SELECT * FROM city WHERE name='" + Controller.this.ciudadSeleccionada + "'";
                    ResultSet resultado = stmt.executeQuery(sql);

                    while(resultado.next()) {
                        Controller.this.id.setText(resultado.getString("ID"));
                        Controller.this.nomC.setText(resultado.getString("Name"));
                        Controller.this.codP.setText(resultado.getString("CountryCode"));
                        Controller.this.Dist.setText(resultado.getString("District"));
                        Controller.this.Pob.setText(resultado.getString("Population"));
                    }
                } catch (SQLException var8) {
                    var8.printStackTrace();
                }

            }
        });

    }

    public void buscarPais(KeyEvent keyEvent) {
        paises.clear();
        String nombreBusqueda = txtPais.getText().trim();
        if (nombreBusqueda.length()>=1){
            Connection con = Main.getConexion();
            try{
                Statement stmt = con.createStatement();
                String sql = "SELECT Name FROM country WHERE Name LIKE '" + nombreBusqueda + "%'";
                ResultSet resultado = stmt.executeQuery(sql);  // SHIF + F6 PARA CAMBIAR UNA VARIABLE
                while (resultado.next()){
                    paises.add(resultado.getString("Name"));
                }
                resultado.close();
            } catch (SQLException e) {
                error.setText(e.getMessage());
            }
        }

    }
}

//        if (keyEvent.getCode()== KeyCode.ENTER){
//            paises.add(txtPais.getText());
//            txtPais.clear();
//        }

//        titulo.setText(String.valueOf(keyEvent.getCode()));
// Se utiliza un observablearraylist para actualizar el listview con los cambios recientes.