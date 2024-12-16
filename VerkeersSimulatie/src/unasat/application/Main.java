package unasat.application;

import unasat.service.serviceimpl.SimImpl;


public class Main {
    public static void main(String[] args) {
        SimImpl sim = new SimImpl();
        System.out.println("Welkom bij de verkeerssimulatie, gemaakt door Binesrie Tushaar voor het vak Datastructuren en Algoritmen.\n");
        sim.simulation();

    }
}