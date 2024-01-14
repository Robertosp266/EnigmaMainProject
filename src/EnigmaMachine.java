public class EnigmaMachine {
    private Rotor rotor1;
    private Rotor rotor2;
    private Rotor rotor3;
    private Reflector reflector;

    public EnigmaMachine() {
        // Ustawienia początkowe dla rotorów i reflectora
        rotor1 = new Rotor("EKMFLGDQVZNTOWYHXUSPAIBRCJ", 'Q');
        rotor2 = new Rotor("AJDKSIRUXBLHWTMCQGZNPYFVOE", 'E');
        rotor3 = new Rotor("BDFHJLCPRTXVZNYEIWGAKMUSQO", 'V');
        reflector = new Reflector("YRUHQSLDPXNGOKMIEBFZCWVJAT");
    }

    public String encrypt(String input) {
        StringBuilder output = new StringBuilder();

        for (char letter : input.toUpperCase().toCharArray()) {
            if (Character.isAlphabetic(letter)) {
                // Wprowadzenie litery do maszyny Enigma
                letter = rotor1.processLetter(letter);
                letter = rotor2.processLetter(letter);
                letter = rotor3.processLetter(letter);
                letter = reflector.reflect(letter);
                letter = rotor3.processLetterBackward(letter);
                letter = rotor2.processLetterBackward(letter);
                letter = rotor1.processLetterBackward(letter);

                // Zapisanie zaszyfrowanej litery
                output.append(letter);
            } else {
                // Zachowanie znaków niealfabetycznych
                output.append(letter);
            }

            // Obrót rotorów przed każdym nowym znakiem
            rotor1.rotate();
            if (rotor1.getRotation() % 26 == 0) {
                rotor2.rotate();
                if (rotor2.getRotation() % 26 == 0) {
                    rotor3.rotate();
                }
            }
        }

        return output.toString();
    }

    public static void main(String[] args) {
        EnigmaMachine enigmaMachine = new EnigmaMachine();
        String message = "HELLOWORLD";
        String encryptedMessage = enigmaMachine.encrypt(message);
        System.out.println("Original: " + message);
        System.out.println("Encrypted: " + encryptedMessage);
    }
}

class Rotor {
    private String wiring;
    private char notch;
    private int rotation;

    public Rotor(String wiring, char notch) {
        this.wiring = wiring;
        this.notch = notch;
        this.rotation = 0;
    }

    public char processLetter(char input) {
        int index = (input - 'A' + rotation) % 26;
        char output = wiring.charAt(index);
        return (char) ((output - 'A' - rotation + 26) % 26 + 'A');
    }

    public char processLetterBackward(char input) {
        int index = (input - 'A' + rotation) % 26;
        char output = (char) (wiring.indexOf((char) ('A' + index)) + 'A');
        return (char) ((output - 'A' - rotation + 26) % 26 + 'A');
    }

    public void rotate() {
        rotation = (rotation + 1) % 26;
    }

    public int getRotation() {
        return rotation;
    }
}

class Reflector {
    private String wiring;

    public Reflector(String wiring) {
        this.wiring = wiring;
    }

    public char reflect(char input) {
        int index = input - 'A';
        return wiring.charAt(index);
    }
}