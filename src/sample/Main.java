package sample;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import javax.swing.*;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class Main extends Application {

    private static final String CONNECTION_STRING = "jdbc:mysql://127.0.0.1:3306/world";
    private static final String USUARIO = "root";
    private static final String CLAVE = "";
    private static Connection conexion;


    @Override
    public void start(Stage primaryStage) throws Exception{
        abrirConexion();
        Parent root = FXMLLoader.load(getClass().getResource("Vistas/Inicio.fxml"));
        primaryStage.setTitle("World");
        primaryStage.setScene(new Scene(root, 450, 300));
        primaryStage.show();
    }


    private static void abrirConexion(){
        try {
            conexion = DriverManager.getConnection(CONNECTION_STRING,USUARIO,CLAVE);
            JOptionPane.showMessageDialog(null,"Exito"); // permite que la nueva ventana de mensaje no bloquee la ventana principal.
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null,"La conexion a la base de datos no fue establecida");
        }
    }
        public static Connection getConexion(){
            return conexion;
    }






    public static void main(String[] args) {
        launch(args);
    }
}
