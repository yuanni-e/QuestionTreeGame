//imports File, FileWriter, and Scanner packages
import java.io.File;
import java.io.FileWriter;
import java.util.Scanner;

/**
   contains methods to implement an interactive game of 20 questions with the user
   with a binary search tree with the BSTNode class on canvas
   games are generated from input txt files and may be updated to a file after the 
   user is finished playing
**/
public class QuestionTreeGame{
   
   /** 
      private static instance variables to store the root of the tree, the metadata
      lines of the input file, the scanner used for user input, the input file, and 
      a count of the wins and losses of the computer
   **/
   private static BSTNode root;
   private static String meta;
   private static Scanner in;
   private static String file;
   private static int W;
   private static int L;

   /** creates a Question Tree with the root set to "computer"
   tree is otherwise unpopulated and wins and losses are set to 0
   initializes scanner instance var to begin taking input
   @param none
   @return none
   **/ 
   public static void QuestionTreeBuilder(){
      L = 0;
      W = 0;
      root = new BSTNode("computer");
      in = new Scanner(System.in);
   }
   
   /** clears tree to be empty and losses and wins are set to 0
   also initializes scanner instance var to take input   
   @param none
   @return none
   **/
   public static void QuestionTree(){
      L = 0;
      W = 0;
      root = new BSTNode();
      in = new Scanner(System.in);
   }
   
   /** returns the number of games lost by the computer
   a game is lost when the user responses no to "Are you thinking of a(n): ..."
   @param none
   @return int number of games lost stored in L
   **/
   public static int getGamesLost(){
      return L;
   }
   
   /** returns the number of games won by the computer
   a game is lost when the user responses yes to "Are you thinking of a(n): ..."
   @param none
   @return int number of games won stored in W
   **/
   public static int getGamesWon(){
      return W;
   }
   
   /** reads the contents of a .txt file entered by the user via a scanner and
   populates the tree for the game with the data
   @param the name of the .txt to be read, but the param is not used because
   i used a scanner class to prompt for that information
   @return none
   **/
   public static void readData(String input){
      in = new Scanner(System.in);
      System.out.println("Which data file would you like to import?: ");
      
      //exception error message in case the file cannot be found
      //also so I don't alter the method with a defined exception
      try{
         in = new Scanner(new File(in.nextLine()));
         System.out.println("");
         file = in.nextLine().substring(2);
      }catch(Exception noFile){
         System.out.println("file not found");
      } 
      
      while(in.hasNextLine()) {
         root = read(in); 
      }
   } 
   
   /** private helper method for readData which reads a line of a file and stores
   the information in a node
   @param the Scanner used to parse the file
   @return the new node created with the stored line of data
   **/
   private static BSTNode read(Scanner scan){
      String line = scan.nextLine();
      String type = line.substring(0,1);
      String node = line.substring(2);
      BSTNode n = new BSTNode(node);  
      
      //if the line is a question, the method uses recursion to traverse from it down
      //to an answer node
      if (type.equals("Q")) {
         n.setLeft(read(in));
         n.setRight(read(in));   
      }
      
      //stores metadata lines in the meta variable
      if (type.equals("#")) {
         meta += node;   
      }
      return n; 
   }
   
   /** writes up the contents of the tree after a game is played into a file
   @param the output file to store new data
   @return none
   **/
   public static void writeData(String output){
      try{
         FileWriter out = new FileWriter(output);
         out.write(meta+"\n");
         writeNode(root, out);
      }catch(Exception noFile){
         System.out.println("file not found");
      }
   }
   
   /** private helper method for the writeData method that stores the information
   of an individual node into a file
   @param the node being stored and the FileWriter object used to update a file
   @return none
   **/
   private static void writeNode(BSTNode node, FileWriter file){
   
     //recursively traverses tree to store each node
      try{
         if(node.isLeaf()){
            file.write("A: " + node.value() + "\n");
            
         }else{
            file.write("Q: " + node.value() + "\n");
            writeNode(node.left(), file);
            writeNode(node.right(), file);
         }
      }catch(Exception noFile){
         System.out.println("file not found");
      }
   
   }
   
   /** plays a full game of 20 questions with the user
   uses recursive private helper ask(BSTNode node)
   @param none
   @return none
   **/
   public static void playGame(){
      System.out.println("Let's play '" + file + "'");
      System.out.println("Computer wins: " + W);
      System.out.println("Human wins: " + L);
      System.out.println("");
      System.out.println("Let's begin, think of something and I will try to guess it.");
      
      root = ask(root);
      
   }
   
   /** private helper method that plays the 20 questions game from a given starting node
   @param the BSTNode object node from which the game should start
   @return the starting node
   **/
   private static BSTNode ask(BSTNode node){
      if (node.isLeaf()) {
         //game is won by computer
         if (response("Are you thinking of a(n): " + node.value() +"?").equals("y")) {
            System.out.println("I win!");
            W++;
         //game is lost by the computer
         }else{
            System.out.println("Drat, I lost! What was your object? ");
            BSTNode answer = new BSTNode(in.nextLine());
            System.out.println("Type a Y/N question to distinguish " + answer.value() + 
               " from a(n) " + node.value() + ": ");
            String question = in.nextLine(); 
            L++;
            if(response("And what is the answer for a(n) " + answer.value() + 
            "? ").equals("y")) {
               node = new BSTNode(question, answer, node); 
            }else{
               node = new BSTNode(question, node, answer); 
            }   
         }
      }
      //uses recursion to keep asking questions if entered node was not a leaf
      else{
         if (response(node.value()).equals("n")) {
            node.setRight(ask(node.right()));
         }else{
            node.setLeft(ask(node.left())); 
         }   
      } 
      return node;
   }
   
   /** private helper method which outputs a question to the user and asks for a y/n
   reponses
   @param the String prompt the computer is asking the user to answer
   @return either "y" or "n" stored in String res
   **/
   private static String response(String prompt) {
      System.out.print(prompt + " (y/n)? ");
      in = new Scanner(System.in);
      String res = in.nextLine().toLowerCase();
      //repeats question if response is not "y" or "n"
      if (!(res.equals("y") || res.equals("n"))) {
         response(prompt);
      }
      return res;
   }

}

/** Binary tree node implementation: supports comparable objects and extends BinNode class
    taken from class canvas page
**/
class BSTNode implements BinNode {
   private String element; // Element for this node
   private BSTNode left;          // Pointer to left child
   private BSTNode right;         // Pointer to right child

  // Constructors
   BSTNode()
   { 
      left = right = null; 
   }
   BSTNode(String val) 
   { 
      left = right = null; 
      element = val; 
   }
   BSTNode(String val, BSTNode l, BSTNode r)
   { 
      left = l; 
      right = r; 
      element = val; 
   }

  // Get and set the element value
   public String value() { 
      return element; 
   }
      
   public void setValue(String v) { 
      element = v; 
   }
   public void setValue(Object v) { // We need this one to satisfy BinNode interface
      if (!(v instanceof String))
         throw new ClassCastException("A Comparable object is required.");
      element = (String)v;
   }

  // Get and set the left child
   public BSTNode left() { 
      return left; 
   }
   public void setLeft(BSTNode p) { 
      left = p; 
   }

  // Get and set the right child
   public BSTNode right() { 
      return right; 
   }
   public void setRight(BSTNode p) { 
      right = p; 
   }

  // return TRUE if a leaf node, FALSE otherwise
   public boolean isLeaf() { 
      return (left == null) && (right == null); 
   }
}
