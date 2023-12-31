package src.model;

import java.time.LocalDateTime;

import src.Utils;
import src.custom_exception.InvalidInputDataType;

/**
 * The PostBuilder class is responsible for constructing Post objects by setting their attributes.
 */
public class PostBuilder {
    private Integer postId;
    private Integer likes;
    private Integer shares;
    private String content;
    private LocalDateTime postDate;
    private String author;

    public PostBuilder addPostId(Object postId) throws NumberFormatException{
        this.postId = Integer.parseInt((String)postId);
        return this;
    }

    public PostBuilder addLikes(Object likes) throws NumberFormatException, InvalidInputDataType{
        this.likes = Integer.parseInt((String)likes);
        if(this.likes < 0){
            this.likes = null;
            throw new InvalidInputDataType("");
        }
        return this;
    }

    public PostBuilder addShares(Object shares) throws NumberFormatException, InvalidInputDataType{
        this.shares = Integer.parseInt((String)shares);
        if(this.shares < 0){
            this.shares = null;
            throw new InvalidInputDataType("");
        }
        return this;
    }
    public PostBuilder addContent(Object content){
        this.content = (String)content;
        return this;
    }

    public PostBuilder addAuthor(Object author){
        this.author = (String)author;
        return this;
    }

    public PostBuilder addPostDate(Object date) throws InvalidInputDataType{
        this.postDate = Utils.parseDate((String)date);
        return this;
    }

    /**
     * Builds a Post object with the attributes set in the builder.
     *
     * @return The constructed Post object.
     */
    public Post build(){
        return new Post(postId, content, author, likes, shares, postDate);
    }
}
