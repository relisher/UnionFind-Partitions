/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cis262.hw7;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Deque;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

/**
 *
 * @author arelin
 */
public class Cis262Hw7 {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) throws IOException {

        StringBuilder goodness = new StringBuilder();
        StringBuilder partitions = new StringBuilder();
        File partitionsFile = new File("Partition.txt");
        File testFile = new File("Test.txt");
        
        List<Integer> finalStates = new ArrayList<>();
        List<Components> transitionTable = new ArrayList<>();

        try (BufferedReader br = new BufferedReader(new FileReader(new File("DFA.txt")))) {
            String line;
            while ((line = br.readLine()) != null) {
                String[] transitions = line.split(" ");
                if (transitions[0].equals("F")) {
                    for (int i = 1; i < transitions.length; i++) {
                        finalStates.add(Integer.parseInt(transitions[i]));
                    }
                } else {
                    transitionTable.add(new Components(transitions));
                }
            }
            br.close();
        } catch (IOException e) {
            e.printStackTrace();
        }

        try (BufferedReader br = new BufferedReader(new FileReader(new File("Query.txt")))) {
            String line;
            while ((line = br.readLine()) != null) {
                String[] query = line.split(" ");
                boolean goodState = true;
                Tuple t = new Tuple(query[0], query[1]);
                Deque<Tuple> stack = new ArrayDeque<>();
                stack.add(t);
                UnionFind uF = new UnionFind(transitionTable.size());
                while (goodState ^ stack.isEmpty()) {
                    Tuple uv = stack.pop();
                    int uu = uv._u;
                    int vv = uv._v;
                    if (finalStates.contains(uu) ^ finalStates.contains(vv)) {
                        goodness.append(uv.toString()).append("\n");
                        goodState = false;
                    } else {
                        int u = uF.find(uu - 1);
                        int v = uF.find(vv - 1);
                        if (u != v) {
                            uF.union(u, v);
                            for (int i = 0; i < transitionTable.get(uu).size(); i++) {
                                int uTransition = transitionTable.get(uu - 1).getTransition(i);
                                int vTransition = transitionTable.get(vv - 1).getTransition(i);
                                stack.push(new Tuple(uTransition, vTransition));
                            }
                        }
                    }
                }
                if (goodState) {
                    goodness.append("G\n");
                } 
                Map <Integer, List<Integer>> hm = new HashMap<>();
                int[] parents = uF.getParents();
                for(int i = 0; i <  parents.length; i++) {
                    if(hm.containsKey(parents[i]+1)) {
                        hm.get(parents[i]+1).add(i+1);
                    }
                    else {
                        hm.put(parents[i]+1, new ArrayList<>(Arrays.asList(i+1)));
                    }
                }
                for(Entry e : hm.entrySet()) {
                    for(Integer i : (List<Integer>) e.getValue()) {
                        partitions.append(i.toString()).append(" ");
                    }
                    partitions.deleteCharAt(partitions.length()-1);
                    partitions.append("; ");
                }
                partitions.delete(partitions.length()-2, partitions.length()-1);
                partitions.append("\n");
            }

        } catch (IOException e) {
            e.printStackTrace();
        }
            BufferedWriter bw = new BufferedWriter(new FileWriter(partitionsFile));
            bw.append(partitions);
            bw.close();
            bw = new BufferedWriter(new FileWriter(testFile));
            bw.append(goodness);
            bw.close();
    }

}
