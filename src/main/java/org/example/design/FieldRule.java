package org.example.design;

import java.util.Optional;

public interface FieldRule extends Rule {
    boolean matches(Message message);
    Optional<Rule> from(String rule);
}
