# Corrida Maluca

Projeto em Java com foco didático em orientação a objetos, abstração, encapsulamento e uso de `ArrayList`.
Uso de utilitários Menu, Video e Teclado.

## App

A aplicação permite uso direto pelo usuário no console:

- configurar a pista
- cadastrar veículos manualmente
- listar veículos cadastrados
- carregar um exemplo pronto
- iniciar a corrida

## Estrutura principal

- `App.java`: fluxo principal da aplicação
- `Pista.java`: controle da corrida
- `Veiculo.java`: modelagem do carro
- `menus/MenuPrincipal.java`: opções do sistema
- `utilitarios/*`: leitura de teclado e exibição no console

## Como executar

Compile os arquivos fonte:

```bash
javac -d bin src/*.java src/menus/*.java src/utilitarios/*.java
```

Execute a aplicação:

```bash
java -cp bin App
```

## Fluxo sugerido

1. Configurar nova pista
2. Cadastrar pelo menos dois veículos
3. Iniciar corrida

Ou então:

1. Carregar exemplo automático
2. Iniciar corrida

## Observação

O projeto foi mantido em um nível compatível com introdução a abstração e encapsulamento, sem exigir herança ou interfaces.
O objetivo é exemplificar o pensamento de abstrações e sistema que o fluxo é executado pela troca de mensagens entre objetos.
