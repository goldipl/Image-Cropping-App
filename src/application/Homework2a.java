package application;

import java.util.ArrayList;
import java.util.List;

import javafx.application.Application;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.PixelReader;
import javafx.scene.image.PixelWriter;
import javafx.scene.image.WritableImage;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.HBox;
import javafx.scene.layout.TilePane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.paint.ImagePattern;
import javafx.scene.shape.Rectangle;
import javafx.stage.Stage;

public class Homework2a extends Application {

	private int count = -1;
	private WritableImage image2;
	private List<Rectangle> rectangles;
	private Rectangle rectangle;
	private TilePane tilePane;
	private HBox root;
	private HBox hbox;
	private VBox vbox1;
	private VBox vbox2;

	@Override
	public void start(Stage primaryStage) {
		try {
			Button buttonClean = new Button("Czysc");
			buttonClean.getStyleClass().add("my-button");
			Button buttonLoad = new Button("Wczytaj");
			buttonLoad.getStyleClass().add("my-button");
			Label label = new Label("Wycinki w kolejnosci malejacej sredniej");
			Label label1 = new Label("wartosci skladowej czerwonej.");

			root = new HBox(15); 
			root.setPadding(new Insets(25)); 
			hbox = new HBox(5); // KONTENER NA PRZYCISKI
			vbox1 = new VBox(5); // KONTENER ZAWIERAJACY 'VBOX2' I PANEL KWADRATOW/WYCINEK
			vbox2 = new VBox(5); // KONTENER ZAWIERAJACY PRZYCISKI I OPIS
			tilePane = new TilePane(8, 8); // PANEL Z WYCINKAMI - odstep 8
			rectangles = new ArrayList<>();

			// --- PETLA TWORZACA PROSTOKATY ---
			for (int i = 0; i < 25; i++) {
				CreateRect(rectangle);
			}

			// --- STWORZENIE PLOTNA I DODANIE OBRAZKA DO PLOTNA ---
			Image image = new Image(
					getClass().getResourceAsStream("20190924_152112.jpg"));
			int imageHeight = (int)image.getHeight();
			int imageWidth = (int)image.getWidth();
			Canvas canvas = new Canvas(imageWidth, imageHeight);
			GraphicsContext gc = canvas.getGraphicsContext2D();
			gc.drawImage(image, 0, 0);

			// --- TWORZENIE PIXELREADERA ---
			PixelReader reader = image.getPixelReader();
		
			// --- AKCJA DLA PRZYCISKU 'CZYSC' ---
			buttonClean.setOnAction(event -> {
				cleanList(rectangles);
				count = -1;
			});
			
			// --- AKCJA DLA PRZYCISKU 'WCZYTAJ' ---
			buttonLoad.setOnAction(event -> {
				Alert();
			});
					
			// --- AKCJA DLA MYSZKI - WYCINANIE OBRAZKA ---
			EventHandler<MouseEvent> mouseHandler = new EventHandler<MouseEvent>() {

				public void handle(MouseEvent event) {

						count++;
						
						image2 = new WritableImage(41, 41);
						PixelWriter writer = image2.getPixelWriter();
						Color color = null;
						
						// --- POBIERANIE WYCINKA Z KLIKIEM W SRODKU PROSTOKATA ---
						int x = (int) event.getX()-41/2;
						int y = (int) event.getY()-41/2;
						color = reader.getColor(x, y);
						System.out.println("Zawartosc czerwonego: " + color.getRed());
						
						if (count == 25) count = 0; 
						
						rectangle = rectangles.get(count);
						rectangle.setFill(new ImagePattern(image2));
										
						sort();
						
						writer.setPixels(0, 0, 41, 41, reader, x, y);

				}
			};
						

			// --- DODANIE REAKCJI MYSZY DO PLOTNA ---
			canvas.addEventHandler(MouseEvent.MOUSE_PRESSED, mouseHandler);

			// --- DODANIE ELEMENTOW DO KONTENEROW 'HBOX' 'VBOX1' 'VBOX2' 'ROOT' ---
			hbox.getChildren().addAll(buttonLoad);
			hbox.getChildren().addAll(buttonClean);
			vbox2.getChildren().addAll(hbox, label, label1);
			vbox1.getChildren().addAll(vbox2, tilePane);
			root.getChildren().addAll(canvas, vbox1);

			Scene scene = new Scene(root, 780, 650);
			scene.getStylesheets().add(getClass().getResource("application.css").toExternalForm());
			primaryStage.setScene(scene);
			primaryStage.setResizable(true);
			primaryStage.setTitle("Praca domowa nr 2a");
			primaryStage.show();
			
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	// --- METODA TWORZACA PROSTOKAT O WYM. 41x41 ---
	public Rectangle CreateRect(Rectangle r) {
		r = new Rectangle(41, 41);
		r.setFill(Color.TRANSPARENT);
		r.setStroke(Color.BLACK);
		r.setStrokeWidth(2);
		rectangles.add(r);
		tilePane.getChildren().add(r);
		return r;
	}
	
	// --- METODA 'CZYSC' ---
	public void cleanList(List<Rectangle> list) {
		for (Rectangle r : list) {
			r.setFill(Color.TRANSPARENT);
			r.setStroke(Color.BLACK);
			r.setStrokeWidth(2);
		}	
	}
	
	// --- METODA SORTOWANIA ---
	
	public void sort() {

	// metoda sortowania nie zostala dokonczona
			
	}
	
	// --- OBSLUGA PRZYCISKU WCZYTAJ - WYSWIETLANIE ALERTU ---
	
	public void Alert() {
			
		Alert alert = new Alert(Alert.AlertType.WARNING, "UWAGA", ButtonType.OK);
		alert.setTitle("Uwaga! Aplikacja w trakcie rozbudowywania!");
		alert.setHeaderText("W trakcie rozbudowywania!");
		alert.setContentText("Aplikacja trakcie rozbudowywania! Przycisk wczytywania nowego obrazka nie jest jeszcze obslugiwany!");
		alert.showAndWait();
		if (alert.getResult() == ButtonType.OK) {
			alert.close();
		}
	}

		
	public static void main(String[] args) {
		launch(args);
	}
}
