package com.trading.service;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.trading.domain.OrderStatus;
import com.trading.domain.OrderType;
import com.trading.model.Asset;
import com.trading.model.Coin;
import com.trading.model.Order;
import com.trading.model.OrderItem;
import com.trading.model.User;
import com.trading.repository.OrderItemRepository;
import com.trading.repository.OrderRepository;

import jakarta.transaction.Transactional;

@Service
public class OrderServiceImpl implements OrderService{

    @Autowired
    private OrderRepository orderRepository;

    @Autowired
    private WalletService walletService;

    @Autowired
    private OrderItemRepository orderItemRepository;

    @Autowired
    private AssetService assetService;

    

    @Override
    public Order createOrder(User user, OrderItem orderItem, OrderType orderType) {
        
        double price=orderItem.getCoin().getCurrentPrice()*orderItem.getQuantity();

        Order order=new Order();
        order.setUser(user);
        order.setOrderItem(orderItem);
        order.setOrderType(orderType);
        order.setPrice(BigDecimal.valueOf(price));
        order.setTimeStamp(LocalDateTime.now());
        order.setStatus(OrderStatus.PENDING);

        return orderRepository.save(order);
    }

    @Override
    public Order getOrderById(Long orderId) throws Exception {
        Optional<Order> order=orderRepository.findById(orderId);
        if(order.isEmpty()) throw new Exception("Order not found");
        return order.get();
    }

    @Override
    public List<Order> getAllOrdersOfUser(Long userId, OrderType orderType, String assetSymbol) {
        return orderRepository.findByUserId(userId);
    }
    public OrderItem createOrderItem(Coin coin,double quantity, double buyPrice,double sellPrice){
        OrderItem orderItem=new OrderItem();
        orderItem.setCoin(coin);
        orderItem.setQuantity(quantity);
        orderItem.setByePrice(sellPrice);
        orderItem.setSellPrice(sellPrice);
        return orderItemRepository.save(orderItem);

    }
    @Transactional
    public Order buyAsset(Coin coin, double quantity, User user) throws Exception{
        if(quantity<=0) throw new Exception("Quantity should be more that zero");
        
        double buyPrice=coin.getCurrentPrice();
        OrderItem orderItem=createOrderItem(coin, quantity, buyPrice, 0);

        Order order=createOrder(user, orderItem, OrderType.BUY);
        orderItem.setOrder(order);
        walletService.payOrderPayment(order, user);
        order.setStatus(OrderStatus.SUCCESS);
        order.setOrderType(OrderType.BUY);

        Order savedOrder=orderRepository.save(order);

        //create assest 
        return savedOrder;

    }

    @Transactional
    public Order sellAsset(Coin coin, double quantity, User user) throws Exception{
        // if(quantity<=0) throw new Exception("Quantity should be more that zero");
        
        // double sellPrice=coin.getCurrentPrice();
        // double buyPrice=assetToSell.getPrice();
        // OrderItem orderItem=createOrderItem(coin, quantity, buyPrice, sellPrice);

        // Order order=createOrder(user, orderItem, OrderType.SELL);
        // orderItem.setOrder(order);
        // order.setStatus(OrderStatus.SUCCESS);
        // order.setOrderType(OrderType.SELL);
        // Order savedOrder=orderRepository.save(order);
        // if(assetToSell.getQuantity()>=quantity){
        //     walletService.payOrderPayment(order, user);

        //     Asset updatedAsset=assetService.updateAsset(assetToSell.getId(),-quantity);
        //     if(updatedAsset.getQuantity()*coin.getCurrentPrice()<=1){
        //         assetService.deleteAsset(updatedAsset.getId());
        //     }
        //     return savedOrder;
        // }
        // throw new Exception("Insufficient quantity to sell");

        double buyPrice=coin.getCurrentPrice();
        OrderItem orderItem=createOrderItem(coin, quantity, buyPrice, 0);

        Order order=createOrder(user, orderItem, OrderType.BUY);
        orderItem.setOrder(order);
        walletService.payOrderPayment(order, user);
        order.setStatus(OrderStatus.SUCCESS);
        order.setOrderType(OrderType.BUY);

        Order savedOrder=orderRepository.save(order);

        //create assest 
        return savedOrder;
    }

    @Override
    @Transactional
    public Order processOrder(Coin coin, double quantity, OrderType orderType, User user)throws Exception {

        if(orderType.equals(OrderType.BUY)){
            return buyAsset((coin), quantity, user);
        }else if(orderType.equals(OrderType.SELL)){
            return sellAsset(coin, quantity, user);
        }
        throw new Exception("Invalid order type");
    }

}
