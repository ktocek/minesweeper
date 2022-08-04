package minesweeper.test;

import entity.Comment;
import entity.Score;
import org.junit.jupiter.api.Test;
import service.CommentService;
import service.CommentServiceFile;
import service.CommentServiceJDBC;

import java.util.Date;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class CommentServiceTest {

    //private CommentService commentService = new CommentServiceJDBC();
    private CommentService commentService = new CommentServiceFile();

    @Test
    public void testReset(){
        commentService.addComment(new Comment("minesweeper", "Tibi", "Komentar 1", new Date()));
        commentService.reset();
        assertEquals(0,commentService.getComments("minesweeper").size());
    }

    @Test
    public void testAddComment(){
        commentService.reset();
        var date = new Date();
        commentService.addComment(new Comment("minesweeper", "Tibi", "Komentar 1", date));

        var comments = commentService.getComments("minesweeper");

        assertEquals(1, comments.size());
        assertEquals("minesweeper", comments.get(0).getGame());
        assertEquals("Tibi", comments.get(0).getUsername());
        assertEquals("Komentar 1", comments.get(0).getComment());
        assertEquals(date, comments.get(0).getPlayedOn());
    }

    @Test
    public void testGetBestScores() {
        commentService.reset();
        var date = new Date();
        commentService.addComment(new Comment("minesweeper", "Peto", "Komentar 1", date));
        commentService.addComment(new Comment("minesweeper", "Katka", "Komentar 1", date));
        commentService.addComment(new Comment("tiles", "Zuzka", "Komentar 1", date));
        commentService.addComment(new Comment("minesweeper", "Jergus", "Komentar 1", date));

        var scores = commentService.getComments("minesweeper");

        assertEquals(3, scores.size());

        assertEquals("minesweeper", scores.get(0).getGame());
        assertEquals("Peto", scores.get(0).getUsername());
        assertEquals("Komentar 1", scores.get(0).getComment());
        assertEquals(date, scores.get(0).getPlayedOn());

        assertEquals("minesweeper", scores.get(1).getGame());
        assertEquals("Katka", scores.get(1).getUsername());
        assertEquals("Komentar 1", scores.get(1).getComment());
        assertEquals(date, scores.get(1).getPlayedOn());

        assertEquals("minesweeper", scores.get(2).getGame());
        assertEquals("Jergus", scores.get(2).getUsername());
        assertEquals("Komentar 1", scores.get(2).getComment());
        assertEquals(date, scores.get(2).getPlayedOn());
    }
}
