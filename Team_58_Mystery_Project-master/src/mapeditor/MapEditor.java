package mapeditor;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.VBox;
import javafx.stage.Modality;
import javafx.stage.Stage;


/**Contributors:
 * Jaimar Ong,
 * Tom Clark,
 * Shiv Gandhi
 */

public class MapEditor {


  public static int[][] map = new int[][] {
    {0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0},
    {0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0},
    {0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0},
    {0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0},
    {0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0},
    {0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0},
    {0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0},
    {0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0},
    {0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0},
    {0, 0, 0, 0, 0, 0, 0, 0, 0, 4, 4, 0, 0, 0, 0, 0, 0, 0, 0, 0},
    {0, 0, 0, 0, 0, 0, 0, 0, 0, 4, 4, 0, 0, 0, 0, 0, 0, 0, 0, 0},
    {0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0},
    {0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0},
    {0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0},
    {0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0},
    {0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0},
    {0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0},
    {0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0},
    {0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0},
    {0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0},

  };

  public MapEditor() {


  }

  public static void MapEditor(Stage ps) {
    final Stage mapEditStage = new Stage();
    mapEditStage.initModality(Modality.APPLICATION_MODAL); //blocks interaction with main window
    mapEditStage.initOwner(ps); 							//Dialog belongs to primaryStage

    GridPane grid = new GridPane();
    grid.setPadding(new Insets(10,10,10,10)); //fuck with this to see what it does
    grid.setVgap(5);
    grid.setHgap(5);

    final TextArea editorTextArea = new TextArea();
    String mapString = mapToString(map);
    editorTextArea.setText(mapString);
    editorTextArea.setPromptText("Map");
    editorTextArea.setPrefColumnCount(15);
    editorTextArea.setPrefSize(375, 375);
    editorTextArea.getText();
    GridPane.setConstraints(editorTextArea, 0, 0);
    grid.getChildren().add(editorTextArea);

    // container to hold all buttons on the right of the editor area
    // TODO maybe make this a method elsewhere to tidy up main of MapEditor.
    VBox btnBox = new VBox();
    btnBox.setSpacing(5);
    btnBox.setPrefWidth(100); // set width for buttons to match
    GridPane.setConstraints(btnBox,1,0);
    grid.getChildren().add(btnBox);


    Button submit = new Button("Submit");
    submit.setOnAction(event -> stringToMap(editorTextArea.getText()));
    GridPane.setConstraints(submit, 0, 1);
    grid.getChildren().add(submit);

    Scene dialogScene = new Scene(grid, 500, 500);
    mapEditStage.setScene(dialogScene);
    mapEditStage.show();

  }

  /**
   * This loads from the int[][] map to the textfield via making it a string
   * @param array - This is the map 2D array
   * @return - Returns the new String version of int[][] map
   */
  private static String mapToString(int[][] array) {
    String arrayText = "";

    for (int i = 0; i < array.length; i++) {
      arrayText += "{ ";
      for (int j = 0; j < array[i].length; j++) {
        if(j < array[i].length - 1) {
          arrayText += array[i][j] + ", ";
        } else {
          arrayText += array[i][j];
        }
      }
      arrayText += " }\n";
    }
    return arrayText;
  }

  /**
   * Loads from textField back into the floor int[][] map
   * @param s - This is the String inside the text field
   */
  private static void stringToMap(String s){

    //Uses parser to get all numbers and ignore whitespaces
    //and brackets,commas
    Pattern p = Pattern.compile("(\\d+)");
    Matcher m = p.matcher(s);
    List<Integer> numbers = new ArrayList<>();
    while(m.find()) {
      String token = m.group(1);
      int number = Integer.parseInt(token);
      numbers.add(number);

    }

    //Stores data from numbers arraylist to floor 2d array
    int index=0;
    for(int i = 0; i < map.length; i++) {
      for(int j = 0; j < map.length;j++) {
        map[i][j]=numbers.get(index++);

      }
    }
  }
}
