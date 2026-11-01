import java.security.SecureRandom;
import java.util.Scanner; 

public class PasswordGenerator { 
    static final SecureRandom random = new SecureRandom(); 
    static final String lower = "abcdefghijklmnopqrstuvwxyz"; 
    static final String upper = "ABCDEFGHIJKLMNOPQRSTUVWXYZ"; 
    static final String numbers = "0123456789"; 
    static final String symbols = "!@#$%^&*"; 

    // Generate a random password 
    static String generatePassword(int length) { 
        String all = lower + upper + numbers + symbols; 
        StringBuilder password = new StringBuilder(); 

        // Include at least one character from each category 
        password.append(lower.charAt(random.nextInt(lower.length()))); 
        password.append(upper.charAt(random.nextInt(upper.length()))); 
        password.append(numbers.charAt(random.nextInt(numbers.length()))); 
        password.append(symbols.charAt(random.nextInt(symbols.length()))); 

        // Fill the remaining length 
        for (int i = 4; i < length; i++) { 
            password.append(all.charAt(random.nextInt(all.length()))); 
        } 

        // Fisher-Yates Shuffle 
        for (int i = password.length() - 1; i > 0; i--) { 
            int j = random.nextInt(i + 1); 
            char temp = password.charAt(i); 
            password.setCharAt(i, password.charAt(j)); 
            password.setCharAt(j, temp); 
        } 
        return password.toString(); 
    } 

    // Check password strength accurately
    static String checkStrength(String password) { 
        boolean hasLower = false; 
        boolean hasUpper = false; 
        boolean hasDigit = false; 
        boolean hasSymbol = false; 

        for (int i = 0; i < password.length(); i++) { 
            char ch = password.charAt(i); 
            if (Character.isLowerCase(ch)) hasLower = true; 
            else if (Character.isUpperCase(ch)) hasUpper = true; 
            else if (Character.isDigit(ch)) hasDigit = true; 
            else if (symbols.indexOf(ch) != -1) hasSymbol = true; // Explicitly validates against allowed symbols
        } 

        int score = 0; 
        if (password.length() >= 8) score++; 
        if (password.length() >= 12) score++; 
        if (hasLower) score++; 
        if (hasUpper) score++; 
        if (hasDigit) score++; 
        if (hasSymbol) score++; 

        if (score >= 5) return "Strong"; 
        else if (score >= 3) return "Medium"; 
        else return "Weak"; 
    } 

    public static void main(String[] args) { 
        Scanner sc = new Scanner(System.in); 
        int choice=0; 
        
        do { 
            System.out.println("\n=============================="); 
            System.out.println(" SMART PASSWORD GENERATOR"); 
            System.out.println("=============================="); 
            System.out.println("1. Generate Password"); 
            System.out.println("2. Check Password Strength"); 
            System.out.println("3. Exit"); 
            System.out.print("Enter your choice: "); 
            
            if (!sc.hasNextInt()) { 
                System.out.println("Please enter a number."); 
                sc.nextLine(); 
                continue; 
            } 
            choice = sc.nextInt(); 
            sc.nextLine(); 

            switch (choice) { 
                case 1: 
                    System.out.print("Enter password length (4-50): "); 
                    if (!sc.hasNextInt()) { 
                        System.out.println("Invalid length."); 
                        sc.nextLine(); 
                        break; 
                    } 
                    int length = sc.nextInt(); 
                    sc.nextLine(); 
                    
                    if (length < 4 || length > 50) { 
                        System.out.println("Length must be between 4 and 50."); 
                    } else { 
                        String password = generatePassword(length); 
                        System.out.println("Generated Password: " + password); 
                        System.out.println("Strength: " + checkStrength(password)); 
                    } 
                    break; 
                case 2: 
                    System.out.print("Enter a password to check: "); 
                    String input = sc.nextLine(); 
                    System.out.println("Password Strength: " + checkStrength(input)); 
                    break; 
                case 3: 
                    System.out.println("Thank you for using the application!"); 
                    break; 
                default: 
                    System.out.println("Invalid choice. Try again."); 
            } 
        } while (choice != 3); 
        sc.close(); 
    } 
}
