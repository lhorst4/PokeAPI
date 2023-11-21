package com.example.myapplication;

public class Pokemon implements Comparable<Pokemon> {
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

    public String toString(){
        return String.valueOf(id) + ": " + name;
    }

    @Override
    public int compareTo(Pokemon o) {

        if(o.getId() == this.getId()) {
            return 0;
        }else{
            return 1;
        }
    }

    @Override
    public boolean equals(Object o){
        Pokemon p = (Pokemon) o;

        if(p.getId() == this.getId()){
            return true;
        }else{
            return false;
        }
    }
}
