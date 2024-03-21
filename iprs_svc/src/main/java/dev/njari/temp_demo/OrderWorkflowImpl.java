package dev.njari.temp_demo;

import io.temporal.activity.Activity;
import io.temporal.activity.ActivityOptions;
import io.temporal.common.RetryOptions;
import io.temporal.spring.boot.WorkflowImpl;
import io.temporal.workflow.Workflow;
import iprs.temporal_demo.Order;

import java.time.Duration;
import java.time.temporal.ChronoUnit;

/**
 * @author njari_mathenge
 * on 21/03/2024.
 * github.com/iannjari
 */


@WorkflowImpl(taskQueues = "demo")
public class OrderWorkflowImpl implements OrderWorkflow{

    ActivityOptions opts = ActivityOptions.newBuilder()
            .setRetryOptions(
                    RetryOptions.newBuilder()
                            .setInitialInterval(Duration.of(2, ChronoUnit.SECONDS))
                            .setBackoffCoefficient(2)
                            .setMaximumAttempts(20000)
                            .setMaximumInterval(Duration.of(2, ChronoUnit.MINUTES))
                            .build()
            ).setStartToCloseTimeout(Duration.of(5,ChronoUnit.SECONDS))
            .build();

    OrderActivity orderActivity = Workflow.newActivityStub(OrderActivity.class, opts);

    private double walletBal =0;
    private double orderBal =0;
    private boolean isPaid = false;

    private Order order;


    // 2, 2, 2, 2
    /**
     * @param order
     */
    @Override
    public void placeOrder(Order order) {

        this.order = order;
        this.orderBal = order.getTotalPrice();

        // save to the DB
        orderActivity.save(this.order );

        // publish to kafka
        orderActivity.publish(this.order );

        // dispatch
        this.order = orderActivity.dispatch(this.order);

        // balance check
        this.walletBal = orderActivity.checkForBal(this.order );


//        Workflow.await(()-> this.isPaid);

        Workflow.sleep(Duration.ofSeconds(20));



        // branch out to prompt payment or use wallet balance
        if (this.walletBal > 0) {
            this.orderBal = orderActivity.consumeBalance(this.order );
        }


        if (this.orderBal > 0) {
            orderActivity.promptPay(this.order);
            this.order = Order.newBuilder(this.order)
                    .setTotalPrice(0)
                    .build();
        }

        // update data.
        orderActivity.save(this.order);


        // publish
        orderActivity.publish(this.order);

        //TODO: workflow await scenario
        //TODO: failing activities
        //TODO: start -> schedule -> close
        //TODO: signals


    }
}
