/*
 * Copyright (C) 2013 Facebook, Inc.
 *
 * Licensed under the Apache License, Version 2.0 (the "License"); you may
 * not use this file except in compliance with the License. You may obtain
 * a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS, WITHOUT
 * WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied. See the
 * License for the specific language governing permissions and limitations
 * under the License.
 */
package io.airlift.drift.transport.netty;

import com.google.common.util.concurrent.ListenableFuture;
import io.airlift.drift.transport.InvokeRequest;
import io.airlift.drift.transport.MethodInvoker;
import io.airlift.drift.transport.MethodMetadata;
import io.airlift.drift.transport.netty.ThriftClientHandler.ThriftRequest;
import io.netty.channel.Channel;

import java.util.List;

import static com.google.common.util.concurrent.Futures.immediateFailedFuture;
import static java.util.Objects.requireNonNull;

class DriftNettyMethodInvoker
        implements MethodInvoker
{
    private final ConnectionManager connectionManager;

    public DriftNettyMethodInvoker(ConnectionManager connectionManager)
    {
        this.connectionManager = requireNonNull(connectionManager, "connectionManager is null");
    }

    @Override
    public ListenableFuture<Object> invoke(InvokeRequest request)
    {
        try {
            InvocationAttempt invocationAttempt = new InvocationAttempt(
                    request.getAddress(),
                    connectionManager,
                    new MethodInvocationFunction(request.getMethod(), request.getParameters()));
            return invocationAttempt.getFuture();
        }
        catch (Exception e) {
            return immediateFailedFuture(e);
        }
    }

    private static class MethodInvocationFunction
            implements InvocationFunction<Channel>
    {
        private final MethodMetadata method;
        private final List<Object> parameters;

        public MethodInvocationFunction(MethodMetadata method, List<Object> parameters)
        {
            this.method = method;
            this.parameters = parameters;
        }

        @Override
        public ListenableFuture<Object> invokeOn(Channel channel)
        {
            try {
                ThriftRequest thriftRequest = new ThriftRequest(method, parameters);
                channel.writeAndFlush(thriftRequest);
                return thriftRequest;
            }
            catch (Throwable throwable) {
                return immediateFailedFuture(throwable);
            }
        }
    }
}