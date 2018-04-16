package com.kitaphana.Service;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;

public class Trie {
    protected Node root;
    protected int size = 10;
    protected Connection connection;
    protected Statement statement;
    protected HashMap<String, Integer> priority;
    Trie(Connection connection) throws SQLException {
        this.connection = connection;
        this.statement = connection.createStatement();
        initializeTrie();
        priority = new HashMap<>();
    }
    class Node
    {
        HashMap<Character, Node> next = new HashMap<Character, Node>();
        ArrayList<String> possible_strings = new ArrayList<String>();
        boolean isEndOfWord;
        Node(){
            isEndOfWord = false;
        }
    };
    public void initializeTrie() throws SQLException {
        ResultSet resultSet = statement.executeQuery("SELECT * from search");
        while(resultSet.next()){
            String query = resultSet.getString("query");
            int amount = resultSet.getInt("amount");
            priority.put(query, amount);
            add(query);
        }
    }

    public void add(String s){
        Node curNode = root;
        for(int i = 0; i<s.length(); i++){
            if(curNode.possible_strings.size()<size){
                curNode.possible_strings.add(s);
            }
            else if(curNode.possible_strings.size()==size && priority.get(curNode.possible_strings.get(size-1)) < priority.get(s)){
                curNode.possible_strings.set(size-1, s);
            }
            Collections.sort(curNode.possible_strings);
            Character next = s.charAt(i);
            if(curNode.next.containsKey(next)){
                curNode = curNode.next.get(next);
            }
            else{
                Node newNode = new Node();
                curNode.next.put(next, newNode);
                curNode = newNode;
            }
        }
        curNode.possible_strings.add(s);
        curNode.isEndOfWord = true;
    }
    public Node find(String s){
        Node curNode = root;
        for(int i = 0; i<s.length(); i++){
            Character next = s.charAt(i);
            if(!curNode.next.containsKey(next)){
                return null;
            }
            else{
                curNode = curNode.next.get(next);
            }
        }
        return curNode;
    }
    public ArrayList<String> get(String s){
        Node curNode = root;
        for(int i = 0; i<s.length(); i++){
            Character next = s.charAt(i);
            if(!curNode.next.containsKey(next)){
                return curNode.possible_strings;//Если впереди идут ошибки, то возврщаем последние возможные слова
            }
            else{
                curNode = curNode.next.get(next);
            }
        }
        return curNode.possible_strings;
    }
    public void setSize(int size){
        this.size = size;
    }
}
