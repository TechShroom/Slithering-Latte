package com.techshroom.slitheringlatte.ap.underscore.interfaces;

/**
 * Mixin for comparisons that uses the Comparable interface.
 * 
 * @author Kenzie Togami
 *
 * @param <T>
 *            - The type of the other object to compare
 */
public interface ComparableMixin<T> extends Comparable<T>, ComparableGE<T>,
        ComparableGT<T>, ComparableLE<T>, ComparableLT<T>, ComparableEQ<T>,
        ComparableNE<T> {

    @Override
    default boolean greaterThan(T other) {
        return this.compareTo(other) > 0;
    }

    @Override
    default boolean greaterThanOrEqualTo(T other) {
        return this.compareTo(other) >= 0;
    }

    @Override
    default boolean lessThan(T other) {
        return this.compareTo(other) < 0;
    }

    @Override
    default boolean lessThanOrEqualTo(T other) {
        return this.compareTo(other) <= 0;
    }

    @Override
    default boolean equalTo(T other) {
        return this.compareTo(other) == 0;
    }

    @Override
    default boolean notEquals(T other) {
        return this.compareTo(other) != 0;
    }

}
