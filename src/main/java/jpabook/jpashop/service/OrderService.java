package jpabook.jpashop.service;

import jpabook.jpashop.domain.*;
import jpabook.jpashop.domain.item.Item;
import jpabook.jpashop.repository.ItemRepository;
import jpabook.jpashop.repository.MemberRepository;
import jpabook.jpashop.repository.OrderRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class OrderService {

    private final MemberRepository memberRepository;
    private final ItemRepository itemRepository;
    private final OrderRepository ordersRepository;

    /**
      *주문
      */
     @Transactional
    public Long order(Long memberId, Long itemId, int count) {

         //엔티티 조회
         Member member = memberRepository.findOne(memberId);
         Item item = itemRepository.findOne(itemId);

         //배송정보 생성
         Delivery delivery = new Delivery();
         delivery.setAddress(member.getAddress());

         //주문 상품 생성
         OrderItem orderItem = OrderItem.createOrderItem(item, item.getPrice(), count);

         //주문 생성
         Orders orders = Orders.CreateOrder(member, delivery, orderItem);

         //주문 저장
         ordersRepository.save(orders);

         return orders.getId();
     }



    //취소

    /**
     * 주문 최소
     */
    @Transactional
    public void cancelOrder(Long orderId) {
        //주문 엔티티 조회
        Orders orders = ordersRepository.findOne(orderId);
        //주문 취소
        orders.cancel();
        //
    }

    //검색
//    public List<Order> findOrder(OrderSearch orderSearch) {
//        return ordersRepository.findAll(orderSearch);
//    }
}
