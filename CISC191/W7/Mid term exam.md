# Mid term exam
## Inventory control system - version 1.0

``` java
import java.util.Scanner;

// ===== PARENT CLASS =====
class Item {
    protected String name;
    protected String companyName;

    public Item(String name, String companyName) {
        this.name = name;
        this.companyName = companyName;
    }

    // Display method
    public void display() {
        System.out.println("Item: " + name + ", Company: " + companyName);
    }

    // Overloaded update method
    public void update(String name) {
        this.name = name;
        System.out.println("Item name updated to: " + name);
    }

    public void update(String name, String companyName) {
        this.name = name;
        this.companyName = companyName;
        System.out.println("Item and company updated.");
    }
}

// ===== DERIVED CLASS 1 =====
class Painkiller extends Item {
    private String expiryDate;
    private String ageGroup;

    public Painkiller(String name, String companyName, String expiryDate, String ageGroup) {
        super(name, companyName);
        this.expiryDate = expiryDate;
        this.ageGroup = ageGroup;
    }

    @Override
    public void display() {
        System.out.println("Painkiller: " + name + ", Company: " + companyName +
                           ", Expiry: " + expiryDate + ", Age Group: " + ageGroup);
    }
}

// ===== DERIVED CLASS 2 =====
class Bandage extends Item {
    private String expiryDate;
    private String ageGroup;
    private boolean waterproof;

    public Bandage(String name, String companyName, String expiryDate, String ageGroup, boolean waterproof) {
        super(name, companyName);
        this.expiryDate = expiryDate;
        this.ageGroup = ageGroup;
        this.waterproof = waterproof;
    }

    @Override
    public void display() {
        System.out.println("Bandage: " + name + ", Company: " + companyName +
                           ", Expiry: " + expiryDate + ", Age Group: " + ageGroup +
                           ", Waterproof: " + (waterproof ? "Yes" : "No"));
    }
}

// ===== DERIVED CLASS 3 =====
class Equipment extends Item {
    private double weight;

    public Equipment(String name, String companyName, double weight) {
        super(name, companyName);
        this.weight = weight;
    }

    @Override
    public void display() {
        System.out.println("Equipment: " + name + ", Company: " + companyName +
                           ", Weight: " + weight + " lbs");
    }
}

// ===== MAIN CLASS =====
public class InventorySystemV1 {
    public static void main(String[] args) {
        Painkiller p = new Painkiller("Advil", "Pfizer", "12/2026", "Adult");
        Bandage b = new Bandage("Band-Aid", "Johnson & Johnson", "10/2027", "All Ages", true);
        Equipment e = new Equipment("Thermometer", "Omron", 1.2);

        p.display();
        b.display();
        e.display();

        p.update("Ibuprofen");
        p.display();
    }
}
```

Example Output:

```yaml
Painkiller: Advil, Company: Pfizer, Expiry: 12/2026, Age Group: Adult
Bandage: Band-Aid, Company: Johnson & Johnson, Expiry: 10/2027, Age Group: All Ages, Waterproof: Yes
Equipment: Thermometer, Company: Omron, Weight: 1.2 lbs
Item name updated to: Ibuprofen
Painkiller: Ibuprofen, Company: Pfizer, Expiry: 12/2026, Age Group: Adult
```

## Inventory control system - version 2.0

``` java
import java.util.*;

public class InventorySystemV2 {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);

        try {
            System.out.print("Enter equipment weight (lbs): ");
            double weight = Double.parseDouble(sc.nextLine());
            if (weight <= 0) throw new IllegalArgumentException("Weight must be positive.");

            Equipment eq = new Equipment("Thermometer", "Omron", weight);
            eq.display();
        } 
        catch (NumberFormatException e) {
            System.out.println("Error: Please enter a valid numeric value for weight.");
        } 
        catch (IllegalArgumentException e) {
            System.out.println("Error: " + e.getMessage());
        }
    }
}
```

Example Output

Valid Input:
```yaml
Enter equipment weight (lbs): 2.5
Equipment: Thermometer, Company: Omron, Weight: 2.5 lbs
```
Invalid Input (non-numeric):
```pgsql
Enter equipment weight (lbs): abc
Error: Please enter a valid numeric value for weight.
```
Invalid Input (negative number):
```java
Enter equipment weight (lbs): -4
Error: Weight must be positive.
```

## Inventory control system - version 3.0

``` java
// Custom Exception
class InvalidAgeGroupException extends Exception {
    public InvalidAgeGroupException(String message) {
        super(message);
    }
}

import java.util.Scanner;

public class InventorySystemV3 {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);

        try {
            System.out.print("Enter age group for painkiller (Adult/Child): ");
            String ageGroup = sc.nextLine();

            if (!ageGroup.equalsIgnoreCase("Adult") && !ageGroup.equalsIgnoreCase("Child")) {
                throw new InvalidAgeGroupException("Invalid age group entered. Only 'Adult' or 'Child' allowed.");
            }

            Painkiller p = new Painkiller("Advil", "Pfizer", "12/2026", ageGroup);
            p.display();
        } 
        catch (InvalidAgeGroupException e) {
            System.out.println("Custom Exception: " + e.getMessage());
        }
    }
}
```
Example Output

Valid Input:
```yaml
Enter age group for painkiller (Adult/Child): Adult
Painkiller: Advil, Company: Pfizer, Expiry: 12/2026, Age Group: Adult
```
Invalid Input:
```pgsql
Enter age group for painkiller (Adult/Child): Teen
Custom Exception: Invalid age group entered. Only 'Adult' or 'Child' allowed.
```
