package PcMania.Computador;

public class Computador {
    //Variaveis
    private String marca;
    private float preco;
    //Agregacao e Composicoes
    private HardwareBasico[] hardwareBasico;
    private SistemaOperacional so;
    private int qtdHardware = 0; // quantos itens realmente existem
    private MemoriaUSB memoriaUSB;


    //Construtores
    //Parametros para serem passados como marca e preco das ofertas
    public  Computador(String marca, float preco){
        this.marca=marca;
        this.preco=preco;
        //Incializando o array
        this.hardwareBasico =  new HardwareBasico[5];
        this.memoriaUSB=null; //Memoria como padrao sendo 0, so ira mudar se o usuario escolher ter
    }


    //Metodos
    public void addOfertas(int numeroOferta){
        if(numeroOferta==1){
            //Colocando as ofertas dentro de um array
            hardwareBasico[0] = new HardwareBasico("Petium Core i5",2200);
            hardwareBasico[1] = new HardwareBasico("Memoria Ram",8);
            hardwareBasico[2] = new HardwareBasico("HD",500);
            this.so=new SistemaOperacional("MacOS Sequoia",64);
            qtdHardware=3;
        }
        if(numeroOferta==2){
            hardwareBasico[0] = new HardwareBasico("Petium Core i7",3370);
            hardwareBasico[1] = new HardwareBasico("Memoria Ram",16);
            hardwareBasico[2] = new HardwareBasico("HD",1);
            this.so=new SistemaOperacional("Windows 8",64);
            qtdHardware=3;
        }
        if(numeroOferta==3){
            hardwareBasico[0] = new HardwareBasico("Petium Core i7",4500);
            hardwareBasico[1] = new HardwareBasico("Memoria Ram",32);
            hardwareBasico[2] = new HardwareBasico("HD",2);
            this.so=new SistemaOperacional("Windows 10",64);
            qtdHardware=3;
        }
    }
    public void mostraPCConfigs() {
        //Chamando o cosntrutor para atribuir os valores e salvar no array que foi criado
        System.out.println("Marca: " + this.marca);
        System.out.println("Preço: " + this.preco);
        for (int i = 0; i < qtdHardware; i++) {
            if (hardwareBasico[i].getCapacidade() >= 8 && hardwareBasico[i].getCapacidade() < 1000)
                System.out.println(hardwareBasico[i].getNome() + " " + hardwareBasico[i].getCapacidade() + " GB");
            else if (hardwareBasico[i].getCapacidade() <= 2) {
                System.out.println(hardwareBasico[i].getNome() + " " + hardwareBasico[i].getCapacidade() + " TB");
            }
            else {
                System.out.println(hardwareBasico[i].getNome() + " " + hardwareBasico[i].getCapacidade() + " Mhz");
            }
        }
        System.out.print(so.getNome() + " ");
        System.out.println(so.getTipo() + " bits");
        if(memoriaUSB!=null){
            System.out.print(memoriaUSB.getNome()+" ");
            if(memoriaUSB.getCapacidade()>1)
                System.out.println(memoriaUSB.getCapacidade()+" GB");
            else {
                System.out.println(memoriaUSB.getCapacidade()+" TB");

            }
        }
        else{
            System.out.println("Sem memoria adicionada!");
        }

    }
    public void addMemoriaUSB(MemoriaUSB musb){
        this.memoriaUSB=musb;

    }
    //Getters
    public float getPreco() {
        return preco;
    }
    public MemoriaUSB getMemoriaUSB() {
        return memoriaUSB;
    }
}
