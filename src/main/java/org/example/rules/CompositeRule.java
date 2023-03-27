package org.example.rules;


import org.example.design.Rule;

abstract public class CompositeRule implements Rule {
    
    protected final Rule[] rules;
    
    protected CompositeRule(Rule[] rules) {
        this.rules = rules;
    }
    
}

