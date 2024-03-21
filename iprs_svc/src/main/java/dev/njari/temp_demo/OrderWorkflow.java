package dev.njari.temp_demo;

import io.temporal.workflow.WorkflowInterface;
import io.temporal.workflow.WorkflowMethod;
import iprs.temporal_demo.Order;

/**
 * @author njari_mathenge
 * on 21/03/2024.
 * github.com/iannjari
 */

@WorkflowInterface
public interface OrderWorkflow {

    @WorkflowMethod
    void placeOrder(Order order);


}
