package com.kitaphana.Service;

import com.kitaphana.Database.Database;
import com.kitaphana.exceptions.OperationFailedException;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;

public class Trie {
  protected Node root;
  protected int size = 10;
  protected HashMap<String, Integer> priority;
  private static final String init = "SELECT * FROM search";
  Database db = Database.getInstance();

  Trie() {
    root = new Node();
    priority = new HashMap<>();
    initializeTrie();
  }

  class Node {
    HashMap<Character, Node> next = new HashMap<Character, Node>();
    ArrayList<String> possible_strings = new ArrayList<String>();
    boolean isEndOfWord;

    Node() {
      isEndOfWord = false;
    }
  }

  public void initializeTrie() {
    try {
      PreparedStatement ps = db.connect().prepareStatement(init);
      ResultSet resultSet = ps.executeQuery();
      while (resultSet.next()) {
        String query = resultSet.getString("query");
        int amount = resultSet.getInt("amount");
        priority.put(query, amount);
        add(query);
      }
    } catch (SQLException e) {
      throw new OperationFailedException();
    }
  }

  public void add(String s) {
    Node curNode = root;
    for (int i = 0; i < s.length(); i++) {
      if (curNode.possible_strings.size() < size) {
        curNode.possible_strings.add(s);
      } else if (curNode.possible_strings.size() == size && priority.get(curNode.possible_strings.get(size - 1)) < priority.get(s)) {
        curNode.possible_strings.set(size - 1, s);
      }
      Collections.sort(curNode.possible_strings);
      Character next = s.charAt(i);
      if (curNode.next.containsKey(next)) {
        curNode = curNode.next.get(next);
      } else {
        Node newNode = new Node();
        curNode.next.put(next, newNode);
        curNode = newNode;
      }
    }
    curNode.possible_strings.add(s);
    curNode.isEndOfWord = true;
  }

  public Node find(String s) {
    Node curNode = root;
    for (int i = 0; i < s.length(); i++) {
      Character next = s.charAt(i);
      if (!curNode.next.containsKey(next)) {
        return null;
      } else {
        curNode = curNode.next.get(next);
      }
    }
    return curNode;
  }

  public ArrayList<String> get(String s) {
    return find(s).possible_strings;
  }

  public void setSize(int size) {
    this.size = size;
  }
}
