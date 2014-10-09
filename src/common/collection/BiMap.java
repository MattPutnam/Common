package common.collection;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;

public class BiMap<A, B> {
  private final Map<A, B> _m1;
  private final Map<B, A> _m2;
  
  public BiMap() {
    _m1 = new HashMap<>();
    _m2 = new HashMap<>();
  }
  
  public void put(A a, B b) {
    _m1.put(a, b);
    _m2.put(b, a);
  }
  
  public B get(A a) {
    return _m1.get(a);
  }
  
  public A getReverse(B b) {
    return _m2.get(b);
  }

  public void clear() {
    _m1.clear();
    _m2.clear();
  }

  public boolean containsKey(A key) {
    return _m1.containsKey(key);
  }

  public boolean containsValue(B value) {
    return _m2.containsKey(value);
  }

  public Set<Entry<A, B>> entrySet() {
    return _m1.entrySet();
  }

  public boolean isEmpty() {
    return _m1.isEmpty();
  }

  public Set<A> keySet() {
    return _m1.keySet();
  }

  public void putAll(Map<? extends A, ? extends B> m) {
    for (Entry<? extends A, ? extends B> entry : m.entrySet()) {
      put(entry.getKey(), entry.getValue());
    }
  }

  public B remove(A key) {
    final B b = _m1.remove(key);
    _m2.remove(b);
    return b;
  }

  public int size() {
    return _m1.size();
  }

  public Collection<B> values() {
    return _m1.values();
  }
}
