import PcMania.Cliente.Cliente;
import PcMania.Pedido.ProcessarPedido;
import PcMania.Computador.Computador;
import PcMania.Computador.MemoriaUSB;

import java.util.Scanner;

public  class Main {
    public static void main(String[] args){

        Scanner entrada = new Scanner(System.in);
        Computador pcEscolhido;
        float matricula;
        int oferta;
        int escolhaMemoria;
        int qtdComputadoresAdquiridos=0;
        int continuar;


        //Entradas
        System.out.print("Qual o seu nome: ");
        String nome =entrada.nextLine();
        System.out.print("Qual o seu cpf: ");
        String cpf =entrada.nextLine();
        System.out.print("Qual a sua matricula: ");
        matricula=entrada.nextFloat();

        Cliente cliente = new Cliente(nome,cpf);

        //Setando o nome,cpf,matricula de acordo com o que o usuario entrar
        cliente.setNome(nome);
        cliente.setCpf(cpf);


        //Colocando os preco e a marca dos computadores(ofertas)

        Computador[] computadoresAdquiridos = new Computador[10];
        Computador computador1 = new Computador("Apple",matricula);
        Computador computador2= new Computador("Samsung",matricula+1);
        Computador computador3 = new Computador("Dell",matricula+2);

        //Instanciando o obejeto com metodo para poder mostrar a oferta completa com a memoria extra pro cliente
        //Esse obejto "computar1" 2 e 3 serao apenas para mostrar par ao cliente as ofertas
        computador1.addMemoriaUSB(new MemoriaUSB("Memoria extra: Pen Drive",16));
        computador2.addMemoriaUSB(new MemoriaUSB("Memoria extra: Pen Drive",32));
        computador3.addMemoriaUSB(new MemoriaUSB("Memoria extra: HD Externo",1));


        //Instaciando as ofertas aos seus respectivos objetos
        computador1.addOfertas(1);
        computador2.addOfertas(2);
        computador3.addOfertas(3);



        //Mostrando as configuracoes do computador de cada oferta
        System.out.println("-- OFERTA 1 --");
        computador1.mostraPCConfigs();
        System.out.println("R$: "+computador1.getPreco());
        System.out.println("-- OFERTA 2 --");
        computador2.mostraPCConfigs();
        System.out.println("R$: "+computador2.getPreco());
        System.out.println("-- OFERTA 3 --");
        computador3.mostraPCConfigs();
        System.out.println("R$: "+computador3.getPreco());


        //Entrada oferta
        System.out.println("Lembrando que trabalhamos apensa com no minimo duas escolhas de ofertas.\"");
        while (true) {
            System.out.print("Escolha uma oferta: \n"+
                    "[1] para oferta 1 \n" +
                    "[2] para oferta 2 \n" +
                    "[3] para oferta 3 \n" +
                    "ou [0] para finalizar: ");
            oferta = entrada.nextInt();
            if (oferta == 0) {
                break;
            }
            if (oferta == 1) {
                pcEscolhido = new Computador("Apple",matricula); //Criando o objeto aqui novamnte para poder resetar o objeto, assim eu posso escolher a mesma oferta com e sem memoria extra
                pcEscolhido.addOfertas(1);
            } else if (oferta == 2) {
                pcEscolhido = new Computador("Samsung",matricula+1);
                pcEscolhido.addOfertas(2);
            } else if (oferta == 3) {
                pcEscolhido = new Computador("Dell",matricula+2);
                pcEscolhido.addOfertas(3);
            } else {
                System.out.println("Oferta inválida!");
                continue;
            }

            if (qtdComputadoresAdquiridos < computadoresAdquiridos.length) {
                computadoresAdquiridos[qtdComputadoresAdquiridos] = pcEscolhido;
                qtdComputadoresAdquiridos++;
            } else {
                System.out.println("Voce escolheu o maximo de ofertas possivel!");
            }

            //Adicionando o pc escolhido a conta do cliente
            cliente.comprarComputador(pcEscolhido);

            System.out.print("Deseja manter Memoria Extra: [1-Sim][0-Nao] ");
            escolhaMemoria= entrada.nextInt();

            if(oferta==1 && escolhaMemoria==1){
                pcEscolhido.addMemoriaUSB(new MemoriaUSB("Pen Drive",16));
                System.out.println("Oferta " + oferta + " com memoria extra de "+  pcEscolhido.getMemoriaUSB().getNome()+" "+ pcEscolhido.getMemoriaUSB().getCapacidade()+ " GB" + " adicionada à compra de " + cliente.getNome() + ".");
            } else if (oferta ==2 && escolhaMemoria==1) {
                pcEscolhido.addMemoriaUSB(new MemoriaUSB("Pen Drive",32));
                System.out.println("Oferta " + oferta + " com memoria extra de "+ pcEscolhido.getMemoriaUSB().getNome()+ " " + pcEscolhido.getMemoriaUSB().getCapacidade()+" GB" + " adicionada à compra de " + cliente.getNome() + ".");
            }
            else if (oferta==3 && escolhaMemoria==1) {
                pcEscolhido.addMemoriaUSB(new MemoriaUSB("HD Externo",1));
                System.out.println("Oferta " + oferta + " com memoria extra de "+ pcEscolhido.getMemoriaUSB().getNome()+" "+ pcEscolhido.getMemoriaUSB().getCapacidade()+ " TB" + " adicionada à compra de " + cliente.getNome() + ".");
            }
            else {
                System.out.println("Voce escolheu nao manter a memoria extra!");
                System.out.println("Oferta " + oferta + " adicionada à compra de " + cliente.getNome() + ".");
            }
            if(qtdComputadoresAdquiridos<2){
                System.out.println("Escolha mais uma oferta ou cancele toda a sua compra");
                System.out.println("Digite [1] para continuar ou [0] para cancelar");
                continuar=entrada.nextInt();
                if (continuar==0){
                    break;
                }
                else{
                    System.out.println("Voce ira ver novamente as ofertas");
                }
            }
        }

        //Nota Fiscal
        ProcessarPedido.processarPedido(computadoresAdquiridos, qtdComputadoresAdquiridos,cliente);
        entrada.close();
    }
}
