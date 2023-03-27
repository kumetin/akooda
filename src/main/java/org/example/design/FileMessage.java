package org.example.design;

import java.io.File;

public record FileMessage(File origFile, Message message) {
    
}
