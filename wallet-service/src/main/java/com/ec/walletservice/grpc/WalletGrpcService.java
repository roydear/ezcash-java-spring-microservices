package com.ec.walletservice.grpc;

import com.ec.walletservice.service.WalletService;
import io.grpc.stub.StreamObserver;
import net.devh.boot.grpc.server.service.GrpcService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import wallet.WalletServiceGrpc.WalletServiceImplBase;
import wallet.WalletRequest;
import wallet.WalletResponse;

@GrpcService
public class WalletGrpcService extends WalletServiceImplBase {

    private final WalletService walletService;

    public WalletGrpcService(WalletService walletService) {
        this.walletService = walletService;
    }

    private static final Logger log = LoggerFactory.getLogger(WalletGrpcService.class);

    @Override
    public void createWalletAccount(WalletRequest walletRequest,
                                    StreamObserver<WalletResponse> responseObserver) {

        log.info("createWalletAccount request received {}", walletRequest.toString());

        String walletId = walletService.createWalletAccount(walletRequest.getUserId());
        WalletResponse response = WalletResponse.newBuilder()
                .setWalletId(walletId)
                .build();

        responseObserver.onNext(response);
        responseObserver.onCompleted();
    }
}
