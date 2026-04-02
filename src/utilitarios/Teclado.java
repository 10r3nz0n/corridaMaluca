package utilitarios;

import java.lang.reflect.Method;
import java.util.Scanner;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;

public class Teclado {

    private static final Scanner scanner = new Scanner(System.in);
    private static final DateTimeFormatter DATE_FORMAT = DateTimeFormatter.ofPattern("yyyy-MM-dd");

    public static int solicitarInt() {
        while (true) {
            String entrada = scanner.nextLine().trim();

            if (entrada.isEmpty()) {
                System.out.println("Entrada vazia! Digite um numero inteiro.");
                continue;
            }

            try {
                return Integer.parseInt(entrada);
            } catch (NumberFormatException e) {
                System.out.println("Entrada invalida! Digite um numero inteiro.");
            }
        }
    }

    public static int solicitarInt(String mensagem) {
        System.out.print(mensagem + " ");
        return solicitarInt();
    }

    public static int integer() {
        return solicitarInt();
    }

    public static int integer(String mensagem) {
        return solicitarInt(mensagem);
    }

    public static String solicitarString() {
        while (true) {
            String valor = scanner.nextLine().trim();
            if (!valor.isEmpty()) {
                return valor;
            }
            System.out.println("Entrada vazia! Digite novamente.");
        }
    }

    public static String solicitarString(String mensagem) {
        System.out.print(mensagem + " ");
        return solicitarString();
    }

    public static String string(String mensagem) {
        return solicitarString(mensagem);
    }

    public static String string() {
        return solicitarString();
    }

    public static double solicitarDouble() {
        while (true) {
            String entrada = scanner.nextLine().trim();

            if (entrada.isEmpty()) {
                System.out.println("Entrada vazia! Digite um numero decimal.");
                continue;
            }

            try {
                return Double.parseDouble(entrada);
            } catch (NumberFormatException e) {
                System.out.println("Entrada invalida! Digite um numero decimal.");
            }
        }
    }

    public static double solicitarDouble(String mensagem) {
        System.out.print(mensagem + " ");
        return solicitarDouble();
    }

    public static double decimal(String mensagem) {
        return solicitarDouble(mensagem);
    }

    public static double decimal() {
        return solicitarDouble();
    }

    public static LocalDate solicitarData() {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");

        while (true) {
            String entrada = solicitarString();
            try {
                return LocalDate.parse(entrada, formatter);
            } catch (DateTimeParseException e) {
                System.out.println("Data invalida! Use o formato dd/MM/yyyy");
            }
        }
    }

    public static LocalDate solicitarData(String mensagem) {
        System.out.print(mensagem + " ");
        return solicitarData();
    }

    public static <T> T solicitar(String mensagem, Class<T> tipo) {
        System.out.print(mensagem + " ");
        return solicitar(tipo);
    }

    public static <T> T solicitar(Class<T> tipo) {
        while (true) {
            String entrada = scanner.nextLine().trim();

            try {
                if (tipo == String.class) {
                    if (entrada.isEmpty()) {
                        throw new IllegalArgumentException("Entrada vazia! Digite novamente.");
                    }
                    return tipo.cast(entrada);
                }

                if (tipo == LocalDate.class) {
                    return tipo.cast(LocalDate.parse(entrada, DATE_FORMAT));
                }

                Method parseMethod = encontrarMetodoConversao(tipo);
                if (parseMethod != null) {
                    Object valorConvertido = parseMethod.invoke(null, entrada);
                    return tipo.cast(valorConvertido);
                }

                throw new IllegalArgumentException("Tipo nao suportado: " + tipo.getSimpleName());

            } catch (NumberFormatException e) {
                System.out.println("Entrada invalida! Esperado um valor do tipo " + tipo.getSimpleName() + ".");
            } catch (DateTimeParseException e) {
                System.out.println("Data invalida! Use o formato " + DATE_FORMAT);
            } catch (IllegalArgumentException e) {
                System.out.println(e.getMessage());
            } catch (Exception e) {
                System.out.println("Erro ao converter valor: " + e.getMessage());
            }
        }
    }

    private static Method encontrarMetodoConversao(Class<?> tipo) {
        try {
            return tipo.getMethod("valueOf", String.class);
        } catch (NoSuchMethodException e) {
            try {
                return tipo.getMethod("parse" + tipo.getSimpleName(), String.class);
            } catch (NoSuchMethodException ex) {
                return null;
            }
        }
    }
}