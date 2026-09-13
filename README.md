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

- **Por que associação simples e não agregação?** Composição e agregação exigem que o "todo" guarde uma referência duradoura à "parte". Como o `Cliente` não armazena os `Computador` comprados (só processa e acumula o total), a relação é associação simples — mesmo assim, o `Cliente` pôde manter um método `calculaTotalCompra()` sem parâmetros guardando **um valor primitivo** (`float`), o que não fere a associação simples, já que não é uma referência a objeto.
- **`addOfertas` como método estático quebraria a composição?** Não — o que define composição é *quem* cria e controla o ciclo de vida da parte, não se o método é estático ou de instância. Um método estático dentro da própria classe `Computador` continuaria sendo composição, desde que o resultado fique preso a um atributo privado da instância.
- **Cada compra precisa de um `Computador` independente.** As 3 ofertas (`computador1`, `computador2`, `computador3`) mostradas no início servem só de vitrine. Cada vez que o cliente escolhe uma oferta dentro do loop, um `Computador` novo é instanciado com aquela configuração — evitando que a memória extra de uma compra "vaze" para outra compra da mesma oferta (bug de aliasing corrigido durante o desenvolvimento).

## Regras de negócio implementadas

- Mínimo de 2 computadores por pedido (`2..*` do UML), com opção de cancelar a compra se o cliente desistir antes de atingir esse mínimo.
- Memória extra é opcional e específica por compra (não compartilhada entre computadores da mesma oferta).
- O pedido só é processado (`ProcessarPedido`) se o mínimo de 2 computadores for atingido.
