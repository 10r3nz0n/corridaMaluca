import java.util.ArrayList;

public class Pista {

    private String nome;
    private double tamanhoKm;
    private int totalVoltasCorrida;
    private ArrayList<Veiculo> veiculos;
    private int tempoDecorridoMinutos;
    private boolean corridaEncerrada;

    public Pista(String nome, double tamanhoKm, int totalVoltasCorrida) {
        this.nome = nome;
        this.tamanhoKm = tamanhoKm;
        this.totalVoltasCorrida = totalVoltasCorrida;
        this.veiculos = new ArrayList<Veiculo>();
        this.tempoDecorridoMinutos = 0;
        this.corridaEncerrada = false;

        validarEstadoInicial();
    }

    private void validarEstadoInicial() {
        if (nome == null || nome.trim().isEmpty()) {
            throw new IllegalArgumentException("O nome da pista é obrigatório.");
        }
        if (tamanhoKm <= 0) {
            throw new IllegalArgumentException("O tamanho da pista deve ser maior que zero.");
        }
        if (totalVoltasCorrida <= 0) {
            throw new IllegalArgumentException("A quantidade de voltas deve ser maior que zero.");
        }
    }

    public void adicionarVeiculo(Veiculo veiculo) {
        if (veiculo == null) {
            throw new IllegalArgumentException("O veículo não pode ser nulo.");
        }
        veiculos.add(veiculo);
        atualizarPosicoes();
    }

    public boolean possuiVeiculosSuficientes() {
        return veiculos.size() >= 2;
    }

    public void iniciarCorrida() {
        if (veiculos.isEmpty()) {
            throw new IllegalStateException("Não há veículos para iniciar a corrida.");
        }
        if (veiculos.size() < 2) {
            throw new IllegalStateException("Cadastre ao menos dois veículos para iniciar a corrida.");
        }

        exibirEstadoInicial();

        while (!corridaEncerrada) {
            avancarUmMinuto();
        }

        exibirResultadoFinal();
    }

    private void avancarUmMinuto() {
        tempoDecorridoMinutos++;

        for (int i = 0; i < veiculos.size(); i++) {
            Veiculo veiculo = veiculos.get(i);
            veiculo.atualizarMinuto(tamanhoKm);
        }

        atualizarPosicoes();
        verificarFimDaCorrida();
        exibirParcialDoMinuto();
    }

    private void atualizarPosicoes() {
        for (int i = 0; i < veiculos.size() - 1; i++) {
            for (int j = i + 1; j < veiculos.size(); j++) {
                Veiculo atual = veiculos.get(i);
                Veiculo proximo = veiculos.get(j);

                double progressoAtual = atual.getProgressoTotal(tamanhoKm);
                double progressoProximo = proximo.getProgressoTotal(tamanhoKm);

                if (progressoProximo > progressoAtual) {
                    veiculos.set(i, proximo);
                    veiculos.set(j, atual);
                }
            }
        }

        for (int i = 0; i < veiculos.size(); i++) {
            veiculos.get(i).setPosicao(i + 1);
        }
    }

    private void verificarFimDaCorrida() {
        boolean todosInativos = true;

        for (int i = 0; i < veiculos.size(); i++) {
            Veiculo veiculo = veiculos.get(i);

            if (veiculo.getVoltasCompletas() >= totalVoltasCorrida) {
                corridaEncerrada = true;
                return;
            }

            if (veiculo.isAtivo()) {
                todosInativos = false;
            }
        }

        if (todosInativos) {
            corridaEncerrada = true;
        }
    }

    private void exibirEstadoInicial() {
        System.out.println("==============================================");
        System.out.println("INÍCIO DA CORRIDA");
        System.out.println("Pista: " + nome);
        System.out.println("Tamanho da pista: " + tamanhoKm + " km");
        System.out.println("Total de voltas: " + totalVoltasCorrida);
        System.out.println("Grid de largada:");
        for (int i = 0; i < veiculos.size(); i++) {
            System.out.println(veiculos.get(i).gerarResumo());
        }
        System.out.println("==============================================");
    }

    private void exibirParcialDoMinuto() {
        System.out.println();
        System.out.println("Minuto " + tempoDecorridoMinutos);
        for (int i = 0; i < veiculos.size(); i++) {
            System.out.println(veiculos.get(i).gerarResumo());
        }
    }

    private void exibirResultadoFinal() {
        System.out.println();
        System.out.println("==============================================");
        System.out.println("FIM DA CORRIDA");
        System.out.println("Tempo total: " + tempoDecorridoMinutos + " minuto(s)");
        System.out.println("Classificação final:");
        for (int i = 0; i < veiculos.size(); i++) {
            System.out.println(veiculos.get(i).gerarResumo());
        }

        System.out.println();
        System.out.println("PÓDIO");
        for (int i = 0; i < veiculos.size() && i < 3; i++) {
            System.out.println((i + 1) + "º lugar: " + veiculos.get(i).getNome());
        }
        System.out.println("==============================================");
    }

    public void exibirParticipantes() {
        System.out.println("==============================================");
        System.out.println("PARTICIPANTES CADASTRADOS");
        if (veiculos.isEmpty()) {
            System.out.println("Nenhum veículo cadastrado.");
        } else {
            for (int i = 0; i < veiculos.size(); i++) {
                System.out.println(veiculos.get(i).gerarResumo());
            }
        }
        System.out.println("==============================================");
    }

    public String getNome() {
        return nome;
    }

    public double getTamanhoKm() {
        return tamanhoKm;
    }

    public int getTotalVoltasCorrida() {
        return totalVoltasCorrida;
    }

    public int getTempoDecorridoMinutos() {
        return tempoDecorridoMinutos;
    }

    public boolean isCorridaEncerrada() {
        return corridaEncerrada;
    }

    public ArrayList<Veiculo> getVeiculos() {
        return new ArrayList<Veiculo>(veiculos);
    }
}
