/*
 * Copyright 2019 Vladimir Sitnikov <sitnikov.vladimir@gmail.com>
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 *
 */
package com.github.vlsi.gradle.checksum

import java.util.concurrent.atomic.AtomicInteger
import java.util.concurrent.atomic.AtomicLong

class Stopwatch {
    val elapsed: Long get() = elapsedTime.get()
    val starts: Int get() = startCount.get()
    val bytes: Long get() = bytesProcessed.get()

    private var elapsedTime = AtomicLong()
    private var startCount = AtomicInteger()
    private var bytesProcessed = AtomicLong()

    operator fun <T> invoke(bytes: Long = 0, action: () -> T): T {
        val startTime = System.currentTimeMillis()
        startCount.incrementAndGet()
        bytesProcessed.addAndGet(bytes)
        try {
            return action()
        } finally {
            elapsedTime.addAndGet(System.currentTimeMillis() - startTime)
        }
    }
}
