package ru.otus.dataprocessor;

import ru.otus.model.Measurement;

import java.util.*;
import java.util.stream.Collectors;

public class ProcessorAggregator implements Processor {

    @Override
    public Map<String, Double> process(List<Measurement> data) {
        //группирует выходящий список по name, при этом суммирует поля value

        var map = data.stream().collect(
                Collectors.toMap(Measurement::getName, Measurement::getValue, Double::sum)
        );
        return new TreeMap<>(map);
    }
}
