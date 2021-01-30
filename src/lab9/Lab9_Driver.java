/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package lab9;

import java.util.Scanner;

/**
 *
 * @author Maiah Pardo
 * CIS 36B
 * Due 11/5/16
 * Sub 11/4/16
 */
public class Lab9_Driver {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
	//secret input is 25 for maintenance
        Scanner input = new Scanner(System.in);
	FileIO fio = new FileIO();
	fio.CreateHeader();
        CoffeeMachine cm = new CoffeeMachine();
        cm.RefillCreamCups(1);
	cm.RefillCups(30);
	cm.RefillSugarCubes(50);
	cm.RefillSugarFreePackets(50);
	cm.RefillGramsofDecafPikePlace(100);
	cm.RefillGramsofRegularPikePlace(100);
	cm.RefillGramsofRegCafeVerona(100);
	cm.RefillHotChocolate(60);
	
        int userinput;
        do {
            System.out.print(cm.createMenu());
            fio.print(cm.createMenu());
            userinput = input.nextInt();
	    fio.print(Integer.toString(userinput));
	    cm.stateSequencer(userinput);
	    
        } while (userinput != 100);
    }
}
