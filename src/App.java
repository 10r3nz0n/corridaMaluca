import menus.MenuPrincipal;
import utilitarios.Teclado;
import utilitarios.Video;

public class App {

    private static Pista pista;

    public static void main(String[] args) {
        executar();
    }

    private static void executar() {
        boolean continuar = true;

        while (continuar) {
            int opcao = MenuPrincipal.exibir();

            switch (opcao) {
                case 1:
                    configurarPista();
                    break;
                case 2:
                    cadastrarVeiculo();
                    break;
                case 3:
                    listarVeiculos();
                    break;
                case 4:
                    carregarExemploAutomatico();
                    break;
                case 5:
                    iniciarCorrida();
                    break;
                case 6:
                    continuar = false;
                    Video.exibirMensagemOk("Programa encerrado.");
                    break;
                default:
                    Video.exibirMensagemErro("Opção inválida.");
            }

            if (continuar) {
                Video.pausarEnterContinuar();
            }
        }
    }

    private static void configurarPista() {
        Video.exibirCabecalho("Configuração da pista");

        String nome = Teclado.string("Nome da pista:");
        double tamanhoKm = lerDoublePositivo("Tamanho da pista em km:");
        int totalVoltas = lerIntPositivo("Quantidade de voltas da corrida:");

        pista = new Pista(nome, tamanhoKm, totalVoltas);
        Video.exibirMensagemOk("Pista configurada com sucesso.");
    }

    private static void cadastrarVeiculo() {
        if (!validarPistaConfigurada()) {
            return;
        }

        Video.exibirCabecalho("Cadastro de veículo");

        String nome = Teclado.string("Nome do veículo:");
        double capacidadeTanque = lerDoublePositivo("Capacidade máxima do tanque:");
        double combustivelAtual = lerDoubleIntervalo("Combustível atual:", 0.0, capacidadeTanque);
        double quilometrosPorLitro = lerDoublePositivo("Quantos km por litro ele faz:");
        int potencia = lerIntPositivo("Potência do veículo:");
        double velocidadeMaxima = lerDoublePositivo("Velocidade máxima:");
        double zeroACem = lerDoublePositivo("Tempo de 0 a 100 km/h em segundos:");
        String tipoFalcatrua = lerTipoFalcatrua();
        int tempoRecarga = lerIntPositivo("Tempo de recarga da falcatrua em minutos:");
        int intensidadeFalcatrua = lerIntPositivo("Intensidade da falcatrua:");
        int chanceSucessoFalcatrua = lerIntIntervalo("Chance de sucesso da falcatrua (1 a 100):", 1, 100);
        int posicaoInicial = pista.getVeiculos().size() + 1;

        try {
            Veiculo veiculo = new Veiculo(
                nome,
                capacidadeTanque,
                combustivelAtual,
                quilometrosPorLitro,
                potencia,
                velocidadeMaxima,
                zeroACem,
                posicaoInicial,
                tipoFalcatrua,
                tempoRecarga,
                intensidadeFalcatrua,
                chanceSucessoFalcatrua
            );

            pista.adicionarVeiculo(veiculo);
            Video.exibirMensagemOk("Veículo cadastrado com sucesso na posição " + posicaoInicial + ".");
        } catch (IllegalArgumentException e) {
            Video.exibirMensagemErro(e.getMessage());
        }
    }

    private static String lerTipoFalcatrua() {
        Video.exibirMensagemAlerta("Tipos de falcatrua disponíveis: OLEO, FUMACA, BOMBA, COLA, RAIO, TURBO_SUJO.");
        while (true) {
            String tipo = Teclado.string("Digite o tipo de falcatrua do veículo:");
            if (tipo != null) {
                String tipoMaiusculo = tipo.trim().toUpperCase();
                if (tipoMaiusculo.equals("OLEO")
                        || tipoMaiusculo.equals("FUMACA")
                        || tipoMaiusculo.equals("BOMBA")
                        || tipoMaiusculo.equals("COLA")
                        || tipoMaiusculo.equals("RAIO")
                        || tipoMaiusculo.equals("TURBO_SUJO")) {
                    return tipoMaiusculo;
                }
            }
            Video.exibirMensagemAlerta("Tipo inválido. Escolha um dos tipos apresentados.");
        }
    }

    private static void listarVeiculos() {
        if (!validarPistaConfigurada()) {
            return;
        }

        Video.exibirCabecalho("Listagem de veículos");
        System.out.println("Pista: " + pista.getNome());
        System.out.println("Tamanho: " + pista.getTamanhoKm() + " km");
        System.out.println("Voltas: " + pista.getTotalVoltasCorrida());
        pista.exibirParticipantes();
    }

    private static void carregarExemploAutomatico() {
        pista = new Pista("Autódromo Central", 5.0, 10);
        pista.adicionarVeiculo(new Veiculo("Falcao GT", 50.0, 45.0, 8.0, 220, 210.0, 8.5, 1, "OLEO", 3, 12, 60));
        pista.adicionarVeiculo(new Veiculo("Trovao X", 55.0, 50.0, 7.5, 240, 220.0, 7.8, 2, "BOMBA", 4, 18, 45));
        pista.adicionarVeiculo(new Veiculo("Vortex R", 48.0, 48.0, 9.0, 210, 205.0, 9.2, 3, "FUMACA", 3, 10, 65));
        pista.adicionarVeiculo(new Veiculo("Cometa Z", 52.0, 46.0, 8.3, 230, 215.0, 8.0, 4, "TURBO_SUJO", 5, 16, 50));
        Video.exibirMensagemOk("Exemplo carregado com sucesso.");
    }

    private static void iniciarCorrida() {
        if (!validarPistaConfigurada()) {
            return;
        }

        if (!pista.possuiVeiculosSuficientes()) {
            Video.exibirMensagemAlerta("Cadastre ao menos dois veículos antes de iniciar a corrida.");
            return;
        }

        Video.exibirCabecalho("Início da corrida");
        try {
            pista.iniciarCorrida();
        } catch (IllegalStateException e) {
            Video.exibirMensagemErro(e.getMessage());
        }
    }

    private static boolean validarPistaConfigurada() {
        if (pista == null) {
            Video.exibirMensagemAlerta("Configure a pista primeiro.");
            return false;
        }
        return true;
    }

    private static int lerIntPositivo(String mensagem) {
        int valor;
        do {
            valor = Teclado.integer(mensagem);
            if (valor <= 0) {
                Video.exibirMensagemAlerta("Digite um valor inteiro maior que zero.");
            }
        } while (valor <= 0);
        return valor;
    }

    private static int lerIntIntervalo(String mensagem, int minimo, int maximo) {
        int valor;
        do {
            valor = Teclado.integer(mensagem);
            if (valor < minimo || valor > maximo) {
                Video.exibirMensagemAlerta("Digite um valor entre " + minimo + " e " + maximo + ".");
            }
        } while (valor < minimo || valor > maximo);
        return valor;
    }

    private static double lerDoublePositivo(String mensagem) {
        double valor;
        do {
            valor = Teclado.decimal(mensagem);
            if (valor <= 0) {
                Video.exibirMensagemAlerta("Digite um valor maior que zero.");
            }
        } while (valor <= 0);
        return valor;
    }

    private static double lerDoubleIntervalo(String mensagem, double minimo, double maximo) {
        double valor;
        do {
            valor = Teclado.decimal(mensagem);
            if (valor < minimo || valor > maximo) {
                Video.exibirMensagemAlerta("Digite um valor entre " + minimo + " e " + maximo + ".");
            }
        } while (valor < minimo || valor > maximo);
        return valor;
    }
}
