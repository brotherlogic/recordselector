package com.github.brotherlogic.recordselector;

import java.util.concurrent.TimeUnit;

import godiscogs.Godiscogs.Release;
import io.grpc.ManagedChannel;
import io.grpc.ManagedChannelBuilder;
import recordgetter.RecordGetterGrpc;
import recordgetter.Recordgetter;

public class Getter {

    public Release getRecord(String host, int port, boolean refresh) {
        Release response = null;
        if (host != null) {
            ManagedChannel channel = ManagedChannelBuilder.forAddress(host, port).usePlaintext(true).build();
            try {
                RecordGetterGrpc.RecordGetterBlockingStub client = RecordGetterGrpc.newBlockingStub(channel);
                if (refresh) {
                		System.err.println("Request refresh!");
                }
                response = client.getRecord(Recordgetter.GetRecordRequest.newBuilder().setRefresh(refresh).build())
                        .getRelease();
            } catch (Exception e) {
                e.printStackTrace();
            }
            try {

                channel.shutdown().awaitTermination(5, TimeUnit.SECONDS);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
        return response;
    }
}
