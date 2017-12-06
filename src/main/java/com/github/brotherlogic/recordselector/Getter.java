package com.github.brotherlogic.recordselector;

import java.util.concurrent.TimeUnit;

import godiscogs.Godiscogs.Release;
import io.grpc.ManagedChannel;
import io.grpc.ManagedChannelBuilder;
import recordgetter.RecordGetterGrpc;
import recordgetter.Recordgetter;

public class Getter {

	public Release getRecord(String host, int port) {
		ManagedChannel channel = ManagedChannelBuilder.forAddress(host, port).usePlaintext(true).build();
		RecordGetterGrpc.RecordGetterBlockingStub client = RecordGetterGrpc.newBlockingStub(channel);
		Release response = client.getRecord(Recordgetter.Empty.getDefaultInstance()).getRelease();
		try {
			channel.shutdown().awaitTermination(5, TimeUnit.SECONDS);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		return response;
	}
}
