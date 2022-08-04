package service;

import entity.Comment;
import entity.Score;

import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

public class CommentServiceJDBC implements CommentService{
    private static final String URL = "jdbc:postgresql://localhost/gamestudio";

    private static final String USER = "postgres";

    private static final String PASSWORD = "postgres";

    private static final String STATEMENT_ADD_COMMENT = "INSERT INTO comment VALUES (?, ?, ?, ?)";

    private static final String STATEMENT_GET_COMMENTS = "SELECT game, username, comment, commented_on FROM comment WHERE game= ? order by commented_on desc";

    private static final String STATEMENT_RESET = "DELETE FROM comment";


    @Override
    public void addComment(Comment comment) {
        try (var connection = DriverManager.getConnection("jdbc:postgresql://localhost/gamestudio", "postgres", "postgres");
             var statement = connection.prepareStatement(STATEMENT_ADD_COMMENT)
        ) {
            statement.setString(1, comment.getGame());
            statement.setString(2, comment.getUsername());
            statement.setString(3, comment.getComment());
            statement.setTimestamp(4, new Timestamp(comment.getPlayedOn().getTime()));
            statement.executeUpdate();
        } catch (SQLException e) {
            throw new GameStudioException(e);
        }
    }

    @Override
    public List<Comment> getComments(String game) {
        try (var connection = DriverManager.getConnection("jdbc:postgresql://localhost/gamestudio", "postgres", "postgres");
             var statement = connection.prepareStatement(STATEMENT_GET_COMMENTS)
        ) {
            statement.setString(1, game);
            try (var rs = statement.executeQuery()) {
                var comments = new ArrayList<Comment>();
                while (rs.next()) {
                    comments.add(new Comment(rs.getString(1), rs.getString(2), rs.getString(3), rs.getTimestamp(4)));
                }
                return comments;
            }
        } catch (SQLException e) {
            throw new GameStudioException(e);
        }
    }

    @Override
    public void reset() {
        try (var connection = DriverManager.getConnection(URL, USER, PASSWORD);
             var statement = connection.createStatement()
        ) {
            statement.executeUpdate(STATEMENT_RESET);
            System.out.println("Pripojenie je uspesne.");
        } catch (SQLException e) {
            throw new GameStudioException(e);
        }
    }
}
