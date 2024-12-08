package com.hamNews.Views;

import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.shape.Arc;
import javafx.scene.shape.ArcType;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;

public class AuthentificationBanner {
    private Button signUpButton = new Button("Rejoindre nous !");
    private Button LoginBtton = new Button("Se connecter ");


    public VBox ShowAuthenBanner(){

        Text signUpText = new Text("Vous ne possédez pas encore un compte ?");
        signUpText.setFont(Font.font("Arial", FontWeight.BOLD, 26));
        signUpText.setFill(Color.WHITE);

        Text LoginText = new Text("Vous avez deja un compte ?");
        LoginText.setFont(Font.font("Arial", FontWeight.BOLD, 26));
        LoginText.setFill(Color.WHITE);



        signUpButton.setStyle("-fx-background-color: white; -fx-text-fill: #2980b9; -fx-font-size: 14px; -fx-padding: 10;");
        signUpButton.setPrefWidth(200);

        LoginBtton.setStyle("-fx-background-color: white; -fx-text-fill: #2980b9; -fx-font-size: 14px; -fx-padding: 10;");
        LoginBtton.setPrefWidth(200);


        double radius = 50;

        Arc arcBlue = new Arc(0, 0, radius, radius, 270, 180);
        arcBlue.setFill(Color.WHITE);
        arcBlue.setType(ArcType.ROUND);

        StackPane rightCirclePane = new StackPane(arcBlue);
        rightCirclePane.setAlignment(Pos.CENTER_LEFT);
        rightCirclePane.setTranslateX(-21);
        rightCirclePane.setPrefWidth(radius);

        VBox signUpContainer = new VBox(15, signUpText, signUpButton);
        signUpContainer.setAlignment(Pos.CENTER);
        VBox LoginContainer = new VBox(15,LoginText,LoginBtton, rightCirclePane);

        LoginContainer.setAlignment(Pos.CENTER);
        VBox TotaleContainer = new VBox(100,signUpContainer,LoginContainer);
        TotaleContainer.setAlignment(Pos.CENTER);
        TotaleContainer.setStyle("-fx-background-color: #2980b9; -fx-padding: 20;");
        TotaleContainer.setPrefWidth(575);
        return  TotaleContainer;
    }

    public Button getSignup(){

        return signUpButton;
    }
    public Button getLogin(){

        return LoginBtton;
    }
}