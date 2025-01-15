package com.ll.coffee.service;

import com.ll.coffee.repository.OrderMenuRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

/**
 * @author shbaek
 * @since 25. 1. 15
 */
@Service
@RequiredArgsConstructor
public class OrderMenuService {
    private final OrderMenuRepository orderMenuRepository;
}
