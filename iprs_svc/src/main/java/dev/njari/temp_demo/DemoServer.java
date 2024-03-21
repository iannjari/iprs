package dev.njari.temp_demo;

import io.grpc.stub.StreamObserver;
import io.temporal.api.enums.v1.WorkflowIdReusePolicy;
import io.temporal.client.WorkflowClient;
import io.temporal.client.WorkflowOptions;
import io.temporal.common.RetryOptions;
import iprs.temporal_demo.DemoServiceGrpc;
import iprs.temporal_demo.Order;
import lombok.RequiredArgsConstructor;
import net.devh.boot.grpc.server.service.GrpcService;

import java.time.Duration;

/**
 * @author njari_mathenge
 * on 21/03/2024.
 * github.com/iannjari
 */

@GrpcService
@RequiredArgsConstructor
public class DemoServer extends DemoServiceGrpc.DemoServiceImplBase {

    private final WorkflowClient workflowClient;



    /**
     * @param request
     * @param responseObserver
     */
    @Override
    public void placeOrder(Order request, StreamObserver<Order> responseObserver) {

        WorkflowOptions opts = WorkflowOptions.newBuilder()
                .setWorkflowId("0EV5THAPQY21R")
                .setRetryOptions(RetryOptions.newBuilder()
                        .setInitialInterval(Duration.ofSeconds(1))
                        .setMaximumInterval(Duration.ofSeconds(100))
                        .setBackoffCoefficient(2)
                        .setMaximumAttempts(50000)
                        .build())
                .setWorkflowIdReusePolicy(WorkflowIdReusePolicy.WORKFLOW_ID_REUSE_POLICY_ALLOW_DUPLICATE_FAILED_ONLY)
                .setTaskQueue("demo")
                .build();

        OrderWorkflow workflow = workflowClient.newWorkflowStub(OrderWorkflow.class, opts);

        WorkflowClient.start(workflow::placeOrder, request);

        responseObserver.onNext(request);
        responseObserver.onCompleted();

    }
}
