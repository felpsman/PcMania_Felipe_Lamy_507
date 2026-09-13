# Nome: Felipe Lamy Rosa Renno
# Curso: Enhenharia de Software
# Matricula: 507


# PCMania

Projeto acadêmico em Java para praticar Programação Orientada a Objetos (POO), implementado a partir de um diagrama de classes UML. Simula uma loja de computadores onde o cliente escolhe entre 3 ofertas fixas, pode adicionar memória extra e recebe um resumo da compra ao final.

## Como executar

O projeto usa pacotes (`Cliente.PCmania` e `Computador.PCmania`), então a estrutura de pastas precisa refletir isso:

```
src/
├── Main.java
├── Cliente/
│   └── PCmania/
│       ├── Cliente.java
│       └── ProcessarPedido.java
└── Computador/
    └── PCmania/
        ├── Computador.java
        ├── HardwareBasico.java
        ├── MemoriaUSB.java
        └── SistemaOperacional.java
```

Compilar e rodar:
```bash
javac Main.java Cliente/PCmania/*.java Computador/PCmania/*.java
java Main
```

## Fluxo do programa

1. O usuário informa nome, CPF e matrícula.
2. O sistema mostra as 3 ofertas disponíveis com suas configurações e preços.
3. O usuário escolhe ofertas uma a uma (é obrigatório escolher **no mínimo 2**, conforme a multiplicidade `2..*` do diagrama) e pode adicionar uma memória extra a cada uma.
4. Ao finalizar, o sistema mostra o resumo da compra: computadores adquiridos, valor total e confirmação do pedido. Se o cliente sair com menos de 2 computadores escolhidos, o pedido é cancelado.

## Modelagem (UML)

| Classes | Relação | Por quê |
|---|---|---|
| `Computador` ◆— `HardwareBasico` | **Composição** | Os componentes de hardware são criados dentro do próprio `Computador` (em `addOfertas`) e não existem fora dele — se o computador "morre", eles morrem junto. |
| `Computador` ◆— `SistemaOperacional` | **Composição** | Mesmo raciocínio: o SO é instanciado internamente e não é compartilhável entre computadores. |
| `Computador` ◇— `MemoriaUSB` (0..1) | **Agregação** | A `MemoriaUSB` é criada de forma independente (no `Main`) e só depois associada via `addMemoriaUSB()` — tem existência própria fora do `Computador`. |
| `Cliente` → `Computador` (2..*, "compra") | **Associação simples** | O `Cliente` não guarda nenhuma lista de computadores como atributo. Ele só recebe o `Computador` como parâmetro em `comprarComputador()`, usa o preço pra acumular o total e "esquece" o objeto em seguida. Quem mantém a lista de computadores comprados é o `Main`. |

## Classes

- **`Cliente`** — nome, CPF e o total acumulado da compra (`totalCompra`). `comprarComputador(Computador)` soma o preço ao total; `calculaTotalCompra()` retorna o total acumulado (sem parâmetros, fiel à assinatura do UML).
- **`Computador`** — marca, preço, hardware básico (composição), sistema operacional (composição) e memória USB opcional (agregação). `addOfertas(int)` monta a configuração pré-definida de cada oferta (1, 2 ou 3).
- **`HardwareBasico`** — nome e capacidade de um componente (processador, RAM, HD).
- **`SistemaOperacional`** — nome e tipo (32/64 bits).
- **`MemoriaUSB`** — nome e capacidade de uma memória extra (pen drive, HD externo).
- **`ProcessarPedido`** — método estático que confirma o envio do pedido para cada computador da compra.
- **`Main`** — orquestra o fluxo: cadastro do cliente, exibição das ofertas, loop de escolha (criando um `Computador` novo a cada compra), validação do mínimo de 2 computadores e emissão do resumo final.

## Decisões de design (perguntas resolvidas durante o desenvolvimento)

- **Cada compra precisa de um `Computador` independente.** As 3 ofertas (`computador1`, `computador2`, `computador3`) mostradas no início servem só de vitrine. Cada vez que o cliente escolhe uma oferta dentro do loop, um `Computador` novo é instanciado com aquela configuração — evitando que a memória extra de uma compra "vaze" para outra compra da mesma oferta (bug de aliasing corrigido durante o desenvolvimento).

## Regras de negócio implementadas

- Mínimo de 2 computadores por pedido (`2..*` do UML), com opção de cancelar a compra se o cliente desistir antes de atingir esse mínimo.
- Memória extra é opcional e específica por compra (não compartilhada entre computadores da mesma oferta).
- O pedido só é processado (`ProcessarPedido`) se o mínimo de 2 computadores for atingido.

Utilizei a I.A para tirar uma duvida em especifico e para perguntar se o meu projeto estava de acordo com o UML. 
Tambem utilizei I.A para criar esse ReadMe. Utilizei o Claude Sonnet 5.


Segue os promts com os resultados:

Promt 1:
Estou com um problema no meu codigo, quando eu coloco que quero memoria a mais em uma oferta, eu nao consigo tirar essa memoria extra da oferta depois, ou seja, eu nao consigo depois de colocar a memoria extra no array de computadores, eu nao consigo escolher a mesma oferta sem memoria extra

Resultado 1:
Drafting a per-purchase object creation approach to fix the leak.
Boa observação — isso é um bug real de aliasing (referências compartilhadas), bem diferente dos anteriores. Deixa eu explicar a causa raiz.

Por que isso acontece:

computador1, computador2 e computador3 são criados uma única vez, lá no início do Main, e continuam sendo os mesmos objetos durante todo o programa. Toda vez que o cliente escolhe a "oferta 1", você está pegando essa mesma referência de computador1 de novo — não uma cópia nova.

Então, se o cliente:

Escolhe oferta 1 → adiciona memória extra → computador1.memoriaUSB fica preenchido.
Escolhe oferta 1 de novo, dessa vez sem memória → cai no else, que nunca reseta memoriaUSB de volta pra null.
Resultado: o mesmo objeto computador1 continua com a memória "grudada" — e pior, como o array computadoresAdquiridos guarda referências, não cópias, a 1ª compra (que está no array) também "sente" qualquer mudança feita depois no mesmo computador1. Ou seja, mesmo resetando no else, você corrigiria só sintomaticamente e ainda bagunçaria a 1ª compra que já estava no array.

A causa de fundo: o programa trata as "ofertas" como se fossem produtos únicos e reaproveitáveis, mas na prática cada compra deveria gerar um computador independente — um novo objeto, não o mesmo reaproveitado.

A correção certa: criar um Computador novo a cada vez que uma oferta é escolhida dentro do loop, em vez de reaproveitar computador1/computador2/computador3:


Promt 2: 
Qual nota voce daria para esse versao do projeto ?

Checking for a cancellation-order processing bug.
Boa evolução! Essa versão corrigiu os bugs mais graves da anterior. Eu daria 8.5/10.

O que foi corrigido (e ficou muito bom):

✅ Bug da duplicação do total resolvido. Agora comprarComputador() acumula internamente e calculaTotalCompra() só retorna o valor final, chamado uma única vez no resumo. Sem soma dupla.
✅ Assinatura bate 100% com o UML — calculaTotalCompra(): float, sem parâmetro nenhum, exatamente como no diagrama.
✅ Oferta inválida agora tem continue — evita que um pcEscolhido antigo (ou null) seja adicionado por engano ao array.
✅ Removeu aquele print morto ("Nenhuma oferta foi escolhida") que não fazia sentido.
✅ A matrícula aparece certinho no resumo (+matricula).
Um bug que sobrou:

ProcessarPedido.processarPedido(...) está sendo chamado fora do if(qtdComputadoresAdquiridos>=2), ou seja, roda sempre — mesmo quando o pedido foi cancelado por ter menos de 2 computadores.

Cenário que quebra: o cliente escolhe 1 computador, depois decide cancelar (continuar=0). O programa já imprime "Pedido Cancelado", mas logo depois ProcessarPedido roda mesmo assim e imprime "Pedido enviado..." para aquele computador que ficou no array — contradizendo o cancelamento que acabou de acontecer.

Correção simples — mover a chamada pra dentro do bloco if:

java
if(qtdComputadoresAdquiridos>=2 ){
    System.out.println("\n-- Resumo da compra --");
    // ... resto do resumo ...
    System.out.println("Valor total a pagar: R$ " + cliente.calculaTotalCompra());
    ProcessarPedido.processarPedido(computadoresAdquiridos, qtdComputadoresAdquiridos); // <- move pra cá
}
Corrigindo isso, sobe fácil pra 9.5–10 — a modelagem OO e a fidelidade ao UML já estão excelentes.

