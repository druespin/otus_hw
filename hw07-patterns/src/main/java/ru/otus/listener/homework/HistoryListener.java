package ru.otus.listener.homework;

import ru.otus.listener.Listener;
import ru.otus.model.Message;

import java.util.*;

public class HistoryListener implements Listener, HistoryReader {

    private final Map<Long, Deque> map = new HashMap<>();
    private Deque<Message> deque;

    @Override
    public void onUpdated(Message msg) {
        System.out.println("Message updated: " + msg + "\n");
        deque = map.get(msg.getId()) == null ? new ArrayDeque<>() : map.get(msg.getId());
        deque.push(msg.getCopy());
        map.put(msg.getId(), deque);
    }

    @Override
    public Optional<Message> findMessageById(long id) {
        return Optional.of((Message) Objects.requireNonNull(map.get(id).peekFirst()));
    }

    public void saveMessage(Message msg) {
        deque = map.get(msg.getId()) == null ? new ArrayDeque<>() : map.get(msg.getId());
        deque.push(msg);
        map.put(msg.getId(), deque);
    }

    public void printHistory() {
        System.out.println("Message History: ");
        map.keySet().forEach(id ->
            map.get(id).forEach(msg ->
                    System.out.println("id=" + id + ": " + msg + "\n")
            )
        );
    }
}
