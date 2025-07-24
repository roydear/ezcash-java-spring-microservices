package com.ec.authservice.grpc;

import account.AccountRequest;
import account.AccountResponse;
import account.AccountServiceGrpc;
import account.AccountServiceGrpc.AccountServiceBlockingStub;
import io.grpc.ManagedChannel;
import io.grpc.ManagedChannelBuilder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

@Service
public class AccountServiceGrpcClient {

    private static final Logger log = LoggerFactory.getLogger(AccountServiceGrpcClient.class);
    private final AccountServiceBlockingStub blockingStub;

    public AccountServiceGrpcClient(
            @Value("${account.service.address:localhost}") String serverAddress,
            @Value("${account.service.grpc.port:9002}") int serverPort
    ) {
        log.info("Connecting to Account Service GRPC service at {}:{}", serverAddress, serverPort);

        ManagedChannel channel = ManagedChannelBuilder.forAddress(serverAddress, serverPort)
                .usePlaintext()
                .build();

        blockingStub = AccountServiceGrpc.newBlockingStub(channel);
    }

    public AccountResponse createUserAccount(
            String userId,
            String firstName,
            String lastName,
            String address,
            String dateOfBirth
    ) {

        AccountRequest request = AccountRequest.newBuilder()
                .setUserId(userId)
                .setFirstName(firstName)
                .setLastName(lastName)
                .setAddress(address)
                .setDateOfBirth(dateOfBirth)
                .build();

        AccountResponse response = blockingStub.createUserAccount(request);
        log.info("Received response from Account Service via GRPC: {}", response);
        return response;
    }
}
