package com.example.tom.projetcsc;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.concurrent.ThreadLocalRandom;

public class PlayerList {

    private static ArrayList<Player> playerList = new ArrayList<>();
    public static Player currentNarrator;

    public static ArrayList<Player> getPlayerList() {
        return playerList;
    }


    public static void setPlayerList(ArrayList<Player> playerList) {
        PlayerList.playerList = playerList;
    }

    public static void addPlayer(Player newPlayer){
        playerList.add(newPlayer);
    }

    public static String[] getPlayerNames(){
        String[] playerNames = new String[playerList.size()];
        Iterator<Player> it = playerList.iterator();
        int i = 0;
        while (it.hasNext()) {
            playerNames[i] = it.next().name;
            i++;
        }
        return playerNames;
    }

    public static Player getPlayerByName(String playerName){
        Iterator<Player> it = playerList.iterator();
        while (it.hasNext()) {
            Player cur = it.next();
            if (playerName == cur.name){
                return cur;
            }
        }
        return null;
    }

    public static void changeNarrator(Player oldNarrator, Player newNarrator){
        newNarrator.isNarrator = true;
        oldNarrator.isNarrator = false;
        oldNarrator.roundsAsNarrator=0;
        currentNarrator=newNarrator;
    }

    public static Player getRandomPlayer(){
        int rd = ThreadLocalRandom.current().nextInt(playerList.size());
        return playerList.get(rd);
    }

    public static Player getRandomPlayerDifferentFrom(Player notThisPlayer){
        int rd = ThreadLocalRandom.current().nextInt(playerList.size());
        while (playerList.get(rd)==notThisPlayer){
            rd = ThreadLocalRandom.current().nextInt(playerList.size());
        }
        return playerList.get(rd);
    }

    public static void clearPlayerList(){
        playerList = new ArrayList<>();
    }

    public static void clearScores() {
        Iterator<Player> it = playerList.iterator();
        while (it.hasNext()) {
            Player cur = it.next();
                cur.score=0;
        }
    }
}
