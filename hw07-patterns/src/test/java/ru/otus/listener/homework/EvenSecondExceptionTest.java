package ru.otus.listener.homework;

import org.junit.jupiter.api.Test;
import ru.otus.model.Message;
import ru.otus.model.ObjectForMessage;
import ru.otus.processor.homework.EvenSecondProcessor;

import static org.assertj.core.api.Assertions.assertThatExceptionOfType;

public class EvenSecondExceptionTest {

    @Test
    public void getEvenSecondException() {

        Message message = new Message.Builder(1L)
                .field2("1")
                .field1("2")
                .field3("3")
                .field4("4")
                .field5("5")
                .field6("6")
                .field7("7")
                .field8("8")
                .field9("9")
                .field10("10")
                .field11("11")
                .field12("12")
                .field13(new ObjectForMessage())
                .build();

        var processor = new EvenSecondProcessor();

        assertThatExceptionOfType(
                EvenSecondProcessor.EvenSecondException.class).isThrownBy(() -> processor.process(message));
    }
}
