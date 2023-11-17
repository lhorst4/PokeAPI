package com.example.myapplication;

public class Pokemon {
    String name;
    int id;
    int weight;
    int height;
    int base_experience;
    String move;
    String ability;

    public Pokemon(String name, int id, int weight, int height, int base_experience, String move, String ability){
        this.name = name;
        this.id = id;
        this.weight = weight;
        this.height = height;
        this.base_experience = base_experience;
        this.move = move;
        this.ability = ability;
    }

    public String getName() {
        return name;
    }

    public int getId() {
        return id;
    }

    public int getWeight() {
        return weight;
    }

    public int getHeight() {
        return height;
    }

    public int getBase_experience() {
        return base_experience;
    }

    public String getMove() {
        return move;
    }

    public String getAbility() {
        return ability;
    }
}
