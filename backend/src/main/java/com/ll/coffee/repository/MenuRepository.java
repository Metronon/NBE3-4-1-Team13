package com.ll.coffee.repository;

import com.ll.coffee.menu.Menu;
import org.springframework.data.jpa.repository.JpaRepository;

public interface MenuRepository extends JpaRepository<Menu, Long> {

}