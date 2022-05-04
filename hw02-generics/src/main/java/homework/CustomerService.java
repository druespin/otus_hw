package homework;


import java.util.*;

public class CustomerService {

    //todo: 3. надо реализовать методы этого класса
    //важно подобрать подходящую Map-у, посмотрите на редко используемые методы, они тут полезны

    private final TreeMap<Customer, String> mutableMap = new TreeMap<>(Comparator.comparingLong(Customer::getScores));

    public Map.Entry<Customer, String> getSmallest() {
        //Возможно, чтобы реализовать этот метод, потребуется посмотреть как Map.Entry сделан в jdk
        Customer leastKey = mutableMap.firstKey();
        return leastKey == null ? null : Map.entry(leastKey.getCopy(), mutableMap.get(leastKey));
    }

    public Map.Entry<Customer, String> getNext(Customer customer) {
        Customer nextKey = mutableMap.higherKey(customer);
        return nextKey == null ? null : Map.entry(nextKey.getCopy(), mutableMap.get(nextKey));
    }

    public void add(Customer customer, String data) {
        mutableMap.put(customer, data);
    }
}
