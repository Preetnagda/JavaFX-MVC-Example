package src.controller;

import java.util.ArrayList;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.chart.PieChart;
import src.model.Post;
import src.model.dao.PostDAOImpl;

public class AnalysisController extends LoggedInController {

    @FXML
    private PieChart pieChart;

    @Override
    public void initWithUser(){
        PostDAOImpl postDAO = new PostDAOImpl();
        ArrayList<Post> posts = postDAO.retrievePosts(null);
        // Calculate aggregated data
        int lowShares = 0;
        int mediumShares = 0;
        int highShares = 0;

        for (Post post : posts) {
            int shares = post.getShares();
            if (shares >= 1000) {
                highShares++;
            } else if (shares >= 100) {
                mediumShares++;
            } else {
                lowShares++;
            }
        }

        ObservableList<PieChart.Data> pieChartData = FXCollections.observableArrayList(
            new PieChart.Data("0-99 Shares", lowShares),
            new PieChart.Data("100-999 Shares", mediumShares),
            new PieChart.Data("1000+ Shares", highShares)
        );
        pieChart.setData(pieChartData);
    }

    @FXML
    private void onBack(ActionEvent event){
        switchScene("Dashboard", user);
    }
    
}
