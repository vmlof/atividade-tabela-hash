public class TabelaHashRehashing {
    private Registro[] tabela;
    private int tamanho;
    private int colisoes = 0;

    public TabelaHashRehashing(int tamanho) {
        this.tamanho = tamanho;
        this.tabela = new Registro[tamanho];
    }


    private int hash(String codigo, int tentativa) {
        return (Math.abs(codigo.hashCode()) + tentativa) % tamanho;
    }

    public void inserir(Registro registro) {
        int indice = hash(registro.getCodigo(), 0);
        if (tabela[indice] != null) {
            colisoes++;
            return;
        }
        tabela[indice] = registro;
    }

    public Registro buscar(String codigo) {
        int indice = hash(codigo, 0);
        if (tabela[indice] != null && tabela[indice].getCodigo().equals(codigo)) {
            return tabela[indice];
        }
        return null;
    }

    public int getColisoes() { return colisoes; }
}