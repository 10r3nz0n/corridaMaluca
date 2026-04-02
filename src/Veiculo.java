public class Veiculo {

    private String nome;
    private double combustivelAtual;
    private double capacidadeMaximaTanque;
    private double quilometrosPorLitro;
    private int potencia;
    private double velocidadeMaxima;
    private double velocidadeAtual;
    private double zeroACemSegundos;
    private int posicao;
    private double distanciaNaVolta;
    private int voltasCompletas;
    private double distanciaTotal;
    private boolean ativo;

    public Veiculo(String nome,
                   double capacidadeMaximaTanque,
                   double combustivelAtual,
                   double quilometrosPorLitro,
                   int potencia,
                   double velocidadeMaxima,
                   double zeroACemSegundos,
                   int posicaoInicial) {

        this.nome = nome;
        this.capacidadeMaximaTanque = capacidadeMaximaTanque;
        this.combustivelAtual = combustivelAtual;
        this.quilometrosPorLitro = quilometrosPorLitro;
        this.potencia = potencia;
        this.velocidadeMaxima = velocidadeMaxima;
        this.zeroACemSegundos = zeroACemSegundos;
        this.posicao = posicaoInicial;
        this.velocidadeAtual = 0.0;
        this.distanciaNaVolta = 0.0;
        this.voltasCompletas = 0;
        this.distanciaTotal = 0.0;
        this.ativo = true;

        validarEstadoInicial();
    }

    private void validarEstadoInicial() {
        if (nome == null || nome.trim().isEmpty()) {
            throw new IllegalArgumentException("O nome do veículo é obrigatório.");
        }
        if (capacidadeMaximaTanque <= 0) {
            throw new IllegalArgumentException("A capacidade do tanque deve ser maior que zero.");
        }
        if (combustivelAtual < 0 || combustivelAtual > capacidadeMaximaTanque) {
            throw new IllegalArgumentException("O combustível atual é inválido.");
        }
        if (quilometrosPorLitro <= 0) {
            throw new IllegalArgumentException("O consumo em km/l deve ser maior que zero.");
        }
        if (potencia <= 0) {
            throw new IllegalArgumentException("A potência deve ser maior que zero.");
        }
        if (velocidadeMaxima <= 0) {
            throw new IllegalArgumentException("A velocidade máxima deve ser maior que zero.");
        }
        if (zeroACemSegundos <= 0) {
            throw new IllegalArgumentException("O tempo de 0 a 100 deve ser maior que zero.");
        }
        if (posicao <= 0) {
            throw new IllegalArgumentException("A posição inicial deve ser maior que zero.");
        }
    }

    public void atualizarMinuto(double tamanhoPistaKm) {
        if (!ativo) {
            return;
        }

        atualizarVelocidade();
        double distanciaPercorridaNoMinuto = calcularDistanciaPercorridaNoMinuto();
        double combustivelNecessario = calcularCombustivelNecessario(distanciaPercorridaNoMinuto);

        if (combustivelNecessario > combustivelAtual) {
            distanciaPercorridaNoMinuto = combustivelAtual * quilometrosPorLitro;
            combustivelAtual = 0.0;
            distanciaNaVolta += distanciaPercorridaNoMinuto;
            distanciaTotal += distanciaPercorridaNoMinuto;
            processarVoltas(tamanhoPistaKm);
            ativo = false;
            velocidadeAtual = 0.0;
            return;
        }

        combustivelAtual -= combustivelNecessario;
        distanciaNaVolta += distanciaPercorridaNoMinuto;
        distanciaTotal += distanciaPercorridaNoMinuto;
        processarVoltas(tamanhoPistaKm);
    }

    private void atualizarVelocidade() {
        double ganhoBase = 100.0 / zeroACemSegundos;
        double bonusPotencia = potencia / 200.0;
        double incremento = ganhoBase + bonusPotencia;

        velocidadeAtual += incremento;

        if (velocidadeAtual > velocidadeMaxima) {
            velocidadeAtual = velocidadeMaxima;
        }
    }

    private double calcularDistanciaPercorridaNoMinuto() {
        return velocidadeAtual / 60.0;
    }

    private double calcularCombustivelNecessario(double distanciaKm) {
        return distanciaKm / quilometrosPorLitro;
    }

    private void processarVoltas(double tamanhoPistaKm) {
        while (distanciaNaVolta >= tamanhoPistaKm) {
            distanciaNaVolta -= tamanhoPistaKm;
            voltasCompletas++;
        }
    }

    public double getProgressoTotal(double tamanhoPistaKm) {
        return (voltasCompletas * tamanhoPistaKm) + distanciaNaVolta;
    }

    public String getNome() {
        return nome;
    }

    public double getCombustivelAtual() {
        return combustivelAtual;
    }

    public double getCapacidadeMaximaTanque() {
        return capacidadeMaximaTanque;
    }

    public double getQuilometrosPorLitro() {
        return quilometrosPorLitro;
    }

    public int getPotencia() {
        return potencia;
    }

    public double getVelocidadeMaxima() {
        return velocidadeMaxima;
    }

    public double getVelocidadeAtual() {
        return velocidadeAtual;
    }

    public double getZeroACemSegundos() {
        return zeroACemSegundos;
    }

    public int getPosicao() {
        return posicao;
    }

    public double getDistanciaNaVolta() {
        return distanciaNaVolta;
    }

    public int getVoltasCompletas() {
        return voltasCompletas;
    }

    public double getDistanciaTotal() {
        return distanciaTotal;
    }

    public boolean isAtivo() {
        return ativo;
    }

    public void setPosicao(int posicao) {
        if (posicao <= 0) {
            throw new IllegalArgumentException("A posição deve ser maior que zero.");
        }
        this.posicao = posicao;
    }

    public String getStatus() {
        if (ativo) {
            return "EM CORRIDA";
        }
        return "FORA DA CORRIDA";
    }

    public String gerarResumo() {
        StringBuilder sb = new StringBuilder();
        sb.append("Posição: ").append(posicao);
        sb.append(" | Carro: ").append(nome);
        sb.append(" | Voltas: ").append(voltasCompletas);
        sb.append(" | Distância na volta: ").append(String.format("%.2f", distanciaNaVolta)).append(" km");
        sb.append(" | Combustível: ").append(String.format("%.2f", combustivelAtual)).append(" L");
        sb.append(" | Velocidade atual: ").append(String.format("%.2f", velocidadeAtual)).append(" km/h");
        sb.append(" | Situação: ").append(getStatus());
        return sb.toString();
    }
}
