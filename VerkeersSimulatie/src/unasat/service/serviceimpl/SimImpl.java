package unasat.service.serviceimpl;

import unasat.datastructures.Queue;
import unasat.datastructures.NodeCar;
import unasat.datastructures.Stack;
import unasat.model.*;
import unasat.service.Simulation;


public class SimImpl implements Simulation {
    Queue<Wegdek> wegdekQueue = new Queue<>();

    private int roundCounter = 0;  // Round counter

    NodeCar carsNoord = new NodeCar();
    NodeCar carsZuid = new NodeCar();
    NodeCar carsOost = new NodeCar();
    NodeCar carsWest = new NodeCar();

    public SimImpl(){
        carsNoord.insert(new Car(1, "Toyota"));
        carsNoord.insert(new Car(2, "Honda"));
        carsNoord.insert(new Car(3, "Ford"));
        carsNoord.insert(new Car(4, "Ambulance"));

        carsZuid.insert(new Car(1, "Nissan"));
        carsZuid.insert(new Car(2, "Mazda"));
        carsZuid.insert(new Car(3, "Volkswagen"));
        carsZuid.insert(new Car(4, "Chevrolet"));
        carsZuid.insert(new Car(5, "Kia"));
        carsZuid.insert(new Car(6, "Hyundai"));
        carsZuid.insert(new Car(7, "BMW"));
        carsZuid.insert(new Car(8, "Audi"));
        carsZuid.insert(new Car(9, "Mercedes"));
        carsZuid.insert(new Car(10, "Peugeot"));
        carsZuid.insert(new Car(11, "Renault"));
        carsZuid.insert(new Car(12, "Fiat"));
        carsZuid.insert(new Car(13, "Jeep"));
        carsZuid.insert(new Car(14, "Subaru"));
        carsZuid.insert(new Car(15, "Mitsubishi"));
        carsZuid.insert(new Car(16, "Suzuki"));
        carsZuid.insert(new Car(3, "Brandweer"));
        carsZuid.insert(new Car(18, "Honda" ));

        carsOost.insert(new Car(1, "Opel"));
        carsOost.insert(new Car(2, "Citroën"));
        carsOost.insert(new Car(3, "Volvo"));
        carsOost.insert(new Car(4, "Toyota"));
        carsOost.insert(new Car(5, "Nissan"));

        carsWest.insert(new Car(1, "Mazda"));
        carsWest.insert(new Car(2, "Honda"));
        carsWest.insert(new Car(3, "Ford"));
        carsWest.insert(new Car(4, "Chevrolet"));
        carsWest.insert(new Car(5, "Hyundai"));
        carsWest.insert(new Car(6, "Kia"));
        carsWest.insert(new Car(7, "BMW"));
        carsWest.insert(new Car(8, "Audi"));
        carsWest.insert(new Car(9, "Politie"));
        carsWest.insert(new Car(10, "Volkswagen"));
        carsWest.insert(new Car(11, "Mercedes"));
        carsWest.insert(new Car(12, "Peugeot"));
        carsWest.insert(new Car(13, "Renault"));
        carsWest.insert(new Car(14, "Fiat"));

        wegdekQueue.enqueue(new Wegdek("Noord", 4, carsNoord));
        wegdekQueue.enqueue(new Wegdek("Zuid", 2, carsZuid));
        wegdekQueue.enqueue(new Wegdek("Oost", 1, carsOost));
        wegdekQueue.enqueue(new Wegdek("West", 3, carsWest));
    }


//-----------------------------------------------Normal simulation----------------------------------------------//
    public void simulation() {
        Stack<Car> removedCars = new Stack<>();

//-------------------------------------------------------------------------------------------------------------//
//                                              Priority Cars                                                  //
//-------------------------------------------------------------------------------------------------------------//
        for (int priority = 1; priority <= 3; priority++) {
            Queue<Wegdek> tempQueue = new Queue<>();

            while (!wegdekQueue.isEmpty()) {
                Wegdek wegdek = wegdekQueue.dequeue();
                NodeCar highPriorityCars = new NodeCar();

                // Iterate through cars to select and sort by priority
                for (Car car : wegdek.getCars()) {
                    if (NodeCar.prioriteit(car.getCarName()) == priority) {
                        highPriorityCars.insert(car);
                    }
                }

                // Sort the high priority cars
                NodeCar.insertionSort(highPriorityCars);

                // Process the sorted high priority cars
                while (!highPriorityCars.isEmpty()) {
                    Car car = highPriorityCars.removeFirst();
                    System.out.println("Auto rijdt weg (prioriteit): " + car.getCarName() + "   nummerplaat: " + car.getNummerplaat());
                    wegdek.getCars().remove(car);

                    // Push the removed car onto the stack
                    removedCars.push(car);
                }

                // Re-enqueue the updated wegdek
                tempQueue.enqueue(wegdek);
            }

            // Restore the original queue order
            while (!tempQueue.isEmpty()) {
                wegdekQueue.enqueue(tempQueue.dequeue());
            }
        }

        System.out.println("\n");

//-------------------------------------------------------------------------------------------------------------//
//                                              Normale Auto's Simulatie                                       //
//-------------------------------------------------------------------------------------------------------------//
        boolean carsRemaining;
        do {
            roundCounter++;
            System.out.println("Ronde: " + roundCounter);
            carsRemaining = false;
            Queue<Wegdek> tempQueue = new Queue<>();

            while (!wegdekQueue.isEmpty()) {
                Wegdek wegdek = wegdekQueue.dequeue();
                Sensor.sensorResult(wegdek);

                if (!wegdek.getCars().isEmpty()) {
                    carsRemaining = true;

                    // Create a NodeCar for the sorted cars
                    NodeCar sortedCars = new NodeCar();
                    for (Car car : wegdek.getCars()) {
                        sortedCars.insert(car);
                    }

                    // Sort the cars
                    NodeCar.insertionSort(sortedCars);

                    System.out.println("Groen licht voor " + wegdek.getWegdekNaam() + "!");

                    int carsPassed = 0;
                    int maxCarsToPass = 5;

                    if ((wegdek.getSensor() == 2 || wegdek.getSensor() == 3) && wegdek.getCars().size() > 10) {
                        maxCarsToPass = 10;
                    }

                    // Process cars within the allowed time
                    while (carsPassed < maxCarsToPass && !sortedCars.isEmpty()) {
                        Car car = sortedCars.removeFirst();
                        System.out.println("Auto rijdt weg: " + car.getCarName() + "   nummerplaat: " + car.getNummerplaat());
                        wegdek.getCars().remove(car);
                        carsPassed++;

                        // Push the removed car onto the stack
                        removedCars.push(car);
                    }

                    System.out.println("\n");
                }

                // Re-enqueue the updated wegdek
                tempQueue.enqueue(wegdek);
            }

            // Restore the original queue order
            while (!tempQueue.isEmpty()) {
                wegdekQueue.enqueue(tempQueue.dequeue());
            }

        } while (carsRemaining);

        System.out.println("Alle auto's zijn doorgereden.");
        System.out.println("Aantal rondes: " + roundCounter + "\n");

//-------------------------------------------------------------------------------------------------------------//
//                                              Reverse Auto's Simulatie                                       //
//-------------------------------------------------------------------------------------------------------------//
        // Optional: Print the cars in the stack
        System.out.println("""
                reverse simulation:
                alle auto's worden op hun beginpositie geplaatst""");
        removedCars.printStack();

    }

}
