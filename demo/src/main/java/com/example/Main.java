package com.example;

import javafx.application.Application;
import javafx.stage.Stage;

import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.VBox;

/** Application entry point for the console Tic-Tac-Toe game. */
public class Main extends Application {
    
    private Board board = new Board();
private HumanPlayer human = new HumanPlayer('X', new java.util.Scanner(""));
private CpuPlayer cpu = new CpuPlayer('O');

private Button[][] cells = new Button[3][3];
private Label status;
private boolean gameOver = false;

    @Override
public void start(Stage primaryStage) {
    status = new Label("Player's turn (X)");

GridPane gameBoard = new GridPane();
gameBoard.setAlignment(Pos.CENTER);
gameBoard.setHgap(5);
gameBoard.setVgap(5);

for (int row = 0; row < 3; row++) {
    for (int column = 0; column < 3; column++) {

        Button cell = new Button("");
        cell.setPrefSize(110, 110);
        cell.setStyle("-fx-font-size: 32px;");

        final int selectedRow = row;
        final int selectedColumn = column;

        cell.setOnAction(event ->
            handleCellClick(selectedRow, selectedColumn));

        cells[row][column] = cell;
        gameBoard.add(cell, column, row);
    }
}

VBox top = new VBox(status);
top.setAlignment(Pos.CENTER);

BorderPane root = new BorderPane();
root.setTop(top);
root.setCenter(gameBoard);
Button restartButton = new Button("Restart Game");
restartButton.setOnAction(event -> restartGame());

VBox bottom = new VBox(restartButton);
bottom.setAlignment(Pos.CENTER);

root.setBottom(bottom);

Scene scene = new Scene(root, 400, 450);

primaryStage.setTitle("Tic-Tac-Toe");
primaryStage.setScene(scene);
primaryStage.show();
}

private void handleCellClick(int row, int column) {

    if (gameOver || !board.place(row, column, 'X')) {
        return;
    }

    updateBoard();

   if (board.checkWin()) {
    status.setText("Player wins!");
    highlightWinningCells();
    gameOver = true;
    return;
}

    if (board.isFull()) {
        status.setText("It's a draw!");
        gameOver = true;
        return;
    }

    cpu.makeMove(board);
    updateBoard();

    if (board.checkWin()) {
    status.setText("CPU wins!");
    highlightWinningCells();
    gameOver = true;
} else if (board.isFull()) {
        status.setText("It's a draw!");
        gameOver = true;
    }
}

private void updateBoard() {

    for (int row = 0; row < 3; row++) {
        for (int column = 0; column < 3; column++) {

            char marker = board.getCell(row, column);

            if (marker != ' ') {
                cells[row][column].setText(String.valueOf(marker));
                cells[row][column].setDisable(true);
            }
        }
    }
}

private void highlightWinningCells() {

    for (int row = 0; row < 3; row++) {
        if (board.getCell(row, 0) != ' '
                && board.getCell(row, 0) == board.getCell(row, 1)
                && board.getCell(row, 1) == board.getCell(row, 2)) {

            for (int column = 0; column < 3; column++) {
                cells[row][column].setStyle(
                    "-fx-background-color: lightgreen; -fx-font-size: 32px;");
            }
            return;
        }
    }

    for (int column = 0; column < 3; column++) {
        if (board.getCell(0, column) != ' '
                && board.getCell(0, column) == board.getCell(1, column)
                && board.getCell(1, column) == board.getCell(2, column)) {

            for (int row = 0; row < 3; row++) {
                cells[row][column].setStyle(
                    "-fx-background-color: lightgreen; -fx-font-size: 32px;");
            }
            return;
        }
    }

    if (board.getCell(0, 0) != ' '
            && board.getCell(0, 0) == board.getCell(1, 1)
            && board.getCell(1, 1) == board.getCell(2, 2)) {

        for (int i = 0; i < 3; i++) {
            cells[i][i].setStyle(
                "-fx-background-color: lightgreen; -fx-font-size: 32px;");
        }
        return;
    }

    if (board.getCell(0, 2) != ' '
            && board.getCell(0, 2) == board.getCell(1, 1)
            && board.getCell(1, 1) == board.getCell(2, 0)) {

        for (int i = 0; i < 3; i++) {
            cells[i][2 - i].setStyle(
                "-fx-background-color: lightgreen; -fx-font-size: 32px;");
        }
    }
}

    private void restartGame() {

    board.initialize();
    gameOver = false;

    for (int row = 0; row < 3; row++) {
        for (int column = 0; column < 3; column++) {

            cells[row][column].setText("");
            cells[row][column].setDisable(false);
            cells[row][column].setStyle("-fx-font-size: 32px;");
        }
    }

    status.setText(human.getName() + "'s turn (X)");

}
    /** Starts a new console game. */
    public static void main(String[] args) {
        launch(args);

    }

    }
