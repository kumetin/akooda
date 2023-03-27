package org.example;

import org.example.rules.MessageRule;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.List;
import java.util.Optional;
import org.example.design.FileMessage;
import java.util.stream.Stream;

public class AkoodaMain {
    public static void main(String[] args) throws IOException {
        String messagesPath;
        String rulesPath;
        if (args.length < 1) messagesPath = args[0]; else throw new RuntimeException("Messages path is not specified");
        if (args.length < 2) rulesPath    = args[1]; else throw new RuntimeException("Rules path is not specified");
        Stream<FileMessage> messageStream = new MessageReader().messageStream(new File(messagesPath));
        List<MessageRule> ruleList = Files.lines(Paths.get(rulesPath)).map(MessageRule::from)
            .filter(Optional::isPresent).map(Optional::get).toList();
        //printRules(ruleList);
        messageStream.forEach(m -> {
            if (ruleList.stream().anyMatch(r -> r.matches(m.message()))) {
                System.out.printf("%s: %s; %s|%s%n", m.origFile(), m.message().username(), m.message().channel(), m.message().content());
            } else {
                //System.out.printf("NO MATCH %s: %s; %s|%s%n", m.origFile(), m.message().username(), m.message().channel(), m.message().content());
            }
        });
    }
    
    private static void printRules(List<MessageRule> rules) {
        System.out.println("Loaded rules:");
        for (MessageRule rule : rules) {
            System.out.printf("\t%s%n",rule);
        }
    }
}
