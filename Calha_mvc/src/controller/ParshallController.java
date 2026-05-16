package controller;

import model.ParshallModel;
import view.ParshallView;

import java.math.BigDecimal;

public class ParshallController {
    private ParshallModel model;
    private ParshallView view;

    public ParshallController(ParshallModel model, ParshallView view) {
        this.model = model;
        this.view = view;
    }

    public void executar() {
        view.mostrarCabecalho();

        int habitantes = view.pedirHabitantes();
        double consumo = view.pedirConsumo();


        model.setDados(habitantes, consumo);


        boolean sucesso = model.calcular();


        view.mostrarCalculoVazao(habitantes, consumo, model.COEF_RETORNO, model.getQr());
        view.mostrarPassoAPasso(model);

        if (sucesso) {
            view.mostrarTabela(model);
            view.mostrarResultadosFinais(model);
        } else {
            view.mostrarErro();
        }

        view.fechar();
    }
}

