package сontroller;
import exceptions.EmptyException;
import exceptions.IncorrectData;
import exceptions.MyMessageException;
import javafx.application.Platform;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.Event;
import javafx.event.EventHandler;
import javafx.scene.chart.CategoryAxis;
import javafx.scene.chart.LineChart;
import javafx.scene.chart.NumberAxis;
import javafx.scene.chart.XYChart;
import javafx.scene.input.MouseEvent;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.control.cell.TextFieldTableCell;
import main.Table;
import math.MMethod;
import math.Validation;

import java.io.File;
import java.util.ArrayList;

import org.apache.commons.lang.math.NumberUtils;
import parser.JaxbParser;

import javax.xml.bind.JAXBException;

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
    private Tab enterTab;
    @FXML
    private Tab solutionTab;
    @FXML
    private Tab checkTab;
    @FXML
    private Tab findTab;
    @FXML
    private Tab graphicTab;
    @FXML
    private TabPane tabPane;
    @FXML
    private TextArea textArea;
    @FXML
    private TextArea textAreaCheck;
    @FXML
    private  Menu menuReport;
    @FXML
    private LineChart<String, Double> AlfaLineChart;
    @FXML
    private CategoryAxis xAlfa;
    @FXML
    private NumberAxis yAlfa;
    @FXML
    private LineChart<String, Double> BettaLineChart;
    @FXML
    private CategoryAxis xBetta;
    @FXML
    private NumberAxis yBetta;
    @FXML
    private void initialize() {
        if (check == false) {
            comboBoxExtr.getItems().add("max");
            comboBoxExtr.getItems().add("min");
            initLoader();
            tableA.setVisible(false);
            tableB.setVisible(false);
            tableC.setVisible(false);
            solutionTab.setDisable(true);
            findTab.setDisable(true);
            checkTab.setDisable(true);
            graphicTab.setDisable(true);
            menuReport.setDisable(true);

        }
        else {
            initListeners();
            solutionTab.setDisable(true);
            findTab.setDisable(true);
            checkTab.setDisable(true);
            graphicTab.setDisable(true);
            menuReport.setDisable(true);
        }
    }

    private ArrayList<String> arrayErrors;
    private ArrayList<Double> iterAlfaList, iterBettaList;
    private static Stage mainStage;
    private static ArrayList<TableColumn> arrayTableAColumn, arrayTableBColumn, arrayTableCColumn;
    private ObservableList data;
    private boolean extr, check = false;
    private FileChooser fileChooser;
    private File file;
    private FileChooser.ExtensionFilter extFilter;
    private MMethod method;
    XYChart.Series<String, Double> iterAlfa, iterBetta;

    private void initLoader() {
        setMainStage(mainStage);
    }

    public void setMainStage(Stage mainStage) {
        this.mainStage = mainStage;
    }

    public void menuActions(ActionEvent actionEvent) throws JAXBException {
        Object source = actionEvent.getSource();
        if (!(source instanceof MenuItem)) {
            return;
        }
        MenuItem clickedItem = (MenuItem) source;
        switch (clickedItem.getId()) {
            case "openMenuItem":
                JaxbParser jaxbParser = new JaxbParser();
                fileChooser = new FileChooser();//Класс работы с диалогом выборки и сохранения
                fileChooser.setTitle("Открытие документа");//Заголовок диалога
                extFilter =  new FileChooser.ExtensionFilter("XML files (*.xml)", "*.xml");//Расширение
                fileChooser.getExtensionFilters().add(extFilter);
                file = fileChooser.showOpenDialog(mainStage);//Указываем текущую сцену
                if (file != null) {
                    arrayTableAColumn = new ArrayList<>();
                    arrayTableBColumn = new ArrayList<>();
                    arrayTableCColumn = new ArrayList<>();
                    MMethod mMethod = (MMethod) jaxbParser.getObject(file,MMethod.class);
                    check = true;
                    tableA.getColumns().clear();
                    tableA.getItems().clear();
                    tableA.setVisible(true);
                    tableB.getColumns().clear();
                    tableB.getItems().clear();
                    tableB.setVisible(true);
                    tableC.getColumns().clear();
                    tableC.getItems().clear();
                    tableC.setVisible(true);
                    Table.createTable(tableA, mMethod.getA()[0].length, mMethod.getA().length, "A");
                    Table.createTable(tableB, 1, mMethod.getB().length, "B");
                    Table.createTable(tableC, mMethod.getC().length, 1, "C");
                    Table.setTable(tableA, mMethod.getA());
                    Table.setTable(tableB, mMethod.getB());
                    Table.setTable(tableC, mMethod.getC());
                    if(mMethod.getExtr()){
                        comboBoxExtr.getSelectionModel().select("min");
                    }else{
                        comboBoxExtr.getSelectionModel().select("max");
                    }
                    textFieldRestrictions.setText(String.valueOf(mMethod.getA().length));
                    textFieldVariables.setText(String.valueOf(mMethod.getA()[0].length));
                    tabPane.getSelectionModel().select(enterTab);
                    textArea.clear();
                    textAreaCheck.clear();
                    initialize();
                }
                break;
            case "saveMenuItem":
                try {
                    if (tableA.getItems().isEmpty() || tableB.getItems().isEmpty() || tableC.getItems().isEmpty()) {
                        throw new MyMessageException("Постройте для начала таблицы!");
                    }
                    else if ( comboBoxExtr.getSelectionModel().isEmpty()||
                            EmptyException.emptyTable(tableA) || EmptyException.emptyTable(tableB) || EmptyException.emptyTable(tableC)) {
                        arrayErrors = new ArrayList<>();
                        if (comboBoxExtr.getSelectionModel().isEmpty()) {
                            arrayErrors.add(labelExtr.getText());
                        }
                        if (EmptyException.emptyTable(tableA)) {
                            arrayErrors.add("таблица А");
                        }
                        if (EmptyException.emptyTable(tableB)) {
                            arrayErrors.add("таблица В");
                        }
                        if (EmptyException.emptyTable(tableC)) {
                            arrayErrors.add("вектор С");
                        }
                        throw new EmptyException(arrayErrors);
                    }
                    fileChooser = new FileChooser();//Класс работы с диалогом выборки и сохранения
                    fileChooser.setTitle("Сохранение документа");//Заголовок диалога
                    extFilter =  new FileChooser.ExtensionFilter("XML files (*.xml)", "*.xml");//Расширение
                    fileChooser.getExtensionFilters().add(extFilter);
                    file = fileChooser.showSaveDialog(mainStage);//Указываем текущую сцену CodeNote.mainStage
                    if (file != null) { //Save
                        MMethod myMethod = new MMethod(Table.getTableC(tableC, tableC.getColumns().size()),
                                Table.getTableA(tableA, tableA.getColumns().size(), tableA.getItems().size()),
                                Table.getTableB(tableB, tableB.getItems().size()), extr);

                        jaxbParser = new JaxbParser();
                        jaxbParser.saveObject(file,myMethod);
                    }
                }
                catch (MyMessageException ex) {
                    Alert alert = new Alert(Alert.AlertType.INFORMATION);
                    alert.setTitle("Ошибка");
                    alert.setContentText(ex.getStackTrace().toString());
                    alert.setHeaderText(ex.getMessage());
                    alert.showAndWait();
                }
                catch (EmptyException ex) {
                    Alert alert = new Alert(Alert.AlertType.INFORMATION);
                    alert.setTitle("Ошибка");
                    alert.setContentText(ex.getStackTrace().toString());
                    alert.setHeaderText(ex.getMessageTablesAndFields());
                    alert.showAndWait();
                }
                break;
            case "exitMenuItem":
                Platform.exit();
                break;
            case "aboutMenuItem":
                Alert alert = new Alert(Alert.AlertType.INFORMATION);
                alert.setTitle("О программе");
                alert.setHeaderText("Программа для решения задач линейного программирования М-методом(I-агоритм)");
                alert.setContentText("Выполнили студенты группы КН-34б:\nКондратьев Виталий, Ворона Борис, Кущ Алина");
                alert.showAndWait();
                break;
            case "reportMenuItem":
                fileChooser = new FileChooser();//Класс работы с диалогом выборки и сохранения
                fileChooser.setTitle("Сохранение отчета");//Заголовок диалога
                extFilter =  new FileChooser.ExtensionFilter("PDF files (*.pdf)", "*.PDF", "*.pdf");//Расширение
                fileChooser.getExtensionFilters().add(extFilter);
                file = fileChooser.showSaveDialog(mainStage);//Указываем текущую сцену CodeNote.mainStage
                if (file != null) { //Save
                    //
                }
                break;
        }
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
        case "buttonSolve" :
            try {
                if (tableA.getItems().isEmpty() || tableB.getItems().isEmpty() || tableC.getItems().isEmpty()) {
                    throw new MyMessageException("Постройте для начала таблицы!");
                } else if (EmptyException.emptyTable(tableA) || EmptyException.emptyTable(tableB) || EmptyException.emptyTable(tableC)) {
                    arrayErrors = new ArrayList<>();
                    if (EmptyException.emptyTable(tableA)) {
                        arrayErrors.add("Матрица А");
                    }
                    if (EmptyException.emptyTable(tableB)) {
                        arrayErrors.add("Вектор ограничений");
                    }
                    if (EmptyException.emptyTable(tableC)) {
                        arrayErrors.add("Целевая функция");
                    }
                    throw new EmptyException(arrayErrors);
                } else if (IncorrectData.incorrectTable(tableA) || IncorrectData.incorrectTable(tableB) || IncorrectData.incorrectTable(tableC)) {
                    arrayErrors = new ArrayList<>();
                    if (IncorrectData.incorrectTable(tableA)) {
                        arrayErrors.add("Матрица А");
                    }
                    if (IncorrectData.incorrectTable(tableC)) {
                        arrayErrors.add("Вектор ограничений");
                    }
                    if (IncorrectData.incorrectTable(tableC)) {
                        arrayErrors.add("Целевая функция");
                    }
                    throw new IncorrectData(arrayErrors);
                }
                if (comboBoxExtr.getSelectionModel().getSelectedItem() == "max"){
                    extr = false;}
                else {extr = true;}
                method = new MMethod(Table.getTableC(tableC, tableC.getColumns().size()),
                        Table.getTableA(tableA, tableA.getColumns().size(), tableA.getItems().size()),
                        Table.getTableB(tableB, tableB.getItems().size()), extr);
                method.run();
                textArea.clear();
                for(int i=0;i<method.getAnswer().size();i++) {
                    textArea.setText(textArea.getText()+method.getAnswer().get(i).toString());
                }
                textArea.setEditable(false);
                solutionTab.setDisable(false);
                AlfaLineChart.getData().clear();
                BettaLineChart.getData().clear();
                iterAlfa = new XYChart.Series();
                iterBetta = new XYChart.Series();
                iterAlfaList = method.getIteratAlfa();
                iterBettaList = method.getIteratBetta();
                for (int i = 0; i < iterAlfaList.size(); i++) {
                    iterAlfa.getData().add(new XYChart.Data(Integer.toString(i), iterAlfaList.get(i).doubleValue()));
                }
                for (int i = 0; i < iterBettaList.size(); i++) {
                    iterBetta.getData().add(new XYChart.Data(Integer.toString(i), iterBettaList.get(i).doubleValue()));
                }
                AlfaLineChart.getData().addAll(iterAlfa);
                BettaLineChart.getData().addAll(iterBetta);
                AlfaLineChart.setLegendVisible(false);
                BettaLineChart.setLegendVisible(false);
                AlfaLineChart.setStyle("CHART_COLOR_1: red;");
                BettaLineChart.setStyle("CHART_COLOR_1: blue;");
                AlfaLineChart.setTitle("График изменения α по итерациям");
                BettaLineChart.setTitle("График изменения β по итерациям");
                for(final XYChart.Data<String, Double> data : iterAlfa.getData()){
                    data.getNode().addEventHandler(MouseEvent.MOUSE_ENTERED, new EventHandler<MouseEvent>() {
                        @Override
                        public void handle(MouseEvent event) {
                            Tooltip.install(data.getNode(), new Tooltip("Итерация : " + data.getXValue() + "\nЗначение : " + String.valueOf(data.getYValue())));
                        }
                    });
                }
                for(final XYChart.Data<String, Double> data : iterBetta.getData()){
                    data.getNode().addEventHandler(MouseEvent.MOUSE_ENTERED, new EventHandler<MouseEvent>() {
                        @Override
                        public void handle(MouseEvent event) {
                            Tooltip.install(data.getNode(), new Tooltip("Итерация : " + data.getXValue() + "\nЗначение : " + String.valueOf(data.getYValue())));
                        }
                    });
                }
                graphicTab.setDisable(false);
                tabPane.getSelectionModel().select(solutionTab);
            } catch (EmptyException ex) {
                Alert alert = new Alert(Alert.AlertType.INFORMATION);
                alert.setTitle("Ошибка");
                alert.setContentText(ex.getStackTrace().toString());
                ex.printStackTrace();
                alert.setHeaderText(ex.getMessageTables());
                alert.showAndWait();
            } catch (IncorrectData ex) {
                Alert alert = new Alert(Alert.AlertType.INFORMATION);
                alert.setTitle("Ошибка");
                alert.setContentText(ex.getStackTrace().toString());
                alert.setHeaderText(ex.getMessageTables());
                ex.printStackTrace();
                alert.showAndWait();
            } catch (MyMessageException ex) {
                Alert alert = new Alert(Alert.AlertType.INFORMATION);
                alert.setTitle("Ошибка");
                alert.setContentText(ex.getStackTrace().toString());
                alert.setHeaderText(ex.getMessage());
                ex.printStackTrace();
                alert.showAndWait();
            } catch (Exception ex) {
                Alert alert = new Alert(Alert.AlertType.INFORMATION);
                alert.setTitle("Ошибка");
                ex.printStackTrace();
                alert.setHeaderText(ex.getMessage());
                ex.printStackTrace();
                alert.showAndWait();
            }
            break;
            case "buttonCheck":
                checkTab.setDisable(false);
                Validation val = new Validation();
                val.OpornoCheck();
                val.DopustimostCheck();
                val.OptimalityCheck();
                textAreaCheck.clear();
                textAreaCheck.setText(val.getListCheck().toString());
                textAreaCheck.setEditable(false);
                tabPane.getSelectionModel().select(checkTab);

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
