/*
 * Copyright 2018, Robert Dyer, Mohd Arafat
 *                 and Bowling Green State University
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package boa.graphs.pdg;

import boa.graphs.cdg.CDGNode;
import boa.types.Ast.*;
import boa.types.Control;
import boa.types.Control.PDGNode.*;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * Program Dependence Graph builder node
 *
 * @author marafat
 */

public class PDGNode implements Comparable<PDGNode> {

    private int id;
    private Statement stmt;
    private Expression expr;
    private PDGNodeType kind = PDGNodeType.OTHER;

    private String defVariable;
    private HashSet<String> useVariables = new HashSet<String>();

    private ArrayList<PDGNode> successors = new ArrayList<PDGNode>();
    private ArrayList<PDGNode> predecessors = new ArrayList<PDGNode>();
    private HashSet<PDGEdge> inEdges = new HashSet<PDGEdge>();
    private HashSet<PDGEdge> outEdges = new HashSet<PDGEdge>();

    // Constructors

    /**
     * Constructs a PDG node.
     *
     * @param node control dependence graph node
     */
    public PDGNode(final CDGNode node) {
        this.id = node.getId();
        this.stmt = node.getStmt();
        this.expr = node.getExpr();
        this.kind = convertKind(node.getKind());
        this.defVariable = node.getDefVariable();
        this.useVariables = node.getUseVariables();
    }

    /**
     * Constructs a PDG node
     *
     * @param id node id. Uses default values for remaining fields
     */
    public PDGNode(int id) {
        this.id = id;
    }

    // Setters
    public void setId(int id) {
        this.id = id;
    }

    public void setStmt(final Statement stmt) {
        this.stmt = stmt;
    }

    public void setExpr(final Expression expr) {
        this.expr = expr;
    }

    public void setKind(final PDGNodeType kind) {
        this.kind = kind;
    }

    public void setKind(final Control.CDGNode.CDGNodeType kind) {
        this.kind = convertKind(kind);
    }

    public void setDefVariable(final String defVariables) {
        this.defVariable = defVariables;
    }

    public void setUseVariables(final HashSet<String> useVariables) {
        this.useVariables = useVariables;
    }

    public void addUseVariable(final String useVariables) {
        this.useVariables.add(useVariables);
    }

    public void addInEdge(final PDGEdge inEdges) {
        this.inEdges.add(inEdges);
    }

    public void addOutEdge(final PDGEdge outEdges) {
        this.outEdges.add(outEdges);
    }

    public void addSuccessor(final PDGNode node) {
        if (!successors.contains(node))
            successors.add(node);
    }

    public void addPredecessor(final PDGNode node) {
        if (!predecessors.contains(node))
            predecessors.add(node);
    }

    //Getters
    public int getId() {
        return id;
    }

    public boolean hasStmt() { return this.stmt != null; }

    public Statement getStmt() {
        return stmt;
    }

    public boolean hasExpr() {
        return this.expr != null;
    }

    public Expression getExpr() {
        return expr;
    }

    public PDGNodeType getKind() {
        return kind;
    }

    public String getDefVariable() {
        return defVariable;
    }

    public Set<String> getUseVariables() {
        return useVariables;
    }

    public Set<PDGEdge> getInEdges() {
        return inEdges;
    }

    public Set<PDGEdge> getOutEdges() {
        return outEdges;
    }

    public List<PDGNode> getSuccessors() {
        return successors;
    }

    public List<PDGNode> getPredecessors() {
        return predecessors;
    }

    /**
     * Returns list of out edges with control edge at the first location.
     * There can be at max two edges, one control and one data
     *
     * @param node destination node
     * @return list of out edges
     */
    public List<PDGEdge> getOutEdges(PDGNode node) {
        List<PDGEdge> edges = new ArrayList<PDGEdge>();
        for (PDGEdge e: outEdges) {
            if (e.getDest().equals(node))
                if (e.getKind() == Control.PDGEdge.PDGEdgeType.CONTROL)
                    edges.add(0, e);
                else
                    edges.add(e);
        }
        return  edges;
    }

    /**
     * Returns equivalent PDG node type for the given CDG node type
     *
     * @param type CDG node type
     * @return PDG node type for the given CDG node type
     */
    public PDGNodeType convertKind(final Control.CDGNode.CDGNodeType type) {
        switch(type) {
            case ENTRY:
                return PDGNodeType.ENTRY;
            case OTHER:
                return PDGNodeType.OTHER;
            case METHOD:
                return PDGNodeType.METHOD;
            case CONTROL:
                return PDGNodeType.CONTROL;
        }

        return null;
    }

    @Override
    public int compareTo(final PDGNode node) {
        return node.id - this.id;
    }

    @Override
    public boolean equals(final Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        PDGNode pdgNode = (PDGNode) o;

        return id == pdgNode.id;
    }

    @Override
    public int hashCode() {
        return id;
    }

    @Override
    public String toString() {
        return "" + id;
    }
}
