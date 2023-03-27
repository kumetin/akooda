package org.example.design;

import java.util.Optional;

public record Message(String username, String channel, String content) {
    public static Optional<Message> from(String rawMessage) {
        String[] split = rawMessage.split("\\|");
        if (split.length!= 3) {
            return Optional.empty();
        }
        return Optional.of(new Message(split[0], split[1], split[2]));
    }
}

