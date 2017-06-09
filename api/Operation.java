/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package api;

import java.util.ArrayList;

/**
 *
 * @author Jonisan
 */
public class Operation
{
    private String name;
    private ArrayList<Operation> childOperations;
    
    public void operate()
    {}

    /**
     * @return the name
     */
    public String getName()
    {
        return name;
    }

    /**
     * @param name the name to set
     */
    public void setName(String name)
    {
        this.name = name;
    }

    /**
     * @param childOperations the childOperations to set
     */
    public void setChildOperations(ArrayList<Operation> childOperations)
    {
        this.childOperations = childOperations;
    }
}
