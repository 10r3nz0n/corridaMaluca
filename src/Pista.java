import java.util.ArrayList;

import utilitarios.Video;

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

        Video.exibirBarraProgresso(100, 20);

        processarFalcatruasAleatorias();

        for (int i = 0; i < veiculos.size(); i++) {
            Veiculo veiculo = veiculos.get(i);
            veiculo.atualizarMinuto(tamanhoKm);
        }

        atualizarPosicoes();
        verificarFimDaCorrida();
        exibirParcialDoMinuto();
    }

    private void processarFalcatruasAleatorias() {
        if (veiculos.size() < 2) {
            return;
        }

        int chanceEvento = (int) (Math.random() * 100) + 1;
        if (chanceEvento > 38) {
            return;
        }

        Veiculo atacante = sortearVeiculoAtivoComFalcatruaDisponivel();
        if (atacante == null) {
            return;
        }

        Veiculo alvo = sortearOutroVeiculoAtivo(atacante);
        if (alvo == null) {
            return;
        }

        System.out.println();
        System.out.println("*** EVENTO DE FALCATRUA ***");

        if (atacante.falcatruaAcertou()) {
            atacante.aplicarEfeitoFalcatruaNoAlvo(alvo);
            atacante.iniciarRecargaFalcatrua();
            exibirMensagemFalcatrua(atacante, alvo);
        } else {
            atacante.iniciarRecargaFalcatrua();
            System.out.println(atacante.getNome() + " tentou usar "
                    + atacante.getNomeFalcatruaFormatado()
                    + " contra " + alvo.getNome() + ", mas falhou.");
        }
    }

    private void exibirMensagemFalcatrua(Veiculo atacante, Veiculo alvo) {
        String tipo = atacante.getTipoFalcatrua();

        if (tipo.equals("OLEO")) {
            System.out.println(atacante.getNome() + " lançou óleo na pista contra " + alvo.getNome() + ".");
        } else if (tipo.equals("FUMACA")) {
            System.out.println(atacante.getNome() + " soltou fumaça e prejudicou o consumo de " + alvo.getNome() + ".");
        } else if (tipo.equals("BOMBA")) {
            System.out.println(atacante.getNome() + " lançou uma bomba e abalou o motor de " + alvo.getNome() + ".");
        } else if (tipo.equals("COLA")) {
            System.out.println(
                    atacante.getNome() + " espalhou cola na pista e travou a aceleração de " + alvo.getNome() + ".");
        } else if (tipo.equals("RAIO")) {
            System.out.println(atacante.getNome() + " disparou um raio desregulador contra " + alvo.getNome() + ".");
        } else if (tipo.equals("TURBO_SUJO")) {
            System.out.println(atacante.getNome() + " ativou um turbo sujo e ainda atrapalhou " + alvo.getNome() + ".");
        }
    }

    private Veiculo sortearVeiculoAtivoComFalcatruaDisponivel() {
        ArrayList<Veiculo> candidatos = new ArrayList<Veiculo>();

        for (int i = 0; i < veiculos.size(); i++) {
            Veiculo atual = veiculos.get(i);
            if (atual.podeUsarFalcatrua()) {
                candidatos.add(atual);
            }
        }

        if (candidatos.isEmpty()) {
            return null;
        }

        int indice = (int) (Math.random() * candidatos.size());
        return candidatos.get(indice);
    }

    private Veiculo sortearOutroVeiculoAtivo(Veiculo atacante) {
        ArrayList<Veiculo> candidatos = new ArrayList<Veiculo>();

        for (int i = 0; i < veiculos.size(); i++) {
            Veiculo atual = veiculos.get(i);
            if (atual.isAtivo() && atual != atacante) {
                candidatos.add(atual);
            }
        }

        if (candidatos.isEmpty()) {
            return null;
        }

        int indice = (int) (Math.random() * candidatos.size());
        return candidatos.get(indice);
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
                System.out.println("   Falcatrua configurada: " + veiculos.get(i).getDescricaoFalcatrua());
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
