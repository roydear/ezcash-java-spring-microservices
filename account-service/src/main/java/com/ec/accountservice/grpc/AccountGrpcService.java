package com.ec.accountservice.grpc;

import account.AccountRequest;
import account.AccountResponse;
import com.ec.accountservice.mapper.AccountMapper;
import com.ec.accountservice.service.AccountService;
import io.grpc.stub.StreamObserver;
import net.devh.boot.grpc.server.service.GrpcService;
import account.AccountServiceGrpc.AccountServiceImplBase;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@GrpcService
public class AccountGrpcService extends AccountServiceImplBase {

    private static final Logger log = LoggerFactory.getLogger(AccountGrpcService.class);

    private final AccountService accountService;
    private final AccountMapper accountMapper;


    public AccountGrpcService(AccountService accountService, AccountMapper accountMapper) {
        this.accountService = accountService;
        this.accountMapper = accountMapper;
    }

    @Override
    public void createUserAccount(AccountRequest request,
                                  StreamObserver<AccountResponse> responseObserver) {

        log.info("createUserAccount request received {}", request.toString());

        String newAccount = accountService.register(accountMapper.toDTO(request));

        AccountResponse response = AccountResponse.newBuilder()
                .setAccountId(newAccount)
                .setStatus("ACTIVE")
                .build();

        responseObserver.onNext(response);
        responseObserver.onCompleted();
    }
}
