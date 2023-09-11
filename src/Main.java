import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;

public class Main {
    private static final Map<String, Integer> RM = new HashMap<>();

    static {
        RM.put("I", 1);
        RM.put("II", 2);
        RM.put("III", 3);
        RM.put("IV", 4);
        RM.put("V", 5);
        RM.put("VI", 6);
        RM.put("VII", 7);
        RM.put("VIII", 8);
        RM.put("IX", 9);
        RM.put("X", 10);
    }

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        System.out.println("Введите арифметическое выражение (например, '3 + 2'): ");
        String input = scanner.nextLine();

        try {
            String result = calc(input);
            System.out.println("Результат: " + result);
        } catch (IllegalArgumentException | ArithmeticException e) {
            System.err.println("Ошибка: " + e.getMessage());
        }

        scanner.close();
    }

    public static String calc(String input) {
        String[] parts = input.split(" ");
        if (parts.length != 3) {
            throw new Error("Неправильный формат ввода");
        }

        String operand1 = parts[0];
        String operator = parts[1];
        String operand2 = parts[2];

        int num1, num2;
        boolean isRM = false;

        // Проверка и преобразование операндов
        if (isRM(operand1) && isRM(operand2)) {
            num1 = romanToArabic(operand1);
            num2 = romanToArabic(operand2);
            isRM = true;
        } else if (isNumeric(operand1) && isNumeric(operand2)) {
            num1 = Integer.parseInt(operand1);
            num2 = Integer.parseInt(operand2);
        } else {
            throw new IllegalArgumentException("Операнды должны быть либо арабскими, либо римскими числами");
        }

        int result;
        switch (operator) {
            case "+":
                result = num1 + num2;
                break;
            case "-":
                result = num1 - num2;
                break;
            case "*":
                result = num1 * num2;
                break;
            case "/":
                if (num2 == 0) {
                    throw new ArithmeticException("Деление на ноль запрещено");
                }
                result = num1 / num2;
                break;
            default:
                throw new IllegalArgumentException("Неподдерживаемая операция: " + operator);
        }

        if (isRM) {
            if (result <= 0) {
                throw new IllegalArgumentException("Результат вычисления с римскими числами должен быть положительным");
            }
            return arabicToRoman(result);
        } else {
            return String.valueOf(result);
        }
    }

    private static boolean isRM(String operand1) {
        return false;
    }

    private static boolean isRomanNumber(String input) {
        return RM.containsKey(input);
    }

    private static int romanToArabic(String input) {
        return RM.get(input);
    }

    private static boolean isNumeric(String input) {
        try {
            Integer.parseInt(input);
            return true;
        } catch (NumberFormatException e) {
            return false;
        }
    }

    private static String arabicToRoman(int number) {
        if (number < 1 || number > 10) {
            throw new IllegalArgumentException("Римские числа поддерживаются только для чисел от 1 до 10");
        }

        for (Map.Entry<String, Integer> entry : RM.entrySet()) {
            if (entry.getValue() == number) {
                return entry.getKey();
            }
        }

        return "";
    }
}
