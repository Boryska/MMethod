package main;
import math.BigFraction;
import сontroller.Controller;
import javafx.beans.property.ReadOnlyObjectWrapper;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;

import java.util.ArrayList;
import java.util.List;
/**
 * Created by Борис on 27.10.2016.
 */
public class Table {
    public static void createTable(TableView tableView, int cols, int rows, String name){
        TestDataGenerator dataGenerator = new TestDataGenerator();
        List<String> columnNames = dataGenerator.getNext(cols);
        TableColumn<ObservableList<String>, String> column;
        for (int i = 0; i < columnNames.size(); i++) {
            final int finalIdx = i;
            if (name =="A") {
                column = new TableColumn<>(
                        "X" + (i+1)
                );
                column.setStyle("-fx-alignment: CENTER;");
                if (name =="A") {
                    Controller.setArrayTableAColumn(column);
                }
            }
            else{
                column = new TableColumn<>(
                        "B"
                );
                column.setStyle("-fx-alignment: CENTER;");
                Controller.setArrayTableBColumn(column);
            }
            if(name == "C") {
                column = new TableColumn<>(
                        "C" + (i+1)
                );
                column.setStyle("-fx-alignment: CENTER;");
                Controller.setArrayTableCColumn(column);
            }
            column.setCellValueFactory(param ->
                    new ReadOnlyObjectWrapper<String>(param.getValue().get(finalIdx))
            );
            tableView.getColumns().add(column);
        }
        // add data
        for (int i = 0; i <rows; i++) {
            tableView.getItems().add(
                    FXCollections.observableArrayList(
                            dataGenerator.getNext(cols)
                    )
            );
        }
    }

    public static BigFraction[][] getTableA(TableView tableA, int cols, int rows){
        BigFraction[][] A = new BigFraction[rows][cols];
        for (int i=0;i<rows;i++){
            ObservableList table = (ObservableList) tableA.getItems().get(i);
            for (int j=0;j<cols;j++){
                A[i][j] = new BigFraction(table.get(j).toString());
                //A[i][j]= Double.parseDouble(table.get(j).toString());
            }
        }
        return A;
    }

    public static BigFraction[] getTableB(TableView tableB, int rows){
        BigFraction[] B = new BigFraction[rows];
        for (int j=0;j<rows;j++){
            ObservableList table = (ObservableList) tableB.getItems().get(j);
            B[j] = new BigFraction(table.get(0).toString());
            //B[j]= Double.parseDouble(table.get(0).toString());
        }
        return B;
    }

    public static BigFraction[] getTableC(TableView tableC, int cols){
        BigFraction[] C = new BigFraction[cols];
        ObservableList table = (ObservableList) tableC.getItems().get(0);
        for (int j=0;j<cols;j++){
            C[j] = new BigFraction(table.get(j).toString());
            //C[j]= Double.parseDouble(table.get(j).toString());
        }
        return C;
    }
    private static class TestDataGenerator {
        //private static final String[] LOREM = "Lorem ipsum dolor sit amet, consectetur adipiscing elit. Nunc tempus cursus diam ac blandit. Ut ultrices lacus et mattis laoreet. Morbi vehicula tincidunt eros lobortis varius. Nam quis tortor commodo, vehicula ante vitae, sagittis enim. Vivamus mollis placerat leo non pellentesque. Nam blandit, odio quis facilisis posuere, mauris elit tincidunt ante, ut eleifend augue neque dictum diam. Curabitur sed lacus eget dolor laoreet cursus ut cursus elit. Phasellus quis interdum lorem, eget efficitur enim. Curabitur commodo, est ut scelerisque aliquet, urna velit tincidunt massa, tristique varius mi neque et velit. In condimentum quis nisi et ultricies. Nunc posuere felis a velit dictum suscipit ac non nisl. Pellentesque eleifend, purus vel consequat facilisis, sapien lacus rutrum eros, quis finibus lacus magna eget est. Nullam eros nisl, sodales et luctus at, lobortis at sem.".split(" ");
        private static final String[] LOREM = ("0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 " +
                "0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0" +
                " 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0.").split(" ");
        private int curWord = 0;

        List<String> getNext(int nWords) {
            List<String> words = new ArrayList<>();

            for (int i = 0; i < nWords; i++) {
                if (curWord == Integer.MAX_VALUE) {
                    curWord = 0;
                }

                words.add(LOREM[curWord % LOREM.length]);
                curWord++;
            }

            return words;
        }
    }
}