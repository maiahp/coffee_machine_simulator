/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package lab9;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

/**
 *
 * @author Maiah Pardo
 * CIS 36B
 * Due 11/5/16
 * Sub 11/4/16
 */
public class FileIO {
    public void print(String text) {
	BufferedWriter bw = null;
        try {
            bw = new BufferedWriter(new FileWriter("lab9test.txt", true));
            bw.write(text);
            bw.flush();
        } catch (IOException ioe) {
        } finally { 
            if (bw != null) try {
                bw.close();
            } catch (IOException e) {
                System.out.printf("Error ");
		e.printStackTrace();
            }
        }
    }

    public void CreateHeader() {
       BufferedWriter bw = null;
       try {
           bw = new BufferedWriter(new FileWriter("lab9test.txt", false));
           bw.write("Maiah Pardo\nCIS 36B\nDue 12/5/16\nSubmitted 12/4/16"
		   + "\n\n\n");
           bw.flush();
       } catch (IOException ioe) {
       } finally { 
           if (bw != null) try {
               bw.close();
           } catch (IOException ioe2) {

           }
        }
    }
}
