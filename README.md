# Corrida Maluca

Projeto didático em Java com foco em orientação a objetos, encapsulamento, ArrayList e simulação de corrida no console.

## O que esta versão tem

- Cadastro de pista e veículos pelo usuário
- Corrida simulada minuto a minuto
- Classificação dinâmica por progresso total na pista
- Pódio ao final da corrida
- Falcatrua configurada em cada veículo
- Recarga obrigatória entre usos de falcatrua
- Efeitos ofensivos aleatórios entre os carros durante a corrida

## Falcatruas disponíveis

Cada veículo possui uma falcatrua fixa, configurada no cadastro:

- `OLEO`: reduz a velocidade do alvo
- `FUMACA`: aumenta o consumo do alvo
- `BOMBA`: aplica uma penalidade forte de velocidade
- `COLA`: reduz a aceleração do alvo por alguns minutos
- `RAIO`: reduz temporariamente a velocidade máxima do alvo
- `TURBO_SUJO`: melhora o atacante por pouco tempo e ainda atrapalha o alvo

## Regras importantes

- Apenas veículos ativos podem lançar falcatrua
- Um veículo não pode lançar falcatrua em minutos seguidos
- Depois de usar uma falcatrua, o carro entra em recarga
- O acerto da falcatrua depende da chance configurada no veículo
- Os efeitos são temporários e duram poucos minutos

## Estrutura de classes

- `App`: fluxo principal e interação com o usuário
- `Pista`: controla a corrida, as posições e os eventos ofensivos
- `Veiculo`: mantém estado, movimento, combustível e falcatrua

## Como executar

Compile os arquivos da pasta `src` e execute a classe `App`.

Exemplo com `javac` e `java`:

```bash
javac -d bin src/*.java src/menus/*.java src/utilitarios/*.java
java -cp bin App
```

## Observação didática

A modelagem foi mantida simples para turmas que estudaram abstração e encapsulamento, sem depender de herança ou interfaces.
