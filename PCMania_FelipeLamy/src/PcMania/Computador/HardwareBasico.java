package PcMania.Computador;

public class HardwareBasico {
    private String nome;
    private int capacidade;
    public HardwareBasico(String nome, int capacidade){
        this.nome=nome;
        this.capacidade=capacidade;
    }
    //Getters
    public String getNome() {
        return nome;
    }
    public int getCapacidade() {
        return capacidade;
    }
}
