package org.example.rules;

import org.example.design.Message;
import org.example.design.Rule;

import java.util.Arrays;

public class AndRule extends CompositeRule implements Rule {
    
    protected AndRule(Rule[] rules) {
        super(rules);
    }
    
    @Override
    final public boolean matches(Message message) {
        return Arrays.stream(rules).allMatch(r -> r.matches(message));
    }
}
