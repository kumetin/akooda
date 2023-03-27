package org.example.rules;

import java.io.*;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

public class Rules {
    public static List<MessageRule> from(File file) throws FileNotFoundException {
        InputStream is = new FileInputStream(file);
        return new BufferedReader(new InputStreamReader(is)).lines()
            .map(MessageRule::from).flatMap(Optional::stream)
            .collect(Collectors.toList());
    }
}
