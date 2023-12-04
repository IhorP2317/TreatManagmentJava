package utils;

@FunctionalInterface
public interface ThreadTask {

    void doTask(Thread thread);
}
