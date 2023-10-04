package src.Model;
import java.util.Comparator;

/** 
 * This class is implements a comparator to compare posts on likes.
 * It is used to sort the Posts.
*/
public class PostLikesComparator implements Comparator<Post>{
    @Override
    public int compare(Post p1, Post p2) {
       return Integer.compare(p2.getLikes(), p1.getLikes());
    }
}
