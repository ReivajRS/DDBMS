package Models;

import java.util.ArrayList;

public class ConditionChain {
    private ArrayList<Condition<Object>> chain;
    private ArrayList<String> relation;

    public ConditionChain(ArrayList<Condition<Object>> chain, ArrayList<String> relation){
        this.chain = chain;
        this.relation = relation;
    }

    public ArrayList<Condition<Object>> getChain() {
        return chain;
    }

    public ArrayList<String> getRelation() {
        return relation;
    }
}
