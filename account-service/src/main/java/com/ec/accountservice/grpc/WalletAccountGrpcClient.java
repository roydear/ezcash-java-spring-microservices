package com.ec.accountservice.grpc;

import io.grpc.ManagedChannel;
import io.grpc.ManagedChannelBuilder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import wallet.WalletRequest;
import wallet.WalletResponse;
import wallet.WalletServiceGrpc;
import wallet.WalletServiceGrpc.WalletServiceBlockingStub;

@Service
public class WalletAccountGrpcClient {

    private static final Logger log = LoggerFactory.getLogger(WalletAccountGrpcClient.class);

    private final WalletServiceBlockingStub blockingStub;

    public WalletAccountGrpcClient(
            @Value("${wallet.service.address:localhost}") String serverAddress,
            @Value("${wallet.service.grpc.port:9001}") int serverPort
    ) {
        log.info("Connecting to Wallet Service GRPC service at {}:{}", serverAddress, serverPort);

        ManagedChannel channel = ManagedChannelBuilder.forAddress(serverAddress, serverPort)
                .usePlaintext()
                .build();

        blockingStub = WalletServiceGrpc.newBlockingStub(channel);
    }

    public WalletResponse createWalletAccount(String userId) {

        WalletRequest request = WalletRequest.newBuilder()
                .setUserId(userId)
                .build();

        WalletResponse response = blockingStub.createWalletAccount(request);
        log.info("Received response from Wallet Service via GRPC: {}", response);
        return response;
    }
}
