/*
 * Copyright (C) 2016 The Android Open Source Project
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

package gra.com.android.build.gradle.internal.pipeline;

import com.android.annotations.NonNull;
import com.android.build.api.transform.QualifiedContent;
import java.util.Set;

/**
 * Predicate for filtering streams in a {@link FilterableStreamCollection}.
 */
public interface StreamFilter {

    boolean accept(
            @NonNull Set<QualifiedContent.ContentType> types,
            @NonNull Set<? super QualifiedContent.Scope> scopes);

    StreamFilter DEX = (types, scopes) -> types.contains(ExtendedContentType.DEX);

    StreamFilter DEX_ARCHIVE = (types, scopes) -> types.contains(ExtendedContentType.DEX_ARCHIVE);

    StreamFilter RESOURCES =
            (types, scopes) ->
                    types.contains(QualifiedContent.DefaultContentType.RESOURCES)
                            && !scopes.contains(QualifiedContent.Scope.PROVIDED_ONLY)
                            && !scopes.contains(QualifiedContent.Scope.TESTED_CODE);

    StreamFilter PROJECT_RESOURCES =
            (types, scopes) ->
                    types.contains(QualifiedContent.DefaultContentType.RESOURCES)
                            && scopes.size() == 1
                            && scopes.contains(QualifiedContent.Scope.PROJECT);

    StreamFilter NATIVE_LIBS =
            (types, scopes) ->
                    types.contains(ExtendedContentType.NATIVE_LIBS)
                            && !scopes.contains(QualifiedContent.Scope.PROVIDED_ONLY)
                            && !scopes.contains(QualifiedContent.Scope.TESTED_CODE);
}
