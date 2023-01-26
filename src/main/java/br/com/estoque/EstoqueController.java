package br.com.estoque;

import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@RestController
@RequestMapping("/itens")
public class EstoqueController {


    private final ItemRepository itemRepository;
    private final EstoqueRepository estoqueRepository;

    public EstoqueController(ItemRepository itemRepository, EstoqueRepository estoqueRepository) {
        this.itemRepository = itemRepository;
        this.estoqueRepository = estoqueRepository;
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    @Transactional
    public void cadastraItemNoEstoque(@RequestBody @Valid NovoItemRequest request) {
        Estoque estoque = request.toModel();
        estoqueRepository.save(estoque);
    }

    @DeleteMapping("/{itemId}")
    @Transactional
    public ResponseEntity deletaItemDoEstoque(@PathVariable Long itemId) {

        Optional<Estoque> estoque = estoqueRepository.findById(itemId);

        if (estoque.isPresent()) {
            estoqueRepository.delete(estoque.get());
            return ResponseEntity.noContent().build();
        }

        return ResponseEntity.notFound().build();
    }


    @GetMapping
    public void buscaItem(@RequestParam(value = "nome", required = false) String nome,
                          @RequestParam(value = "descricao", required = false) String descricao,
                          @RequestParam(value = "categoria", required = false) String categoria) {

        //TODO implementar get com múltiplos parâmetros


    }


    @PatchMapping("/{itemId}/acrescenta")
    @Transactional
    public ResponseEntity acrescentaItem(@PathVariable Long itemId, @RequestBody @Valid AlteraQtdItensRequest request) {

        System.out.println(itemId);
        System.out.println(request.quantidade());

        Optional<Estoque> estoque = estoqueRepository.findById(itemId);

        if (estoque.isPresent()) {
            estoque.get().adicionaQuantidade(request.quantidade());
            return ResponseEntity.ok().build();
        }

        return ResponseEntity.notFound().build();

    }

    @PatchMapping("/{itemId}/remove")
    @Transactional
    public ResponseEntity removeItem(@PathVariable Long itemId, @RequestBody @Valid AlteraQtdItensRequest request) {

        Optional<Estoque> estoque = estoqueRepository.findById(itemId);

        if (estoque.isPresent()) {
            try {
                estoque.get().retiraQuantidade(request.quantidade());
                return ResponseEntity.ok().build();
            } catch (ApiErrorException e){
                return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
            }
        }

        return ResponseEntity.notFound().build();

    }

}
