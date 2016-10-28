package сontroller;
import exceptions.EmptyException;
import exceptions.IncorrectData;
import exceptions.MyMessageException;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.stage.Stage;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.control.cell.TextFieldTableCell;
import main.Table;
import java.util.ArrayList;
import org.apache.commons.lang.math.NumberUtils;

public class Controller {
    @FXML
    private TableView tableA;
    @FXML
    private TableView tableB;
    @FXML
    private TableView tableC;
    @FXML
    private ComboBox comboBoxExtr;
    @FXML
    private TextField textFieldVariables;
    @FXML
    private TextField textFieldRestrictions;
    @FXML
    private Label labelVariables;
    @FXML
    private Label labelRestrictions;
    @FXML
    private Label labelExtr;

    @FXML
    private void initialize() {
        if (check == false) {
            comboBoxExtr.getItems().add("max");
            comboBoxExtr.getItems().add("min");
            initLoader();
            tableA.setVisible(false);
            tableB.setVisible(false);
            tableC.setVisible(false);
            //solutionOfTask.setDisable(true);
            //findTab.setDisable(true);
            //checkTab.setDisable(true);
            //graphicTab.setDisable(true);
        } else initListeners();
    }
    private ArrayList<String> arrayErrors;
    private static Stage mainStage;
    private static ArrayList<TableColumn> arrayTableAColumn, arrayTableBColumn, arrayTableCColumn;
    private ObservableList data;
    private boolean extr, check = false;

    private void initLoader() {
        setMainStage(mainStage);
    }
    public void setMainStage(Stage mainStage) {
        this.mainStage = mainStage;
    }

    public void buttonAction(ActionEvent actionEvent) {
        Object source = actionEvent.getSource();
        if (!(source instanceof Button)) {
            return;
        }
        Button clickedButton = (Button) source;
        switch (clickedButton.getId()) {
            case "buttonBuild":
                try {
                    if (textFieldRestrictions.getText().isEmpty() ||
                            textFieldVariables.getText().isEmpty()) {
                        arrayErrors = new ArrayList<>();
                        if (textFieldRestrictions.getText().isEmpty()) {
                            arrayErrors.add(labelRestrictions.getText());
                        }
                        if (textFieldVariables.getText().isEmpty()) {
                            arrayErrors.add(labelVariables.getText());
                        }
                        if (comboBoxExtr.getSelectionModel().isEmpty()) {
                            arrayErrors.add(labelExtr.getText());
                        }
                        throw new EmptyException(arrayErrors);
                    } else if (!NumberUtils.isNumber(textFieldRestrictions.getText()) ||
                            !NumberUtils.isNumber(textFieldVariables.getText())) {
                        arrayErrors = new ArrayList<>();
                        if (!NumberUtils.isNumber(textFieldRestrictions.getText())) {
                            arrayErrors.add(labelRestrictions.getText());
                        }
                        if (!NumberUtils.isNumber(textFieldVariables.getText())) {
                            arrayErrors.add(labelVariables.getText());
                        }
                        throw new IncorrectData(arrayErrors);
                    }
                    arrayTableAColumn = new ArrayList<>();
                    arrayTableBColumn = new ArrayList<>();
                    arrayTableCColumn = new ArrayList<>();
                    tableA.getColumns().clear();
                    tableA.getItems().clear();
                    tableA.setVisible(true);
                    tableB.getColumns().clear();
                    tableB.getItems().clear();
                    tableB.setVisible(true);
                    tableC.getColumns().clear();
                    tableC.getItems().clear();
                    tableC.setVisible(true);
                    check = true;
                    Table.createTable(tableA, Integer.parseInt(textFieldVariables.getText()), Integer.parseInt(textFieldRestrictions.getText()), "A");
                    Table.createTable(tableB, 1, Integer.parseInt(textFieldRestrictions.getText()), "B");
                    Table.createTable(tableC, Integer.parseInt(textFieldVariables.getText()), 1, "C");
                    initialize();
                } catch (EmptyException ex) {
                    Alert alert = new Alert(Alert.AlertType.INFORMATION);
                    alert.setTitle("Ошибка");
                    alert.setHeaderText(ex.getMessageFields());
                    alert.showAndWait();
                } catch (IncorrectData ex) {
                    Alert alert = new Alert(Alert.AlertType.INFORMATION);
                    alert.setTitle("Ошибка");
                    alert.setHeaderText(ex.getMessageFields());
                    alert.showAndWait();
                }
                break;
        case "buttonSolve" : break;
        }
    }

    private void initListeners() {

        for (int i = 0; i < tableA.getColumns().size(); i++) {
            arrayTableAColumn.get(i).setCellFactory(TextFieldTableCell.forTableColumn());
            arrayTableAColumn.get(i).setOnEditCommit(
                    new EventHandler<TableColumn.CellEditEvent<ObservableList, String>>() {
                        @Override
                        public void handle(TableColumn.CellEditEvent<ObservableList, String> t) {
                            data = (ObservableList) tableA.getItems().get(t.getTablePosition().getRow());
                            String value = data.get(t.getTablePosition().getColumn()).toString();
                            data.set(t.getTablePosition().getColumn(), t.getNewValue());
                        }
                    }
            );

            arrayTableCColumn.get(i).setCellFactory(TextFieldTableCell.forTableColumn());
            arrayTableCColumn.get(i).setOnEditCommit(
                    new EventHandler<TableColumn.CellEditEvent<ObservableList, String>>() {
                        @Override
                        public void handle(TableColumn.CellEditEvent<ObservableList, String> t) {
                            data = (ObservableList) tableC.getItems().get(t.getTablePosition().getRow());
                            String value = data.get(t.getTablePosition().getColumn()).toString();
                            data.set(t.getTablePosition().getColumn(), t.getNewValue());
                        }
                    }
            );
        }
        arrayTableBColumn.get(0).setCellFactory(TextFieldTableCell.forTableColumn());
        arrayTableBColumn.get(0).setOnEditCommit(
                new EventHandler<TableColumn.CellEditEvent<ObservableList, String>>() {
                    @Override
                    public void handle(TableColumn.CellEditEvent<ObservableList, String> t) {
                        data = (ObservableList) tableB.getItems().get(t.getTablePosition().getRow());
                        String value = data.get(t.getTablePosition().getColumn()).toString();
                        data.set(t.getTablePosition().getColumn(), t.getNewValue());
                    }
                }
        );
    }

    public static ArrayList<TableColumn> getArrayTableAColumn() {
        return arrayTableAColumn;
    }

    public static void setArrayTableAColumn(TableColumn<ObservableList<String>, String> column) {
        arrayTableAColumn.add(column);
    }

    public static ArrayList<TableColumn> getArrayTableBColumn() {
        return arrayTableBColumn;
    }

    public static void setArrayTableBColumn(TableColumn<ObservableList<String>, String> column) {
        arrayTableBColumn.add(column);
    }

    public static ArrayList<TableColumn> getArrayTableCColumn() {
        return arrayTableCColumn;
    }

    public static void setArrayTableCColumn(TableColumn<ObservableList<String>, String> column) {
        arrayTableCColumn.add(column);
    }
}
