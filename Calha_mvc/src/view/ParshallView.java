package view;
import model.ParshallModel;
import java.util.Scanner;

public class ParshallView {
    private Scanner scanner;

    public ParshallView() {
        this.scanner = new Scanner(System.in);
    }

    public void mostrarCabecalho() {
        System.out.println("============================================================");
        System.out.println("       DIMENSIONAMENTO DA CALHA PARSHALL                   ");
        System.out.println("============================================================\n");
    }

    public int pedirHabitantes() {
        System.out.print("Informe o numero de habitantes: ");
        return scanner.nextInt();
    }

    public double pedirConsumo() {
        System.out.print("Informe o consumo per capita (L/hab.dia): ");
        return scanner.nextDouble();
    }

    public void mostrarCalculoVazao(int habitantes, double consumo, double coefRetorno, double qr) {
        System.out.println("\n------------------------------------------------------------");
        System.out.printf("  Qr = (%d x %.1f x %.1f) / 86400%n", habitantes, consumo, coefRetorno);
        System.out.printf("  Qr = %.4f L/s%n", qr);
        System.out.println("------------------------------------------------------------");
    }

    public void mostrarPassoAPasso(ParshallModel model) {
        System.out.println("\n  VERIFICACAO DAS CALHAS:");
        System.out.println("  ----------------------------------------------------");
        for (String log : model.getLogVerificacao()) {
            System.out.println("  " + log);
        }
        System.out.println("  ----------------------------------------------------");
    }

    public void mostrarErro() {
        System.out.println("\n  ATENCAO: Nenhuma calha atendeu os criterios!");
    }

    public void mostrarResultadosFinais(ParshallModel model) {
        int idx = model.getIdx();
        System.out.println("\n============================================================");
        System.out.println("                   RESULTADOS FINAIS                       ");
        System.out.println("============================================================");
        System.out.printf("  Habitantes              : %d hab%n", model.getHabitantes());
        System.out.printf("  Consumo per capita      : %.2f L/hab.dia%n", model.getConsumo());
        System.out.printf("  Coeficiente de retorno  : %.1f%n", model.COEF_RETORNO);
        System.out.println("------------------------------------------------------------");
        System.out.printf("  Vazao de Projeto (Qr)   : %.4f L/s%n", model.getQr());
        System.out.println("------------------------------------------------------------");
        System.out.printf("  Calha adotada           : W = %s (%d mm)%n", model.W_POL[idx], (int) model.W_MM[idx]);
        System.out.printf("  K (tabelado)            : %.4f%n", model.K_TAB[idx]);
        System.out.printf("  n (tabelado)            : %.4f%n", model.N_TAB[idx]);
        System.out.println("------------------------------------------------------------");
        System.out.printf("  Lamina d'agua (H)       : %.4f cm%n", model.getHFinal());
        System.out.printf("  E (borda da calha)      : %.1f cm%n", model.E_CM[idx]);
        System.out.printf("  epsilon = 0,7 x E       : %.2f cm%n", model.getEpsilonFinal());
        System.out.printf("  Criterio epsilon >= H   : %.2f >= %.4f -> ATENDE%n", model.getEpsilonFinal(), model.getHFinal());
        System.out.println("============================================================");
    }

    public void mostrarTabela(ParshallModel model) {
        System.out.println("\n  TABELA - CALHA PARSHALL (Q em L/s):");
        System.out.println("  +--------+-------------------+--------+--------+-------+");
        System.out.println("  | W      | Faixa Q (L/s)     |   K    |   n    | E(cm) |");
        System.out.println("  +--------+-------------------+--------+--------+-------+");
        for (int i = 0; i < model.W_MM.length; i++) {
            String marca = (i == model.getIdx()) ? " <<" : "";
            System.out.printf("  | %-6s | %6.2f - %8.1f | %.4f | %.4f | %5.1f |%s%n",
                    model.W_POL[i], model.Q_MIN[i], model.Q_MAX[i],
                    model.K_TAB[i], model.N_TAB[i], model.E_CM[i], marca);
        }
        System.out.println("  +--------+-------------------+--------+--------+-------+");
    }

    public void fechar() {
        scanner.close();
    }
}