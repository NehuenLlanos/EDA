import java.util.Map;

public interface Bag<T> extends Map<T, Integer> {
    int getCount(T value);
    void add(T value);
    void removeBag(T value);
}
