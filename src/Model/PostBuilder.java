package src.Model;

import java.time.LocalDateTime;

import src.Utils;
import src.CustomExceptions.InvalidInputDataType;

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

    public PostBuilder addLikes(Object likes) throws NumberFormatException{
        this.likes = Integer.parseInt((String)likes);
        return this;
    }

    public PostBuilder addShares(Object shares) throws NumberFormatException{
        this.shares = Integer.parseInt((String)shares);
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

    public Post build(){
        return new Post(postId, content, author, likes, shares, postDate);
    }
}
