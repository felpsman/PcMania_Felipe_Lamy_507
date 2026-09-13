package PcMania.Cliente;

import PcMania.Computador.Computador;

public class Cliente {
    //Variaveis
    private String nome;
    private String cpf;
    private float totalCompra;


    //Construtor
    public Cliente(String nome, String cpf){
        this.nome=nome;
        this.cpf=cpf;
    }

    //Metodos
    //Criei esse metodo para nao alterar a construcao do metodo calculaTotalCompra de como ele estava no UML
    public void comprarComputador(Computador pc) {
        this.totalCompra += pc.getPreco();
    }

    public float calculaTotalCompra(){
       return totalCompra;
    }


    //Getters e Setters
    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getCpf() {
        return cpf;
    }

    public void setCpf(String cpf) {
        this.cpf = cpf;
    }
}
