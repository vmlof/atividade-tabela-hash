import java.io.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class TesteDesempenho {

    public static void main(String[] args) {
        int[] tamanhosTabela = {1000, 10000, 100000};
        String[] arquivosDados = {"dados_1000000.txt", "dados_5000000.txt", "dados_20000000.txt"};


        List<String> resultados = new ArrayList<>();
        resultados.add("Tipo,Tamanho,Arquivo,Colisoes,TempoInsercao,TempoBusca");

        for (int tamanho : tamanhosTabela) {
            for (String arquivo : arquivosDados) {
                List<Registro> registros = carregarDados(arquivo);


                for (int tipoHash = 1; tipoHash <= 3; tipoHash++) {
                    TabelaHashEncadeamento tabelaEnc = new TabelaHashEncadeamento(tamanho);

                    long inicioInsercao = System.currentTimeMillis();
                    for (Registro registro : registros) {
                        tabelaEnc.inserir(registro, tipoHash);
                    }
                    long tempoInsercao = System.currentTimeMillis() - inicioInsercao;

                    long inicioBusca = System.currentTimeMillis();
                    Random random = new Random();
                    for (int i = 0; i < 5; i++) {
                        Registro registro = registros.get(random.nextInt(registros.size()));
                        tabelaEnc.buscar(registro.getCodigo(), tipoHash);
                    }
                    long tempoBusca = System.currentTimeMillis() - inicioBusca;

                    resultados.add(String.format("Encadeamento-Hash%d,%d,%s,%d,%d,%d",
                            tipoHash, tamanho, arquivo, tabelaEnc.getColisoes(), tempoInsercao, tempoBusca));
                }


                TabelaHashRehashing tabelaRehash = new TabelaHashRehashing(tamanho);

                long inicioInsercao = System.currentTimeMillis();
                for (Registro registro : registros) {
                    tabelaRehash.inserir(registro);
                }
                long tempoInsercao = System.currentTimeMillis() - inicioInsercao;

                long inicioBusca = System.currentTimeMillis();
                Random random = new Random();
                for (int i = 0; i < 5; i++) {
                    Registro registro = registros.get(random.nextInt(registros.size()));
                    tabelaRehash.buscar(registro.getCodigo());
                }
                long tempoBusca = System.currentTimeMillis() - inicioBusca;

                resultados.add(String.format("Rehashing,%d,%s,%d,%d,%d",
                        tamanho, arquivo, tabelaRehash.getColisoes(), tempoInsercao, tempoBusca));
            }
        }


        try (BufferedWriter writer = new BufferedWriter(new FileWriter("resultados.csv"))) {
            for (String linha : resultados) {
                writer.write(linha);
                writer.newLine();
            }
            System.out.println("Resultados salvos em resultados.csv");
        } catch (IOException e) {
            System.err.println("Erro ao salvar resultados");
            e.printStackTrace();
        }
    }

    private static List<Registro> carregarDados(String arquivo) {
        List<Registro> registros = new ArrayList<>();
        try (BufferedReader br = new BufferedReader(new FileReader("src/dados/" + arquivo))) {
            String linha;
            while ((linha = br.readLine()) != null) {
                registros.add(new Registro(linha.trim()));
            }
        } catch (IOException e) {
            System.err.println("Erro ao ler arquivo: " + arquivo);
            e.printStackTrace();
        }
        return registros;
    }
}