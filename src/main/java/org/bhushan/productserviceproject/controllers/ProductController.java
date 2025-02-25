package org.bhushan.productserviceproject.controllers;

import jakarta.websocket.server.PathParam;
import org.bhushan.productserviceproject.authCommons.AuthenticationCommons;
import org.bhushan.productserviceproject.dtos.*;
import org.bhushan.productserviceproject.exceptions.CategoryNotFoundException;
import org.bhushan.productserviceproject.exceptions.InvalidLimitException;
import org.bhushan.productserviceproject.exceptions.InvalidTokenException;
import org.bhushan.productserviceproject.exceptions.ProductNotFoundException;
import org.bhushan.productserviceproject.models.Product;
import org.bhushan.productserviceproject.models.Rating;
import org.bhushan.productserviceproject.services.ProductService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@RestController
public class ProductController {

    private ProductService productService;
    private ModelMapper modelMapper;
    private AuthenticationCommons authenticationCommons;


    public ProductController(
            @Qualifier("selfProductService") ProductService productService ,
            ModelMapper modelMapper,
            AuthenticationCommons authenticationCommons) {
        this.productService = productService;
        this.modelMapper = modelMapper;
        this.authenticationCommons = authenticationCommons;
    }

    @GetMapping("/products/page")
    public ResponseEntity<List<ProductResponseDto>> getAllProducts(
            @RequestParam("pageNumber") int pageNumber,
            @RequestParam("pageSize") int pageSize,
            @RequestParam("sortBy") String sortBy,
            @RequestHeader String authenticationToken) throws InvalidTokenException
    {
        authenticationCommons.validateToken(authenticationToken);
        Page<Product> products = productService.getAllProducts(pageNumber, pageSize, sortBy);
        List<ProductResponseDto> productResponseDtos = new ArrayList<>();
        for (Product product : products.getContent()) {
            productResponseDtos.add(convertToProductResponseDTO(product));
        }
        return new ResponseEntity<>(productResponseDtos, HttpStatus.OK);
    }


    @GetMapping("/products/{id}")
    public ResponseEntity<ProductResponseDto> getProductDetails(@PathVariable("id") Long productId,
                                                                @RequestHeader String authenticationToken) throws  InvalidTokenException, ProductNotFoundException {

        UserDto userDto = authenticationCommons.validateToken(authenticationToken);
        Product product = productService.getSingleProduct(productId);
        ProductResponseDto productResponseDto = convertToProductResponseDTO(product);
        return new ResponseEntity<>(productResponseDto, HttpStatus.OK);
    }


    @GetMapping("/products/")
    public ResponseEntity<List<ProductResponseDto>> getAllProducts(@RequestHeader String authenticationToken) throws InvalidTokenException{
        authenticationCommons.validateToken(authenticationToken);
        List<ProductResponseDto> productResponseDtos = new ArrayList<>();
        List<Product> products = productService.getAllProducts();
        for (Product product : products) {
            productResponseDtos.add(convertToProductResponseDTO(product));
//            productResponseDtos.add(convertToProductResponseDTO(product));
//            RatingDto ratingDto = new RatingDto();
//            ratingDto.setCount(product.getCount());
//            ratingDto.setRate(product.getRate());
        }
        return new ResponseEntity<>(productResponseDtos, HttpStatus.OK);
    }

    @GetMapping("/products")
    public ResponseEntity<List<ProductResponseDto>> getLimitedProducts(@RequestParam("limit") int limit,
                                                                       @RequestHeader String authenticationToken) throws InvalidTokenException, InvalidLimitException {
        authenticationCommons.validateToken(authenticationToken);
        List<Product> products = productService.getLimitedProducts(limit);
        List<ProductResponseDto> productResponseDtos = new ArrayList<>();
        for (Product product : products) {
            productResponseDtos.add(convertToProductResponseDTO(product));
        }
        return new ResponseEntity<>(productResponseDtos, HttpStatus.OK);
    }

    @GetMapping("/products/categories")
    public ResponseEntity<List<String>> getAllCategories() {
        List<String> categories = productService.getAllCategories();
        return new ResponseEntity<>(categories, HttpStatus.OK);
//        return categories;
    }

    @GetMapping("/products/category/{categoryName}")
    public ResponseEntity<List<ProductResponseDto>> getProductsByCategory(@PathVariable("categoryName") String categoryName,
                                                                          @RequestHeader String authenticationToken) throws InvalidTokenException, CategoryNotFoundException {
        authenticationCommons.validateToken(authenticationToken);
        List<Product> products = productService.getProductsByCategory(categoryName);
        List<ProductResponseDto> productResponseDtos = new ArrayList<>();
        for (Product product : products) {
            productResponseDtos.add(convertToProductResponseDTO(product));
        }
        return new ResponseEntity<>(productResponseDtos, HttpStatus.OK);
    }


    @PostMapping("/products")
    public ResponseEntity<NewProductResponseDto> addANewProduct(
            @RequestBody ProductRequestDto productRequestDto,
            @RequestHeader String authenticationToken) throws InvalidTokenException {
        authenticationCommons.validateToken(authenticationToken);
        Product product = productService.addANewProduct(
                productRequestDto.getTitle() , productRequestDto.getPrice() ,
                productRequestDto.getDescription() , productRequestDto.getImage(),
                productRequestDto.getCategory() , productRequestDto.getRating().getRate() ,
                    productRequestDto.getRating().getCount()
        );

        NewProductResponseDto newProductResponseDto = convertToNewProductResponse(product);
        return new ResponseEntity<>(newProductResponseDto, HttpStatus.CREATED);
    }


    @PatchMapping("/products/{productID}")
    public ResponseEntity<NewProductResponseDto > updateAProduct(
            @PathVariable("productID") Long productID ,
            @RequestBody ProductRequestDto productRequestDto,
            @RequestHeader String authenticationToken
    ) throws InvalidTokenException, ProductNotFoundException {
        authenticationCommons.validateToken(authenticationToken);
        Product product = productService.updateAProduct(productID ,
                productRequestDto.getTitle() , productRequestDto.getPrice(),
                productRequestDto.getDescription() , productRequestDto.getImage(),
                productRequestDto.getCategory());
        NewProductResponseDto newProductResponseDto = convertToNewProductResponse(product);
        return new ResponseEntity<>(newProductResponseDto, HttpStatus.OK);
    }

    @PutMapping("/products/{productID}")
    public ResponseEntity<NewProductResponseDto> replaceAProduct(
            @PathVariable("productID") Long productID ,
            @RequestBody ProductRequestDto productRequestDto,
            @RequestHeader String authenticationToken) throws InvalidTokenException, ProductNotFoundException {
        authenticationCommons.validateToken(authenticationToken);
        Product product = productService.replaceAProduct(
                productID , productRequestDto.getTitle() , productRequestDto.getPrice(),
                productRequestDto.getDescription() , productRequestDto.getImage(),
                productRequestDto.getCategory());
        NewProductResponseDto newProductResponseDto = convertToNewProductResponse(product);
        return new ResponseEntity<>(newProductResponseDto, HttpStatus.OK);
    }

    @DeleteMapping("/products/{productID}")
    public ResponseEntity<NewProductResponseDto> deleteProduct(@PathVariable("productID")
                                                                Long productID,
                                                               String authenticationToken)
            throws InvalidTokenException, ProductNotFoundException {
        authenticationCommons.validateToken(authenticationToken);
        Product product = productService.deleteProduct(productID);
        NewProductResponseDto newProductResponseDto  = convertToNewProductResponse(product);
        return new ResponseEntity<>(newProductResponseDto, HttpStatus.OK);

//        NewProductResponseDto newProductResponseDto = convertToNewProductResponse(product);
//        return new ResponseEntity<>(newProductResponseDto, HttpStatus.OK);
    }

//    private CategoryDto convertToCategoryDto(Category category) {
//        return modelMapper.map(category, CategoryDto.class);
//    }
    private NewProductResponseDto convertToNewProductResponse(Product product){
//        float rate = product.getRating().getRate();
//        int count = product.getRating().getCount();
        String category = product.getCategory().getTitle();
        NewProductResponseDto newProductResponseDto = new NewProductResponseDto();
        newProductResponseDto.setId(product.getId());
        newProductResponseDto.setTitle(product.getTitle());
        newProductResponseDto.setPrice(product.getPrice());
        newProductResponseDto.setDescription(product.getDescription());
        newProductResponseDto.setImage(product.getImageUrl());
        newProductResponseDto.setCategory(category);
        return newProductResponseDto;
    }
    private ProductResponseDto convertToProductResponseDTO(Product product) {
        float rate = product.getRate();
        int count = product.getCount();
        String productCategoryTitle = product.getCategory().getTitle();
        ProductResponseDto productResponseDto = modelMapper.map(product, ProductResponseDto.class);
        productResponseDto.setName(product.getTitle());
        RatingDto ratingDto= new RatingDto();
        ratingDto.setRate(rate);
        ratingDto.setCount(count);
        productResponseDto.setRating(ratingDto);

        CategoryDto categoryDto = new CategoryDto();
        categoryDto.setTitle(productCategoryTitle);
        productResponseDto.setCategory(categoryDto);

        return productResponseDto;
    }
}
