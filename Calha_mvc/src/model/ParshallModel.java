package model;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

public class ParshallModel {

    public final double[] W_MM  = { 76, 152, 229, 305, 457, 610, 915, 1220, 1525 };
    public final String[] W_POL = { "3\"", "6\"", "9\"", "1'", "1.5'", "2'", "3'", "4'", "5'" };
    public final double[] Q_MIN = { 0.85, 1.52, 2.55, 3.11, 4.25, 11.89, 17.26, 36.79, 62.80 };
    public final double[] Q_MAX = { 53.8, 110.4, 251.9, 455.6, 696.2, 936.7, 1426.0, 1921.0, 2422.0 };
    public final double[] K_TAB = { 0.1771, 0.3812, 0.5354, 0.6909, 1.0560, 1.4290, 2.1840, 2.9630, 3.7320 };
    public final double[] N_TAB = { 1.5447, 1.5300, 1.5300, 1.5220, 1.5380, 1.5500, 1.5666, 1.5738, 1.5870 };
    public final double[] E_CM  = { 38.1, 45.7, 61.0, 91.5, 91.5, 91.5, 91.5, 91.5, 91.5 };

    public final double COEF_RETORNO = 0.8;
    public final double SEGUNDOS_DIA = 86400.0;

    private int habitantes;
    private double consumo;

    private double qr;
    private int idx = -1;
    private double hFinal;
    private double epsilonFinal;

    private List<String> logVerificacao = new ArrayList<>();

    public void setDados(int habitantes, double consumo) {
        this.habitantes = habitantes;
        this.consumo = consumo;
    }

    public boolean calcular() {
        this.qr = (habitantes * consumo * COEF_RETORNO) / SEGUNDOS_DIA;
        this.logVerificacao.clear();

        for (int i = 0; i < W_MM.length; i++) {
            if (qr < Q_MIN[i] || qr > Q_MAX[i]) continue;

            double K = K_TAB[i];
            double n = N_TAB[i];
            double E = E_CM[i];
            double H = Math.pow(qr / K, 1.0 / n);
            double epsilon = 0.7 * E;

            String logTentativa = String.format(
                    "Tentando W = %-5s | K=%.4f | n=%.4f | E=%.1f cm\n" +
                            "  H       = (%.4f / %.4f)^(1/%.4f) = %.4f cm\n" +
                            "  epsilon = 0,7 x %.1f = %.2f cm",
                    W_POL[i], K, n, E, qr, K, n, H, E, epsilon);

            if (epsilon >= H) {
                logVerificacao.add(logTentativa + String.format("\n  epsilon (%.2f) >= H (%.4f) -> ATENDE!\n", epsilon, H));
                this.idx = i;
                this.hFinal = H;
                this.epsilonFinal = epsilon;
                return true; // Encontrou uma calha válida
            } else {
                logVerificacao.add(logTentativa + String.format("\n  epsilon (%.2f) < H (%.4f) -> NAO ATENDE. Subindo...\n", epsilon, H));
            }
        }
        return false; // Nenhuma calha atendeu
    }

    public int getHabitantes() { return habitantes; }
    public double getConsumo() { return consumo; }
    public double getQr() { return qr; }
    public int getIdx() { return idx; }
    public double getHFinal() { return hFinal; }
    public double getEpsilonFinal() { return epsilonFinal; }
    public List<String> getLogVerificacao() { return logVerificacao; }
}