package com.example.demo.model;

import java.util.List;

public class UserListResponse {

    /**
     * id : 306010
     * type : simple
     * name : Vintage Brandy Fruit Pudding 900g
     * vendorId : 6951
     * categories : [{"id":7003,"name":"Bakery & Desserts","slug":"woolworths-woolworths-food-bakery-desserts"},{"id":10241,"name":"Cakes, Pastries & Desserts","slug":"woolworths-bakery-desserts-cakes-pastries-desserts"},{"id":11670,"name":"Desserts","slug":"desserts"},{"id":11679,"name":"Warm Dessert","slug":"warm-dessert"}]
     * image : https://images.weserv.nl/?url=onecart.co.za/wp-content/uploads/2018/11/Vintage-Brandy-Fruit-Pudding-900g-6009204075429.jpg&w=235&h=235&mode=max&format=png
     * price : 219.99
     * purchase_note : null
     * description : <p>Our Vintage Brandy Fruit Pudding is expertly crafted with free range eggs, cirtus zest, juicy vine fruits, azo dye-free cherries soaked in brandy with Christmas spices and matured for 9 months. A wonderfully charming dessert your guests will love. Serve with ice cream, custard or a dollop of fresh cream.Locally made and expertly steamed.  	9 Months matured  </p>
     * in_stock : true
     * relatedIds : [125718,134298,114507,114567,134284]
     * categoryId : 7003
     * featured : false
     * lastUpdated : 0001-01-01T00:00:00
     * discountPercentage : 0
     * custom_vendor_id : 0
     * regular_price : 219.99
     * attributes : []
     * variations : []
     */

    private int id;
    private String type;
    private String name;
    private int vendorId;
    private String image;
    private double price;
    private Object purchase_note;
    private String description;
    private boolean in_stock;
    private int categoryId;
    private boolean featured;
    private String lastUpdated;
    private int discountPercentage;
    private int custom_vendor_id;
    private double regular_price;
    private List<CategoriesBean> categories;
    private List<Integer> relatedIds;
    private List<?> attributes;
    private List<?> variations;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getVendorId() {
        return vendorId;
    }

    public void setVendorId(int vendorId) {
        this.vendorId = vendorId;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public Object getPurchase_note() {
        return purchase_note;
    }

    public void setPurchase_note(Object purchase_note) {
        this.purchase_note = purchase_note;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public boolean isIn_stock() {
        return in_stock;
    }

    public void setIn_stock(boolean in_stock) {
        this.in_stock = in_stock;
    }

    public int getCategoryId() {
        return categoryId;
    }

    public void setCategoryId(int categoryId) {
        this.categoryId = categoryId;
    }

    public boolean isFeatured() {
        return featured;
    }

    public void setFeatured(boolean featured) {
        this.featured = featured;
    }

    public String getLastUpdated() {
        return lastUpdated;
    }

    public void setLastUpdated(String lastUpdated) {
        this.lastUpdated = lastUpdated;
    }

    public int getDiscountPercentage() {
        return discountPercentage;
    }

    public void setDiscountPercentage(int discountPercentage) {
        this.discountPercentage = discountPercentage;
    }

    public int getCustom_vendor_id() {
        return custom_vendor_id;
    }

    public void setCustom_vendor_id(int custom_vendor_id) {
        this.custom_vendor_id = custom_vendor_id;
    }

    public double getRegular_price() {
        return regular_price;
    }

    public void setRegular_price(double regular_price) {
        this.regular_price = regular_price;
    }

    public List<CategoriesBean> getCategories() {
        return categories;
    }

    public void setCategories(List<CategoriesBean> categories) {
        this.categories = categories;
    }

    public List<Integer> getRelatedIds() {
        return relatedIds;
    }

    public void setRelatedIds(List<Integer> relatedIds) {
        this.relatedIds = relatedIds;
    }

    public List<?> getAttributes() {
        return attributes;
    }

    public void setAttributes(List<?> attributes) {
        this.attributes = attributes;
    }

    public List<?> getVariations() {
        return variations;
    }

    public void setVariations(List<?> variations) {
        this.variations = variations;
    }

    public static class CategoriesBean {
        /**
         * id : 7003
         * name : Bakery & Desserts
         * slug : woolworths-woolworths-food-bakery-desserts
         */

        private int id;
        private String name;
        private String slug;

        public int getId() {
            return id;
        }

        public void setId(int id) {
            this.id = id;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public String getSlug() {
            return slug;
        }

        public void setSlug(String slug) {
            this.slug = slug;
        }
    }
}
