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

    private String tipoFalcatrua;
    private int tempoRecargaFalcatrua;
    private int minutosRestantesRecarga;
    private int intensidadeFalcatrua;
    private int chanceSucessoFalcatrua;

    private int minutosComVelocidadeReduzida;
    private int minutosComConsumoAumentado;
    private int minutosComAceleracaoReduzida;
    private int minutosComVelocidadeMaximaReduzida;
    private int minutosComTurbo;
    private double penalidadeVelocidadeAtual;
    private String ultimoEfeitoRecebido;

    public Veiculo(String nome,
            double capacidadeMaximaTanque,
            double combustivelAtual,
            double quilometrosPorLitro,
            int potencia,
            double velocidadeMaxima,
            double zeroACemSegundos,
            int posicaoInicial) {

        this(nome,
                capacidadeMaximaTanque,
                combustivelAtual,
                quilometrosPorLitro,
                potencia,
                velocidadeMaxima,
                zeroACemSegundos,
                posicaoInicial,
                "OLEO",
                3,
                12,
                55);
    }

    public Veiculo(String nome,
            double capacidadeMaximaTanque,
            double combustivelAtual,
            double quilometrosPorLitro,
            int potencia,
            double velocidadeMaxima,
            double zeroACemSegundos,
            int posicaoInicial,
            String tipoFalcatrua,
            int tempoRecargaFalcatrua,
            int intensidadeFalcatrua,
            int chanceSucessoFalcatrua) {

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

        this.tipoFalcatrua = normalizarTipoFalcatrua(tipoFalcatrua);
        this.tempoRecargaFalcatrua = tempoRecargaFalcatrua;
        this.minutosRestantesRecarga = 0;
        this.intensidadeFalcatrua = intensidadeFalcatrua;
        this.chanceSucessoFalcatrua = chanceSucessoFalcatrua;

        this.minutosComVelocidadeReduzida = 0;
        this.minutosComConsumoAumentado = 0;
        this.minutosComAceleracaoReduzida = 0;
        this.minutosComVelocidadeMaximaReduzida = 0;
        this.minutosComTurbo = 0;
        this.penalidadeVelocidadeAtual = 0.0;
        this.ultimoEfeitoRecebido = "Nenhum";

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
        if (tempoRecargaFalcatrua < 1) {
            throw new IllegalArgumentException("O tempo de recarga da falcatrua deve ser ao menos 1 minuto.");
        }
        if (intensidadeFalcatrua < 1) {
            throw new IllegalArgumentException("A intensidade da falcatrua deve ser maior que zero.");
        }
        if (chanceSucessoFalcatrua < 1 || chanceSucessoFalcatrua > 100) {
            throw new IllegalArgumentException("A chance de sucesso da falcatrua deve ficar entre 1 e 100.");
        }
    }

    private String normalizarTipoFalcatrua(String tipoFalcatrua) {
        if (tipoFalcatrua == null || tipoFalcatrua.trim().isEmpty()) {
            return "OLEO";
        }

        String normalizada = tipoFalcatrua.trim().toUpperCase();

        if (normalizada.equals("OLEO")
                || normalizada.equals("FUMACA")
                || normalizada.equals("BOMBA")
                || normalizada.equals("COLA")
                || normalizada.equals("RAIO")
                || normalizada.equals("TURBO_SUJO")) {
            return normalizada;
        }

        throw new IllegalArgumentException("Tipo de falcatrua inválido.");
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
            atualizarDuracaoDosEfeitos();
            reduzirRecargaFalcatrua();
            return;
        }

        combustivelAtual -= combustivelNecessario;
        distanciaNaVolta += distanciaPercorridaNoMinuto;
        distanciaTotal += distanciaPercorridaNoMinuto;
        processarVoltas(tamanhoPistaKm);
        atualizarDuracaoDosEfeitos();
        reduzirRecargaFalcatrua();
    }

    private void atualizarVelocidade() {
        double ganhoBase = 100.0 / zeroACemSegundos;
        double bonusPotencia = potencia / 200.0;
        double incremento = ganhoBase + bonusPotencia;

        if (minutosComTurbo > 0) {
            incremento += intensidadeFalcatrua / 2.0;
        }

        if (minutosComAceleracaoReduzida > 0) {
            incremento -= 8.0;
        }

        if (incremento < 0) {
            incremento = 0;
        }

        velocidadeAtual += incremento;

        if (minutosComVelocidadeReduzida > 0) {
            velocidadeAtual -= 10.0;
        }

        if (penalidadeVelocidadeAtual > 0) {
            velocidadeAtual -= penalidadeVelocidadeAtual;
            penalidadeVelocidadeAtual = 0.0;
        }

        double velocidadeMaximaEfetiva = getVelocidadeMaximaEfetiva();

        if (velocidadeAtual < 0) {
            velocidadeAtual = 0.0;
        }

        if (velocidadeAtual > velocidadeMaximaEfetiva) {
            velocidadeAtual = velocidadeMaximaEfetiva;
        }
    }

    private double getVelocidadeMaximaEfetiva() {
        if (minutosComVelocidadeMaximaReduzida > 0) {
            double reducao = intensidadeFalcatrua * 0.8;
            double velocidadeReduzida = velocidadeMaxima - reducao;
            if (velocidadeReduzida < 40.0) {
                return 40.0;
            }
            return velocidadeReduzida;
        }
        return velocidadeMaxima;
    }

    private double calcularDistanciaPercorridaNoMinuto() {
        return velocidadeAtual / 60.0;
    }

    private double calcularCombustivelNecessario(double distanciaKm) {
        double combustivel = distanciaKm / quilometrosPorLitro;

        if (minutosComConsumoAumentado > 0) {
            combustivel = combustivel * 1.35;
        }

        if (minutosComTurbo > 0 && tipoFalcatrua.equals("TURBO_SUJO")) {
            combustivel = combustivel * 1.20;
        }

        return combustivel;
    }

    private void processarVoltas(double tamanhoPistaKm) {
        while (distanciaNaVolta >= tamanhoPistaKm) {
            distanciaNaVolta -= tamanhoPistaKm;
            voltasCompletas++;
        }
    }

    private void atualizarDuracaoDosEfeitos() {
        if (minutosComVelocidadeReduzida > 0) {
            minutosComVelocidadeReduzida--;
        }
        if (minutosComConsumoAumentado > 0) {
            minutosComConsumoAumentado--;
        }
        if (minutosComAceleracaoReduzida > 0) {
            minutosComAceleracaoReduzida--;
        }
        if (minutosComVelocidadeMaximaReduzida > 0) {
            minutosComVelocidadeMaximaReduzida--;
        }
        if (minutosComTurbo > 0) {
            minutosComTurbo--;
        }

        if (minutosComVelocidadeReduzida == 0
                && minutosComConsumoAumentado == 0
                && minutosComAceleracaoReduzida == 0
                && minutosComVelocidadeMaximaReduzida == 0
                && minutosComTurbo == 0) {
            ultimoEfeitoRecebido = "Nenhum";
        }
    }

    public boolean podeUsarFalcatrua() {
        return ativo && minutosRestantesRecarga == 0;
    }

    public void iniciarRecargaFalcatrua() {
        this.minutosRestantesRecarga = tempoRecargaFalcatrua;
    }

    public void reduzirRecargaFalcatrua() {
        if (minutosRestantesRecarga > 0) {
            minutosRestantesRecarga--;
        }
    }

    public boolean falcatruaAcertou() {
        int sorteio = (int) (Math.random() * 100) + 1;
        return sorteio <= chanceSucessoFalcatrua;
    }

    public void aplicarEfeitoFalcatruaNoAlvo(Veiculo alvo) {
        if (alvo == null) {
            return;
        }

        if (tipoFalcatrua.equals("OLEO")) {
            alvo.aplicarReducaoVelocidade(2, intensidadeFalcatrua);
        } else if (tipoFalcatrua.equals("FUMACA")) {
            alvo.aplicarAumentoConsumo(3, intensidadeFalcatrua);
        } else if (tipoFalcatrua.equals("BOMBA")) {
            alvo.aplicarBomba(1, intensidadeFalcatrua);
        } else if (tipoFalcatrua.equals("COLA")) {
            alvo.aplicarReducaoAceleracao(3, intensidadeFalcatrua);
        } else if (tipoFalcatrua.equals("RAIO")) {
            alvo.aplicarReducaoVelocidadeMaxima(3, intensidadeFalcatrua);
        } else if (tipoFalcatrua.equals("TURBO_SUJO")) {
            this.aplicarTurbo(2, intensidadeFalcatrua);
            alvo.aplicarReducaoVelocidade(1, intensidadeFalcatrua / 2);
        }
    }

    public void aplicarReducaoVelocidade(int minutos, int intensidade) {
        if (minutos > 0) {
            this.minutosComVelocidadeReduzida = minutos;
            this.ultimoEfeitoRecebido = "Velocidade reduzida";
            this.penalidadeVelocidadeAtual += intensidade;
        }
    }

    public void aplicarAumentoConsumo(int minutos, int intensidade) {
        if (minutos > 0) {
            this.minutosComConsumoAumentado = minutos;
            this.ultimoEfeitoRecebido = "Consumo aumentado";
            this.penalidadeVelocidadeAtual += intensidade / 3.0;
        }
    }

    public void aplicarBomba(int minutos, int intensidade) {
        if (minutos > 0) {
            this.minutosComVelocidadeReduzida = minutos;
            this.ultimoEfeitoRecebido = "Bomba recebida";
            this.penalidadeVelocidadeAtual += intensidade * 1.5;
        }
    }

    public void aplicarReducaoAceleracao(int minutos, int intensidade) {
        if (minutos > 0) {
            this.minutosComAceleracaoReduzida = minutos;
            this.ultimoEfeitoRecebido = "Aceleração reduzida";
            this.penalidadeVelocidadeAtual += intensidade / 4.0;
        }
    }

    public void aplicarReducaoVelocidadeMaxima(int minutos, int intensidade) {
        if (minutos > 0) {
            this.minutosComVelocidadeMaximaReduzida = minutos;
            this.ultimoEfeitoRecebido = "Velocidade máxima reduzida";
            this.penalidadeVelocidadeAtual += intensidade / 5.0;
        }
    }

    public void aplicarTurbo(int minutos, int intensidade) {
        if (minutos > 0) {
            this.minutosComTurbo = minutos;
            this.ultimoEfeitoRecebido = "Turbo ativado";
            this.penalidadeVelocidadeAtual -= intensidade / 4.0;
            if (this.penalidadeVelocidadeAtual < 0) {
                this.penalidadeVelocidadeAtual = 0.0;
            }
        }
    }

    public String getDescricaoFalcatrua() {
        return tipoFalcatrua + " | recarga: " + tempoRecargaFalcatrua
                + " | intensidade: " + intensidadeFalcatrua
                + " | chance: " + chanceSucessoFalcatrua + "%";
    }

    public String getNomeFalcatruaFormatado() {
        if (tipoFalcatrua.equals("TURBO_SUJO")) {
            return "TURBO SUJO";
        }
        return tipoFalcatrua;
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

    public String getTipoFalcatrua() {
        return tipoFalcatrua;
    }

    public int getTempoRecargaFalcatrua() {
        return tempoRecargaFalcatrua;
    }

    public int getMinutosRestantesRecarga() {
        return minutosRestantesRecarga;
    }

    public int getIntensidadeFalcatrua() {
        return intensidadeFalcatrua;
    }

    public int getChanceSucessoFalcatrua() {
        return chanceSucessoFalcatrua;
    }

    public String getUltimoEfeitoRecebido() {
        return ultimoEfeitoRecebido;
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
        sb.append(" | Falcatrua: ").append(getNomeFalcatruaFormatado());
        sb.append(" | Recarga: ").append(minutosRestantesRecarga);
        sb.append(" | Efeito: ").append(ultimoEfeitoRecebido);
        sb.append(" | Situação: ").append(getStatus());
        return sb.toString();
    }
}
