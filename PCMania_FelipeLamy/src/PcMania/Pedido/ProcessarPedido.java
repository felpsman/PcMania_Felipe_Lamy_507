package PcMania.Pedido;
import PcMania.Cliente.Cliente;
import PcMania.Computador.Computador;
public class ProcessarPedido {
    public static void processarPedido(Computador[] computadoresAdquiridos, int qtdComputadoresAdquiridos, Cliente cliente) {
        if ((qtdComputadoresAdquiridos>=2)){
            System.out.println("\n-- Resumo da compra --");
            System.out.println("Cliente: " + cliente.getNome() + " com cpf: " + cliente.getCpf());
            for(int i=0;i<qtdComputadoresAdquiridos;i++)
                if (computadoresAdquiridos[i]!=null){
                    computadoresAdquiridos[i].mostraPCConfigs();
                    System.out.println();
                    }
            System.out.println("Valor total a pagar: R$ " + cliente.calculaTotalCompra());
            System.out.println("Pedido enviado...");
                }
        else if(qtdComputadoresAdquiridos==1) {
            System.out.println(cliente.getNome()+" voce escolheu cancelar o pedido, volte sempre!");
        }
        else {
            System.out.println(cliente.getNome()+" voce escolheu fechar o pedido, volte sempre!");

        }
        }
    }
