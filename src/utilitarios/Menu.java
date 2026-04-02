package utilitarios;

import java.util.ArrayList;

public class Menu {
    private String titulo;
    private ArrayList<String> opcoesMenu;

    public Menu(String titulo, ArrayList<String> opcoesMenu) {
        this.titulo = titulo;
        this.opcoesMenu = opcoesMenu;
    }

    public int exibir() {
        Video.exibirCabecalho(titulo);
        Video.println("");

        for (int i = 0; i < opcoesMenu.size(); i++) {
            Video.println((i + 1) + " - " + opcoesMenu.get(i));
        }

        Video.println("");

        int opcaoEscolhida;
        do {
            opcaoEscolhida = Teclado.integer("Qual a sua opcao:");
            if (opcaoEscolhida < 1 || opcaoEscolhida > opcoesMenu.size()) {
                Video.exibirMensagemAlerta("Opcao invalida, tente novamente!");
            }
        } while (opcaoEscolhida < 1 || opcaoEscolhida > opcoesMenu.size());

        return opcaoEscolhida;
    }
}
