package com.github.brotherlogic.recordselector;

import java.util.concurrent.TimeUnit;

import io.grpc.ManagedChannel;
import io.grpc.ManagedChannelBuilder;
import recordgetter.RecordGetterGrpc;
import recordgetter.Recordgetter;
import recordgetter.Recordgetter.GetRecordResponse;

public class Getter {

    String host;
    int port;

    public Getter(String h, int p) {
        host = h;
        port = p;
    }

    public GetRecordResponse getRecord(boolean refresh) throws Exception {
        GetRecordResponse response = null;
        if (host != null) {
            ManagedChannel channel = ManagedChannelBuilder.forAddress(host, port).usePlaintext(true).build();
            RecordGetterGrpc.RecordGetterBlockingStub client = RecordGetterGrpc.newBlockingStub(channel);
            if (refresh) {
                System.err.println("Request refresh!");
            }
            response = client.withDeadlineAfter(1, TimeUnit.SECONDS).getRecord(Recordgetter.GetRecordRequest.newBuilder().setRefresh(refresh).build());
            channel.shutdown().awaitTermination(1, TimeUnit.SECONDS);
        }
        return response;
    }

    public void setScore(int value) throws Exception {
        System.err.println("SETTING SCORE " + value);
    }
}
