import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.sql.*;

public class AutoDbLoader {

    private static final String DB_URL = "jdbc:mysql://localhost:3306/Auto?useSSL=false&serverTimezone=UTC";
    private static final String DB_USER = "root";
    private static final String DB_PASS = "root123"; 

    // Dataset location inside the repo
    private static final String DATA_FILE = "data/auto-mpg.data-original";

    public static void main(String[] args) {
        String insertSql =
                "INSERT INTO AutoData (mpg, cylinders, displacement, horsepower, weight, acceleration, model_year, origin, car_name) " +
                "VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?)";

        int inserted = 0;
        int skipped = 0;

        try (Connection conn = DriverManager.getConnection(DB_URL, DB_USER, DB_PASS);
             PreparedStatement ps = conn.prepareStatement(insertSql);
             BufferedReader br = new BufferedReader(new FileReader(DATA_FILE))) {

            conn.setAutoCommit(false);

            String line;
            int batchCount = 0;

            while ((line = br.readLine()) != null) {
                line = line.trim();
                if (line.isEmpty()) continue;

                // The file is whitespace-separated (tabs/spaces). This handles both.
                String[] parts = line.split("\\s+");
                if (parts.length < 9) {
                    skipped++;
                    continue;
                }

                try {
                    double mpg = Double.parseDouble(parts[0]);
                    int cylinders = Integer.parseInt(parts[1]);
                    double displacement = Double.parseDouble(parts[2]);

                    // horsepower can be "?"
                    if (parts[3].equals("?")) {
                        skipped++;
                        continue;
                    }
                    double horsepower = Double.parseDouble(parts[3]);

                    int weight = Integer.parseInt(parts[4]);
                    double acceleration = Double.parseDouble(parts[5]);
                    int modelYear = Integer.parseInt(parts[6]);
                    int origin = Integer.parseInt(parts[7]);

                    // car name is the rest of the line
                    StringBuilder name = new StringBuilder();
                    for (int i = 8; i < parts.length; i++) {
                        if (i > 8) name.append(" ");
                        name.append(parts[i]);
                    }
                    String carName = name.toString();

                    ps.setDouble(1, mpg);
                    ps.setInt(2, cylinders);
                    ps.setDouble(3, displacement);
                    ps.setDouble(4, horsepower);
                    ps.setInt(5, weight);
                    ps.setDouble(6, acceleration);
                    ps.setInt(7, modelYear);
                    ps.setInt(8, origin);
                    ps.setString(9, carName);

                    ps.addBatch();
                    batchCount++;

                    if (batchCount >= 200) {
                        int[] res = ps.executeBatch();
                        conn.commit();
                        inserted += countInserted(res);
                        batchCount = 0;
                    }

                } catch (NumberFormatException ex) {
                    skipped++;
                }
            }

            // remaining batch
            if (batchCount > 0) {
                int[] res = ps.executeBatch();
                conn.commit();
                inserted += countInserted(res);
            }

            System.out.println("Insert complete.");
            System.out.println("Inserted rows: " + inserted);
            System.out.println("Skipped rows: " + skipped);

        } catch (SQLException e) {
            System.out.println("SQL error: " + e.getMessage());
        } catch (IOException e) {
            System.out.println("File error: " + e.getMessage());
        }
    }

    private static int countInserted(int[] res) {
        int total = 0;
        for (int r : res) {
            if (r > 0) total += r;
        }
        return total;
    }
}
