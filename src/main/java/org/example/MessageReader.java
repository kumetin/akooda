package org.example;

import org.example.design.FileMessage;
import org.example.design.Message;
import org.example.design.MessageStream;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Optional;
import java.util.stream.Stream;


public class MessageReader implements MessageStream {
    
    public Stream<FileMessage> messageStream(File file)  {
        if (file.isDirectory()) {
    
            Stream<File> fileStream = null;
            try {
                fileStream = Files.list(file.toPath()).map(Path::toFile);
            } catch (IOException e) {
                System.out.printf("Skipping directory. Unable to list files in %s: %s\n", file.getAbsolutePath(), e.getMessage());
                return Stream.empty();
            }
    
            return fileStream.flatMap(this::messageStream);

        } else {
            return fromFile(file.toPath());
        }
    }
    
    private Stream<FileMessage> fromFile(Path path) {
        try {
            return Files.lines(path).map(Message::from)
                .peek(optMsg -> {
                    if (optMsg.isEmpty()) {
                        System.out.printf("Skipping line. Unable to parse line as content: %s%n", optMsg);
                    }
                })
                .flatMap(Optional::stream)
                .map(msg -> new FileMessage(path.toFile(), msg));
        } catch (IOException e) {
            System.out.printf("Unable to read file: %s%n", path);
            return Stream.empty();
        }
    }

}
