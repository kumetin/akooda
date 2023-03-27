package org.example.design;

import java.io.File;
import java.io.IOException;
import java.util.stream.Stream;

public interface MessageStream {
    Stream<FileMessage> messageStream(File file) throws IOException;
}

