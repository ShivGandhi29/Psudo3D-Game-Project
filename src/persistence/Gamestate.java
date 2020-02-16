package persistence;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.io.FileNotFoundException;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

import com.sun.org.apache.xml.internal.serialize.XMLSerializer;
import com.sun.org.apache.xml.internal.serialize.OutputFormat;

import gameworld.Board;
import gameworld.Key;
import gameworld.Level;
import gameworld.MainGameWorld;
import gameworld.Player;
import gameworld.Timer;
import renderer.Camera;



/**
 * @author Tom
 *
 * Contributors:
 *      David
 */
public class Gamestate {
    private static Document xmlDoc;
    private MainGameWorld gameWorld;


    /**
     * Saves the game to a XML format file
     * @param String gameName - This is the name of the file the users would like to assign
     * @param MainGameWorld game - This is the gameWorld in which everything needed to save is located
     */
    public void saveGame(String gameName, MainGameWorld game) throws ParserConfigurationException, FileNotFoundException, IOException, Exception{


        // ************  Initiate the XML file  ******************** //

        DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
        DocumentBuilder builder = factory.newDocumentBuilder();
        xmlDoc = builder.newDocument();

         Element rootElement = xmlDoc.createElement("mainGameWorld");

        // Player Object

         Element player = xmlDoc.createElement("Player");

        // Player Keys

         Element keys = xmlDoc.createElement("Keys");

         keys.setAttribute("doorKey", Boolean.toString(game.getPlayer().getPlayerKey()));
         keys.setAttribute("doorHiddenKey", Boolean.toString(game.getPlayer().getPlayerHiddenKey()));

        // Player Positions

         Element position = xmlDoc.createElement("Position");

         position.setAttribute("playerPosX", Double.toString(game.getPlayer().getPositionX()));
         position.setAttribute("playerPosY", Double.toString(game.getPlayer().getPositionY()));

        // Player Camera

         Element camera = xmlDoc.createElement("Camera");

         camera.setAttribute("xDir", Double.toString(game.getPlayer().getCam().getXDir()));
         camera.setAttribute("yDir", Double.toString(game.getPlayer().getCam().getYDir()));
         camera.setAttribute("xPlane", Double.toString(game.getPlayer().getCam().getXPlane()));
         camera.setAttribute("yPlane", Double.toString(game.getPlayer().getCam().getYPlane()));

        // Map

         Element maps = xmlDoc.createElement("Maps");

        // Board

         Element board = xmlDoc.createElement("Board");
         board.setAttribute("Tiles", mapToString(game.getBoard().getMap()));
         board.setAttribute("Width", Integer.toString(game.getBoard().getLvl().getWidth()));
         board.setAttribute("Height", Integer.toString(game.getBoard().getLvl().getWidth()));

        // Level

         Element level = xmlDoc.createElement("Level");
         level.setAttribute("CurrentLevel", game.getBoard().getLvl().toString());

        // Timer

         Element timer = xmlDoc.createElement("Timer");
         timer.setAttribute("seconds", Integer.toString(game.getTimer().getSeconds()));
         timer.setAttribute("minutes", Integer.toString(game.getTimer().getMinutes()));



         // ************* Association formatting ********** //

         // Fourth Tier XML


         // Third Tier XML


         // Second Tier XML

         maps.appendChild(board);
         maps.appendChild(level);
         player.appendChild(position);
         player.appendChild(camera);
         player.appendChild(keys);

         // First Tier XML

         rootElement.appendChild(maps);
         rootElement.appendChild(player);
         rootElement.appendChild(timer);

         // Root Tier

         xmlDoc.appendChild(rootElement);




         // *****************  Finalizing the File  **************** //

         // Set Output format

         OutputFormat outFormat = new OutputFormat(xmlDoc);
         outFormat.setIndenting(true);

         // Declare the File

         File dir = new File ("saves"); //declare the intended directory.  (David)
         dir.mkdirs();                        //create the directory if not already existing. (David)
         File xmlFile = new File(dir, gameName + ".xml"); // save game to the directory using given name.

         // Declare the FileOutputStream

         FileOutputStream outStream = new FileOutputStream(xmlFile);

         // XMLSerializer to serialize the xml data with the specified Output Format

         XMLSerializer serializer = new XMLSerializer(outStream, outFormat);

         // specified OutputFormat

         serializer.serialize(xmlDoc);

    }

    /**
     * This loads the game file that they player has chosen.
     * @param gameName - Name of the game the player would like to load
     */
    public MainGameWorld loadGame(String gameName) {

        this.gameWorld = new MainGameWorld(Level.LEVEL_ONE);  // For the creation of a new world

        try {

        DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();

            factory.setIgnoringComments(true);
            factory.setValidating(false);

        // **************  Makes file to store and load from the games ***********

        File dir = new File ("saves/"); //declare the intended directory.  (David)
        dir.mkdirs();                         //create the directory if not already existing. (David)



        // **************  Builds the Formatting Reader ***********

        DocumentBuilder builder = factory.newDocumentBuilder();
        Document xmlDoc = builder.parse(dir + "/" +gameName + ".xml");

        xmlDoc.getDocumentElement().normalize(); // This may effect format



        // ************** Reading Nodes from File ****************

        System.out.println("Root: " + xmlDoc.getDocumentElement().getNodeName());


        getElementAndAttrib(gameWorld, xmlDoc);


        } catch (ParserConfigurationException e) {
            e.printStackTrace();
        } catch (SAXException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }

        System.out.println("Load Successful");
        return gameWorld;

    }

    /**
     * This loads from the int[][] map to the text field via making it a string
     * @param array - This is the map 2D array
     * @return - Returns the new String version of int[][] map
     */
    private static String mapToString(int[][] array) {
        String arrayText = "";

        for (int i = 0; i < array.length; i++) {
            arrayText += "{";
            for (int j = 0; j < array[i].length; j++) {
                if(j < array[i].length - 1) {
                    arrayText += array[i][j] + ", ";
                } else {
                    arrayText += array[i][j];
                }
            }
            arrayText += "}\n";
        }
        return arrayText;
    }

    /**
     * Loads from textField back into the floor int[][] map
     * @param s - This is the String inside the text field
     */
    private static void xmlMapToIntMap(String s, int width, int height, MainGameWorld game){

        Pattern p = Pattern.compile("(\\d+)");
        Matcher m = p.matcher(s);
        List<Integer> numbers = new ArrayList<>();
        while(m.find()) {
            String token = m.group(1);
            int number = Integer.parseInt(token);
            numbers.add(number);
        }
        System.out.println(numbers);

        //Stores data from numbers array list to floor 2d array
        int inx=0;
        for(int i = 0; i < height; i++) {
            for(int j = 0; j < width;j++) {
                game.getBoard().getLvl().getMap()[i][j] = numbers.get(inx++);
            }
        }

    }


    /**
     *Parses through Nodes in XML document and translates to gameWorld variables to load an old game.
     */
    private static void getElementAndAttrib(MainGameWorld game, Document xmlDoc){

        try {

                NodeList mapList = xmlDoc.getElementsByTagName("Maps");

                Node mapNode = mapList.item(0);

                Element mapElement = (Element) mapNode;


                ////////////////  BOARD INFO  ////////////////

                NodeList boardList = mapElement.getElementsByTagName("Board");

                Element boardElement = (Element) boardList.item(0);

                // String to int
                int boardHeight = Integer.parseInt(boardElement.getAttribute("Height"));
                int boardWidth = Integer.parseInt(boardElement.getAttribute("Width"));


                /////////////   LEVEL INFO   /////////////////

                NodeList levelList = mapElement.getElementsByTagName("Level");

                Element levelElement = (Element) levelList.item(0);                     //  may not need yet waiting on ENUM use

                /////////////   PLAYER INFO   /////////////////

                NodeList playerList = xmlDoc.getElementsByTagName("Player");

                Node playerNode = playerList.item(0);

                Element playerElement = (Element) playerNode;


                /////////////   PLAYER KEYS  /////////////////

                NodeList keyList = xmlDoc.getElementsByTagName("Keys");

                Node keyNode = keyList.item(0);

                Element keyElement = (Element) keyNode;

                /////////////   PLAYER POSITION   /////////////////

                NodeList posList = playerElement.getElementsByTagName("Position");

                Element posElement = (Element) posList.item(0);


                /////////////   PLAYER CAMERA   /////////////////

                NodeList camList = playerElement.getElementsByTagName("Camera");

                Element camElement = (Element) camList.item(0);


                /////////////   TIMER   /////////////////

                NodeList timerList =  xmlDoc.getElementsByTagName("Timer");

                Node timerNode = timerList.item(0);

                Element timerElement = (Element) timerNode;


                /////////////  LOAD GAME TO NEW WORLD  /////////////

                //Board makes game with board enum
                String level = levelElement.toString();
                switch (level) {
                    case "LEVEL_ONE":
                            Board a = new Board(Level.LEVEL_ONE);
                            game.setBoard(a);
                            break;
                    case "LEVEL_TWO":
                            Board b = new Board(Level.LEVEL_TWO);
                            game.setBoard(b);
                            break;
                    case "LEVEL_THREE":
                            Board c = new Board(Level.LEVEL_THREE);
                            game.setBoard(c);
                            break;
                    default:
                            break;
                }


                    xmlMapToIntMap(boardElement.getAttribute("Tiles"), boardWidth , boardHeight, game);

                    game.getPlayer().setPositionXY(Double.parseDouble(posElement.getAttribute("playerPosX")),
                                                    Double.parseDouble(posElement.getAttribute("playerPosY")));

                    game.getPlayer().setPlayerKey(Boolean.getBoolean(keyElement.getAttribute("doorKey")));
                    game.getPlayer().setPlayerKey(Boolean.getBoolean(keyElement.getAttribute("doorHiddenKey")));

                    Camera cam = new Camera(Double.parseDouble(posElement.getAttribute("playerPosX")),
                                               Double.parseDouble(posElement.getAttribute("playerPosY")),
                                               Double.parseDouble(camElement.getAttribute("xDir")),
                                               Double.parseDouble(camElement.getAttribute("yDir")),
                                               Double.parseDouble(camElement.getAttribute("xPlane")),
                                               Double.parseDouble(camElement.getAttribute("yPlane")));
                    System.out.println("pos x:  " + Double.parseDouble(posElement.getAttribute("playerPosX")));
                    System.out.println("pos y:  " + Double.parseDouble(posElement.getAttribute("playerPosY")));
                    System.out.println("direction x:  " + Double.parseDouble(camElement.getAttribute("xDir")));
                    System.out.println("direction y:  " + Double.parseDouble(camElement.getAttribute("yDir")));
                    System.out.println("plane x:  " + Double.parseDouble(camElement.getAttribute("xPlane")));
                    System.out.println("plane y:  " + Double.parseDouble(camElement.getAttribute("yPlane")));
                    game.getPlayer().setCamera(cam);

                    Timer timer = new Timer();
                    timer.setSeconds(Integer.parseInt(timerElement.getAttribute("seconds")));
                    timer.setMinutes(Integer.parseInt(timerElement.getAttribute("minutes")));
                    game.setTimer(timer);

        }catch(Exception e){
            e.printStackTrace();
        }

    }


}

