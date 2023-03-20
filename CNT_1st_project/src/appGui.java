/*  Name: Abdullah AL Hinaey
     Course: CNT 4714 – Spring 2023
     Assignment title: Project 1 – Event-driven Enterprise Simulation
     Date: Sunday January 29, 2023
*/

import java.io.IOException;

public class appGui {

    /*
    id in dataBase[0]
    details in dataBase[1]
    Availability in dataBase[2]
    prices in dataBase[3]
     */

    public static void main(String[] args) throws IOException {

        myFrame frame= new myFrame("inventory.txt");

        frame.clearTheFile();

    }

}
