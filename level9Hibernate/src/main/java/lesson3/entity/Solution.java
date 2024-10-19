package lesson3.entity;

public class Solution {
    public static void main(String[] args) {
        AnimalManager animalManager = new AnimalManager();

        System.out.println("All animals: ");
        for (Animal animal : animalManager.getAllAnimals()) {
            System.out.println(animal.getName());
        }

        Animal dog = new Animal();
        dog.setName("Dina");
        dog.setAge(2);
        dog.setFamily("Dog");

        animalManager.addAnimal(dog);

        System.out.println("All animals, after adding a new one: ");
        for(Animal animal: animalManager.getAllAnimals()){
            System.out.println(animal.getName());
        }

        animalManager.removeAnimal(dog);

        System.out.println("All animals, after removing the new one");
        for(Animal animal: animalManager.getAllAnimals()){
            System.out.println(animal.getName());
        }
    }
}
