package src.view;

import java.util.ArrayList;

import javafx.geometry.Insets;
import javafx.scene.control.MenuButton;
import javafx.scene.control.MenuItem;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;
import src.Utils;
import src.model.Post;

public class PostView extends VBox {

        public MenuItem deleteMenuItem;
        public MenuItem exportMenuItem;

        public PostView(Post post, ArrayList<String> permissions) {
            HBox authorDateHBox = new HBox();
            HBox likesSharesHBox = new HBox();
            
            Text authorField = new Text("By: " + post.getAuthor());
            Text dateField = new Text("Date Created: " + Utils.getStringFromDate(post.getCreationDate()));
            Text contentField = new Text("Content: " + post.getContent());
            Text likesField = new Text("Likes: " + Integer.toString(post.getLikes()));
            Text sharesField = new Text("Shares: " + Integer.toString(post.getShares()));
            authorDateHBox.getChildren().addAll(authorField, dateField);

            if(!permissions.isEmpty()){
                MenuButton menuButton = new MenuButton("");
                menuButton.setStyle("-fx-padding: 0;");
                authorDateHBox.getChildren().addAll( menuButton);
                HBox.setMargin(menuButton, new Insets(0, 0, 0, 48));

                if(permissions.contains("delete")){
                    deleteMenuItem = new MenuItem("Delete");
                    menuButton.getItems().addAll(deleteMenuItem);
                }

                if(permissions.contains("export")){
                    exportMenuItem = new MenuItem("Export");
                    menuButton.getItems().addAll(exportMenuItem);
                }
            }
            likesSharesHBox.getChildren().addAll(likesField, sharesField);

            getChildren().addAll(
                authorDateHBox,
                contentField,
                likesSharesHBox
            );

            // Add margins to the TextField elements
            HBox.setMargin(authorField, new Insets(8, 8, 8, 8));
            HBox.setMargin(dateField, new Insets(8, 8, 8, 8));

            VBox.setMargin(contentField, new Insets(8, 8, 8, 8));
            HBox.setMargin(likesField, new Insets(8, 8, 8, 8));
            HBox.setMargin(sharesField, new Insets(8, 8, 8, 8));

            VBox.setMargin(this, new Insets(12,12,12,12));

            setStyle("-fx-background-color: #FFF;");
        }
}
