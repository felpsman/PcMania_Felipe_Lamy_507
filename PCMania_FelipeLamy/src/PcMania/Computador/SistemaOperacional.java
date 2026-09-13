package PcMania.Computador;

public class SistemaOperacional {
    //Variveis
    private String nome;
    private int tipo;

    //Construtor
    public SistemaOperacional(String nome, int tipo){
        this.nome=nome;
        this.tipo=tipo;
    }
    //Getters
    public String getNome() {
        return nome;
    }
    public int getTipo() {
        return tipo;
    }
}
