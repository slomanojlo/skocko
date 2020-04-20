package com.sloman.rs.skocko.util

import java.util.concurrent.Executors

private val IO_EXECUTOR = Executors.newSingleThreadExecutor()

/** Utility method to run blocks on a dedicated background thread, used for io/database work.
 * We could have chose as well an approached with Deferred and Coroutines to handle expections.*/
fun ioThread(f : () -> Unit) {
    IO_EXECUTOR.execute(f)
}