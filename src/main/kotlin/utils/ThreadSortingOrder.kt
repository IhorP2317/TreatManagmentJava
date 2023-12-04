package utils

sealed class ThreadSortingOrder {
    data object Name : ThreadSortingOrder()
    data object State : ThreadSortingOrder()
    data object Priority : ThreadSortingOrder()
    data object Time : ThreadSortingOrder()
    data object None : ThreadSortingOrder()
}