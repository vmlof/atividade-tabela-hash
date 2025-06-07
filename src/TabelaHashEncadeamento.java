import java.util.LinkedList;
import java.util.List;

public class TabelaHashEncadeamento {
    private List<Registro>[] tabela;
    private int tamanho;
    private int colisoes = 0;

    public TabelaHashEncadeamento(int tamanho) {
        this.tamanho = tamanho;
        this.tabela = new LinkedList[tamanho];
        for (int i = 0; i < tamanho; i++) {
            tabela[i] = new LinkedList<>();
        }
    }

    private int hashResto(String codigo) {
        return Math.abs(codigo.hashCode()) % tamanho;
    }

    private int hashMultiplicacao(String codigo) {
        double A = 0.6180339887;
        double valor = codigo.hashCode() * A;
        return (int) (tamanho * (valor - Math.floor(valor)));
    }

    private int hashDobramento(String codigo) {
        int parte1 = Integer.parseInt(codigo.substring(0, 3));
        int parte2 = Integer.parseInt(codigo.substring(3, 6));
        int parte3 = Integer.parseInt(codigo.substring(6, 9));
        return (parte1 + parte2 + parte3) % tamanho;
    }

    public void inserir(Registro registro, int tipoHash) {
        int indice = switch (tipoHash) {
            case 1 -> hashResto(registro.getCodigo());
            case 2 -> hashMultiplicacao(registro.getCodigo());
            case 3 -> hashDobramento(registro.getCodigo());
            default -> throw new IllegalStateException("Tipo de hash inválido");
        };

        if (!tabela[indice].isEmpty()) {
            colisoes++;
        }
        tabela[indice].add(registro);
    }

    public Registro buscar(String codigo, int tipoHash) {
        int indice = switch (tipoHash) {
            case 1 -> hashResto(codigo);
            case 2 -> hashMultiplicacao(codigo);
            case 3 -> hashDobramento(codigo);
            default -> throw new IllegalStateException("Tipo de hash inválido");
        };

        for (Registro registro : tabela[indice]) {
            if (registro.getCodigo().equals(codigo)) {
                return registro;
            }
        }
        return null;

    }

    public int getColisoes() { return colisoes; }

}
