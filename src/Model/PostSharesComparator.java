package src.model;
import java.util.Comparator;

/** 
 * This class is implements a comparator to compare posts on shares.
 * It is used to sort the Posts.
*/
public class PostSharesComparator implements Comparator<Post>{
    @Override
    public int compare(Post p1, Post p2) {
       return Integer.compare(p2.getShares(), p1.getShares());
    }
}