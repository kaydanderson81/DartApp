package com.dartapp.model;

import java.util.List;

public class Player {

    Long id;

    String name;
    boolean isPlayerTurn;

    String favouriteDoubles;

    int bestOut;

    int total;

    public Player() {
    }

    public Player(Long id, boolean isPlayerTurn, String name, List<Integer> favouriteDoubles, int bestOut) {
        this.id = id;
        this.isPlayerTurn = isPlayerTurn;
        this.name = name;
        this.favouriteDoubles = favouriteDoubles.toString();
        this.bestOut = bestOut;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public boolean isPlayerTurn() {
        return isPlayerTurn;
    }

    public void setPlayerTurn(boolean playerTurn) {
        isPlayerTurn = playerTurn;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getFavouriteDoubles() {
        return favouriteDoubles;
    }

    public void setFavouriteDoubles(String favouriteDoubles) {
        this.favouriteDoubles = favouriteDoubles;
    }

    public int getBestOut() {
        return bestOut;
    }

    public void setBestOut(int bestOut) {
        this.bestOut = bestOut;
    }

    public int getTotal() {
        return total;
    }

    public void setTotal(int total) {
        this.total = total;
    }

    @Override
    public String toString() {
        return "Player{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", isPlayerTurn=" + isPlayerTurn +
                ", favouriteDoubles='" + favouriteDoubles + '\'' +
                ", bestOut=" + bestOut +
                ", total=" + total +
                '}';
    }
}
