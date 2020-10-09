package netflix;

import java.io.IOException;
import java.net.URI;
import java.net.URL;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.Random;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import org.json.JSONArray;
import org.json.JSONObject;

public class viewController  implements Initializable  {
    
    @Override
    public void initialize(URL url, ResourceBundle rb){
        
        try {
            //load total questions, initialize score and currentQuestion to 0;
            totalQuestions.setText(String.valueOf(GameState.GAME_ONE_TOTAL_QUESITIONS));
            currentQuestion.setText("0");
            score.setText("0");
      
            //Generate random movie id and fetch from api
            Random rand = new Random(); //instance of random class
            int randInt = rand.nextInt(1000) + 1;
            String stringID = String.valueOf(randInt);
            
            String baseURL = "https://api.themoviedb.org/3/movie/";
            String movieID = stringID;
            String apiKey = "3ee7fedb5506448980b60224316cdcd0";
            
            String imageBaseURL = "https://image.tmdb.org/t/p/";
            String size = "w154";
            
            String[] posterURLS = new String[5];
            String description = "";
            String correctIndex = "";
            
            String firstRequest = baseURL + movieID + "?api_key=" + apiKey;
            String secondRequest = baseURL + movieID + "/similar" + "?api_key=" + apiKey;
            
            //First Request
            
            HttpClient httpClient = HttpClient.newHttpClient();
            HttpResponse response;
            response = httpClient.send(HttpRequest.newBuilder()
                    .GET()
                    .uri(URI.create(firstRequest))
                    .build(), HttpResponse.BodyHandlers.ofString());
            
            
            
            JSONObject obj = new JSONObject(response.body().toString());
            String overview = (String) obj.get("overview");
            String imgPath = (String) obj.get("poster_path");
            String imgUrl = imageBaseURL + size + imgPath;
            posterURLS[0] = imgUrl;
            
            //Second Request
            response = httpClient.send(HttpRequest.newBuilder()
                    .GET()
                    .uri(URI.create(secondRequest))
                    .build(), HttpResponse.BodyHandlers.ofString());
            
            
            
            obj = new JSONObject(response.body().toString());
            JSONArray results = (JSONArray) obj.get("results");
            for(int i=0; i < posterURLS.length - 1; i++){
                
                JSONObject result = (JSONObject) results.get(i);
                String posterPath = (String) result.get("poster_path");
                String posterURL = imageBaseURL + size + posterPath;
                posterURLS[i+1] = posterURL;
            }
            
            //Shuffle array and save correct index
            int correctIndexInt = rand.nextInt(4)+1;
            correctIndex = String.valueOf(correctIndexInt);
            swap(posterURLS, correctIndexInt, 0);
            String correctAnswer = String.valueOf(correctIndexInt + 1);
            GameState.correctAnswer =  correctAnswer;
            
            
            //Add posters and description to view
            imageContainer.setSpacing(20);
            for(int i=0; i < posterURLS.length; i++){
                
                AnchorPane container = new AnchorPane();
                ImageView imgView = new ImageView();
                Image image = new Image(posterURLS[i]);
                imgView.setImage(image);
                Label imageLabel = new Label(String.valueOf(i+1));
                imageLabel.setFont(new Font(30));
                container.getChildren().addAll(imgView, imageLabel);
                imageContainer.getChildren().add(container);
            }
            
            //Add Description
            String[] scentences = overview.split("\\.");
            String firstScentence = scentences[0] + ".";
            questionText.setText(firstScentence);
            
            
            
            
        } catch (Exception ex) {
            System.out.println(ex);
        }
            
            
            
       
       
    }

    @FXML
    private HBox imageContainer;

    @FXML
    private TextField response;

    @FXML
    private Text questionText;
    
        @FXML
    private Text currentQuestion;

    @FXML
    private Text totalQuestions;

    @FXML
    private Text score;

    @FXML
    private void submitAnswer(ActionEvent event) {
        
        //get answers
        String givenAnswer = response.getText();
        String correctAnswer = GameState.correctAnswer;
        
        //process answer
        String result = "";
        if(givenAnswer.equals(correctAnswer)){
            result = "Correct!";
            GameState.currentScore = GameState.currentScore + 1;
        }
        else{
            result = "Incorrect, the correct answer was: " + correctAnswer;
        }
        
        //display result
        questionText.setText("");
        questionText.setText(result);
        
        //check if game over
       
        int currentQ = GameState.currentQuestion;
        int totalQ = GameState.GAME_ONE_TOTAL_QUESITIONS;
        int currentScore = GameState.currentScore;
        boolean gameOver = currentQ > totalQ;
        if(gameOver){
            questionText.setText("Game Over");
            
        }
        
        
        
    }
    
    @FXML
    private void newQuestion(ActionEvent event) {
        
        //clean up view
        
        questionText.setText("");
        imageContainer.getChildren().clear();
        
        //
        
         try {
      
            
            Random rand = new Random(); //instance of random class
            int randInt = rand.nextInt(1000) + 1;
            String stringID = String.valueOf(randInt);
            
            String baseURL = "https://api.themoviedb.org/3/movie/";
            String movieID = stringID;
            String apiKey = "3ee7fedb5506448980b60224316cdcd0";
            
            String imageBaseURL = "https://image.tmdb.org/t/p/";
            String size = "w154";
            
            String[] posterURLS = new String[5];
            String description = "";
            String correctIndex = "";
            
            String firstRequest = baseURL + movieID + "?api_key=" + apiKey;
            String secondRequest = baseURL + movieID + "/similar" + "?api_key=" + apiKey;
            
            //First Request
            
            HttpClient httpClient = HttpClient.newHttpClient();
            HttpResponse response;
            response = httpClient.send(HttpRequest.newBuilder()
                    .GET()
                    .uri(URI.create(firstRequest))
                    .build(), HttpResponse.BodyHandlers.ofString());
            
            
            
            JSONObject obj = new JSONObject(response.body().toString());
            String overview = (String) obj.get("overview");
            String imgPath = (String) obj.get("poster_path");
            String imgUrl = imageBaseURL + size + imgPath;
            posterURLS[0] = imgUrl;
            
            //Second Request
            response = httpClient.send(HttpRequest.newBuilder()
                    .GET()
                    .uri(URI.create(secondRequest))
                    .build(), HttpResponse.BodyHandlers.ofString());
            
            
            
            obj = new JSONObject(response.body().toString());
            JSONArray results = (JSONArray) obj.get("results");
            for(int i=0; i < posterURLS.length - 1; i++){
                
                JSONObject result = (JSONObject) results.get(i);
                String posterPath = (String) result.get("poster_path");
                String posterURL = imageBaseURL + size + posterPath;
                posterURLS[i+1] = posterURL;
            }
            
            //Shuffle array and save correct index
            int correctIndexInt = rand.nextInt(4)+1;
            correctIndex = String.valueOf(correctIndexInt);
            swap(posterURLS, correctIndexInt, 0);
            String correctAnswer = String.valueOf(correctIndexInt + 1);
            GameState.correctAnswer =  correctAnswer;
            
            //Add posters and description to view
            imageContainer.setSpacing(20);
            for(int i=0; i < posterURLS.length; i++){
                AnchorPane container = new AnchorPane();
                ImageView imgView = new ImageView();
                Image image = new Image(posterURLS[i]);
                imgView.setImage(image);
                Label imageLabel = new Label(String.valueOf(i+1));
                imageLabel.setFont(new Font(30));
                container.getChildren().addAll(imgView, imageLabel);
                imageContainer.getChildren().add(container);
            }
            
            
            //Add Description
            String[] scentences = overview.split("\\.");
            String firstScentence = scentences[0] + ".";
            questionText.setText(firstScentence);
            
            //Update state
            GameState.currentQuestion = GameState.currentQuestion + 1;
            int currentQ = GameState.currentQuestion;
            int totalQ = GameState.GAME_ONE_TOTAL_QUESITIONS;
            int currentScore = GameState.currentScore;
            currentQuestion.setText(String.valueOf(currentQ));
            totalQuestions.setText(String.valueOf(totalQ));
            score.setText(String.valueOf(currentScore));
            
         } catch(Exception ex){
             
         }
        
    }
    
    public void swap(String[] posters, int i, int j){
        String temp = posters[i];
        posters[i] = posters[j];
        posters[j] = temp;
    }

    

}
