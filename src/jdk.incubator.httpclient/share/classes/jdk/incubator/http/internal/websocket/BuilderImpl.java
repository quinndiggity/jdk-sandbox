/*
 * Copyright (c) 2015, 2017, Oracle and/or its affiliates. All rights reserved.
 * DO NOT ALTER OR REMOVE COPYRIGHT NOTICES OR THIS FILE HEADER.
 *
 * This code is free software; you can redistribute it and/or modify it
 * under the terms of the GNU General Public License version 2 only, as
 * published by the Free Software Foundation.  Oracle designates this
 * particular file as subject to the "Classpath" exception as provided
 * by Oracle in the LICENSE file that accompanied this code.
 *
 * This code is distributed in the hope that it will be useful, but WITHOUT
 * ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or
 * FITNESS FOR A PARTICULAR PURPOSE.  See the GNU General Public License
 * version 2 for more details (a copy is included in the LICENSE file that
 * accompanied this code).
 *
 * You should have received a copy of the GNU General Public License version
 * 2 along with this work; if not, write to the Free Software Foundation,
 * Inc., 51 Franklin St, Fifth Floor, Boston, MA 02110-1301 USA.
 *
 * Please contact Oracle, 500 Oracle Parkway, Redwood Shores, CA 94065 USA
 * or visit www.oracle.com if you need additional information or have any
 * questions.
 */

package jdk.incubator.http.internal.websocket;

import jdk.incubator.http.HttpClient;
import jdk.incubator.http.WebSocket;
import jdk.incubator.http.WebSocket.Builder;
import jdk.incubator.http.WebSocket.Listener;
import jdk.incubator.http.internal.common.Pair;

import java.net.ProxySelector;
import java.net.URI;
import java.time.Duration;
import java.util.Collection;
import java.util.LinkedList;
import java.util.List;
import java.util.Optional;
import java.util.concurrent.CompletableFuture;

import static java.lang.String.format;
import static java.util.Objects.requireNonNull;
import static jdk.incubator.http.internal.common.Pair.pair;

public final class BuilderImpl implements Builder {

    private final HttpClient client;
    private final URI uri;
    private final Listener listener;
    private final Collection<Pair<String, String>> headers = new LinkedList<>();
    private final Collection<String> subprotocols = new LinkedList<>();
    private final Optional<ProxySelector> proxySelector;
    private Duration timeout;

    public BuilderImpl(HttpClient client, URI uri, Listener listener, ProxySelector proxySelector) {
        this.client = requireNonNull(client, "client");
        this.uri = checkURI(requireNonNull(uri, "uri"));
        this.listener = requireNonNull(listener, "listener");
        this.proxySelector = Optional.ofNullable(proxySelector);
        // if the proxy selector was supplied by the user, it should be present
        // on the client and should be the same than what we get as argument.
        assert !client.proxy().isPresent() || client.proxy().get() == proxySelector;
    }

    private static IllegalArgumentException newIAE(String message, Object... args) {
        return new IllegalArgumentException(format(message, args));
    }

    private static URI checkURI(URI uri) {
        String scheme = uri.getScheme();
        if (scheme == null)
            throw newIAE("URI with undefined scheme");
        scheme = scheme.toLowerCase();
        if (!(scheme.equals("ws") || scheme.equals("wss")))
            throw newIAE("invalid URI scheme %s", scheme);
        if (uri.getHost() == null)
            throw newIAE("URI must contain a host: %s", uri);
        if (uri.getFragment() != null)
            throw newIAE("URI must not contain a fragment: %s", uri);
        return uri;
    }

    @Override
    public Builder header(String name, String value) {
        requireNonNull(name, "name");
        requireNonNull(value, "value");
        headers.add(pair(name, value));
        return this;
    }

    @Override
    public Builder subprotocols(String mostPreferred,
                                String... lesserPreferred)
    {
        requireNonNull(mostPreferred, "mostPreferred");
        requireNonNull(lesserPreferred, "lesserPreferred");
        List<String> subprotocols = new LinkedList<>();
        subprotocols.add(mostPreferred);
        for (int i = 0; i < lesserPreferred.length; i++) {
            String p = lesserPreferred[i];
            requireNonNull(p, "lesserPreferred[" + i + "]");
            subprotocols.add(p);
        }
        this.subprotocols.clear();
        this.subprotocols.addAll(subprotocols);
        return this;
    }

    @Override
    public Builder connectTimeout(Duration timeout) {
        this.timeout = requireNonNull(timeout, "timeout");
        return this;
    }

    @Override
    public CompletableFuture<WebSocket> buildAsync() {
        return WebSocketImpl.newInstanceAsync(this);
    }

    HttpClient getClient() { return client; }

    URI getUri() { return uri; }

    Listener getListener() { return listener; }

    Collection<Pair<String, String>> getHeaders() { return headers; }

    Collection<String> getSubprotocols() { return subprotocols; }

    Duration getConnectTimeout() { return timeout; }

    Optional<ProxySelector> proxySelector() { return proxySelector; }
}
