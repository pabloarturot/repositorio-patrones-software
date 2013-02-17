package com.greenhouse.controller;

import java.util.Random;

public class Thermometer {

	public static double getTemperature() {
        Random randomGenerator = new Random();
        return (randomGenerator.nextDouble()+10)*randomGenerator.nextInt(10);
    }

}
