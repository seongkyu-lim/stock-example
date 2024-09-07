package com.example.stock.facade;

import com.example.stock.repository.RedisLockRepository;
import com.example.stock.service.StockService;
import org.springframework.stereotype.Component;

@Component
public class LettuceLockStockFacade {

private RedisLockRepository redisLockRepository;

private StockService stockService;

public LettuceLockStockFacade(RedisLockRepository redisLockRepository,
StockService stockService) {
this.redisLockRepository = redisLockRepository;
this.stockService = stockService;
}

public void decrease(Long key, Long quantity) throws InterruptedException {
    //락을 획득할 때까지 대기 하다가
while (!redisLockRepository.lock(key)) {
Thread.sleep(100);
}
할
try {
stockService.decrease(key, quantity);
} finally {
    // 로직 종료되 락 해제.
redisLockRepository.unlock(key);
}
}
}
면