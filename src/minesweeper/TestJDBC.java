package minesweeper;

import entity.Score;
import entity.Comment;
import service.ScoreService;
import service.ScoreServiceJDBC;
import service.CommentService;
import service.CommentServiceJDBC;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Date;

public class TestJDBC {

    public static void main(String[] args) {
//        ScoreService service = new ScoreServiceJDBC();
        CommentService commentService = new CommentServiceJDBC();
//        service.reset();

//        service.addScore(new Score("minesweeper","Kiko", 456, new Date()));
//
//        var scores = service.getBestScores("minesweeper");
//        System.out.println(scores);

        commentService.addComment(new Comment("minesweeper","Kristian","Dobra hra.", new Date()));
        var comments = commentService.getComments("minesweeper");
        System.out.println(comments);
    }

//    public static void main(String[] args) throws Exception {
//
//        try (var connection = DriverManager.getConnection("jdbc:postgresql://localhost/gamestudio","postgres","postgres");
//             var statement  = connection.createStatement();
//             var rs = statement.executeQuery("SELECT game, username, points, played_on FROM score WHERE game='minesweeper' ORDER BY points DESC LIMIT 5;")
//            ){
//            while (rs.next()){
//                System.out.printf("%s, %s, %d, %s \n",rs.getString(1),rs.getString(2),rs.getInt(3), rs.getTimestamp(4));
//            }
//            System.out.println("Pripojenie je uspesne.");
//        }catch (SQLException e){
//            System.out.println(e.getMessage());
//        }
//    }
}
