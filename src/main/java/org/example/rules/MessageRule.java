package org.example.rules;

import org.example.design.FieldRule;
import org.example.design.Message;
import org.example.design.Rule;

import java.util.Arrays;
import java.util.Optional;

public class MessageRule extends AndRule {
    
    protected MessageRule(Rule usernameRule,
                          Rule channelRule,
                          Rule messageRule) {
        super(new Rule[]{usernameRule, channelRule, messageRule});
    }
    
    public MessageRule(String sourceUsername, String publicChannelName, String keyword) {
        this(
            new SourceUsernameRule(sourceUsername),
            new PublicChannelNameRule(publicChannelName),
            new MessageKeywordRule(keyword)
            // In part 2 we'll add regex support by replacing the current MessageKeywordRule with a CompositeRule which
            // will combine the current keyword rule with the new regex rule
        );
    }
    
    public static Optional<MessageRule> from(String ruleString) {
        String split[] = ruleString.split("\\|");
        if (split.length != 3) {
            return Optional.empty();
        }
        return Optional.of(new MessageRule(split[0], split[1], split[2]));
    }
    
    private record SourceUsernameRule(String sourceUsername) implements FieldRule {
        @Override
        public boolean matches(Message message) {
            return message.username().equals(sourceUsername) || message.username().equals("all");
        }
        
        @Override
        public Optional<Rule> from(String rule) {
            return Optional.of(new SourceUsernameRule(rule));
        }
    }
    
    private record PublicChannelNameRule(String publicChannelName) implements FieldRule {
        @Override
        public boolean matches(Message message) {
            return message.channel().equals(publicChannelName);
        }
        
        @Override
        public Optional<Rule> from(String channelName) {
            return Optional.of(new PublicChannelNameRule(channelName));
        }
    }
    
    public static class MessageKeywordRule implements FieldRule {
        final String keyword;
        
        private MessageKeywordRule(String keyword) {
            this.keyword = keyword;
        }
        
        @Override
        public boolean matches(Message message) {
            return message.content().contains(keyword);
        }
        
        @Override
        public Optional<Rule> from(String rule) {
            return Optional.of(new MessageKeywordRule(rule));
        }
    
        @Override
        public String toString() {
            return "MessageKeywordRule{" +
                "keyword='" + keyword + '\'' +
                '}';
        }
    }
    
    @Override
    public String toString() {
        return "MessageRule{" +
            "rules=" + Arrays.toString(rules) +
            '}';
    }
}
