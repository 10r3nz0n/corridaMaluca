package menus;

import java.util.ArrayList;
import utilitarios.Menu;

public class MenuPrincipal {

    public static int exibir() {
        ArrayList<String> opcoes = new ArrayList<String>();
        opcoes.add("Configurar nova pista");
        opcoes.add("Cadastrar veículo");
        opcoes.add("Listar veículos cadastrados");
        opcoes.add("Carregar exemplo automático");
        opcoes.add("Iniciar corrida");
        opcoes.add("Sair");

        Menu menu = new Menu("Corrida Maluca - Menu principal", opcoes);
        return menu.exibir();
    }
}
