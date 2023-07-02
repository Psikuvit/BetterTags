package me.psikuvit.bettertags;

import me.psikuvit.bettertags.datas.Category;
import me.psikuvit.bettertags.datas.Tag;
import org.bukkit.Material;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class CategoryManager {

    private final List<Category> categoryList;
    private final List<Tag> tags;
    private final Map<Category, List<Tag>> categoryTags;
    private final List<Material> materials;
    private static CategoryManager INSTANCE;
    public CategoryManager(){
        this.categoryTags = new HashMap<>();
        this.tags = new ArrayList<>();
        this.categoryList = new ArrayList<>();
        this.materials = new ArrayList<>();
        this.materials.add(Material.NAME_TAG);
    }

    public static CategoryManager getInstance(){
        if(INSTANCE == null){
            INSTANCE = new CategoryManager();
        }
        return INSTANCE;
    }

    public void cacheCategory(Category category) {
        categoryList.add(category);
    }
    public void cacheTag(Tag tag) {
        tags.add(tag);
    }

    public List<Category> getCategoryList() {
        return categoryList;
    }

    public Map<Category, List<Tag>> getCategoryTags() {
        return categoryTags;
    }

    public void cacheMap() {
        for (Category category : categoryList) {
            List<Tag> tagList = new ArrayList<>();
            for (Tag tag : tags) {
                if (tag.category().equals(category.id())) {
                    tagList.add(tag);
                    categoryTags.put(category, tagList);
                }
            }
        }
    }

    public Category getCategoryByID(String id) {
        for (Category category : categoryList) {
            if (category.id().equals(id))
                return category;
        }
        return null;
    }
    public Tag getTagByID(String id) {
        for (Tag tag : tags) {
            if (tag.id().equals(id))
                return tag;
        }
        return null;
    }
    public List<Tag> getCategoryTags(Category category) {
        return categoryTags.get(category);
    }

    public List<Material> getMaterials() {
        return materials;
    }
}
