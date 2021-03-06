/*
 * Copyright (C) 2017 The Android Open Source Project
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package gra.com.android.build.gradle.external.cmake.server;

/**
 * Cache request's response. More info:
 * https://cmake.org/cmake/help/v3.7/manual/cmake-server.7.html#type-cache
 */
public class CacheResult {
    public final String type;
    public final String cookie;
    public final String inReplyTo;
    public final Cache cache[];

    private CacheResult() {
        this.type = null;
        this.cookie = null;
        this.inReplyTo = null;
        this.cache = null;
    }

    public class Cache {
        public String key = null;
        public String type = null;
        public String value = null;

        public class Property {
            public String ADVANCED = null;
            public String HELPSTRING = null;
        }
    }
}
