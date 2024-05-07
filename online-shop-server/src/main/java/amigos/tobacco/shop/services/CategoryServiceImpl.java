package amigos.tobacco.shop.services;

import amigos.tobacco.shop.model.Category;
import amigos.tobacco.shop.repositories.CategoryRepository;
import amigos.tobacco.shop.services.interfaces.CategoryService;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CategoryServiceImpl implements CategoryService {

    private final CategoryRepository categoryRepository;

    public CategoryServiceImpl(CategoryRepository categoryRepository) {
        this.categoryRepository = categoryRepository;
    }

    @Override
    public List<Category> getCategories() {
        return categoryRepository.findAll();
    }
}
