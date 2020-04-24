package converter;

import converter.exceptions.MalformedNumberException;
import converter.exceptions.ValueOutOfBoundsException;

import java.util.ArrayList;
import java.util.HashMap;

/**
 * This class implements a converter that takes a string that represents a number in either the
 * Elbonian or Arabic numeral form. This class has methods that will return a value in the chosen form.
 *
 * @version 3/18/17
 */
public class ElbonianArabicConverter {

    // A string that holds the number (Elbonian or Arabic) you would like to convert
    private final String number;
    ArrayList<Character> validCharacters = new ArrayList<>();
    ArrayList<Character> singleCharacters = new ArrayList<>();
    ArrayList<Character> doubleCharacters = new ArrayList<>();
    HashMap<Character, Integer> characterValues = new HashMap<>();
    HashMap<Character, Integer> pointsRemaining = new HashMap<>();
    private Type type;

    /**
     * Constructor for the ElbonianArabic class that takes a string. The string should contain a valid
     * Elbonian or Arabic numeral. The String can have leading or trailing spaces. But there should be no
     * spaces within the actual number (ie. "9 9" is not ok, but " 99 " is ok). If the String is an Arabic
     * number it should be checked to make sure it is within the Elbonian number systems bounds. If the
     * number is Elbonian, it must be a valid Elbonian representation of a number.
     *
     * @param number A string that represents either a Elbonian or Arabic number.
     * @throws MalformedNumberException  Thrown if the value is an Elbonian number that does not conform
     *                                   to the rules of the Elbonian number system. Leading and trailing spaces should not throw an error.
     * @throws ValueOutOfBoundsException Thrown if the value is an Arabic number that cannot be represented
     *                                   in the Elbonian number system.
     */
    public ElbonianArabicConverter(String number) throws MalformedNumberException, ValueOutOfBoundsException {
        fillWithValidElbonian(validCharacters);
        fillWithSingleCharacters(singleCharacters);
        fillWithDoubleCharacters(doubleCharacters);
        fillWithCharacterValues(characterValues);
        fillWithPointsRemaining(pointsRemaining);
        // TODO check to see if the number is valid, then set it equal to the string
        // Check if the string is a valid Arabic numeral
        String trimmedString = number.trim();
        if(trimmedString.length() > 0) {
            if (checkArabic(trimmedString)) {
                this.type = Type.ARABIC;
            } else {
                checkElbonian(trimmedString);
                this.type = Type.ELBONIAN;
            }
            this.number = trimmedString;
        } else {
            throw new MalformedNumberException("String was empty or had no content");
        }
    }

    private void fillWithPointsRemaining(HashMap<Character, Integer> array) {
        array.put('M', 2000);
        array.put('C', 200);
        array.put('D', 300);
        array.put('E', 600);
        array.put('X', 20);
        array.put('Y', 30);
        array.put('Z', 60);
        array.put('I', 2);
        array.put('J', 3);
        array.put('K', 6);
    }

    private void fillWithCharacterValues(HashMap<Character, Integer> array) {
        array.put('M', 1000);
        array.put('C', 100);
        array.put('D', 300);
        array.put('E', 600);
        array.put('X', 10);
        array.put('Y', 30);
        array.put('Z', 60);
        array.put('I', 1);
        array.put('J', 3);
        array.put('K', 6);
    }

    private boolean checkArabic(String string) throws ValueOutOfBoundsException {
        try {
            int arabicRepresentation = Integer.parseInt(string);
            if (arabicRepresentation < 1 || arabicRepresentation > 2999) {
                throw new ValueOutOfBoundsException("Arabic numeral was out of Elbonian number bounds");
            }
        } catch (NumberFormatException e) {
            return false;
        }
        return true;
    }

    private void checkElbonian(String string) throws MalformedNumberException {
        if (!inOrderAndLegalCharacters(string) || !areLegalCount(string) || !areLegalPair(string)) {
            throw new MalformedNumberException("Elbonian number does not conform to the rules of the Elbonian number system");
        }
    }

    private boolean inOrderAndLegalCharacters(String string) {
        int lastIndex = -1;
        for (char c : string.toCharArray()) {
            if (validCharacters.contains(c)) {
                // Was a valid Elbonian character
                int currentIndex = validCharacters.indexOf(c);
                if (lastIndex == -1) {
                    // Very first character
                    // Set lastIndex to our first characters index
                    lastIndex = currentIndex;
                } else if (currentIndex > lastIndex) {
                    // Our current characters index was larger than our previous
                    return false;
                } else {
                    lastIndex = currentIndex;
                }
            } else {
                return false;
            }
        }
        return true;
    }

    private boolean areLegalCount(String string) {
        for (char c : doubleCharacters) {
            int count = string.length() - string.replace(String.valueOf(c), "").length();
            if (count > 2) {
                return false;
            }
        }
        for (char c : singleCharacters) {
            int count = string.length() - string.replace(String.valueOf(c), "").length();
            if (count > 1) {
                return false;
            }
        }
        return true;
    }

    private boolean areLegalPair(String string) {
        if (string.contains("D") && string.contains("E") && string.contains("C")) {
            return false;
        } else if (string.contains("Y") && string.contains("Z") && string.contains("X")) {
            return false;
        } else if (string.contains("J") && string.contains("K") && string.contains("I")) {
            return false;
        } else {
            return true;
        }
    }

    public void fillWithDoubleCharacters(ArrayList<Character> array) {
        array.add('M');
        array.add('C');
        array.add('X');
        array.add('I');
    }

    private void fillWithValidElbonian(ArrayList<Character> array) {
        array.add('I');
        array.add('J');
        array.add('K');
        array.add('X');
        array.add('Y');
        array.add('Z');
        array.add('C');
        array.add('D');
        array.add('E');
        array.add('M');
    }

    private void fillWithSingleCharacters(ArrayList<Character> array) {
        array.add('D');
        array.add('E');
        array.add('Y');
        array.add('Z');
        array.add('J');
        array.add('K');
    }

    /**
     * Converts the number to an Arabic numeral or returns the current value as an int if it is already
     * in the Arabic form.
     *
     * @return An arabic value
     */
    public int toArabic() {
        // TODO Fill in the method's body
        if (type == Type.ARABIC) {
            return Integer.parseInt(number);
        } else {
            // Need to convert Elbonian number to arabic
            int sum = 0;
            for (char c : number.toCharArray()) {
                sum += characterValues.get(c);
            }
            return sum;
        }
    }

    /**
     * Converts the number to an Elbonian numeral or returns the current value if it is already in the Elbonian form.
     *
     * @return An Elbonian value
     */
    public String toElbonian() {
        // TODO Fill in the method's body
        if (type == Type.ELBONIAN) {
            return number;
        } else {
            // Need to convert Arabic number to Elbonian
            int arabicRepresentation = Integer.parseInt(number);
            StringBuilder elbonianRepresentation = new StringBuilder();
            while (arabicRepresentation > 0) {
                if (arabicRepresentation >= 1000 && pointsRemaining.get('M') > 0) {
                    arabicRepresentation -= 1000;
                    pointsRemaining.put('M', pointsRemaining.get('M') - 1000);
                    elbonianRepresentation.append('M');
                } else if (arabicRepresentation >= 600 && pointsRemaining.get('E') > 0) {
                    arabicRepresentation -= 600;
                    pointsRemaining.put('E', pointsRemaining.get('E') - 600);
                    elbonianRepresentation.append('E');
                } else if (arabicRepresentation >= 300 && pointsRemaining.get('D') > 0) {
                    arabicRepresentation -= 300;
                    pointsRemaining.put('D', pointsRemaining.get('D') - 300);
                    elbonianRepresentation.append('D');
                } else if (arabicRepresentation >= 100 && pointsRemaining.get('C') > 0) {
                    arabicRepresentation -= 100;
                    pointsRemaining.put('C', pointsRemaining.get('C') - 100);
                    elbonianRepresentation.append('C');
                } else if (arabicRepresentation >= 60 && pointsRemaining.get('Z') > 0) {
                    arabicRepresentation -= 60;
                    pointsRemaining.put('Z', pointsRemaining.get('Z') - 60);
                    elbonianRepresentation.append('Z');
                } else if (arabicRepresentation >= 30 && pointsRemaining.get('Y') > 0) {
                    arabicRepresentation -= 30;
                    pointsRemaining.put('Y', pointsRemaining.get('Y') - 30);
                    elbonianRepresentation.append('Y');
                } else if (arabicRepresentation >= 10 && pointsRemaining.get('X') > 0) {
                    arabicRepresentation -= 10;
                    pointsRemaining.put('X', pointsRemaining.get('X') - 10);
                    elbonianRepresentation.append('X');
                } else if (arabicRepresentation >= 6 && pointsRemaining.get('K') > 0) {
                    arabicRepresentation -= 6;
                    pointsRemaining.put('K', pointsRemaining.get('K') - 6);
                    elbonianRepresentation.append('K');
                } else if (arabicRepresentation >= 3 && pointsRemaining.get('J') > 0) {
                    arabicRepresentation -= 3;
                    pointsRemaining.put('J', pointsRemaining.get('J') - 3);
                    elbonianRepresentation.append('J');
                } else {
                    arabicRepresentation -= 1;
                    pointsRemaining.put('I', pointsRemaining.get('I') - 1);
                    elbonianRepresentation.append('I');
                }
            }
            return elbonianRepresentation.toString();
        }
    }

    private enum Type {
        ARABIC,
        ELBONIAN
    }
}
