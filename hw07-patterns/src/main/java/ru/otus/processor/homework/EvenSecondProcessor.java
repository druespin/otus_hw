package ru.otus.processor.homework;

import ru.otus.model.Message;
import ru.otus.processor.Processor;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.time.LocalDateTime;

public class EvenSecondProcessor implements Processor {

    @Override
    public Message process(Message message) {
        while (LocalDateTime.now().getSecond() % 2 != 0) {
            try {
                for (int i = 0; i < 1000; i++) {
                    Files.writeString(Path.of("message"), message.getCopy().toString());
                }
            }
            catch (IOException ignored) {}
        }
        throw new EvenSecondException("\nException Second Number: " + LocalDateTime.now().getSecond());
    }

    public static class EvenSecondException extends RuntimeException {

        EvenSecondException(String text) {
            super(text);
            System.out.println(text);
        }
    }
}
