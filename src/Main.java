import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class Main {
    public static void main(String[] args) {
        // Inicializar la tabla hash con una capacidad inicial
        int initialCapacity = 1000;
        HashTable<String, String[]> hashTable1 = new HashTable<>(initialCapacity);
        HashTable<String, String[]> hashTable2 = new HashTable<>(initialCapacity);

        String line = "";
        String splitBy = ",";
        List<String[]> businessData = new ArrayList<>();

        try {
            BufferedReader br = new BufferedReader(new FileReader("bussines.csv"));
            while ((line = br.readLine()) != null) {
                String[] business = line.split(splitBy);
                businessData.add(business);
            }
            br.close(); 
        } catch (IOException e) {
            e.printStackTrace();
        }

        System.out.println("ingreese un ");
      
        // Medir la eficiencia de inserción con los datos leídos
        measureInsertionEfficiency(hashTable1, businessData, 1);
        measureInsertionEfficiency(hashTable2, businessData, 2);


        measureExtractionEfficiency(hashTable2, businessData, 1);
        measureExtractionEfficiency(hashTable2, businessData, 2);
    }

    private static void measureInsertionEfficiency(HashTable<String, String[]> hashTable, List<String[]> businessData, int hashFunction) {
        long startTime = System.nanoTime();
        for (String[] business : businessData) {
            String key = business[0];
            String[] value = { business[1], business[2], business[3], business[4] };
            hashTable.put(key, value, hashFunction);
        }
        long endTime = System.nanoTime();
        long duration = endTime - startTime;
        System.out.println("Tiempo de inserción con hashFunction" + hashFunction + ": " + duration + " nanosegundos");
      
    }

    private static void measureExtractionEfficiency(HashTable<String, String[]> hashTable, List<String[]> businessData, int hashFunction) {
        long startTime = System.nanoTime();
        for (String[] business : businessData) {
            String key = business[0];
            hashTable.get(key, hashFunction);
        }
        long endTime = System.nanoTime();
        long duration = endTime - startTime;
        System.out.println("Tiempo de extracción con hashFunction" + hashFunction + ": " + duration + " nanosegundos");
    }
}
