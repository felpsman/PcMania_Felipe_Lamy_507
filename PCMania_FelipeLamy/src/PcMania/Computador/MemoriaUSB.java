package PcMania.Computador;

public class MemoriaUSB {
    //Variaveis
    private String nome;
    private int capacidade;


    //Construtor
    public MemoriaUSB(String nome,int capacidade) {
        this.nome = nome;
        this.capacidade=capacidade;
    }

    public String getNome() {
        return nome;
    }

    public int getCapacidade() {
        return capacidade;
    }
}
