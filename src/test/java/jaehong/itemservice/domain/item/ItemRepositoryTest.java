package jaehong.itemservice.domain.item;


import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.assertj.core.api.Assertions.*;

public class ItemRepositoryTest {
    ItemRepository itemRepository = new ItemRepository();

    @AfterEach
    void afterEach() {
        itemRepository.clearStore();
    }

    @Test
    void save() {
        //given
        Item tmp = new Item();
        tmp.setQuantity(2);
        tmp.setItemName("rice");
        tmp.setPrice(12000);
        //when
        Item save = itemRepository.save(tmp);
        //then
        Item byId = itemRepository.findById(tmp.getId());
        assertThat(byId).isEqualTo(save);

    }
    @Test
    void findAll() {
        //given
        Item tmp1 = new Item("a", 12000, 1);
        Item tmp2 = new Item("b", 4000, 10);
        itemRepository.save(tmp1);
        itemRepository.save(tmp2);
        //when

        List<Item> all = itemRepository.findAll();

        //then
        assertThat(all.size()).isEqualTo(2);
        assertThat(all).contains(tmp1, tmp2);
    }
    @Test
    void update() {
        //given
        Item tmp = new Item("a", 1200, 1);
        Item save = itemRepository.save(tmp);
        Long id = save.getId();
        Item updateTmp = new Item("b", 400, 22);

        //when
        itemRepository.update(id, updateTmp);

        //then
        Item byId = itemRepository.findById(id);
        assertThat(byId.getItemName()).isEqualTo(updateTmp.getItemName());
        assertThat(byId.getPrice()).isEqualTo(updateTmp.getPrice());
        assertThat(byId.getQuantity()).isEqualTo(updateTmp.getQuantity());
    }


}
