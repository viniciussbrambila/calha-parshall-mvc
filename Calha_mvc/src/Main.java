import controller.ParshallController;
import model.ParshallModel;
import view.ParshallView;

public class Main {
    public static void main(String[] args) {

        ParshallModel model = new ParshallModel();
        ParshallView view = new ParshallView();

        ParshallController controller = new ParshallController(model, view);

        controller.executar();
    }
}
