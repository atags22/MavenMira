
import javafx.event.ActionEvent;
import javafx.scene.control.TextField;

public class Controller {
    public TextField sendField;

    public void sendBytes(ActionEvent actionEvent) {
        String toSend =  sendField.getText();
    }
}

