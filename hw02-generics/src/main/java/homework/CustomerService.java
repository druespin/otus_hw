package homework;


import java.util.*;

public class CustomerService {

    //todo: 3. надо реализовать методы этого класса
    //важно подобрать подходящую Map-у, посмотрите на редко используемые методы, они тут полезны

    private final Map<Customer, String> mutableMap = new HashMap<>();

    public Map.Entry<Customer, String> getSmallest() {
        //Возможно, чтобы реализовать этот метод, потребуется посмотреть как Map.Entry сделан в jdk
        Customer smallestKey = Collections.min(immutableCopy().keySet());
        return Map.entry(smallestKey.getCopy(), immutableCopy().get(smallestKey));
    }

    public Map.Entry<Customer, String> getNext(Customer customer) {
        Customer next = immutableCopy().keySet().stream()
                .filter(key -> key.getScores() > customer.getScores())
                .min(Comparator.comparingLong(key -> key.getScores()))
                .orElse(null);
        return next == null ? null : Map.entry(next.getCopy(), immutableCopy().get(next));
    }

    public void add(Customer customer, String data) {
        mutableMap.put(customer, data);
    }

    private Map<Customer, String> immutableCopy() {
        return Map.copyOf(mutableMap);
    }
}
