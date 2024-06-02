<<<<<<<< HEAD:milkstore/src/main/java/com/cookswp/milkstore/repository/ShoppingCartRepository.java
package com.cookswp.milkstore.repository;
========
package com.cookswp.milkstore.repository.ShoppingCartRepository;
>>>>>>>> main:milkstore/src/main/java/com/cookswp/milkstore/repository/ShoppingCartRepository/ShoppingCartRepository.java

import com.cookswp.milkstore.pojo.entities.ShoppingCart;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ShoppingCartRepository extends JpaRepository<ShoppingCart, Integer> {
}
