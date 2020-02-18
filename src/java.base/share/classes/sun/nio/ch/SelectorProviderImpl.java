/*
 * Copyright (c) 2000, 2019, Oracle and/or its affiliates. All rights reserved.
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

package sun.nio.ch;

import java.io.IOException;
import java.net.ProtocolFamily;
import java.net.StandardProtocolFamily;
import java.nio.channels.DatagramChannel;
import java.nio.channels.Pipe;
import java.nio.channels.ServerSocketChannel;
import java.nio.channels.SocketChannel;
import java.nio.channels.spi.AbstractSelector;
import java.nio.channels.spi.SelectorProvider;

public abstract class SelectorProviderImpl
    extends SelectorProvider
{
    @Override
    public DatagramChannel openDatagramChannel() throws IOException {
        return new DatagramChannelImpl(this, /*interruptible*/true);
    }

    /**
     * SelectorProviderImpl specific method to create a DatagramChannel that
     * is not interruptible.
     */
    public DatagramChannel openUninterruptibleDatagramChannel() throws IOException {
        return new DatagramChannelImpl(this, /*interruptible*/false);
    }

    @Override
    public DatagramChannel openDatagramChannel(ProtocolFamily family) throws IOException {
        return new DatagramChannelImpl(this, family, /*interruptible*/true);
    }

    @Override
    public Pipe openPipe() throws IOException {
        return new PipeImpl(this);
    }

    @Override
    public abstract AbstractSelector openSelector() throws IOException;

    @Override
    public ServerSocketChannel openServerSocketChannel() throws IOException {
        return new InetServerSocketChannelImpl(this);
    }

    @Override
    public SocketChannel openSocketChannel() throws IOException {
        return new InetSocketChannelImpl(this);
    }

    public SocketChannel openSocketChannel(ProtocolFamily family) throws IOException {
        if (family == StandardProtocolFamily.INET6 && !Net.isIPv6Available()) {
            throw new UnsupportedOperationException("IPv6 not available");
        } else if (family == StandardProtocolFamily.INET || family == StandardProtocolFamily.INET6)  {
            return new InetSocketChannelImpl(this, family);
        } else if (family == StandardProtocolFamily.UNIX && Net.isUnixDomainSupported()) {
            return new UnixDomainSocketChannelImpl(this, Net.unixDomainSocket(), false);
        } else
            return super.openSocketChannel(family);
    }

    public ServerSocketChannel openServerSocketChannel(ProtocolFamily family) throws IOException {
        if (family == StandardProtocolFamily.INET6 && !Net.isIPv6Available()) {
            throw new UnsupportedOperationException("IPv6 not available");
        } else if (family == StandardProtocolFamily.INET || family == StandardProtocolFamily.INET6)  {
            return new InetServerSocketChannelImpl(this, family);
        } else if (family == StandardProtocolFamily.UNIX && Net.isUnixDomainSupported()) {
            return new UnixDomainServerSocketChannelImpl(this);
        } else
            return super.openServerSocketChannel(family);
    }
}
