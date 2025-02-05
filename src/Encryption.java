import java.util.Set;
import java.util.HashMap;

public class Encryption {

    public static void main(String[] args) {
        int shift = 8;
        String encryptedCeasar = encryptCeasar("What a beautiful wedding", shift);
        System.out.println(encryptedCeasar);
        String encryptedMessage = encryptPlayfair("I want candy", "Chocolate");
        String decryptedCeasar = decryptCeasar(encryptedCeasar, shift);
        System.out.println(decryptedCeasar);
        int[][] matrix_key = { { 5, 17 }, { 8, 3 } };
        String encryptedHill = encryptHill("I Want Candy", matrix_key);
        System.out.println(encryptedHill);
        String decryptedHill = decryptHill(encryptedHill, matrix_key);
        System.out.println(decryptedHill);

    }

    public static String encryptPlayfair(String plainText, String key) {
        char[] alphabet_list = { 'a', 'b', 'c', 'd', 'e', 'f', 'g', 'h', 'i', 'j', 'k', 'l', 'm', 'n', 'o', 'p', 'q',
                'r', 's', 't', 'u', 'v', 'w', 'x', 'y', 'z' };
        String[][] grid = new String[5][5];
        char[] charOrder = key.toCharArray();
        return "";
    }

    public static String encryptCeasar(String plainText, int shift) {
        char[] alphabet_list = { 'a', 'b', 'c', 'd', 'e', 'f', 'g', 'h', 'i', 'j', 'k', 'l', 'm', 'n', 'o', 'p', 'q',
                'r', 's', 't', 'u', 'v', 'w', 'x', 'y', 'z' };
        HashMap<Character, Character> shiftedChars = new HashMap<Character, Character>();
        plainText = plainText.toLowerCase();
        for (int i = 0; i < alphabet_list.length; i++) {
            if (i - shift < 0) {
                shiftedChars.put(alphabet_list[i], alphabet_list[(alphabet_list.length + i) - shift]);
            } else {
                shiftedChars.put(alphabet_list[i], alphabet_list[i - shift]);
            }
        }
        String encryptedText = "";
        for (int i = 0; i < plainText.length(); i++) {
            if (!Character.isLetter(plainText.charAt(i))) {
                encryptedText += plainText.charAt(i);
            } else {
                encryptedText += shiftedChars.get(plainText.charAt(i));
            }
        }
        return encryptedText;
    }

    public static String decryptCeasar(String plainText, int shift) {
        char[] alphabet_list = { 'a', 'b', 'c', 'd', 'e', 'f', 'g', 'h', 'i', 'j', 'k', 'l', 'm', 'n', 'o', 'p', 'q',
                'r', 's', 't', 'u', 'v', 'w', 'x', 'y', 'z' };
        HashMap<Character, Character> shiftedChars = new HashMap<Character, Character>();
        plainText = plainText.toLowerCase();
        for (int i = 0; i < alphabet_list.length; i++) {
            if (i + shift >= alphabet_list.length) {
                shiftedChars.put(alphabet_list[i], alphabet_list[(i + shift) - alphabet_list.length]);
            } else {
                shiftedChars.put(alphabet_list[i], alphabet_list[i + shift]);
            }
        }
        String decryptedText = "";
        for (int i = 0; i < plainText.length(); i++) {
            if (!Character.isLetter(plainText.charAt(i))) {
                decryptedText += plainText.charAt(i);
            } else {
                decryptedText += shiftedChars.get(plainText.charAt(i));
            }
        }
        return decryptedText;
    }

    public static String encryptHill(String plainText, int[][] key) {
        HashMap<Character, Integer> charMap = new HashMap<>();
        char[] alphabetList = "abcdefghijklmnopqrstuvwxyz".toCharArray();

        for (int i = 0; i < alphabetList.length; i++) {
            charMap.put(alphabetList[i], i);
        }

        // Ensure lowercase and remove spaces
        plainText = plainText.toLowerCase().replaceAll("\\s+", "");

        // If plainText length is odd, add a padding character ('x')
        if (plainText.length() % 2 == 1) {
            plainText += 'x';
        }

        // Map characters to integers
        int[] matrixArr = new int[plainText.length()];
        for (int i = 0; i < plainText.length(); i++) {
            matrixArr[i] = charMap.get(plainText.charAt(i));
        }

        // Group into pairs
        int[][] matrixGroupArr = new int[matrixArr.length / 2][2];
        int matrix_i = 0;
        for (int i = 0; i < matrixGroupArr.length; i++) {
            for (int j = 0; j < 2; j++) {
                matrixGroupArr[i][j] = matrixArr[matrix_i++];
            }
        }

        // Encrypt using the key matrix
        StringBuilder encryptedText = new StringBuilder();
        for (int i = 0; i < matrixGroupArr.length; i++) {
            int[] vector = new int[2];
            vector[0] = (key[0][0] * matrixGroupArr[i][0] + key[0][1] * matrixGroupArr[i][1]) % 26;
            vector[1] = (key[1][0] * matrixGroupArr[i][0] + key[1][1] * matrixGroupArr[i][1]) % 26;

            encryptedText.append(alphabetList[vector[0]]).append(alphabetList[vector[1]]);
        }

        return encryptedText.toString();
    }

    public static String decryptHill(String cipherText, int[][] key) {
        HashMap<Character, Integer> charMap = new HashMap<>();
        char[] alphabetList = "abcdefghijklmnopqrstuvwxyz".toCharArray();

        // Create character-to-number map
        for (int i = 0; i < alphabetList.length; i++) {
            charMap.put(alphabetList[i], i);
        }

        // Calculate determinant and modular inverse
        int determinant = (key[0][0] * key[1][1] - key[0][1] * key[1][0]) % 26;
        determinant = (determinant + 26) % 26; // Ensure positive determinant
        int detInverse = -1;

        // Find modular inverse of the determinant
        for (int i = 0; i < 26; i++) {
            if ((determinant * i) % 26 == 1) {
                detInverse = i;
                break;
            }
        }
        if (detInverse == -1)
            throw new IllegalArgumentException("Matrix key is not invertible!");

        // Compute inverse key matrix
        int[][] inverseKey = new int[2][2];
        inverseKey[0][0] = (key[1][1] * detInverse) % 26;
        inverseKey[0][1] = (-key[0][1] * detInverse + 26) % 26;
        inverseKey[1][0] = (-key[1][0] * detInverse + 26) % 26;
        inverseKey[1][1] = (key[0][0] * detInverse) % 26;

        // Preprocess cipherText
        cipherText = cipherText.toLowerCase().replaceAll("\\s+", ""); // Remove spaces and ensure lowercase

        // Check if padding is required
        if (cipherText.length() % 2 == 1) {
            cipherText += 'x'; // Add padding character
        }

        // Map cipherText to numbers
        int[] matrixArr = new int[cipherText.length()];
        for (int i = 0; i < cipherText.length(); i++) {
            if (!charMap.containsKey(cipherText.charAt(i))) {
                throw new IllegalArgumentException("Invalid character in cipherText: " + cipherText.charAt(i));
            }
            matrixArr[i] = charMap.get(cipherText.charAt(i));
        }

        // Group into pairs
        int[][] matrixGroupArr = new int[matrixArr.length / 2][2];
        int matrix_i = 0;
        for (int i = 0; i < matrixGroupArr.length; i++) {
            for (int j = 0; j < 2; j++) {
                matrixGroupArr[i][j] = matrixArr[matrix_i++];
            }
        }

        // Decrypt using the inverse key matrix
        StringBuilder decryptedText = new StringBuilder();
        for (int i = 0; i < matrixGroupArr.length; i++) {
            int[] vector = new int[2];
            vector[0] = (inverseKey[0][0] * matrixGroupArr[i][0] + inverseKey[0][1] * matrixGroupArr[i][1]) % 26;
            vector[1] = (inverseKey[1][0] * matrixGroupArr[i][0] + inverseKey[1][1] * matrixGroupArr[i][1]) % 26;

            // Ensure positive results
            vector[0] = (vector[0] + 26) % 26;
            vector[1] = (vector[1] + 26) % 26;

            decryptedText.append(alphabetList[vector[0]]).append(alphabetList[vector[1]]);
        }

        return decryptedText.toString();
    }

    public static String encryptVigenere(String plainText, String key) {
        String encryptedText = "";
        plainText = plainText.toLowerCase().strip();
        char[] alphabet_list = { 'a', 'b', 'c', 'd', 'e', 'f', 'g', 'h', 'i', 'j', 'k', 'l', 'm', 'n', 'o', 'p', 'q',
                'r', 's', 't', 'u', 'v', 'w', 'x', 'y', 'z' };
        HashMap<String, Character> vGrid = new HashMap<String, Character>();
        for (int row = 0; row < alphabet_list.length; row++) {
            for (int col = 0; col < alphabet_list.length; col++) {
                String charCombo = alphabet_list[row] + "" + alphabet_list[col];
                if (row + col >= alphabet_list.length) {
                    vGrid.put(charCombo, alphabet_list[(row + col) - alphabet_list.length]);
                } else {
                    vGrid.put(charCombo, alphabet_list[row + col]);
                }
            }
        }
        int keyPtr = 0;
        for (int i = 0; i < plainText.length(); i++) {
            if (keyPtr >= key.length()) {
                keyPtr = 0;
            }
            String currCombo = plainText.charAt(i) + "" + key.charAt(keyPtr++);
            encryptedText += vGrid.get(currCombo);
        }
        return encryptedText;
    }
}