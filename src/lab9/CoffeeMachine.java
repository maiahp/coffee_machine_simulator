/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package lab9;

import lab9.Supplies.*;
import java.util.*;

/**
 *
 * @author Maiah Pardo
 * CIS 36B
 * Due 11/5/16
 * Sub 11/4/16
 */
public class CoffeeMachine extends FileIO{
    final private int IDLE = 0;
    private int _current_state = IDLE;
    private int _next_state = 0;
    final private int MAINTENANCE = 25;
    //drinks
    final private int HOT_WATER = 1;
    final private int HOT_CHOC = 2;
    final private int COFFEE = 3;
    final private int MIX_TWO = 4;
    final private int CONDIMENTS = 5;
    final private int DISPENSE_COFFEE = 7;
    //types of coffee
    private boolean reg_pikeplace = false;
    private boolean dec_pikeplace = false;
    private boolean reg_cafeverona = false;
    //mixed coffee
    private boolean regAndDecPikePlace = false;
    private boolean regPikePlaceAndCafeVerona = false;
    private boolean decPikePlaceAndCafeVerona = false;
    //condiments
    private boolean useSugar = false;
    private boolean useSugarFree = false;
    private boolean useCream = false;
    private boolean useSugarAndCream = false;
    private boolean useSugarFreeAndCream = false;
    private boolean errorflag = false;
    //collections
    private ArrayList<Cup> cups = new ArrayList<Cup>();
    private ArrayList<SugarCube> sugar = new ArrayList<SugarCube>();
    private ArrayList<SugarFreeSweetenerPacket> sugarfree = new ArrayList<SugarFreeSweetenerPacket>();
    private ArrayList<CreamCup> cream = new ArrayList<CreamCup>();
    private ArrayList<HotChocolate> hotchoc = new ArrayList<HotChocolate>();
    private ArrayList<RegCafeVerona> cafeveronaAmount = new ArrayList<RegCafeVerona>();
    private ArrayList<RegPikePlace> regpikeplaceAmount = new ArrayList<RegPikePlace>();
    private ArrayList<DecafPikePlace> decpikeplaceAmount = new ArrayList<DecafPikePlace>();
    FileIO fio = new FileIO();
    Scanner input = new Scanner(System.in);
    
    public void setErrorFlag(boolean errorflag) {
	this.errorflag = errorflag;
    }
    public boolean getErrorFlag() {
	return this.errorflag;
    }
    
    public void stateSequencer (int userinput) {
        switch (_current_state) {
	    case IDLE: 
		
		if (userinput == 25) { //secret input for maintenance display TYPE 25
			_next_state = MAINTENANCE;
		} else if (errorflag == false) {
		    //must reset all boolean elements here so that the next user has new settings(no trues from previous run)
		    setVariablesBackToTrue();
		    if (userinput == 3) {
			_next_state = HOT_WATER;
		    } else if (userinput == 2) {
			_next_state = HOT_CHOC;
		    } else if (userinput == 1) {
			_next_state = COFFEE;
		    } else if (userinput == 0) {
			_next_state = IDLE;
		    } else {
			System.out.printf("%s", "\nThis is not a valid input\n\n");
			print("\nThis is not a valid input\n\n");
			_next_state = IDLE;
		    }
		} else {
		    do {
			System.out.printf("%s", "\nCoffee Machine is running low on supplies and can no longer operate\nEnter code to Enter Maintenance Mode: ");
			fio.print("\nCoffee Machine is running low on supplies and can no longer operate.\nEnter code to Enter Maintenance Mode: ");
			userinput = input.nextInt();
			fio.print(Integer.toString(userinput));
		    } while (userinput != 25);
		    _next_state = MAINTENANCE;
		    }
		break;
	    case MAINTENANCE: 
		int num;
		if (userinput == 0) {
		    _next_state = IDLE;
		} else if (userinput == 1) {
		    System.out.printf("%s", SuppliesList());
		    fio.print(SuppliesList());
		    _next_state = MAINTENANCE;
		} else if (userinput == 2) {
		    System.out.printf("\n1 = Cups\n2 = Sugar Cubes\n3 = Sugar Free Sweetener Packets"
			    + "\n4 = Cream Cups\n5 = Hot Chocolate Powder(grams)"
			    + "\n6 = Regular Cafe Verona\n7 = Regular Pike Place"
			    + "\n8 = Decaf Pike Place\n\n");
		    System.out.printf("\nWhich would you like to fill: ");
		    fio.print("\n1 = Cups\n2 = Sugar Cubes\n3 = Sugar Free Sweetener Packets"
			    + "\n4 = Cream Cups\n5 = Hot Chocolate Powder(grams)"
			    + "\n6 = Regular Cafe Verona\n7 = Regular Pike Place"
			    + "\n8 = Decaf Pike Place\n");
		    fio.print("\nWhich would you like to fill: ");
		    num = input.nextInt();
		    fio.print(Integer.toString(num));
		    if (num == 0) {
			_next_state = IDLE;
			break;
		    } else if (num <= 8 && num >= 1) {
			System.out.printf("\nAmount to be filled: ");
			fio.print("\nAmount to be filled: ");
			int numfilled = input.nextInt(); 
			fio.print(Integer.toString(numfilled));
			System.out.printf("\n\nSuccess\n\n");
			fio.print("\n\nSuccess\n\n");
			//error won't go away if user didn't reset low supply/ only set errorflag false if supplies have been refilled
			RefillElements(num, numfilled);
			if (CheckAllSuppliesAreLow() == false) {
			    setErrorFlag(false);
			} 
		    }
		    _next_state = MAINTENANCE;
		}
		break;
	    case HOT_WATER:
                if (userinput == 0) {
                    _next_state = IDLE;
                } else if (userinput == 1) {
                    // do the dispense stuff and reduce the cup collection
		    DispenseHotWater();
                    _next_state = IDLE;
                } else {
		    System.out.printf("%s", "\nThis is not a valid input\n\n");
		    print("\nThis is not a valid input\n\n");
                   _next_state = IDLE;
                }
                break;
            case HOT_CHOC: 
                if (userinput == 0) {
                    _next_state = IDLE;
                } else if (userinput == 1) {
                    //do dispense stuff and reduce cup collection
		    DispenseHotChocolate();
                    _next_state = IDLE;
                } else {
		    System.out.printf("\nThis is not a valid input\n\n");
                    print("\nThis is not a valid input\n\n");
                    _next_state = IDLE;
                }
                break;
            case COFFEE:
                if (userinput == 0) {
                    _next_state = IDLE;
                } else if (userinput == 1) {
                    reg_pikeplace = true;
                    _next_state = CONDIMENTS;
                } else if (userinput == 2) {
                    dec_pikeplace = true;
                    _next_state = CONDIMENTS;
                } else if (userinput == 3) {
                    reg_cafeverona = true;
                    _next_state = CONDIMENTS;
                } else if (userinput == 4) {
                    _next_state = MIX_TWO;
                } else {
                    System.out.printf("\nThis is not a valid input\n\n");
		    print("\nThis is not a valid input\n\n");
                    _next_state = IDLE;
                }
                break;
            case MIX_TWO: 
                if (userinput == 0) {
                    _next_state = IDLE;
                } else if (userinput == 1) {
                    regAndDecPikePlace = true;
                    _next_state = CONDIMENTS;
                } else if (userinput == 2) {
                    regPikePlaceAndCafeVerona = true;
                    _next_state = CONDIMENTS;
                } else if (userinput == 3) {
                    decPikePlaceAndCafeVerona = false;
                    _next_state = CONDIMENTS;
                } else {
                    System.out.printf("%s", "\nThis is not a valid input\n\n");
		    print("\nThis is not a valid input\n\n");
                    _next_state = IDLE;
                }
                break;
            case CONDIMENTS: 
                if (userinput == 0) {
                    _next_state = IDLE;
                } else if (userinput == 1) {
                    useSugar = true;
                    _next_state = DISPENSE_COFFEE;
                } else if (userinput == 2) {
                    useSugarFree = true;
                    _next_state = DISPENSE_COFFEE;
                } else if (userinput == 3) {
                    useCream = true;
                    _next_state = DISPENSE_COFFEE;
                } else if (userinput == 4) {
                    useSugarAndCream = true;
                    _next_state = DISPENSE_COFFEE;
                } else if (userinput == 5) {
                    useSugarFreeAndCream = true;
                    _next_state = DISPENSE_COFFEE;
		} else if (userinput == 6) {
		    //do nothing, use no condiments
		    _next_state = DISPENSE_COFFEE;
                } else {
                    System.out.printf("%s", "\nThis is not a valid input\n\n");
                    print("\nThis is not a valid input\n\n");
                    _next_state = IDLE;
                }
                break;
            case DISPENSE_COFFEE:
                if (userinput == 0) {
                    _next_state = IDLE;
                } else if (userinput == 1) {
		    DispenseCoffee();
                    _next_state = IDLE;
                } else {
                    System.out.printf("%s", "This is not a valid input\n");
                    print("This is not a valid input\n");
                   _next_state = IDLE;
                }
                break;
        }
        _current_state = _next_state;
    }
    public String createMenu () {
        String returnString = "";
        switch (_current_state) {
            case IDLE: 
                returnString = "\nWelcome to Starbucks's Coffee Machine\n" 
			+ "What would you like today?\n" 
                        + "1 = Coffee\n2 = Hot Chocolate\n3 = Hot Water\n";
                break;
	    case MAINTENANCE: 
		returnString = "\nYou are now in Maintenance Mode.\n"
			+ "\n0 = Exit to Customer Menu\n1 = Show Supply List\n2 = Fill Supplies\n";
		break;
            case HOT_WATER:
                returnString = "\nDispense Hot Water?"
                        + "\n0 = Cancel\n1 = Dispense Hot Water\n"; 
                break;
            case HOT_CHOC: 
                returnString = "\nDispense Hot Chocolate?"
                        + "\n0 = Cancel\n1 = Dispense Hot Chocolate\n";
                break;
            case COFFEE:
                returnString = "\nPlease Choose Your Coffee:" 
                        + "\n0 = cancel\n1 = Regular Pike Place\n2 = Decaf Pike Place\n"
                        + "3 = Regular Cafe Verona\n4 = Mix Two\n";
                break;
            case MIX_TWO: 
                returnString = "\nWhich two would you like to mix?" 
                        + "\n0 = cancel\n1 = Regular and Decaf Pike Place\n2 = Regular Pike Place and Regular Cafe Verona\n" 
                        + "3 = Decaf Pike Place and Regular Cafe Verona\n";
                break;
            case CONDIMENTS:
                returnString = "\nPlease Choose Your Condiments: "
                        + "\n0 = cancel\n1 = Sugar\n2 = Sugar Free Sweetener\n3 = Cream"
                        + "\n4 = Sugar and Cream\n5 = Sugar Free Sweetener and Cream"
			+ "\n6 = none\n";
                break;
            case DISPENSE_COFFEE: 
                returnString = "\nDispense Coffee? " +
                        "\n0 = Cancel\n1 = Dispense Coffee\n";
                break;
        }
        return returnString;
    }
    private void DispenseHotWater() {
	//endless water (no collections)
	if (CheckHotWaterSuppliesAreEmpty() == false) {
	    System.out.printf("%s", "\n\nDispensing Hot Water\n\n");
	    print("\n\nDispensing Hot Water\n\n");

	    RemoveElementsFromCollection(cups, 1);
	    System.out.printf("%s", "\nDone\n\n");
	    print("\nDone\n\n");
	} else {
	    System.out.printf("%s", "Not enough supplies.\n\n");
	    print("Not enough supplies.\n\n");
	}
    }
    private void DispenseHotChocolate() {
	if (CheckHotChocolateSuppliesAreEmpty() == false) {
	    System.out.printf("%s", "\n\nDispensing Hot Chocolate\n\n");
	    print("\n\nDispensing Hot Chocolate\n\n");
	    RemoveElementsFromCollection(hotchoc, 5);
	    RemoveElementsFromCollection(cups, 1);
	    System.out.printf("%s", "Done.\n\n");
	    print("Done.\n\n");
	} else {
	    System.out.printf("%s", "Not enough supplies.\n\n");
	    System.out.printf("%s", "Not enough supplies.\n\n");
	}
    }
    private void DispenseCoffee() {
	if (CheckCoffeeSuppliesAreEmpty() == false) {
	    System.out.printf("%s", "\n\nDispensing Coffee Selection");
	    print("\n\nDispensing Coffee Selection");
	    RemoveElementsFromCollection(cups, 1);
	    if (dec_pikeplace == true) {
		RemoveElementsFromCollection(decpikeplaceAmount, 10);
	    } else if (reg_pikeplace == true) {
		RemoveElementsFromCollection(regpikeplaceAmount, 10);
	    } else if (reg_cafeverona == true) {
		RemoveElementsFromCollection(cafeveronaAmount, 10);
	    } else if (regAndDecPikePlace == true) {
		RemoveElementsFromCollection(regpikeplaceAmount, 5);    
		RemoveElementsFromCollection(decpikeplaceAmount, 5);
	    } else if (regPikePlaceAndCafeVerona == true) {
		RemoveElementsFromCollection(regpikeplaceAmount, 5);
		RemoveElementsFromCollection(cafeveronaAmount, 5);
	    } else if (decPikePlaceAndCafeVerona == true) {
		RemoveElementsFromCollection(decpikeplaceAmount, 5);
		RemoveElementsFromCollection(cafeveronaAmount, 5);
	    } else {
		//do nothing
	    }
	    if (useSugar == true) {
	       RemoveElementsFromCollection(sugar, 1);
	    } else if (useSugarFree == true) {
		RemoveElementsFromCollection(sugarfree, 1);
	    } else if (useCream == true) {
		RemoveElementsFromCollection(cream, 1);
	    } else if (useSugarAndCream) {
		RemoveElementsFromCollection(sugar, 1);
		RemoveElementsFromCollection(cream, 1);
	    } else if (useSugarFreeAndCream) {
		RemoveElementsFromCollection(sugarfree, 1);
		RemoveElementsFromCollection(cream, 1);
	    } else {
		//do nothing
	    }
	    System.out.printf("%s", "\n\nDone\n\n");
	    print("\n\nDone\n\n");

	} else {
	    System.out.printf("%s", "Not enough supplies.\n\n");
	    print("Not enough supplies.\n\n");
	}
    }
    
    public void RefillCups(int numofcups) {
	for (int i = 0; i < numofcups; i++) {
	    Cup cup = new Cup();
	    cups.add(cup);
	}
    }
    public void RefillSugarCubes(int numofsugarcubes) {
	for (int i = 0; i < numofsugarcubes; i++) {
	    SugarCube cube = new SugarCube();
	    sugar.add(cube);
	}
    }
    public void RefillSugarFreePackets(int numofsugarfreepackets) {
	for (int i = 0; i < numofsugarfreepackets; i++) {
	    SugarFreeSweetenerPacket sugarfreepacket = new SugarFreeSweetenerPacket();
	    sugarfree.add(sugarfreepacket);
	}
    }
    public void RefillCreamCups(int numofcreamcups) {
	for (int i = 0; i < numofcreamcups; i++) {
	    CreamCup creamcup = new CreamCup();
	    cream.add(creamcup);
	}
    }
    public void RefillHotChocolate(int gramsofhotchocolatemix) {
	for (int i = 0; i < gramsofhotchocolatemix; i++) {
	    HotChocolate hc = new HotChocolate();
	    hotchoc.add(hc);
	}
    }
    //there are 10g of coffee grounds per cup (when mixed, 5g of each coffee)
    public void RefillGramsofDecafPikePlace(int gramsofcoffeegrounds) {
	for (int i = 0; i < gramsofcoffeegrounds; i++) {
	    DecafPikePlace dpp = new DecafPikePlace();
	    decpikeplaceAmount.add(dpp);
	}
    }
    public void RefillGramsofRegularPikePlace(int gramsofcoffeegrounds) {
	for (int i = 0; i < gramsofcoffeegrounds; i++) {
	    RegPikePlace rpp = new RegPikePlace();
	    regpikeplaceAmount.add(rpp);
	}
    }
     public void RefillGramsofRegCafeVerona(int gramsofcoffeegrounds) {
	for (int i = 0; i < gramsofcoffeegrounds; i++) {
	    RegCafeVerona rcv = new RegCafeVerona();
	    cafeveronaAmount.add(rcv);
	}
    }
    public void setVariablesBackToTrue() {
	reg_pikeplace = false;
	dec_pikeplace = false;
	reg_cafeverona = false;
	regAndDecPikePlace = false;
	regPikePlaceAndCafeVerona = false;
	decPikePlaceAndCafeVerona = false;
	useSugar = false;
	useSugarFree = false;
	useCream = false;
	useSugarAndCream = false;
	useSugarFreeAndCream = false;
    }
    public void RefillElements(int supplyNumber, int num_added) {
	ArrayList arraylist = null;
	switch (supplyNumber) {
	    case 1: 
		arraylist = cups;
		break;
	    case 2: 
		arraylist = sugar;
		break;
	    case 3: 
		arraylist = sugarfree;
		break;
	    case 4: 
		arraylist = cream;
		break;
	    case 5: 
		arraylist = hotchoc;
		break;
	    case 6:
		arraylist = cafeveronaAmount;
		break;
	    case 7: 
		arraylist = regpikeplaceAmount;
		break;
	    case 8: 
		arraylist = decpikeplaceAmount;	
		break;
	}
	for (int i = 0; i < num_added; i++) {
	    arraylist.add(i);
	}
    }
    public void RemoveElementsFromCollection(ArrayList arraylist, int num_removed) { //preventing arraylist exception
	if (arraylist.size() <= num_removed) {
	    errorflag = true;
	    num_removed = arraylist.size();
	}
	for (int i = 0; i < num_removed; i++) {
	    arraylist.remove(0);
	}
    }
    public String SuppliesList() {
	return "\n\nSupplies: "
		+ "\nAmount of Regular Pike Place: " + regpikeplaceAmount.size() + " grams"
		+ "\nAmount of Decaf Pike Place: " + decpikeplaceAmount.size() + " grams"
		+ "\nAmount of Regular Cafe Verona: " + cafeveronaAmount.size() + " grams"
		+ "\nAmount of Hot Chocolate Mix: " + hotchoc.size() + " grams" 
		+ "\nAmount of Cups: " + cups.size()
		+ "\nAmount of Cream Cups: " + cream.size()
		+ "\nAmount of Sugar Cubes: " + sugar.size()
		+ "\nAmount of Sugar Free Sweetener Packets: " + sugarfree.size() 
		+ "\n\n";
    }
    public String WarningSuppliesAreLow() {
	String returnString = "";
	if (regpikeplaceAmount.size() <= 10 && !regpikeplaceAmount.isEmpty()) {
	    returnString = "\n\nWarning! Regular Pike Place Coffee Grounds are Running Low.\n";
	} else if (decpikeplaceAmount.size() <= 10 && !decpikeplaceAmount.isEmpty()) {
	    returnString += "\n\nWarning! Decaf Pike Place Coffee Grounds are Running Low.\n";
	} else if (cafeveronaAmount.size() <= 10 && !cafeveronaAmount.isEmpty()) {
	    returnString += "\n\nWarning! Regular Cafe Verona Coffee Grounds are Running Low.\n";
	} else if (hotchoc.size() <= 10 && !hotchoc.isEmpty()) {
	    returnString += "\n\nWarning! Hot Chocolate Powder is Running Low.\n";
	} else if (cream.size() <= 5 && !cream.isEmpty()) {
	    returnString += "\n\nWarning! Cream cups are Running Low.\n";
	} else if (sugar.size() <= 5 && !sugar.isEmpty()) {
	    returnString += "\n\nWarning! Sugar Cubes are Running Low.\n";
	} else if (sugarfree.size() <= 5 && !sugarfree.isEmpty()) {
	    returnString += "\n\nWarning! Sugar Free Sweetener Packets are Running Low.\n";
	} else if (cups.size() <= 5 && !cups.isEmpty()) {
	    returnString += "\n\nWarning! Cups are Running Low.\n";
	} else if (regpikeplaceAmount.isEmpty()) {
	    returnString = "\nRegular Pike Place Coffee Grounds are Empty.\n";
	} else if (decpikeplaceAmount.isEmpty()) {
	    returnString += "\nDecaf Pike Place Coffee Grounds are Empty.\n";
	} else if (cafeveronaAmount.isEmpty()) {
	    returnString += "\nRegular Cafe Verona Coffee Grounds are Empty.\n";
	} else if (hotchoc.isEmpty()) {
	    returnString += "\nHot Chocolate Powder is Empty.\n";
	} else if (cream.isEmpty()) {
	    returnString += "\nCream cups are Empty.\n";
	} else if (sugar.isEmpty()) {
	    returnString += "\nSugar Cubes are Empty.\n";
	} else if (sugarfree.isEmpty()) {
	    returnString += "\nSugar Free Sweetener Packets are Empty.\n";
	} else if (cups.isEmpty()) {
	    returnString += "\nCups are Empty.\n";
	}
    return returnString;
    }
    public boolean CheckHotWaterSuppliesAreLow() {
	boolean suppliesLow = false;
	if (cups.size() <= 1) {
	    suppliesLow = true;
	}
	return suppliesLow;
    }
    public boolean CheckHotWaterSuppliesAreEmpty() {
	boolean suppliesLow = false;
	if (cups.size() == 0) {
	    suppliesLow = true;
	}
	return suppliesLow;
    }
    public boolean CheckHotChocolateSuppliesAreLow() {
	boolean suppliesLow = false;
	if (hotchoc.size() <= 5) {
	    suppliesLow = true;
	} else if (cups.size() <= 1) {
	    suppliesLow = true;
	}
	return suppliesLow;
    }
    public boolean CheckHotChocolateSuppliesAreEmpty() {
	boolean suppliesLow = false;
	if (hotchoc.size() == 0) {
	    suppliesLow = true;
	} else if (cups.size() == 0) {
	    suppliesLow = true;
	}
	return suppliesLow;
    }
    public boolean CheckCoffeeSuppliesAreLow() {
	boolean suppliesLow = false;
	if (regpikeplaceAmount.size() <= 5) {
	    suppliesLow = true;
	} else if (decpikeplaceAmount.size() <= 5) {
	    suppliesLow = true;
	} else if (cafeveronaAmount.size() <= 5) {
	    suppliesLow = true;
	    } else if (cream.size() <= 1) {
	    suppliesLow = true;
	} else if (sugar.size() <= 1) {
	    suppliesLow = true;
	} else if (sugarfree.size() <= 1) {
	    suppliesLow = true;
	} else if (cups.size() <= 1) {
	    suppliesLow = true;
	}
	return suppliesLow; 
    }
    public boolean CheckCoffeeSuppliesAreEmpty() {
	boolean suppliesLow = false;
	if (regpikeplaceAmount.size() == 0) {
	    suppliesLow = true;
	} else if (decpikeplaceAmount.size() == 0) {
	    suppliesLow = true; 
	} else if (cafeveronaAmount.size() == 0) {
	    suppliesLow = true;
	} else if (cream.size() == 0) {
	    suppliesLow = true;
	} else if (sugar.size() == 0) {
	    suppliesLow = true;
	} else if (sugarfree.size() == 0) {
	    suppliesLow = true;
	} else if (cups.size() == 0) {
	    suppliesLow = true;
	}
	return suppliesLow;
    }
    public boolean CheckAllSuppliesAreLow() {
        return CheckCoffeeSuppliesAreLow() || CheckHotChocolateSuppliesAreLow() || CheckHotWaterSuppliesAreLow();
    }
}
