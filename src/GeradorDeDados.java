import java.io.*;
import java.util.Random;

public class GeradorDeDados {
    private static final long[] SEMENTES = {12345L, 67890L, 13579L};
    private static final int[] TAMANHOS_DOS_DADOS = {1_000_000, 5_000_000, 20_000_000};

    public static void main(String[] args) {
        gerarArquivosDeDados();
    }

    public static void gerarArquivosDeDados() {
        for (int i = 0; i < TAMANHOS_DOS_DADOS.length; i++) {
            gerarESalvarDados(TAMANHOS_DOS_DADOS[i], SEMENTES[i]);
        }
    }

    private static void gerarESalvarDados(int tamanho, long semente) {
        Random aleatorio = new Random(semente);
        String nomeArquivo = "dados_" + tamanho + ".txt";

        try (BufferedWriter escritor = new BufferedWriter(new FileWriter(nomeArquivo))) {
            for (int i = 0; i < tamanho; i++) {
                String codigo = gerarCodigoAleatorio(aleatorio);
                escritor.write(codigo);
                escritor.newLine();
            }
            System.out.println("✅ Arquivo gerado: " + nomeArquivo);
        } catch (IOException e) {
            System.err.println("❌ Erro ao escrever no arquivo: " + nomeArquivo);
            e.printStackTrace();
        }
    }

    private static String gerarCodigoAleatorio(Random aleatorio) {
        int numero = aleatorio.nextInt(1_000_000_000);
        return String.format("%09d", numero);
    }
}