package com.example.mytaxitask.domain.model


sealed interface Either<T> {
    class Right<T>(val data: T) : Either<T>
    class Left<T>(val message: Message) : Either<T>

}