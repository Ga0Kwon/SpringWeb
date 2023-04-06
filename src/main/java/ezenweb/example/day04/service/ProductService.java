package ezenweb.example.day04.service;

import ezenweb.example.day04.domain.dto.ProductDto;
import ezenweb.example.day04.domain.entity.proudct.ProductEntity;
import ezenweb.example.day04.domain.entity.proudct.ProductEntityRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

//서비스[실질적인 기능]
@Service
@Slf4j
public class ProductService {

    @Autowired
    private ProductEntityRepository productEntityRepository;

    //1. 저장
    public boolean write(ProductDto dto){
        log.info("write in : " + dto);

        //1) 입력받은 dto를 엔티티로 변환후에 save메서드에 대입후 생성된 에니티티 얻기
        ProductEntity entity =  productEntityRepository.save(dto.toEntity());

        //자동 생성된 pk로 엔티티 생성여부 확인
        if(entity.getPno() > 0){
            return true;
        }else{
            return false;
        }
    }

    //2. 수정
    @Transactional //commit 용도
    public boolean update(ProductDto dto){
        log.info("update in : " + dto);
        //1. 수정할 번호를 이용한 엔티티 찾기[검색]
        Optional<ProductEntity> optinalProductEntity = productEntityRepository.findById(dto.getPno());

        //2. 포장클래스에서 포장안에 엔티티가 있는지 검사
        if(optinalProductEntity.isPresent()){
            //3. 만약에 있으면, true 없으면 false
            //4. 포장객체안에 있는 엔티티호출
            ProductEntity entity = optinalProductEntity.get();
            entity.setPname(dto.getPname());
            entity.setPcontent(dto.getPcontent());
            return true;
        }
        return false;
    }


    //3. 삭제
    public boolean delete(int pno){
        log.info("delete in : " + pno);
        Optional<ProductEntity> optinalEntity = productEntityRepository.findById(pno);

        if(optinalEntity.isPresent()){
            productEntityRepository.delete(optinalEntity.get());
            return true;
        }
        return false;
    }

    //4. 조회
    public ArrayList<ProductDto> getProduct() {
        log.info("getProduct in");
        List<ProductEntity> list = productEntityRepository.findAll();

        ArrayList<ProductDto> productList = new ArrayList<>();

        list.forEach(e -> {
            productList.add(e.toDto());
        });

        return productList;

    }
}
