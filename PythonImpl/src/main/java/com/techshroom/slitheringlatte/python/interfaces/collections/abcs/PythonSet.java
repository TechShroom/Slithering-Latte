package com.techshroom.slitheringlatte.python.interfaces.collections.abcs;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.Optional;
import java.util.stream.Stream;

import com.google.common.base.Predicate;
import com.google.common.base.Suppliers;
import com.google.common.base.Throwables;
import com.google.common.collect.FluentIterable;
import com.google.common.reflect.TypeToken;
import com.techshroom.slitheringlatte.python.Builtins;
import com.techshroom.slitheringlatte.python.annotations.PythonName;
import com.techshroom.slitheringlatte.python.error.ArithmeticError;
import com.techshroom.slitheringlatte.python.interfaces.ComparableMixin;
import com.techshroom.slitheringlatte.python.interfaces.SharedInterfaceObjectSpace;
import com.techshroom.slitheringlatte.python.interfaces.generated.OperatorAnd;
import com.techshroom.slitheringlatte.python.interfaces.generated.OperatorOr;
import com.techshroom.slitheringlatte.python.interfaces.generated.OperatorRAnd;
import com.techshroom.slitheringlatte.python.interfaces.generated.OperatorROr;
import com.techshroom.slitheringlatte.python.interfaces.generated.OperatorRSub;
import com.techshroom.slitheringlatte.python.interfaces.generated.OperatorRXor;
import com.techshroom.slitheringlatte.python.interfaces.generated.OperatorSub;
import com.techshroom.slitheringlatte.python.interfaces.generated.OperatorXor;

/**
 * A set is a finite, iterable container.
 * 
 * This class provides concrete generic implementations of all methods except
 * for {@link #contains(Object)}, {@link #iterator()} and {@link #length()}.
 * 
 * To override the comparisons (presumably for speed, as the semantics are
 * fixed), redefine {@link #lessThanOrEqualTo} and {@link #greaterThanOrEqualTo}
 * , then the other operations will automatically follow suit.
 */
@PythonName("collections.abc.Set")
public interface PythonSet<T> extends PythonSized, Iterable<T>,
        PythonContainer<T>, ComparableMixin<PythonSet<T>>, OperatorAnd,
        OperatorOr, OperatorSub, OperatorXor, OperatorRAnd, OperatorROr,
        OperatorRSub, OperatorRXor {

    static <X, S extends PythonSet<? extends X>> TypeToken<S> $getTypeToken(
            Class<?> pythonSetClass) {
        return SharedInterfaceObjectSpace.get(pythonSetClass, "TypeToken",
                () -> new TypeToken<S>(pythonSetClass) {

                    private static final long serialVersionUID = 1L;
                });
    }

    @SuppressWarnings("unchecked")
    @PythonName("_from_iterable")
    static <X, S extends PythonSet<? extends X>> S $fromIterable(
            TypeToken<S> token, Iterable<? extends X> iterable) {
        Class<S> formalClass = (Class<S>) token.getRawType();
        Optional<Method> sub$fromIterable =
                SharedInterfaceObjectSpace.get(
                        formalClass,
                        "$fromIterable",
                        () -> Stream
                                .of(formalClass.getMethods())
                                .filter(m -> m.getName()
                                        .equals("$fromIterable")).findFirst());
        if (sub$fromIterable.isPresent()) {
            try {
                return (S) sub$fromIterable.get().invoke(null, iterable);
            } catch (IllegalAccessException e) {
                throw new IllegalArgumentException(
                        "$fromIterable should be public", e);
            } catch (IllegalArgumentException e) {
                throw new IllegalArgumentException(
                        "$fromIterable should take an Iterable", e);
            } catch (InvocationTargetException e) {
                throw Throwables.propagate(e);
            }
        }
        try {
            Constructor<S> cons =
                    SharedInterfaceObjectSpace.get(formalClass, "<init>",
                            Suppliers.ofInstance(formalClass
                                    .getConstructor(Iterable.class))::get);
            return cons.newInstance(iterable);
        } catch (Exception e) {
            if (e instanceof NoSuchMethodException) {
                throw new IllegalStateException(formalClass
                        + " should override $fromIterable", e);
            } else {
                throw Throwables.propagate(e);
            }
        }
    }

    @Override
    default int compareTo(PythonSet<T> o) {
        boolean le = false;
        if (length() == o.length() && (le = lessThanOrEqualTo(o))) {
            return 0;
        }
        if (!le) {
            // automatically greater than
            return 1;
        }
        boolean ge = greaterThanOrEqualTo(o);
        if (ge) {
            // equal
            return 0;
        }
        return -1;
    }

    @Override
    default boolean greaterThanOrEqualTo(PythonSet<T> other) {
        if (length() < other.length()) {
            return false;
        }
        for (T t : this) {
            if (!other.contains(t)) {
                return false;
            }
        }
        return true;
    }

    @Override
    default boolean lessThanOrEqualTo(PythonSet<T> other) {
        if (length() > other.length()) {
            return false;
        }
        for (T t : this) {
            if (!other.contains(t)) {
                return false;
            }
        }
        return true;
    }

    @PythonName("isdisjoint")
    default boolean isDisjoint(PythonSet<T> other) {
        for (T t : this) {
            if (!other.contains(t)) {
                return false;
            }
        }
        return true;
    }

    @Override
    default public Object and(Object other) {
        if (!(other instanceof Iterable)) {
            return Builtins.NotImplemented;
        }
        @SuppressWarnings("unchecked")
        Iterable<? extends T> other_ = (Iterable<? extends T>) other;
        return $fromIterable($getTypeToken(getClass()), other_);
    }

    @Override
    default public Object rAnd(Object other) {
        return and(other);
    }

    @Override
    default public Object or(Object other) {
        if (!(other instanceof Iterable)) {
            return Builtins.NotImplemented;
        }
        @SuppressWarnings("unchecked")
        Iterable<? extends T> other_ = (Iterable<? extends T>) other;
        return $fromIterable($getTypeToken(getClass()),
                FluentIterable.from(this).append(other_));
    }

    @Override
    default public Object rOr(Object other) {
        return or(other);
    }

    @Override
    default public Object sub(Object other) {
        if (!(other instanceof PythonSet)) {
            if (!(other instanceof Iterable)) {
                return Builtins.NotImplemented;
            }
            other =
                    $fromIterable($getTypeToken(getClass()),
                            (Iterable<?>) other);
        }
        @SuppressWarnings("unchecked")
        PythonSet<T> other_ = (PythonSet<T>) other;
        return $fromIterable($getTypeToken(getClass()),
                FluentIterable.from(this).filter(new Predicate<T>() {

                    @Override
                    public boolean apply(T input) {
                        return !other_.contains(input);
                    }

                }));
    }

    @Override
    default public Object rSub(Object other) {
        if (!(other instanceof PythonSet)) {
            if (!(other instanceof Iterable)) {
                return Builtins.NotImplemented;
            }
            other =
                    $fromIterable($getTypeToken(getClass()),
                            (Iterable<?>) other);
        }
        @SuppressWarnings("unchecked")
        PythonSet<T> other_ = (PythonSet<T>) other;
        return $fromIterable($getTypeToken(getClass()),
                FluentIterable.from(other_).filter(new Predicate<T>() {

                    @Override
                    public boolean apply(T input) {
                        return !contains(input);
                    }

                }));
    }

    @SuppressWarnings("unchecked")
    @Override
    default public Object xor(Object other) {
        if (!(other instanceof PythonSet)) {
            if (!(other instanceof Iterable)) {
                return Builtins.NotImplemented;
            }
            other =
                    $fromIterable($getTypeToken(getClass()),
                            (Iterable<?>) other);
        }
        PythonSet<T> other_ = (PythonSet<T>) other;
        PythonSet<T> subResult1 = (PythonSet<T>) sub(other_);
        PythonSet<T> subResult2 = (PythonSet<T>) other_.sub(this);
        return subResult1.or(subResult2);
    }

    @Override
    default public Object rXor(Object other) {
        return xor(other);
    }

    default int _hash() {
        long MAX = 63;
        long MASK = 2 * MAX + 1;
        long n = length();
        long h = 1927868237 * (n + 1);
        h &= MASK;
        for (T t : this) {
            long hx = Builtins.hash(t);
            h ^= (hx ^ (hx << 16) ^ 89869747) * 3644798167L;
            h &= MASK;
        }
        h = h * 69069 + 907133923;
        h &= MASK;
        if (h > MAX) {
            h -= MASK + 1;
        }
        if (h == -1) {
            h = 590923713;
        }
        if (h > Integer.MAX_VALUE || h < Integer.MIN_VALUE) {
            throw new ArithmeticError("Overflowed integer in hash...");
        }
        return (int) h;
    }

}
