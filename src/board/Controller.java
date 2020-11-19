package board;

import java.util.ArrayList;
import java.util.Optional;

import chess_piece.*;
import enums.*;
import javafx.event.*;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.*;
import javafx.scene.control.*;
import javafx.scene.image.*;
import javafx.scene.layout.*;
import javafx.scene.paint.*;
import javafx.scene.shape.*;
import javafx.stage.*;

public class Controller {
	Stage stage = new Stage();
	Player player1, player2, currentPlayer, opponent;
	ArrayList<Circle> validMoveMarkers;
	ArrayList<Coord> validMoves;
	Piece selectedPiece, clickedPiece;
	static boolean pieceCurrentlySelected;
	boolean movingPiece, taking, pieceHighlighted, startingMove = true;
	int gridsize = Utils.GRIDSIZE;
	public ArrayList<Coord> whiteTakenPieces, blackTakenPieces;

	int[] promotionNumber = new int[6];
	ButtonType buttonTypeOne;
	ButtonType buttonTypeTwo;
	ButtonType buttonTypeThree;
	Button buttonOne, buttonTwo, buttonThree;
	boolean promotionActive = false;
	Optional<ButtonType> result;
	Piece promoted;
	String pawn = "pawn.png", knight = "knight.png", rook = "rook.png", queen = "queen.png";

	public Controller(Player player1, Player player2) {
		this.player1 = player1;
		this.player2 = player2;
		currentPlayer = player1;
		opponent = player2;
		initialiseMoves(player1, player2);
		initialiseMoves(player2, player1);
		// Do this next section only when first player is selected:
		player1.setTurn(true);
		validMoves = new ArrayList<>();
		validMoveMarkers = new ArrayList<>();
	}

	private Alert setAlert(String colour) {
		Alert promotion = new Alert(AlertType.NONE);
		promotion.setTitle("Pawn Promotion");
		promotion.getDialogPane().setMaxSize(2, 2);
		Label label = new Label("Choose which piece to promote your pawn to:");
		ImageView im1 = new ImageView(new Image(colour + knight));
		ImageView im2 = new ImageView(new Image(colour + rook));
		ImageView im3 = new ImageView(new Image(colour + queen));
		buttonOne = new Button();
		buttonOne.setOnAction(new EventHandler<ActionEvent>() {
			@Override
			public void handle(ActionEvent event) {
				System.out.println("Promoted to Knight");
				promoted = new Knight(Type.KNIGHT, selectedPiece.isWhite ? Colour.WHITE : Colour.BLACK,
						promotionNumber[selectedPiece.isWhite ? 0 : 1]++, clickedPiece.getCoord());
				promotePawn(promoted);
				promotion.getDialogPane().getButtonTypes().addAll(ButtonType.CANCEL);
				promotion.close();
			}
		});
		buttonTwo = new Button();
		buttonTwo.setOnAction(new EventHandler<ActionEvent>() {
			@Override
			public void handle(ActionEvent event) {
				System.out.println("Promoted to Rook");
				promoted = new Rook(Type.ROOK, selectedPiece.isWhite ? Colour.WHITE : Colour.BLACK,
						promotionNumber[selectedPiece.isWhite ? 0 : 1]++, clickedPiece.getCoord());
				promotePawn(promoted);
				promotion.getDialogPane().getButtonTypes().addAll(ButtonType.CANCEL);
				promotion.close();
			}
		});
		buttonThree = new Button();
		buttonThree.setOnAction(new EventHandler<ActionEvent>() {
			@Override
			public void handle(ActionEvent event) {
				System.out.println("Promoted to Queen");
				promoted = new Queen(Type.QUEEN, selectedPiece.isWhite ? Colour.WHITE : Colour.BLACK,
						promotionNumber[selectedPiece.isWhite ? 0 : 1]++, clickedPiece.getCoord());
				promotePawn(promoted);
				promotion.getDialogPane().getButtonTypes().addAll(ButtonType.CANCEL);
				promotion.close();
			}
		});
		buttonOne.setGraphic(im1);
		buttonTwo.setGraphic(im2);
		buttonThree.setGraphic(im3);
		buttonOne.setScaleX(0.5);
		buttonOne.setScaleY(0.5);
		buttonTwo.setScaleX(0.5);
		buttonTwo.setScaleY(0.5);
		buttonThree.setScaleX(0.5);
		buttonThree.setScaleY(0.5);

		// buttonOne.setOnMouseClicked(value);
		GridPane grid = new GridPane();
		grid.add(label, 0, 0, 3, 1);
		grid.add(buttonOne, 0, 1);
		grid.add(buttonTwo, 1, 1);
		grid.add(buttonThree, 2, 1);
		Stage stage = (Stage) promotion.getDialogPane().getScene().getWindow();
		stage.getIcons().add(new Image(colour + pawn));
		// stage.initStyle(StageStyle.UNDECORATED);
		promotion.setGraphic(grid);
		return promotion;
	}

	public void promotePawn(Piece promoted) {
		selectedPiece.setCoord(currentPlayer.getTakenZone(currentPlayer.getTakenPieces()));
		selectedPiece.setPositionBasedOnCoord(selectedPiece.getCoord());
		currentPlayer.addTakenPiece();
		selectedPiece.setValidMoves(null);
		currentPlayer.pieces.add(promoted);
		selectedPiece = promoted;
	}

	public void initialiseMoves(Player player, Player opponent) {
		for (Piece piece : player.pieces) {
			piece.calculateValidMoves(player, opponent);
		}
	}

	public void print(String str) {
		Utils.print(str, Utils.CONTROLLER_DEBUG);
	}

	private void changeTurns() {
		print("Changing Turns\n__________Current Turn: " + (player1.isTurn() ? "BLACK" : "WHITE") + "__________");
		// Here record the move played
		// recordingMove();
		pieceCurrentlySelected = false;
		// selectedPiece = null;
		// clickedPiece = null;
		changePlayerTurn(player1);
		changePlayerTurn(player2);
	}

	private void changePlayerTurn(Player player) {
		player.setTurn(!player.isTurn());
		if (player.clockActive) {
			player.getClock().setRunning(player.isTurn());
		}
		if (player.isTurn()) {
			setCurrent(player);
			player.getClock().countdown.setTextFill(Utils.run);
		} else {
			setOpponent(player);
			player.getClock().countdown.setTextFill(Utils.wait);
		}
		for (Piece piece : currentPlayer.pieces) {
			if (piece.getState() != State.CAPTURED) {
				piece.calculateValidMoves(currentPlayer, opponent);
			}
		}
	}

	public Player getCurrent() {
		return currentPlayer;
	}

	public void setCurrent(Player currentPlayer) {
		this.currentPlayer = currentPlayer;
	}

	public Player getOpponent() {
		return opponent;
	}

	public void setOpponent(Player opponent) {
		this.opponent = opponent;
	}

	/**
	 * Calculate the valid moves which can be made by the currently selected piece
	 * This will generate green circles as a visual indication of valid moves
	 * 
	 * @param piece
	 */
	private void calculateValidMoves(Player player, Player opponent, Piece piece) {
		validMoves.clear();
		piece.calculateValidMoves(currentPlayer, opponent);
		validMoves.addAll(piece.getValidMoves());
		for (Coord coord : validMoves) {
			Circle circle = new Circle(gridsize / 3);
			circle.setFill(Color.rgb(0, 255, 0, 0.5));
			circle.setCenterX(coord.getX() * gridsize + gridsize / 2);
			circle.setCenterY(coord.getY() * gridsize + gridsize / 2);
			validMoveMarkers.add(circle);
		}
	}

	/**
	 * This is the method to determine which type of click has occurred and what to
	 * do with it This only cares about whether a square is empty, has own colour or
	 * different colour
	 * 
	 * @param coord
	 */
	public void determineClickType(Coord coord) {
		print(".........MOUSE CLICK REGISTERED.........");
		removeValidMoves();
		if (checkCoordForPiece(currentPlayer, coord)) {
			if (startingMove) {
				if (player1.clockActive) {
					player1.getClock().setRunning(true);
					player1.getClock().countdown.setTextFill(Utils.run);
					startingMove = false;
				}
			}
			clickedOnOwnColour();
		} else if (checkCoordForPiece(opponent, coord)) {
			clickedOnOppositeColour();
		} else {
			clickedOnEmptySquare(coord);
		}
	}

	private void removeValidMoves() {
		for (Circle circle : validMoveMarkers) {
			circle.setFill(Color.color(0, 0, 0, 0));
		}
		validMoveMarkers.clear();
	}

	/**
	 * Determine if a piece has been clicked on
	 * 
	 * @param player
	 * @param coord
	 * @return
	 */
	private boolean checkCoordForPiece(Player player, Coord coord) {
		for (Piece piece : player.pieces) {
			if (compareCoords(piece.getCoord(), coord)) {
				clickedPiece = piece;
				clickedPiece.toFront();
				return true;
			}
		}
		return false;
	}

	public boolean compareCoords(Coord coord1, Coord coord2) {
		return (coord1.getX() == coord2.getX() && coord1.getY() == coord2.getY());
	}

	/**
	 * Action to move piece to an empty square
	 * 
	 * @param coord
	 */
	public void movePiece(Coord coord) {
		defaultSizes();
		print("Moving Piece");
		movingPiece = true;
		selectedPiece.setCoord(new Coord(coord.getX(), coord.getY()));
	}

	/**
	 * Action to move piece to an empty square
	 * 
	 * @param coord
	 */
	public void takingPiece(Coord coord) {
		print("Taking Piece");
		taking = true;
		selectedPiece.setCoord(new Coord(coord.getX(), coord.getY()));
	}

	/**
	 * Set piece to the one clicked on
	 * 
	 * @param piece
	 */
	public void selectPiece(Piece piece) {
		defaultSizes();
		print("Selected Piece " + piece.getName());
		selectedPiece = piece;
		pieceCurrentlySelected = true; // Do this when the piece is clicked on
		calculateValidMoves(currentPlayer, opponent, piece);
	}

	/**
	 * Action when clicking on current piece
	 */
	public void clickedOnSelf() {
		print("Clicked on Self");
		unselectPiece();
	}

	/**
	 * Action when clicking on empty square
	 */
	public void clickedOnEmptySquare(Coord coord) {
		print("Clicked on Empty Square");
		if (pieceCurrentlySelected) {
			if (validSquareSelection(coord)) {
				movePiece(coord);
				changeTurns();
			} else {
				unselectPiece();
			}
		} else {
			doNothing();
		}
		pieceCurrentlySelected = false;
	}

	/**
	 * Check if the selected square is a valid move
	 * 
	 * @param square
	 * @return
	 */
	private boolean validSquareSelection(Coord square) {
		for (Coord coord : validMoves) {
			if (square.getX() == coord.getX() && square.getY() == coord.getY()) {
				return true;
			}
		}
		return false;
	}

	/**
	 * Deal with action for when clicking on the same piece
	 * 
	 * @param piece
	 */
	public void clickedOnOwnColour() {
		print("Clicked on Own Colour");
		if (validPieceSelection(clickedPiece)) {
			if (pieceCurrentlySelected) {
				if (compareCoords(clickedPiece.getCoord(), selectedPiece.getCoord())) {
					clickedOnSelf();
				} else {
					changePiece(clickedPiece);
				}
			} else {
				selectPiece(clickedPiece);
			}
		} else {
			doNothing();
		}
	}

	/**
	 * Check to determine if the piece can move
	 * 
	 * @param piece
	 * @return
	 */
	private boolean validPieceSelection(Piece piece) {
		return !piece.getValidMoves().isEmpty();
	}

	/**
	 * Deal with action for when clicking on opposite piece
	 * 
	 * @param piece
	 */
	public void clickedOnOppositeColour() {
		print("Clicked on Opposite Colour");
		if (pieceCurrentlySelected && validPieceCapture(clickedPiece)) {
			takePiece(clickedPiece);
		} else {
			unselectPiece();
		}
	}

	/**
	 * Check the piece can be captured
	 * 
	 * @param piece
	 * @return
	 */
	public boolean validPieceCapture(Piece piece) {
		for (Coord coord : validMoves) {
			if (piece.getCoord().getX() == coord.getX() && piece.getCoord().getY() == coord.getY()) {
				return true;
			}
		}
		return false;
	}

	/**
	 * Action to send taken piece to the graveyard (off grid) and move the current
	 * piece into its position
	 * 
	 * @param piece
	 */
	public void takePiece(Piece piece) {
		defaultSizes();
		Coord current = piece.getCoord();
		piece.setValidMoves(null);
		takingPiece(current);
		changeTurns();
	}

	/**
	 * Action to change to another piece when one is already selected
	 * 
	 * @param piece
	 */
	public void changePiece(Piece piece) {
		print("Changing Piece");
		unselectPiece();
		selectPiece(piece);
	}

	/**
	 * This is for when the click leaves the current state unchanged
	 */
	public void doNothing() {
		unselectPiece();
		print("Doing Nothing");
		return;
	}

	public void defaultSizes() {
		if (pieceCurrentlySelected) {
			selectedPiece.setWidth(Utils.DEFAULT_SIZE);
			selectedPiece.setHeight(Utils.DEFAULT_SIZE);
			selectedPiece.setX((int) (selectedPiece.getX() / gridsize) * gridsize + gridsize / 4);
			selectedPiece.setY((int) (selectedPiece.getY() / gridsize) * gridsize + gridsize / 4);
		}
	}

	/**
	 * Set selectedPiece to false and clear the valid squares
	 */
	public void unselectPiece() {
		defaultSizes();
		print("Unselecting Piece");
		pieceCurrentlySelected = false;
		selectedPiece = null;
		removeValidMoves();
	}

	public boolean checkForPromotion() {
		if (selectedPiece.getType() == Type.PAWN) {
			if (selectedPiece.getCoord().getY() == (selectedPiece.isWhite ? Utils.FIRST_ROW : Utils.EIGHTH_ROW)) {
				getPawnPromotion();
				return true;
			}
		}
		return false;
	}

	public void getPawnPromotion() {
		Alert promotion = setAlert(currentPlayer.colour == Colour.WHITE ? "res/black_" : "res/white_");
		promotionActive = true;
		promotion.showAndWait();
	}
}
