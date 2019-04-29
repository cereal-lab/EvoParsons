package evoparsons.repo;

import java.util.Arrays;
import java.util.List;
import java.util.Map;

public interface IRepo<ID, T> {
    public T get(ID id);
    public String getName();
    public Map<ID, T> getAll();
    public void update(List<T> entities);
    public default void update(T entity) {
        this.update(Arrays.asList(entity));
    }
    public void insert(List<T> entities);
}