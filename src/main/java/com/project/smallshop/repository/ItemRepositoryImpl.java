package com.project.smallshop.repository;

import com.project.smallshop.domain.item.Item;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import java.util.List;

@Repository
@RequiredArgsConstructor
public class ItemRepositoryImpl implements ItemRepository {

    private final EntityManager em;


    @Override
    public void save(Item item) {
        em.persist(item);
    }

    @Override
    public Item findOne(Long itemId) {
        return em.find(Item.class, itemId);
    }

    @Override
    public List<Item> findOtherItems(Long itemId) {
        return em.createNativeQuery("select * from Item where item_Id != ? order by rand() limit 4", Item.class)
                .setParameter(1, itemId)
                .getResultList();
    }

    @Override
    public List<Item> findAll() {
        return em.createQuery("select i from Item i", Item.class)
                .getResultList();
    }

    @Override
    public void remove(Long itemId) {
        Item item = em.find(Item.class, itemId);
        em.remove(item);
    }

    @Override
    public int findAllCnt() {
        return ((Number) em.createQuery("select count(*) from Item")
                .getSingleResult()).intValue();
    }

    @Override
    public List<Item> findListPaging(int startIndex, int pageSize) {
        return em.createQuery("select i from Item i", Item.class)
                .setFirstResult(startIndex)
                .setMaxResults(pageSize)
                .getResultList();
    }
}
