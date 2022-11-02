package ru.otus.services.processors;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import ru.otus.lib.SensorDataBufferedWriter;
import ru.otus.api.SensorDataProcessor;
import ru.otus.api.model.SensorData;

import java.util.*;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.CopyOnWriteArrayList;

// Этот класс нужно реализовать
public class SensorDataProcessorBuffered implements SensorDataProcessor {
    private static final Logger log = LoggerFactory.getLogger(SensorDataProcessorBuffered.class);

    private final int bufferSize;
    private final SensorDataBufferedWriter writer;
    private final ArrayBlockingQueue<SensorData> queue;
    private final List<SensorData> bufferedData = new CopyOnWriteArrayList<>();

    public SensorDataProcessorBuffered(int bufferSize, SensorDataBufferedWriter writer) {
        this.bufferSize = bufferSize;
        this.writer = writer;
        this.queue = new ArrayBlockingQueue(bufferSize, true);
    }

    @Override
    public synchronized void process(SensorData data) {
        if (data.getValue() == null || data.getValue().isNaN()) {
            return;
        }
        if (queue.size() == bufferSize) {
            flush();
        }
        queue.offer(data);
    }

    public synchronized void flush() {
        try {
            bufferedData.clear();
            queue.drainTo(bufferedData);
            bufferedData.sort(Comparator.comparing(SensorData::getMeasurementTime));
            if (bufferedData.size() > 0) {
                writer.writeBufferedData(bufferedData);
            }
            queue.clear();
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
