package knu.hackathon24.cat.thumbler.goods;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class PointStoreService {
    @Autowired
    private GoodsRepository goodsRepository;

    public List<Goods> getAllGoods() {
        return goodsRepository.findAll();
    }

    public Optional<Goods> getGoodsById(Long goodsId) {
        return goodsRepository.findById(goodsId);
    }
}
