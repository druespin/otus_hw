package ru.otus.services.processors;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import ru.otus.lib.SensorDataBufferedWriter;
import ru.otus.api.SensorDataProcessor;
import ru.otus.api.model.SensorData;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;

// Этот класс нужно реализовать
public class SensorDataProcessorBuffered implements SensorDataProcessor {
    private static final Logger log = LoggerFactory.getLogger(SensorDataProcessorBuffered.class);

    private final int bufferSize;
    private final SensorDataBufferedWriter writer;
    private final List<SensorData> dataBuffer = new CopyOnWriteArrayList<>();

    public SensorDataProcessorBuffered(int bufferSize, SensorDataBufferedWriter writer) {
        this.bufferSize = bufferSize;
        this.writer = writer;
    }

    @Override
    public synchronized void process(SensorData data) {
        if (data.getValue() == null || data.getValue().isNaN()) {
            return;
        }
        if (dataBuffer.size() < bufferSize) {
            dataBuffer.add(data);
        }
        if (dataBuffer.size() == bufferSize) {
            flush();
            dataBuffer.clear();
        }
    }

    public void flush() {
        try {
            dataBuffer.sort(Comparator.comparing(SensorData::getMeasurementTime));
            var bufferedData = List.copyOf(dataBuffer);

            synchronized (writer) {
                if (bufferedData.size() < bufferSize - 1 && bufferedData.size() != bufferSize / 2) {
                    Thread.currentThread().wait();
                }
                writer.writeBufferedData(bufferedData);
                Thread.currentThread().join(200);
            }
        }
        catch (Exception e) {
            log.error("Ошибка в процессе записи буфера", e);
        }
    }

    @Override
    public void onProcessingEnd() {
        flush();
    }
}
