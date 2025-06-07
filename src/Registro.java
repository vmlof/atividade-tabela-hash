public class Registro {
    private String codigo;

    public Registro(String codigo) {
        if (codigo.length() != 9 || !codigo.matches("\\d+")) {
            throw new IllegalArgumentException("Código inválido. O código deve ter 9 dígitos numéricos.");
        }
        this.codigo = codigo;
    }

    public String getCodigo() {
        return codigo;
    }

    @Override
    public int hashCode() {
        return codigo.hashCode();
    }
}