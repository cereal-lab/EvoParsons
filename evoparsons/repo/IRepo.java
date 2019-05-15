package evoparsons.repo;

import java.io.IOException;
import java.util.Arrays;
import java.util.List;
import java.util.Map;

public interface IRepo<ID, T> {
    public T get(ID id);
    public String getName();
    public Map<ID, T> getAll() throws IOException;
    public void update(List<T> entities) throws IOException;
    public default void update(T entity) throws IOException {
        this.update(Arrays.asList(entity));
    }
    public void insert(List<T> entities) throws IOException;
}