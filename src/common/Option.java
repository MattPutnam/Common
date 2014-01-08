package common;

public final class Option<T> implements Copyable<Option<T>> {
	public static <U> Option<U> None() {
		return new Option<>(null);
	}
	
	public static <U> Option<U> Some(U obj) {
		if (obj == null)
			throw new IllegalStateException("Cannot create Some(null)");
		return new Option<>(obj);
	}
	
	private final T _obj;
	
	private Option(T obj) {
		_obj = obj;
	}
	
	public T get() {
		if (_obj == null)
			throw new IllegalStateException("Cannot get None");
		return _obj;
	}
	
	public boolean isSome() {
		return _obj != null;
	}
	
	public boolean isNone() {
		return _obj == null;
	}
	
	public T getOrElse(T fallback) {
		return _obj == null ? fallback : _obj;
	}
	
	@Override
	public boolean equals(Object obj) {
		@SuppressWarnings("unchecked")
		Option<T> ot = (Option<T>) obj;
		return _obj.equals(ot);
	}
	
	@Override
	public int hashCode() {
		return _obj == null ? 0 : _obj.hashCode();
	}
	
	@Override
	public Option<T> copy() {
		return new Option<>(_obj);
	}
	
}
