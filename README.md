# Corrida Maluca - Simulador por OO de uma pista da corrida maluca!

## Estrutura principal do sistema

O projeto foi modelado com classes principais:

### `App`

Responsável pelo menu principal e pela interação com o usuário.

### `Pista`

Responsável por controlar a corrida, o tempo, a classificação e os eventos entre os veículos.

### `Veiculo`

Responsável por representar cada carro participante, com seus dados e comportamentos.

---

# Como a corrida funciona

## Etapa 1: criação da pista

Antes da corrida começar, é necessário configurar a pista.

A pista possui:

- nome
- tamanho em km
- número total de voltas

Exemplo:

- nome: Autódromo da Confusão
- tamanho: 5 km
- voltas: 10

## Etapa 2: cadastro de veículos

Depois da pista, o usuário cadastra os veículos participantes.

Cada veículo deve possuir:

- nome do carro
- capacidade do tanque
- combustível atual
- km por litro
- potência
- velocidade máxima
- tempo de 0 a 100
- posição inicial
- tipo de falcatrua
- tempo de recarga da falcatrua
- chance de sucesso
- intensidade da falcatrua

## Etapa 3: início da corrida

Com a pista e os veículos cadastrados, a corrida pode ser iniciada.

Durante a corrida, o sistema executa um laço de simulação por minuto.

A cada minuto:

1. o tempo total da corrida aumenta
2. alguns carros podem tentar uma falcatrua
3. os veículos atualizam sua velocidade
4. os veículos percorrem distância
5. os veículos consomem combustível
6. voltas podem ser completadas
7. a posição dos carros é recalculada
8. o sistema verifica se a corrida terminou

## Etapa 4: fim da corrida

A corrida termina quando ocorre uma destas condições:

- algum carro completa o total de voltas da corrida
- todos os carros ficam fora da corrida

Ao final, o sistema exibe:

- tempo total
- classificação final
- pódio
- situação dos carros

---

## 3. Aplicar

# Como usar o app no console

## Fluxo normal esperado

Ao iniciar o programa, o usuário verá o menu principal.

Dependendo da versão do projeto, o menu pode incluir opções semelhantes a estas:

- configurar pista
- cadastrar veículo
- listar veículos
- carregar exemplo automático
- iniciar corrida
- sair

A ordem recomendada de uso é:

1. configurar a pista
2. cadastrar os veículos
3. revisar os veículos cadastrados
4. iniciar a corrida
5. acompanhar o resultado

---

# Como cadastrar uma pista corretamente

## Dados que devem ser informados

### Nome da pista

É o nome textual da corrida ou autódromo.

Exemplos:

- Pista do Penhasco
- Autódromo Central
- Vale das Armadilhas

### Tamanho da pista

Representa quantos quilômetros existem em uma volta completa.

Exemplo:

- 4.5 km
- 5.0 km
- 8.0 km

### Número de voltas

Define quantas voltas o vencedor deve completar para encerrar a corrida.

Exemplo:

- 5 voltas
- 10 voltas
- 20 voltas

## Dica de teste

Para testes rápidos, use:

- pista de 4 km a 6 km
- entre 5 e 8 voltas

Isso ajuda a corrida a terminar em tempo razoável.

---

# Como cadastrar um veículo corretamente

## Dados do veículo

### Nome

É o nome do carro.

Exemplos:

- Falcao GT
- Trovao X
- Vortex R
- Cometa Z

### Capacidade máxima do tanque

Quantidade máxima de litros que o tanque suporta.

Exemplo:

- 45
- 50
- 60

### Combustível atual

Quantidade de combustível no início da corrida.

Esse valor deve ser:

- maior ou igual a zero
- menor ou igual à capacidade do tanque

Exemplo:

- capacidade: 50
- combustível atual: 40

### Quilômetros por litro

Representa o rendimento do veículo.

Exemplo:

- 7.5 km/l
- 8.0 km/l
- 10.0 km/l

Quanto maior esse valor, menor o consumo.

### Potência

Representa a força do carro, influenciando sua evolução.

Exemplo:

- 180
- 220
- 250

### Velocidade máxima

Limite máximo de velocidade do veículo.

Exemplo:

- 180 km/h
- 210 km/h
- 240 km/h

### Tempo de 0 a 100

Quanto menor esse valor, mais rápido o carro acelera.

Exemplo:

- 9.5 segundos
- 8.0 segundos
- 7.2 segundos

### Posição inicial

Define o grid de largada.

Exemplo:

- 1
- 2
- 3
- 4

### Falcatrua do veículo

Cada veículo possui uma falcatrua fixa.

Exemplos de falcatrua:

- OLEO
- FUMACA
- BOMBA
- COLA
- RAIO
- TURBO_SUJO

### Tempo de recarga da falcatrua

Depois que o carro usa uma falcatrua, ele precisa esperar alguns minutos para poder usar novamente.

Exemplo:

- 2 minutos
- 3 minutos
- 4 minutos

### Chance de sucesso da falcatrua

Representa a probabilidade de a falcatrua funcionar.

Exemplo:

- 30
- 50
- 70

Esses valores normalmente representam percentuais.

### Intensidade da falcatrua

Representa a força do efeito aplicado.

Exemplo:

- 10
- 20
- 30

Quanto maior a intensidade, mais forte o impacto.

---

## 4. Analisar

# Como funcionam as falcatruas

## Conceito geral

As falcatruas são efeitos ofensivos ou estratégicos usados pelos veículos durante a corrida.

Elas servem para:

- atrapalhar outros carros
- alterar temporariamente desempenho
- criar imprevisibilidade
- deixar a corrida mais divertida

## Regras principais das falcatruas

### Regra 1: a falcatrua é fixa no carro

Cada carro possui sua própria falcatrua configurada no cadastro.

### Regra 2: existe tempo de recarga

Depois de usar a falcatrua, o carro precisa esperar alguns minutos antes de poder usar novamente.

Isso evita spam de ataques.

### Regra 3: a falcatrua pode falhar

A chance de sucesso é considerada no momento do ataque.

### Regra 4: apenas carros ativos participam

Um carro sem combustível ou fora da corrida não pode atacar.

### Regra 5: o efeito é temporário ou pontual

As falcatruas não devem quebrar a corrida permanentemente.  
Elas afetam o comportamento do alvo por alguns minutos ou em um instante.

---

# Exemplos de falcatruas

## OLEO

Efeito esperado:

- reduz a velocidade do alvo
- atrapalha o desempenho temporariamente

Uso indicado:

- carros que querem derrubar o ritmo do adversário

## FUMACA

Efeito esperado:

- aumenta o consumo de combustível do alvo
- reduz a eficiência do rival

Uso indicado:

- corridas mais longas, para desgastar o adversário

## BOMBA

Efeito esperado:

- reduz fortemente a velocidade atual do alvo
- pode causar impacto imediato bem forte

Uso indicado:

- momentos de disputa direta

## COLA

Efeito esperado:

- reduz aceleração
- faz o carro demorar mais para voltar ao ritmo forte

Uso indicado:

- segurar o carro rival por mais de um minuto

## RAIO

Efeito esperado:

- reduz temporariamente a velocidade máxima do alvo

Uso indicado:

- limitar carros muito rápidos

## TURBO_SUJO

Efeito esperado:

- pode dar impulso ao atacante
- pode também gerar algum custo ou impacto colateral

Uso indicado:

- recuperar posição rapidamente

---

# Como interpretar a corrida

Durante a simulação, o sistema geralmente exibe informações como:

- minuto atual
- posição
- nome do carro
- voltas completas
- distância na volta
- combustível restante
- velocidade atual
- situação na corrida
- eventos ocorridos

## Como entender a classificação

A posição dos carros depende do progresso total na corrida.

Esse progresso é calculado com base em:

- voltas completas
- distância percorrida na volta atual

Então, um carro está à frente quando seu avanço total é maior.

## O que significa sair da corrida

Um carro sai da corrida quando não consegue mais continuar, geralmente por:

- falta de combustível

Quando isso acontece:

- ele deixa de disputar
- não pode usar falcatruas
- não evolui mais na corrida

---

## 5. Avaliar

# Como testar o sistema de forma detalhada

## Teste 1: corrida básica sem foco em falcatrua

Objetivo:

- verificar se os carros andam
- verificar se completam voltas
- verificar se existe classificação final

Configuração sugerida:

- pista com 5 km
- 5 voltas
- 3 a 4 carros
- combustível suficiente
- chances de falcatrua mais baixas

Resultado esperado:

- a corrida termina normalmente
- existe pódio
- os carros trocam de posição ao longo do tempo

---

## Teste 2: foco em consumo de combustível

Objetivo:

- verificar se o consumo influencia o resultado

Configuração sugerida:

- dois carros com mesma velocidade máxima
- um com melhor km/l
- outro com pior km/l

Resultado esperado:

- o carro mais econômico deve manter combustível por mais tempo
- o carro menos econômico pode sair antes

---

## Teste 3: foco em aceleração

Objetivo:

- verificar a influência do tempo de 0 a 100

Configuração sugerida:

- dois carros com potência parecida
- um com tempo de 0 a 100 menor
- outro com tempo de 0 a 100 maior

Resultado esperado:

- o carro com melhor aceleração deve ganhar velocidade mais cedo

---

## Teste 4: foco em velocidade máxima

Objetivo:

- verificar o comportamento de carros fortes em pista longa

Configuração sugerida:

- pista maior
- um carro com velocidade máxima muito superior

Resultado esperado:

- depois de alguns minutos, o carro mais rápido deve abrir vantagem

---

## Teste 5: foco em falcatrua com recarga

Objetivo:

- validar se o carro não usa falcatrua em minutos consecutivos

Configuração sugerida:

- poucos carros
- chance de sucesso moderada
- recarga igual a 3

Resultado esperado:

- após usar uma falcatrua, o carro deve esperar antes de poder usar de novo

---

## Teste 6: foco em efeito de bomba

Objetivo:

- verificar se a bomba atrapalha fortemente o alvo

Configuração sugerida:

- um carro com falcatrua BOMBA
- chance alta de sucesso
- intensidade média ou alta

Resultado esperado:

- o carro atingido perde desempenho de forma perceptível

---

## Teste 7: foco em fumaça e consumo

Objetivo:

- verificar aumento de consumo no alvo

Configuração sugerida:

- pista mais longa
- vários minutos de corrida
- carro atacante com FUMACA

Resultado esperado:

- o alvo pode perder combustível mais rapidamente

---

## Teste 8: foco em raio

Objetivo:

- verificar se um carro muito rápido pode ser temporariamente limitado

Configuração sugerida:

- um carro muito veloz
- outro com falcatrua RAIO

Resultado esperado:

- o carro atingido deve ter redução temporária no desempenho máximo

---

# Estratégia de testes recomendada

A melhor forma de testar é variar **um fator por vez**.

Exemplo:

1. primeiro testar corrida básica
2. depois testar combustível
3. depois aceleração
4. depois falcatruas
5. depois balanceamento geral

Isso ajuda a identificar melhor se o sistema está se comportando corretamente.

---

# Sinais de que o sistema está funcionando bem

Você pode considerar o sistema bem validado quando observar:

- os carros mudam de posição ao longo da corrida
- as voltas são contabilizadas corretamente
- a corrida termina nas condições esperadas
- carros ficam sem combustível em cenários extremos
- as falcatruas acontecem de forma controlada
- o cooldown é respeitado
- os efeitos das falcatruas ficam perceptíveis no desempenho

---

# Sinais de que algo pode estar errado

Fique atento se acontecer algo como:

- carro usando falcatrua em todos os minutos
- combustível ficando negativo
- velocidade ultrapassando demais o limite esperado
- carro fora da corrida ainda atacando
- classificação não mudando mesmo com progresso diferente
- corrida sem fim
- voltas não sendo contabilizadas
- efeitos nunca aparecendo ou aparecendo fortes demais

---

## 6. Criar

# Sugestão de roteiro completo de teste manual

## Cenário 1: corrida curta equilibrada

### Pista

- nome: Pista de Teste 1
- tamanho: 5 km
- voltas: 6

### Carro 1

- nome: Falcao GT
- tanque: 50
- combustível atual: 45
- km/l: 8
- potência: 220
- velocidade máxima: 210
- 0 a 100: 8.5
- posição inicial: 1
- falcatrua: OLEO
- recarga: 3
- chance: 50
- intensidade: 15

### Carro 2

- nome: Trovao X
- tanque: 55
- combustível atual: 50
- km/l: 7.5
- potência: 240
- velocidade máxima: 220
- 0 a 100: 7.8
- posição inicial: 2
- falcatrua: BOMBA
- recarga: 4
- chance: 40
- intensidade: 20

### Carro 3

- nome: Vortex R
- tanque: 48
- combustível atual: 48
- km/l: 9
- potência: 210
- velocidade máxima: 205
- 0 a 100: 9.2
- posição inicial: 3
- falcatrua: FUMACA
- recarga: 3
- chance: 60
- intensidade: 10

### Carro 4

- nome: Cometa Z
- tanque: 52
- combustível atual: 46
- km/l: 8.3
- potência: 230
- velocidade máxima: 215
- 0 a 100: 8.0
- posição inicial: 4
- falcatrua: RAIO
- recarga: 4
- chance: 45
- intensidade: 12

## O que observar nesse cenário

- quem assume a liderança nos primeiros minutos
- como as posições mudam
- se as falcatruas aparecem
- se os carros respeitam a recarga
- quem chega melhor ao final

---

# Dicas para avaliar equilíbrio

Se a corrida estiver muito caótica:

- diminua intensidade das falcatruas
- aumente recarga
- reduza chance de sucesso

Se a corrida estiver sem graça:

- aumente chance de sucesso
- reduza recarga
- aumente intensidade de alguns efeitos

Se os carros estiverem acabando combustível cedo demais:

- aumente km/l
- aumente combustível inicial
- reduza pista ou número de voltas

---

# Conclusão

O sistema **Corrida Maluca** permite testar, de forma prática, conceitos importantes de orientação a objetos, simulação e regras de negócio.

Do ponto de vista de uso, a aplicação é uma corrida em console onde:

- o usuário configura pista e carros
- os carros disputam posição
- falcatruas interferem no resultado
- a corrida termina com classificação e pódio

Do ponto de vista de teste, o sistema permite observar:

- aceleração
- consumo
- voltas
- ultrapassagens
- efeitos ofensivos
- balanceamento

---

# Tópicos relacionados

- testes de simulação
- balanceamento de regras
- modelagem orientada a objetos
- composição entre objetos
- encapsulamento
- eventos aleatórios
- jogos de turno e corrida
