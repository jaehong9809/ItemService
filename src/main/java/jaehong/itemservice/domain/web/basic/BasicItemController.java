package jaehong.itemservice.domain.web.basic;

import jaehong.itemservice.domain.item.Item;
import jaehong.itemservice.domain.item.ItemRepository;
import jakarta.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.List;

@Controller
@RequestMapping("/basic/items")
@RequiredArgsConstructor
public class BasicItemController {
    private final ItemRepository itemRepository;

    @GetMapping
    public String items(Model model) {
        List<Item> items = itemRepository.findAll();

        model.addAttribute("items", items);
        return "basic/items";
    }

    @GetMapping("/{itemId}")
    public String item(@PathVariable long itemId, Model model) {
        Item item = itemRepository.findById(itemId);
        model.addAttribute("item", item);
        return "basic/item";
    }

    @GetMapping("/add")
    public String addForm() {
        return "/basic/addForm";
    }

    /*@PostMapping("/add")
    public String save(@RequestParam String itemName,
                       @RequestParam int price,
                       @RequestParam int quantity,
                       Model model
                       ) {
        Item item = new Item(itemName, price, quantity);

        itemRepository.save(item);
        model.addAttribute("item", item);
        return "basic/item";
    }*/
   /* @PostMapping("/add")
    public String save(@ModelAttribute("item") Item item,
                       Model model
                       ) {
        itemRepository.save(item);
        //model.addAttribute("item", item);
        //ModelAttribute가 자동으로 모델에 넣어줌
        return "basic/item";
    }*/
    @PostMapping("/add")
    public String save(@ModelAttribute("item") Item item, RedirectAttributes redirectAttributes) {
        Item save = itemRepository.save(item);
        //model.addAttribute("item", item);
        //ModelAttribute가 자동으로 모델에 넣어줌
        redirectAttributes.addAttribute("itemId", save.getId());
        redirectAttributes.addAttribute("status", true);

        return "redirect:/basic/items/{itemId}";

    }

    @GetMapping("/{itemId}/edit")
    public String editForm(@PathVariable Long itemId, Model model) {
        Item item = itemRepository.findById(itemId);
        model.addAttribute("item", item);
        return "basic/editForm";
    }
    @PostMapping("/{itemId}/edit")
    public String edit(@PathVariable Long itemId, @ModelAttribute Item item) {
        itemRepository.update(itemId, item);

        return "redirect:/basic/items/{itemId}";
    }



    @PostConstruct
    public void init() {

        itemRepository.save(new Item("itemA", 12200, 1));
        itemRepository.save(new Item("itemb", 430, 29));
    }
}
