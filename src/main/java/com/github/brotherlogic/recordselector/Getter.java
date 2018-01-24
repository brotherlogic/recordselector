package com.github.brotherlogic.recordselector;

import java.util.concurrent.TimeUnit;

import godiscogs.Godiscogs.Release;
import io.grpc.ManagedChannel;
import io.grpc.ManagedChannelBuilder;
import recordgetter.RecordGetterGrpc;
import recordgetter.Recordgetter;

public class Getter {

    public Release getRecord(String host, int port, boolean refresh) throws Exception {
        Release response = null;
        if (host != null) {
            ManagedChannel channel = ManagedChannelBuilder.forAddress(host, port).usePlaintext(true).build();
            RecordGetterGrpc.RecordGetterBlockingStub client = RecordGetterGrpc.newBlockingStub(channel);
            if (refresh) {
                System.err.println("Request refresh!");
            }
            response = client.getRecord(Recordgetter.GetRecordRequest.newBuilder().setRefresh(refresh).build())
                    .getRecord().getRelease();
            channel.shutdown().awaitTermination(5, TimeUnit.SECONDS);
        }
        return response;
    }
}