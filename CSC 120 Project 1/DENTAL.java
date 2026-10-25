import java.util.Scanner;

/**
 * Records the teeth of the members of one Florida family for the Florida
 * Dental Association. Each tooth is an incisor (I), a bicuspid (B), or a
 * missing tooth (M). The teeth are stored in a three dimensional array of
 * characters: one plane per person, two rows (uppers and lowers), and one
 * column per tooth. After the family is entered, a menu lets the user print
 * the record, extract a tooth, report the root canal indices, or exit.
 */

public class DENTAL {

    private static final Scanner keyboard = new Scanner(System.in);

// declare constants
    private static final int MIN_PEOPLE = 1;
    private static final int MAX_PEOPLE = 6;
    private static final int MAX_TEETH = 8;
    private static final int NUM_LAYERS = 2;
    private static final String[] LAYER_NAMES = {"Uppers" , "Lowers"};
    private static final int UPPER = 0;
    private static final int LOWER = 1;
    private static final char UPPER_LETTER = 'U';
    private static final char LOWER_LETTER = 'L';

    // declare tooth letters
    private static final char INCISOR = 'I';
    private static final char BICUSPID = 'B';
    private static final char MISSING = 'M';


    // declare menu
    private static final char PRINT = 'P';
    private static final char EXTRACT = 'E';
    private static final char ROOT = 'R';
    private static final char EXIT = 'X';

    // declare formatting
    private static final double ROUNDING = 100.0;
    private static final int NOT_FOUND = -1;

    /**
     * Reads the family's teeth, then runs the menu until the user exits.
     */

    public static void main(String[] args){
        int numberOfPeople;
        String[] names;
        char[][][] teeth;
        char option;

        System.out.println("Welcome to the Floridian Tooth Records");
        System.out.println("--------------------------------------");

        // arrays
        numberOfPeople = readNumberOfPeople();
        names = new String[numberOfPeople];
        teeth = new char[numberOfPeople][NUM_LAYERS][];
        readFamily(names, teeth);

        //menu
        do {
            System.out.println();
            option = readMenuOption();
            switch (option){
                case PRINT:
                    System.out.println();
                    printFamily(names, teeth);
                    break;

                case EXTRACT:
                    extractTooth(names, teeth);
                    break;

                case ROOT:
                    reportRootCanals(teeth);
                    break;

                default:
                    break;

            } // end of switch

        } // end of do

        while (option != EXIT);

        System.out.println();
        System.out.println("Exiting the Floridian Tooth Records :-)");

    }// end of the main method

    /**
     * Reads the number of people in the family, asking again until it is in range.
     */

private static int readNumberOfPeople() {

    int numberOfPeople;

    System.out.println("Please enter number of people in the family : ");
    numberOfPeople = keyboard.nextInt();
    while (numberOfPeople < MIN_PEOPLE || numberOfPeople > MAX_PEOPLE) {
        System.out.println("Invalid number of people, try again : ");
        numberOfPeople = keyboard.nextInt();

    } // end of while

    keyboard.nextLine();

    return numberOfPeople;

} // end of readNumberOfPeople method

    /**
     * Reads the name, uppers, and lowers of every family member.
     */

    private static void readFamily(String[] names, char[][][] teeth){

        int person;
        int layer;

        for (person = 0; person < names.length; person++){
            names[person] = readName(person);
            for (layer = 0; layer < NUM_LAYERS; layer++) {
                teeth[person][layer] = readTeeth(names[person], layer);

            } // end of for

        }// end of for

    } // end of readFamily method

    /**
     * Reads one family member's name.
     */

    private static String readName(int person){

        System.out.println(" Please enter the name for family member " + (person + 1 ) + " : ");

        return keyboard.nextLine();

    } // end of readName method

    /**
     * Reads one layer of teeth for a family member, asking again until the
     * letters are all valid and there are not too many teeth.
     */

    private static char[] readTeeth(String name, int layer){
        String teethString;
        char[] layerTeeth;
        int tooth;

        System.out.println("Please enter the " + LAYER_NAMES[layer].toLowerCase() + " for " + name + " : ");
        teethString = keyboard.nextLine().toUpperCase();
        while (!hasValidToothTypes(teethString) || teethString.length() > MAX_TEETH){
            if (!hasValidToothTypes(teethString)) {
                System.out.print("Invalid teeth types, try again              : ");
            } else {
                System.out.print("Too many teeth, try again                   : ");
            }
            teethString = keyboard.nextLine().toUpperCase();
        } // end of while

        layerTeeth = new char[teethString.length()];
        for (tooth = 0; tooth < teethString.length(); tooth++) {
            layerTeeth[tooth] = teethString.charAt(tooth);
        } // end of for

        return layerTeeth;

    }// end of readTeeth method

    /**
     * Checks that every letter in a string is a valid tooth type (I, B, or M).
     */

        private static boolean hasValidToothTypes(String teethString) {

            boolean allValid;
            int index;
            char tooth;

            //----Check each letter, remembering if any is not I, B, or M
            allValid = true;
            for (index = 0; index < teethString.length(); index++) {
                tooth = teethString.charAt(index);
                if (tooth != INCISOR && tooth != BICUSPID && tooth != MISSING) {
                    allValid = false;
                } // end of if

            } // end of for

            return allValid;

        } // end of hasValidToothTypes method

    /**
     * Reads a menu option, asking again until it is valid.
     */

    private static char readMenuOption() {

        String input;

        System.out.print("(P)rint, (E)xtract, (R)oot, e(X)it          : ");
        input = keyboard.nextLine().toUpperCase();
        //----Must be exactly one letter, and one of the menu letters
        while (input.length() != 1
                || (input.charAt(0) != PRINT && input.charAt(0) != EXTRACT
                && input.charAt(0) != ROOT && input.charAt(0) != EXIT)) {
            System.out.print("Invalid menu option, try again              : ");
            input = keyboard.nextLine().toUpperCase();
        } // end of while

        return input.charAt(0);

    } // end of readMenuOption method

    /**
     * Prints the teeth record of the whole family.
     */

    private static void printFamily(String[] names, char[][][] teeth) {

        int person;
        int layer;

        for (person = 0; person < names.length; person++) {
            System.out.println(names[person]);
            for (layer = 0; layer < NUM_LAYERS; layer++) {
                printLayer(LAYER_NAMES[layer], teeth[person][layer]);
            }// end of for

        } // end of for

    } // end of printFamily method

    /**
     * Prints one layer of teeth, numbering each tooth from 1.
     */

    private static void printLayer(String layerName, char[] layerTeeth) {

        int tooth;

        System.out.print("  " + layerName + ":");
        for (tooth = 0; tooth < layerTeeth.length; tooth++) {
            System.out.print("  " + (tooth + 1) + ":" + layerTeeth[tooth]);
        } // end of for

        System.out.println();

    } // end of printLayer method

    /**
     * Extracts one tooth, chosen by family member, layer, and tooth number.
     * The tooth is replaced by a missing tooth.
     */

    private static void extractTooth(String[] names, char[][][] teeth) {

        int person;
        int layer;
        int toothNumber;
        char[] layerTeeth;

        person = readFamilyMember(names);
        layer = readLayer();
        layerTeeth = teeth[person][layer];
        toothNumber = readToothNumber(layerTeeth);
        layerTeeth[toothNumber - 1] = MISSING;

    } // end of the extractTooth method

    /**
     * Reads the name of a family member, asking again until it is found.
     */

    private static int readFamilyMember(String[] names) {

        int person;

        System.out.print("Which family member                         : ");
        person = findFamilyMember(names, keyboard.nextLine());
        while (person == NOT_FOUND) {
            System.out.print("Invalid family member, try again            : ");
            person = findFamilyMember(names, keyboard.nextLine());
        } // end of while

        return person;

    } // end of the readFamilyMember method

    /**
     * Searches for a name in the family, ignoring case.
     */

    private static int findFamilyMember(String[] names, String name) {

        int index;

        //----Linear search for the name
        index = 0;
        while (index < names.length && !name.equalsIgnoreCase(names[index])) {
            index++;
        } // end of while

        if (index < names.length) {
            return index;
        } else {
            return NOT_FOUND;
        } // end of if

    }// end of the findFamilyMember method

    /**
     * Reads which layer to extract from, asking again until it is U or L.
     */

    private static int readLayer() {

        String input;

        System.out.print("Which tooth layer (U)pper or (L)ower        : ");
        input = keyboard.nextLine().toUpperCase();
        //----Must be exactly one letter, either U or L
        while (input.length() != 1
                || (input.charAt(0) != UPPER_LETTER
                && input.charAt(0) != LOWER_LETTER)) {
            System.out.print("Invalid layer, try again                    : ");
            input = keyboard.nextLine().toUpperCase();
        } // end of while

        if (input.charAt(0) == UPPER_LETTER) {
            return UPPER;
        } else {
            return LOWER;
        }// end of if

    } // end of the readLayer method

    /**
     * Reads the number of the tooth to extract, asking again until it is in
     * range and is not a missing tooth.
     */

    private static int readToothNumber(char[] layerTeeth) {

        int toothNumber;

        System.out.print("Which tooth number                          : ");
        toothNumber = keyboard.nextInt();
        while (toothNumber < 1 || toothNumber > layerTeeth.length
                || layerTeeth[toothNumber - 1] == MISSING) {
            if (toothNumber < 1 || toothNumber > layerTeeth.length) {
                System.out.print("Invalid tooth number, try again             : ");
            } else {
                System.out.print("Missing tooth, try again                    : ");
            }
            toothNumber = keyboard.nextInt();
        } // end of while

        keyboard.nextLine();

        return toothNumber;

    }// end of the readToothNumber method

    /**
     * Reports the family's root canal indices, which are the roots of
     * Ix^2 + Bx - M = 0, where I, B, and M are the family's numbers of
     * incisors, bicuspids, and missing teeth.
     */

    private static void reportRootCanals(char[][][] teeth) {

        int incisors;
        int bicuspids;
        int missing;
        double discriminant;
        double firstRoot;
        double secondRoot;

        incisors = countFamilyTeeth(teeth, INCISOR);
        bicuspids = countFamilyTeeth(teeth, BICUSPID);
        missing = countFamilyTeeth(teeth, MISSING);

        //----Quadratic formula, b^2 - 4ac with c = -M
        discriminant = bicuspids * bicuspids + 4.0 * incisors * missing;
        firstRoot = (-bicuspids + Math.sqrt(discriminant)) / (2.0 * incisors);
        secondRoot = (-bicuspids - Math.sqrt(discriminant)) / (2.0 * incisors);
        System.out.println("One root canal at     "
                + Math.round(firstRoot * ROUNDING) / ROUNDING);
        System.out.println("Another root canal at "
                + Math.round(secondRoot * ROUNDING) / ROUNDING);

    } // end of reportRootCanals method

    /**
     * Counts the teeth of one type in the whole family.
     */

    private static int countFamilyTeeth(char[][][] teeth, char toothType) {

        int count;
        int person;
        int layer;

        count = 0;
        for (person = 0; person < teeth.length; person++) {
            for (layer = 0; layer < NUM_LAYERS; layer++) {
                count += countLayerTeeth(teeth[person][layer], toothType);
            } // end of for

        } // end of for

        return count;

    }// end of the countFamilyTeeth method

    /**
     * Counts the teeth of one type in one layer.
     */

    private static int countLayerTeeth(char[] layerTeeth, char toothType) {

        int count;
        int tooth;

        count = 0;
        for (tooth = 0; tooth < layerTeeth.length; tooth++) {
            if (layerTeeth[tooth] == toothType) {
                count++;
            } // end of if

        } // end of for

        return count;

    }// end of the countLayerTeeth method

} // end of class
