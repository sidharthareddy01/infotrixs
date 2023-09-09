import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Scanner;

public class CurrencyConverter {
    private static final Map<String, Double> exchangeRates = new HashMap<>();
    private static final List<String> availableCurrencies = new ArrayList<>();
    private static final List<String> favoriteConversions = new ArrayList<>();

    static {

        availableCurrencies.add("USD");
        availableCurrencies.add("EUR");
        availableCurrencies.add("GBP");
        availableCurrencies.add("INR");

        exchangeRates.put("USD", 1.0);
        exchangeRates.put("EUR", 0.85);
        exchangeRates.put("GBP", 0.73);
        exchangeRates.put("INR", 74.52);
    }

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        boolean continueConversion = true;

        while (continueConversion) {
            System.out.println("Welcome to the Comprehensive Currency Converter!");
            System.out.println("Available currencies: USD, EUR, GBP, INR");

            System.out.print("Enter base currency: ");
            String baseCurrency = scanner.nextLine().toUpperCase();

            System.out.print("Enter target currency: ");
            String targetCurrency = scanner.nextLine().toUpperCase();

            if (!availableCurrencies.contains(baseCurrency) || !availableCurrencies.contains(targetCurrency)) {
                System.out.println("Invalid currency selection.");
                continue;
            }

            System.out.print("Enter amount to convert: ");
            double amountToConvert = getValidAmount(scanner);

            double exchangeRate = fetchExchangeRate(baseCurrency, targetCurrency);
            if (exchangeRate == -1.0) {
                System.out.println("Failed to fetch exchange rate data. Please try again later.");
                continue;
            }

            double convertedAmount = amountToConvert * exchangeRate;

            System.out.printf("%.2f %s is equivalent to %.2f %s\n",
                    amountToConvert, baseCurrency, convertedAmount, targetCurrency);

            System.out.print("Do you want to save this conversion as a favorite? (yes/no): ");
            String favoriteChoice = scanner.next();
            if (favoriteChoice.equalsIgnoreCase("yes")) {
                saveFavoriteConversion(baseCurrency, targetCurrency);
            }

            System.out.print("Do you want to perform another conversion? (yes/no): ");
            String userInput = scanner.next();
            if (userInput.equalsIgnoreCase("no")) {
                continueConversion = false;
            }
            scanner.nextLine();
        }

        System.out.println("Thank you for using the Comprehensive Currency Converter!");
        scanner.close();
    }

    private static double getValidAmount(Scanner scanner) {
        double amount = -1.0;
        boolean validInput = false;

        while (!validInput) {
            try {
                amount = scanner.nextDouble();
                validInput = true;
            } catch (Exception e) {
                System.out.println("Invalid input. Please enter a valid numeric amount.");
                scanner.next();
            }
        }

        return amount;
    }

    private static double fetchExchangeRate(String baseCurrency, String targetCurrency) {
        if (baseCurrency.equals(targetCurrency)) {
            return 1.0;
        } else if (exchangeRates.containsKey(baseCurrency) && exchangeRates.containsKey(targetCurrency)) {
            return exchangeRates.get(targetCurrency) / exchangeRates.get(baseCurrency);
        }
        return -1.0;
    }

    private static void saveFavoriteConversion(String baseCurrency, String targetCurrency) {
        String favorite = baseCurrency + " to " + targetCurrency;
        if (!favoriteConversions.contains(favorite)) {
            favoriteConversions.add(favorite);
            System.out.println("Conversion saved as favorite: " + favorite);
        } else {
            System.out.println("This conversion is already a favorite.");
        }
    }
}