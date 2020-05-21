package com.chentir.favqs.data.utils

/**
 * Copied from Store library.
 * **See** [Store](https://github.com/dropbox/Store/blob/master/app/src/main/java/com/dropbox/android/sample/utils/Lce.kt)
 */
sealed class Lce<out T> {
  open val data: T? = null

  abstract fun <R> map(f: (T) -> R): Lce<R>

  inline fun doOnData(f: (T) -> Unit) {
    if (this is Success) {
      f(data)
    }
  }

  data class Success<out T>(override val data: T) : Lce<T>() {
    override fun <R> map(f: (T) -> R): Lce<R> =
      Success(f(data))
  }

  data class Error(val message: String) : Lce<Nothing>() {
    constructor(t: Throwable) : this(t.message ?: "")

    override fun <R> map(f: (Nothing) -> R): Lce<R> = this
  }

  object Loading : Lce<Nothing>() {
    override fun <R> map(f: (Nothing) -> R): Lce<R> = this
  }
}