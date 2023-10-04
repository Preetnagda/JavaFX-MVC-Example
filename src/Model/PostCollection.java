package src.Model;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;

import src.CustomExceptions.DuplicatePost;

/**
 * PostCollection implements the collection of Posts. 
 * Responsible for storing, retriving, deleting and filtering multiple posts.
 * The Posts are stored in a HashMap JCF.
 */

public class PostCollection{
    private HashMap<Integer, Post> itemList;

    public PostCollection(){
        itemList = new HashMap<Integer, Post>();
    }

    /**
     * The function is responsible for adding multiple posts at one time. 
     * It parses all non duplicate posts and throws exception if any duplicates are present.
     * @param bulkPosts
     * @throws DuplicatePost
     */
    public void addBulkPosts(ArrayList<Post> bulkPosts) throws DuplicatePost{
        String exception = new String();
        for(Post newPost: bulkPosts){
            try{
                addPost(newPost);
            } catch (DuplicatePost e){
                exception =  exception + e.getMessage();
            }
        }
        
        if(!exception.isEmpty()){
            throw new DuplicatePost(exception);
        }
    }

    /**
     * Add a single post to the post collection.
     * @param post
     * @throws DuplicatePost
     */
    public void addPost(Post newItem) throws DuplicatePost{
        Post exists = itemList.put(newItem.getId(), newItem);
        if(exists != null){
            throw new DuplicatePost(String.format("Post with ID %d already exists", exists.getId()));
        };
    }

    public Post retrievePost(int postID){
        return itemList.get(postID);
    }

    public Boolean deletePost(int postID){
        return itemList.remove(postID) != null;
    }

    /**
     * Accepts a size of return and comparator
     * Sorts the post collection based on the comparator and returns the top "size of return" posts.
     * @param sizeOfReturn
     * @param comparator
     * @return
     */
    public ArrayList<Post> getTopPosts(int sizeOfReturn, Comparator<Post> comparator){
        if(sizeOfReturn > itemList.size()){
            sizeOfReturn = itemList.size();
            System.out.println();
            System.out.println("Only " + Integer.toString(sizeOfReturn) + " posts available. Showing " + Integer.toString(sizeOfReturn) + " results.");
            System.out.println();
        }

        ArrayList<Post> postsList = new ArrayList<>(itemList.values());
        Collections.sort(postsList, comparator);
        ArrayList<Post> filteredList = new ArrayList<>(postsList.subList(0, sizeOfReturn));
        return filteredList;
    }

    /**
     * Passes the comparator responsible for sorting on likes, to the getTopPosts function
     * @param sizeOfReturn
     * @return
     */
    public ArrayList<Post> getTopLikedPosts(int sizeOfReturn){
        PostLikesComparator postLikeComparator = new PostLikesComparator();
        return getTopPosts(sizeOfReturn, postLikeComparator);
    }

    /**
     * Passes the comparator responsible for sorting on shares, to the getTopPosts function
     * @param sizeOfReturn
     * @return
     */
    public ArrayList<Post> getTopSharedPosts(int sizeOfReturn){
        PostSharesComparator postSharesComparator = new PostSharesComparator();
        return getTopPosts(sizeOfReturn, postSharesComparator);
    }


}
