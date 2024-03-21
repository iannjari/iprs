package dev.njari.temp_demo;

import io.temporal.activity.ActivityInterface;
import io.temporal.activity.ActivityMethod;
import iprs.temporal_demo.Order;

/**
 * @author njari_mathenge
 * on 21/03/2024.
 * github.com/iannjari
 */

@ActivityInterface
public interface OrderActivity {

    @ActivityMethod
    void save(Order order);

    @ActivityMethod
    void publish(Order order);

    @ActivityMethod
    Order dispatch(Order order);

    @ActivityMethod
    double checkForBal(Order order);

    @ActivityMethod
    void promptPay(Order order);

    @ActivityMethod
    double consumeBalance(Order order);

}
