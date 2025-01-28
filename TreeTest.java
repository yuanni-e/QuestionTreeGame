public class TreeTest{
   public static void main(String[] args){
      QuestionTreeGame game = new QuestionTreeGame();
      game.readData("tree1");
      game.playGame();
      game.writeData("tree1");
   }
}