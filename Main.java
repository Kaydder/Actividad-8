import java.util.ArrayList;
import java.util.InputMismatchException;
import java.util.Scanner;

// Interfaz para operaciones
interface Operacion {
    double calcular();
    String obtenerDescripcion();
}

// Clase base abstracta para operaciones matemáticas básicas
abstract class OperacionBinaria implements Operacion {
    protected double numero1;
    protected double numero2;

    public OperacionBinaria(double numero1, double numero2) {
        this.numero1 = numero1;
        this.numero2 = numero2;
    }
}

class Suma extends OperacionBinaria {
    public Suma(double numero1, double numero2) {
        super(numero1, numero2);
    }

    @Override
    public double calcular() {
        return numero1 + numero2;
    }

    @Override
    public String obtenerDescripcion() {
        return numero1 + " + " + numero2 + " = " + calcular();
    }
}

class Resta extends OperacionBinaria {
    public Resta(double numero1, double numero2) {
        super(numero1, numero2);
    }

    @Override
    public double calcular() {
        return numero1 - numero2;
    }

    @Override
    public String obtenerDescripcion() {
        return numero1 + " - " + numero2 + " = " + calcular();
    }
}

class Multiplicacion extends OperacionBinaria {
    public Multiplicacion(double numero1, double numero2) {
        super(numero1, numero2);
    }

    @Override
    public double calcular() {
        return numero1 * numero2;
    }

    @Override
    public String obtenerDescripcion() {
        return numero1 + " * " + numero2 + " = " + calcular();
    }
}

class Division extends OperacionBinaria {
    public Division(double numero1, double numero2) {
        super(numero1, numero2);
    }

    @Override
    public double calcular() {
        if (numero2 == 0) throw new ArithmeticException("División por cero");
        return numero1 / numero2;
    }

    @Override
    public String obtenerDescripcion() {
        return numero1 + " / " + numero2 + " = " + calcular();
    }
}

class Potencia implements Operacion {
    private double base;
    private int exponente;

    public Potencia(double base, int exponente) {
        this.base = base;
        this.exponente = exponente;
    }

    @Override
    public double calcular() {
        return calcularRecursivo(base, exponente);
    }

    private double calcularRecursivo(double base, int exponente) {
        if (exponente == 0) return 1;
        if (exponente < 0) return 1 / calcularRecursivo(base, -exponente);
        return base * calcularRecursivo(base, exponente - 1);
    }

    @Override
    public String obtenerDescripcion() {
        return base + " ^ " + exponente + " = " + calcular();
    }
}

class RaizCuadrada implements Operacion {
    private double numero;

    public RaizCuadrada(double numero) {
        this.numero = numero;
    }

    @Override
    public double calcular() {
        if (numero < 0) throw new ArithmeticException("Raíz de número negativo");
        return Math.sqrt(numero);
    }

    @Override
    public String obtenerDescripcion() {
        return "√" + numero + " = " + calcular();
    }
}

public class Main {
    public static void main(String[] args) {
        Scanner entrada = new Scanner(System.in);
        ArrayList<String> historial = new ArrayList<>();
        boolean continuar = true;

        while (continuar) {
            try {
                System.out.println("\nSeleccione una operación matemática:");
                System.out.println("1. Suma");
                System.out.println("2. Resta");
                System.out.println("3. Multiplicación");
                System.out.println("4. División");
                System.out.println("5. Potencia");
                System.out.println("6. Raíz Cuadrada");
                System.out.println("7. Ver Historial");
                System.out.println("8. Salir");
                System.out.print("Opción: ");
                int opcion = entrada.nextInt();

                if (opcion == 8) break;

                Operacion operacion = null;

                switch (opcion) {
                    case 1 -> {
                        double[] nums = leerDosNumeros(entrada);
                        operacion = new Suma(nums[0], nums[1]);
                    }
                    case 2 -> {
                        double[] nums = leerDosNumeros(entrada);
                        operacion = new Resta(nums[0], nums[1]);
                    }
                    case 3 -> {
                        double[] nums = leerDosNumeros(entrada);
                        operacion = new Multiplicacion(nums[0], nums[1]);
                    }
                    case 4 -> {
                        double[] nums = leerDosNumeros(entrada);
                        operacion = new Division(nums[0], nums[1]);
                    }
                    case 5 -> {
                        System.out.print("Ingrese la base: ");
                        double base = entrada.nextDouble();
                        System.out.print("Ingrese el exponente: ");
                        int exponente = entrada.nextInt();
                        operacion = new Potencia(base, exponente);
                    }
                    case 6 -> {
                        System.out.print("Ingrese el número: ");
                        double numero = entrada.nextDouble();
                        operacion = new RaizCuadrada(numero);
                    }
                    case 7 -> {
                        mostrarHistorial(historial);
                        continue;
                    }
                    default -> {
                        System.out.println("Opción inválida.");
                        continue;
                    }
                }

                if (operacion != null) {
                    String descripcion = operacion.obtenerDescripcion();
                    historial.add(descripcion);
                    System.out.println(descripcion);
                }
            } catch (InputMismatchException e) {
                System.out.println("Error: Entrada inválida. Intente de nuevo.");
                entrada.next();
            } catch (ArithmeticException e) {
                System.out.println("Error: " + e.getMessage());
            }
        }

        System.out.println("¡Hasta luego!");
        entrada.close();
    }

    public static double[] leerDosNumeros(Scanner entrada) {
        System.out.print("Ingrese el primer número: ");
        double num1 = entrada.nextDouble();
        System.out.print("Ingrese el segundo número: ");
        double num2 = entrada.nextDouble();
        return new double[]{num1, num2};
    }

    public static void mostrarHistorial(ArrayList<String> historial) {
        System.out.println("\nHistorial de operaciones:");
        historial.forEach(System.out::println);
    }
}
