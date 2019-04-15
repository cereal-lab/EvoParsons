package evoparsons.repo;

import java.util.List;
import java.util.function.Function;

public interface IRepo<ID, T> {
    public T get(ID id);
    public void update(List<T> entity, Function<T, ID> idGetter);
}