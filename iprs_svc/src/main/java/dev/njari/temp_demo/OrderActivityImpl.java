package dev.njari.temp_demo;

import io.temporal.spring.boot.ActivityImpl;
import iprs.temporal_demo.Order;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

/**
 * @author njari_mathenge
 * on 21/03/2024.
 * github.com/iannjari
 */

@Slf4j
@ActivityImpl(taskQueues = "demo")
@Service
public class OrderActivityImpl implements OrderActivity{
    /**
     * @param order
     */
    @Override
    public void save(Order order) {
        log.info("saving {}", order);
    }

    /**
     * @param order
     */
    @Override
    public void publish(Order order) {
        log.info("publishing {}", order);

    }

    /**
     * @param order
     */
    @Override
    public Order dispatch(Order order) {
        log.info("dispatching {}", order);
        return order;
    }

    /**
     * @param order
     */
    @Override
    public double checkForBal(Order order) {
        return 200;
    }

    /**
     * @param order
     */
    @Override
    public void promptPay(Order order) {
        log.info("paying {}", order);
    }

    /**
     * @param order
     */
    @Override
    public double consumeBalance(Order order) {
        log.info("using bal {}", order);

        try {
            // dk
        } catch (Exception e) {
            //
        }

        return 100;
    }
}
