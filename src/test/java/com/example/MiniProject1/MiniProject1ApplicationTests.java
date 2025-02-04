package com.example.MiniProject1;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import org.springframework.http.MediaType;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import com.example.controller.UserController;
import com.example.model.Cart;
import com.example.model.Order;
import com.example.model.Product;
import com.example.model.User;
import com.example.repository.CartRepository;
import com.example.repository.OrderRepository;
import com.example.repository.ProductRepository;
import com.example.repository.UserRepository;
import com.example.service.CartService;
import com.example.service.OrderService;
import com.example.service.ProductService;
import com.example.service.UserService;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;

@WebMvcTest
class MiniProject1ApplicationTests {
	@Autowired
	private MockMvc mockMvc;

	@Autowired
	private ObjectMapper objectMapper;

	@Autowired
	private UserService userService;

	@Autowired
	private CartService cartService;

	@Autowired
	private ProductService productService;

	@Autowired
	private OrderService orderService;
	@Autowired
	private UserRepository userRepository;

	@Autowired
	private CartRepository cartRepository;

	@Autowired
	private ProductRepository productRepository;

	@Autowired
	private OrderRepository orderRepository;


	private UUID userId;
	private User testUser;
	@BeforeEach
	void setUp() {
		userId = UUID.randomUUID();
		testUser = new User();
		testUser.setId(userId);
		testUser.setName("Test User");
	}

	// ------------------------ User Tests -------------------------

	@Test
	void testAddUserRepository() {
		User testUser1 = new User();
		testUser1.setId(UUID.randomUUID());
		testUser1.setName("Test User1");
		assertTrue(
			Arrays.stream(userRepository.getClass().getMethods())
				.anyMatch(method -> method.getName().equals("addUser")),
			"UserRepository should contain a method called addUser"
		);
		userRepository.addUser(testUser1);
		assertTrue(userRepository.users.contains(testUser1),"User should be added correctly");
	}
	@Test
	void testAddUserService() {
		User testUser2 = new User();
		testUser2.setId(UUID.randomUUID());
		testUser2.setName("Test User2");
		assertTrue(
			Arrays.stream(userService.getClass().getMethods())
				.anyMatch(method -> method.getName().equals("addUser")),
			"UserService should contain a method called addUser"
		);
		userService.addUser(testUser2);
		assertTrue(userRepository.users.contains(testUser2),"User should be added correctly");
	}

	@Test
	void testAddUserEndPoint() throws Exception {
		User testUser3 = new User();
		testUser3.setId(UUID.randomUUID());
		testUser3.setName("Test User3");
		
		
		mockMvc.perform(MockMvcRequestBuilders.post("/user/")
				.contentType(MediaType.APPLICATION_JSON)
				.content(objectMapper.writeValueAsString(testUser3)))
				.andExpect(MockMvcResultMatchers.status().isOk());
		boolean found=false;

		for(User user: userRepository.users){
			if(user.getId().equals(testUser3.getId()) && user.getName().equals(testUser3.getName())){
				found=true;
				break;
			}
		}
		assertTrue(found,"User should be added correctly");   
	}

	@Test void testGetUsersRepository() {
		User testUser4 = new User();
		testUser4.setId(UUID.randomUUID());
		testUser4.setName("Test User4");
		assertTrue(
			Arrays.stream(userRepository.getClass().getMethods())
				.anyMatch(method -> method.getName().equals("getUsers")),
			"UserRepository should contain a method called getUsers"
		);
		userRepository.users.add(testUser4);
		
		
		assertEquals(userRepository.users, userRepository.getUsers(), "Users should be returned correctly");
	}
	

	@Test void testGetUsersService() {
		User testUser5 = new User();
		testUser5.setId(UUID.randomUUID());
		testUser5.setName("Test User5");
		assertTrue(
			Arrays.stream(userService.getClass().getMethods())
				.anyMatch(method -> method.getName().equals("getUsers")),
			"UserService should contain a method called getUsers"
		);
		userRepository.users.add(testUser5);
		
		assertEquals(userRepository.users, userService.getUsers(), "Users should be returned correctly");
	}

	@Test
	void testGetUsersEndPoint() throws Exception {
		List<User> users = List.of(testUser);
		userRepository.users.add(testUser);
		

		MvcResult result= mockMvc.perform(MockMvcRequestBuilders.get("/user/")
				.contentType(MediaType.APPLICATION_JSON))
				.andExpect(MockMvcResultMatchers.status().isOk())
				.andReturn();
		String responseContent = result.getResponse().getContentAsString();
		List<User> responseUsers = objectMapper.readValue(responseContent, new TypeReference<List<User>>() {});
		
		assertEquals(users.size(), responseUsers.size(), "Users should be returned correctly From Endpoint");
	}

	@Test
	void testGetUserByIdRepository() throws Exception {
		
		
		assertTrue(
			Arrays.stream(userRepository.getClass().getMethods())
				.anyMatch(method -> method.getName().equals("getUserById")),
			"UserRepository should contain a method called getUserById"
		);
		User testUser6=new User();
		testUser6.setId(UUID.randomUUID());
		testUser6.setName("Test User6");
		userRepository.users.add(testUser6);
		
		assertEquals(testUser6, userRepository.getUserById(testUser6.getId()),"User should be returned correctly");
		assertEquals(null, userRepository.getUserById(UUID.randomUUID()), "No User Should be found");
	}

	@Test
	void testGetUserByIdService() throws Exception {
		
		
		assertTrue(
			Arrays.stream(userService.getClass().getMethods())
				.anyMatch(method -> method.getName().equals("getUserById")),
			"UserService should contain a method called getUserById"
		);
		
		User testUser7=new User();
		testUser7.setId(UUID.randomUUID());
		testUser7.setName("Test User7");
		userRepository.users.add(testUser7);
		assertEquals(testUser7, userRepository.getUserById(testUser7.getId()),"User should be returned correctly");
		assertEquals(null, userRepository.getUserById(UUID.randomUUID()), "No User Should be found");
		
	}

	@Test
	void testGetUserByIdEndPoint() throws Exception {
		User testUser8=new User();
		testUser8.setId(UUID.randomUUID());
		testUser8.setName("Test User8");
		userRepository.users.add(testUser8);
		
		mockMvc.perform(MockMvcRequestBuilders.get("/user/{id}", testUser8.getId()))
				.andExpect(MockMvcResultMatchers.status().isOk())
				.andExpect(MockMvcResultMatchers.content().json(objectMapper.writeValueAsString(testUser8)));
	}

	@Test
	void testGetOrdersByUserIdRepository() throws Exception {
		User testUser9=new User();
		testUser9.setId(UUID.randomUUID());
		testUser9.setName("Test User9");
		List<Order> orders = List.of(new Order(UUID.randomUUID(), testUser9.getId(), 10.0, List.of(new Product(UUID.randomUUID(), "Test Product", 10.0))));
		testUser9.setOrders(orders);
		userRepository.users.add(testUser9);
		
		
		
		assertTrue(
			Arrays.stream(userRepository.getClass().getMethods())
				.anyMatch(method -> method.getName().equals("getOrdersByUserId")),
			"UserRepository should contain a method called getOrdersByUserId"
		);
		
		assertEquals(orders, userRepository.getOrdersByUserId(testUser9.getId()),"Orders should be returned correctly");

		
	}

	@Test
	void testGetOrdersByUserIdService() throws Exception {
		User testUser10=new User();
		testUser10.setId(UUID.randomUUID());
		testUser10.setName("Test User10");
		List<Order> orders = List.of(new Order(UUID.randomUUID(), testUser10.getId(), 10.0, List.of(new Product(UUID.randomUUID(), "Test Product", 10.0))));
		testUser10.setOrders(orders);
		userRepository.users.add(testUser10);
		
		assertTrue(
			Arrays.stream(userService.getClass().getMethods())
				.anyMatch(method -> method.getName().equals("getOrdersByUserId")),
			"UserService should contain a method called getOrdersByUserId"
		);
		
		assertEquals(orders, userService.getOrdersByUserId(testUser10.getId()),"Orders should be returned correctly");
	}

	@Test
	void testGetOrdersByUserIdEndPoint() throws Exception {
		User testUser10=new User();
		testUser10.setId(UUID.randomUUID());
		testUser10.setName("Test User10");
		List<Order> orders = List.of(new Order(UUID.randomUUID(), testUser10.getId(), 10.0, List.of(new Product(UUID.randomUUID(), "Test Product", 10.0))));
		testUser10.setOrders(orders);
		userRepository.users.add(testUser10);
		
		mockMvc.perform(MockMvcRequestBuilders.get("/user/{id}/orders", testUser10.getId()))
				.andExpect(MockMvcResultMatchers.status().isOk())
				.andExpect(MockMvcResultMatchers.content().json(objectMapper.writeValueAsString(orders)));
	}

	@Test
	void testAddOrderToUserRepository() throws Exception {
		User testUser11=new User();
		testUser11.setId(UUID.randomUUID());
		testUser11.setName("Test User11");
		Cart cart=new Cart();
		cart.setId(UUID.randomUUID());
		cart.setUserId(testUser11.getId());
		Product tesProduct=new Product(UUID.randomUUID(), "Test Product", 10.0);
		cart.setProducts(List.of(tesProduct));
		userRepository.users.add(testUser11);
		cartRepository.carts.add(cart);
		assertTrue(
			Arrays.stream(userRepository.getClass().getMethods())
				.anyMatch(method -> method.getName().equals("addOrderToUser")),
			"UserRepository should contain a method called addOrderToUser"
		);
		Order order = new Order(UUID.randomUUID(), testUser11.getId(), 10.0, List.of(tesProduct));
		userRepository.addOrderToUser(testUser11.getId(), order);

		assertEquals(order, userRepository.users.getLast().getOrders().get(0),"Order should be added correctly");

		
	}

	@Test
	void testAddOrderToUserService() throws Exception {
		User testUser11=new User();
		testUser11.setId(UUID.randomUUID());
		testUser11.setName("Test User11");
		Cart cart=new Cart();
		cart.setId(UUID.randomUUID());
		cart.setUserId(testUser11.getId());
		Product tesProduct=new Product(UUID.randomUUID(), "Test Product", 10.0);
		cart.setProducts(List.of(tesProduct));
		userRepository.users.add(testUser11);
		cartRepository.carts.add(cart);
		assertTrue(
			Arrays.stream(userService.getClass().getMethods())
				.anyMatch(method -> method.getName().equals("addOrderToUser")),
			"UserService should contain a method called addOrderToUser"
		);

		Order order = new Order(UUID.randomUUID(), testUser11.getId(), 10.0, List.of(tesProduct));
		userService.addOrderToUser(testUser11.getId(), order);
		assertEquals(order, userRepository.users.getLast().getOrders().get(0),"Order should be added correctly");

	
	}

	@Test
	void testAddOrderToUserEndPoint() throws Exception {
		User testUser11=new User();
		testUser11.setId(UUID.randomUUID());
		testUser11.setName("Test User11");
		Cart cart=new Cart();
		cart.setId(UUID.randomUUID());
		cart.setUserId(testUser11.getId());
		Product tesProduct=new Product(UUID.randomUUID(), "Test Product", 10.0);
		cart.setProducts(List.of(tesProduct));
		userRepository.users.add(testUser11);
		cartRepository.carts.add(cart);
		
		mockMvc.perform(MockMvcRequestBuilders.post("/user/{id}/checkout", testUser11.getId()))
				.andExpect(MockMvcResultMatchers.status().isOk())
				.andExpect(MockMvcResultMatchers.content().string("Order added successfully"));
	}

	@Test
	void testAddProductToCartRepository() throws Exception {
		User testUser12=new User();
		testUser12.setId(UUID.randomUUID());
		testUser12.setName("Test User12");
		Product product = new Product(UUID.randomUUID(), "Test Product", 100.0);
		Cart cart = new Cart(UUID.randomUUID(), testUser12.getId(), new ArrayList<>());
		userRepository.users.add(testUser12);
		cartRepository.carts.add(cart);

		
		assertTrue(
			Arrays.stream(cartRepository.getClass().getMethods())
				.anyMatch(method -> method.getName().equals("addProductToCart")),
			"CartRepository should contain a method called addProductToCart"
		);

		cartRepository.addProductToCart(cart.getId(), product);
		assertEquals(product, cartRepository.carts.getLast().getProducts().get(0),"Product should be added correctly");

	}

	@Test
	void testAddProductToCartService() throws Exception {
		User testUser13=new User();
		testUser13.setId(UUID.randomUUID());
		testUser13.setName("Test User13");
		Product product = new Product(UUID.randomUUID(), "Test Product", 100.0);
		Cart cart = new Cart(UUID.randomUUID(), testUser13.getId(), new ArrayList<>());
		userRepository.users.add(testUser13);
		cartRepository.carts.add(cart);
		assertTrue(
			Arrays.stream(cartService.getClass().getMethods())
				.anyMatch(method -> method.getName().equals("addProductToCart")),
			"UserService should contain a method called addProductToCart"
		);

		cartService.addProductToCart(cart.getId(), product);
		assertEquals(product, cartRepository.carts.getLast().getProducts().get(0),"Product should be added correctly");

		
	}

	@Test
	void testAddProductToCartEndPoint() throws Exception {
		User testUser14=new User();
		testUser14.setId(UUID.randomUUID());
		testUser14.setName("Test User14");
		
		Product testProduct=new Product(UUID.randomUUID(), "Test Product", 10.0);
		userRepository.users.add(testUser14);
		productRepository.products.add(testProduct);
		
		mockMvc.perform(MockMvcRequestBuilders.put("/user/addProductToCart")
				.param("userId", testUser14.getId().toString())
				.param("productId", testProduct.getId().toString()))
				.andExpect(MockMvcResultMatchers.status().isOk())
				.andExpect(MockMvcResultMatchers.content().string("Product added to cart"));
		assertTrue(cartRepository.carts.getLast().getUserId().equals(testUser14.getId()),"New Cart Should be created for user");
		assertEquals(testProduct, cartRepository.carts.getLast().getProducts().get(0),"Product should be added correctly");
	}

	@Test
	void testDeleteProductFromCartRepository() throws Exception {
		User testUser15=new User();
		testUser15.setId(UUID.randomUUID());
		testUser15.setName("Test User15");
		
		Product testProduct=new Product(UUID.randomUUID(), "Test Product", 10.0);
		userRepository.users.add(testUser15);
		productRepository.products.add(testProduct);
		Cart cart = new Cart(UUID.randomUUID(), testUser15.getId(), new ArrayList<>(List.of(testProduct)));
		cartRepository.carts.add(cart);

		assertTrue(
			Arrays.stream(cartRepository.getClass().getMethods())
				.anyMatch(method -> method.getName().equals("deleteProductFromCart")),
			"CartRepository should contain a method called deleteProductFromCart"
		);

		cartRepository.deleteProductFromCart(cart.getId(), testProduct);
		assertEquals(0, cartRepository.carts.getLast().getProducts().size(),"Product should be deleted correctly");
		
		
	}

	@Test
	void testDeleteProductFromCartService() throws Exception {
		User testUser15=new User();
		testUser15.setId(UUID.randomUUID());
		testUser15.setName("Test User15");
		
		Product testProduct=new Product(UUID.randomUUID(), "Test Product", 10.0);
		userRepository.users.add(testUser15);
		productRepository.products.add(testProduct);
		Cart cart = new Cart(UUID.randomUUID(), testUser15.getId(), new ArrayList<>(List.of(testProduct)));
		cartRepository.carts.add(cart);
		
		assertTrue(
			Arrays.stream(cartService.getClass().getMethods())
				.anyMatch(method -> method.getName().equals("deleteProductFromCart")),
			"CartService should contain a method called deleteProductFromCart"
		);

		cartService.deleteProductFromCart(cart.getId(), testProduct);
		assertEquals(0, cartRepository.carts.getLast().getProducts().size(),"Product should be deleted correctly");
	}

	@Test
	void testDeleteProductFromCartEndPoint1() throws Exception {
		User testUser15=new User();
		testUser15.setId(UUID.randomUUID());
		testUser15.setName("Test User15");
		
		Product testProduct=new Product(UUID.randomUUID(), "Test Product", 10.0);
		userRepository.users.add(testUser15);
		productRepository.products.add(testProduct);
		Cart cart = new Cart(UUID.randomUUID(), testUser15.getId(), new ArrayList<>(List.of(testProduct)));
		cartRepository.carts.add(cart);
		
		mockMvc.perform(MockMvcRequestBuilders.delete("/user/deleteProductFromCart")
				.param("userId", cart.getUserId().toString())
				.param("productId", testProduct.getId().toString()))
				.andExpect(MockMvcResultMatchers.status().isOk())
				.andExpect(MockMvcResultMatchers.content().string("Product deleted from cart"));
	}
	@Test
	void testDeleteProductFromCartEndPoint2() throws Exception {
		User testUser15=new User();
		testUser15.setId(UUID.randomUUID());
		testUser15.setName("Test User15");
		
		Product testProduct=new Product(UUID.randomUUID(), "Test Product", 10.0);
		userRepository.users.add(testUser15);
		productRepository.products.add(testProduct);
		// Cart cart = new Cart(UUID.randomUUID(), testUser15.getId(), new ArrayList<>(List.of(testProduct)));
		// cartRepository.carts.add(cart);
		
		mockMvc.perform(MockMvcRequestBuilders.delete("/user/deleteProductFromCart")
				.param("userId", testUser15.getId().toString())
				.param("productId", testProduct.getId().toString()))
				.andExpect(MockMvcResultMatchers.status().isOk())
				.andExpect(MockMvcResultMatchers.content().string("Cart is empty"));
	}


	@Test
	void testDeleteUserByIdRepository() throws Exception {
		User testUser16=new User();
		testUser16.setId(UUID.randomUUID());
		testUser16.setName("Test User16");
		userRepository.users.add(testUser16);
		assertTrue(
			Arrays.stream(userRepository.getClass().getMethods())
				.anyMatch(method -> method.getName().equals("deleteUserById")),
			"UserRepository should contain a method called deleteUserById"
		);
		
		userRepository.deleteUserById(testUser16.getId());

		assertFalse(userRepository.users.contains(testUser16),"User should be deleted correctly");

		
	}

	@Test
	void testDeleteUserByIdService() throws Exception {
		User testUser17=new User();
		testUser17.setId(UUID.randomUUID());
		testUser17.setName("Test User17");
		userRepository.users.add(testUser17);

		assertTrue(
			Arrays.stream(userService.getClass().getMethods())
				.anyMatch(method -> method.getName().equals("deleteUserById")),
			"UserService should contain a method called deleteUserById"
		);
		
		userService.deleteUserById(testUser17.getId());
		assertFalse(userRepository.users.contains(testUser17),"User should be deleted correctly");

	}

	@Test
	void testDeleteUserByIdEndPoint1() throws Exception {
		User testUser18=new User();
		testUser18.setId(UUID.randomUUID());
		testUser18.setName("Test User18");
		userRepository.users.add(testUser18);
		
		mockMvc.perform(MockMvcRequestBuilders.delete("/user/delete/{id}", testUser18.getId()))
				.andExpect(MockMvcResultMatchers.status().isOk())
				.andExpect(MockMvcResultMatchers.content().string("User deleted successfully"));
	}
	@Test
	void testDeleteUserByIdEndPoint2() throws Exception {
		User testUser18=new User();
		testUser18.setId(UUID.randomUUID());
		testUser18.setName("Test User18");
		userRepository.users.add(testUser18);
		
		mockMvc.perform(MockMvcRequestBuilders.delete("/user/delete/{id}", UUID.randomUUID()))
				.andExpect(MockMvcResultMatchers.status().isOk())
				.andExpect(MockMvcResultMatchers.content().string("User not found"));
	}


	// ------------------------ Product Tests -------------------------

	@Test
	void testAddProductRepository() {

		assertTrue(
			Arrays.stream(productRepository.getClass().getMethods())
				.anyMatch(method -> method.getName().equals("addProduct")),
			"ProductRepository should contain a method called addProduct"
		);
		Product testProduct1=new Product();
		testProduct1.setId(UUID.randomUUID());
		testProduct1.setName("Test Product");
		testProduct1.setPrice(10.0);
		productRepository.addProduct(testProduct1);
		assertTrue(productRepository.products.contains(testProduct1),"Product should be added correctly");

	}
	@Test
	void testAddProductService() {

		assertTrue(
			Arrays.stream(productService.getClass().getMethods())
				.anyMatch(method -> method.getName().equals("addProduct")),
			"ProductService should contain a method called addProduct"
		);
		Product testProduct2=new Product();
		testProduct2.setId(UUID.randomUUID());
		testProduct2.setName("Test Product");
		testProduct2.setPrice(10.0);
		productService.addProduct(testProduct2);
		assertTrue(productRepository.products.contains(testProduct2),"Product should be added correctly");

	}
	@Test
	void testAddProductEndPoint() throws JsonProcessingException, Exception{

		Product testProduct3=new Product();
		testProduct3.setId(UUID.randomUUID());
		testProduct3.setName("Test Product");
		testProduct3.setPrice(10.0);

		
		
		
		mockMvc.perform(MockMvcRequestBuilders.post("/product/")
				.contentType(MediaType.APPLICATION_JSON)
				.content(objectMapper.writeValueAsString(testProduct3)))
				.andExpect(MockMvcResultMatchers.status().isOk());
		
		boolean found=false;

		for(Product product: productRepository.products){
			if(product.getId().equals(testProduct3.getId()) && product.getName().equals(testProduct3.getName()) && product.getPrice()==testProduct3.getPrice()){
				found=true;
				break;
			}
		}
		assertTrue(found,"Product should be added correctly");   
	}

	@Test
	void testGetProductsRepository(){
		
		assertTrue(
			Arrays.stream(productRepository.getClass().getMethods())
				.anyMatch(method -> method.getName().equals("getProducts")),
			"ProductRepository should contain a method called getProducts"
		);
		Product testProduct4=new Product();
		testProduct4.setId(UUID.randomUUID());
		testProduct4.setName("Test Product");
		testProduct4.setPrice(10.0);
		productRepository.products.add(testProduct4);
		
		assertEquals(productRepository.products, productRepository.getProducts(), "Products should be returned correctly");
	}

	@Test
	void testGetProductsService(){
		
		assertTrue(
			Arrays.stream(productService.getClass().getMethods())
				.anyMatch(method -> method.getName().equals("getProducts")),
			"ProductService should contain a method called getProducts"
		);
		Product testProduct5=new Product();
		testProduct5.setId(UUID.randomUUID());
		testProduct5.setName("Test Product");
		testProduct5.setPrice(10.0);
		productRepository.products.add(testProduct5);
		
		assertEquals(productRepository.products, productService.getProducts(), "Products should be returned correctly");
	}

	@Test
	void testGetProductsEndPoint() throws Exception{
		Product testProduct6=new Product();
		testProduct6.setId(UUID.randomUUID());
		testProduct6.setName("Test Product");
		testProduct6.setPrice(10.0);
		productRepository.products.add(testProduct6);
		
		MvcResult result= mockMvc.perform(MockMvcRequestBuilders.get("/product/")
				.contentType(MediaType.APPLICATION_JSON))
				.andExpect(MockMvcResultMatchers.status().isOk())
				.andReturn();
		String responseContent = result.getResponse().getContentAsString();
		List<Product> responseProducts = objectMapper.readValue(responseContent, new TypeReference<List<Product>>() {});
		
		assertEquals(productRepository.products.size(), responseProducts.size(), "Products should be returned correctly From Endpoint");
	}

	@Test
	void testGetProductByIdRepository() throws Exception{
		
		assertTrue(
			Arrays.stream(productRepository.getClass().getMethods())
				.anyMatch(method -> method.getName().equals("getProductById")),
			"ProductRepository should contain a method called getProductById"
		);
		Product testProduct7=new Product();
		testProduct7.setId(UUID.randomUUID());
		testProduct7.setName("Test Product");
		testProduct7.setPrice(10.0);
		productRepository.products.add(testProduct7);
		
		assertEquals(testProduct7, productRepository.getProductById(testProduct7.getId()),"Product should be returned correctly");
		assertEquals(null, productRepository.getProductById(UUID.randomUUID()), "No Product Should be found");
	}

	@Test
	void testGetProductByIdService() throws Exception{
		
		assertTrue(
			Arrays.stream(productService.getClass().getMethods())
				.anyMatch(method -> method.getName().equals("getProductById")),
			"ProductService should contain a method called getProductById"
		);
		Product testProduct8=new Product();
		testProduct8.setId(UUID.randomUUID());
		testProduct8.setName("Test Product");
		testProduct8.setPrice(10.0);
		productRepository.products.add(testProduct8);
		
		assertEquals(testProduct8, productService.getProductById(testProduct8.getId()),"Product should be returned correctly");
		assertEquals(null, productService.getProductById(UUID.randomUUID()), "No Product Should be found");
	}
	@Test
	void testGetProductByIdEndPoint() throws Exception{
		Product testProduct9=new Product();
		testProduct9.setId(UUID.randomUUID());
		testProduct9.setName("Test Product");
		testProduct9.setPrice(10.0);
		productRepository.products.add(testProduct9);
		
		mockMvc.perform(MockMvcRequestBuilders.get("/product/{id}", testProduct9.getId()))
				.andExpect(MockMvcResultMatchers.status().isOk())
				.andExpect(MockMvcResultMatchers.content().json(objectMapper.writeValueAsString(testProduct9)));
	}
	@Test
	void testUpdateProductRepository() throws Exception{
		Product testProduct10=new Product();
		testProduct10.setId(UUID.randomUUID());
		testProduct10.setName("Test Product");
		testProduct10.setPrice(10.0);
		productRepository.products.add(testProduct10);
		assertTrue(
			Arrays.stream(productRepository.getClass().getMethods())
				.anyMatch(method -> method.getName().equals("updateProduct")),
			"ProductRepository should contain a method called updateProduct"
		);
		Product updatedProduct = productRepository.updateProduct(testProduct10.getId(), "UpdatedName", 20.0);
		assertEquals(updatedProduct.getId(),testProduct10.getId(),"Product should be updated correctly");
		assertEquals(updatedProduct.getName(),"UpdatedName","Product should be updated correctly");
		assertEquals(updatedProduct.getPrice(),20.0,"Product should be updated correctly");
	}
	@Test
	void testUpdateProductService() throws Exception{
		Product testProduct11=new Product();
		testProduct11.setId(UUID.randomUUID());
		testProduct11.setName("Test Product");
		testProduct11.setPrice(10.0);
		productRepository.products.add(testProduct11);
		assertTrue(
			Arrays.stream(productService.getClass().getMethods())
				.anyMatch(method -> method.getName().equals("updateProduct")),
			"ProductService should contain a method called updateProduct"
		);
		Product updatedProduct = productService.updateProduct(testProduct11.getId(), "UpdatedName", 20.0);
		assertEquals(updatedProduct.getId(),testProduct11.getId(),"Product should be updated correctly");
		assertEquals(updatedProduct.getName(),"UpdatedName","Product should be updated correctly");
		assertEquals(updatedProduct.getPrice(),20.0,"Product should be updated correctly");
	}

	@Test
	void testUpdateProductEndPoint() throws Exception{
		Product testProduct12=new Product();
		testProduct12.setId(UUID.randomUUID());
		testProduct12.setName("Test Product");
		testProduct12.setPrice(10.0);
		productRepository.products.add(testProduct12);
		Map<String,Object> body=new HashMap<>();
		body.put("newName", "UpdatedName");
		body.put("newPrice", 20.0);
		MvcResult result= mockMvc.perform(MockMvcRequestBuilders.put("/product/update/{id}", testProduct12.getId())
				.contentType(MediaType.APPLICATION_JSON)
				.content(objectMapper.writeValueAsString(body)))
				.andExpect(MockMvcResultMatchers.status().isOk())
				.andReturn();
		String responseContent = result.getResponse().getContentAsString();
		Product updatedProduct = objectMapper.readValue(responseContent, Product.class);
		assertEquals(updatedProduct.getId(),testProduct12.getId(),"Product should be updated correctly");
		assertEquals(updatedProduct.getName(),"UpdatedName","Product should be updated correctly");
		assertEquals(updatedProduct.getPrice(),20.0,"Product should be updated correctly");
	}

	@Test
	void testDeleteProductByIdRepository1() throws Exception{
		Product testProduct13=new Product();
		testProduct13.setId(UUID.randomUUID());
		testProduct13.setName("Test Product");
		testProduct13.setPrice(10.0);
		productRepository.products.add(testProduct13);
		assertTrue(
			Arrays.stream(productRepository.getClass().getMethods())
				.anyMatch(method -> method.getName().equals("deleteProductById")),
			"ProductRepository should contain a method called deleteProductById"
		);
		productRepository.deleteProductById(testProduct13.getId());
		assertFalse(productRepository.products.contains(testProduct13),"Product should be deleted correctly");
	}
	@Test
	void testDeleteProductByIdRepository2() throws Exception{
		
		assertTrue(
			Arrays.stream(productRepository.getClass().getMethods())
				.anyMatch(method -> method.getName().equals("deleteProductById")),
			"ProductRepository should contain a method called deleteProductById"
		);
		boolean exceptionThrown=false;
		try{

			productRepository.deleteProductById(UUID.randomUUID());
		}catch(Exception e){
			exceptionThrown=true;
		}
		assertTrue(exceptionThrown,"No Product should be found");
	}
	@Test
	void testDeleteProductByIdService1() throws Exception{
		Product testProduct14=new Product();
		testProduct14.setId(UUID.randomUUID());
		testProduct14.setName("Test Product");
		testProduct14.setPrice(10.0);
		productRepository.products.add(testProduct14);
		assertTrue(
			Arrays.stream(productService.getClass().getMethods())
				.anyMatch(method -> method.getName().equals("deleteProductById")),
			"ProductService should contain a method called deleteProductById"
		);
		productService.deleteProductById(testProduct14.getId());
		assertFalse(productRepository.products.contains(testProduct14),"Product should be deleted correctly");
	}

	@Test
	void testDeleteProductByIdService2() throws Exception{
		
		assertTrue(
			Arrays.stream(productService.getClass().getMethods())
				.anyMatch(method -> method.getName().equals("deleteProductById")),
			"ProductService should contain a method called deleteProductById"
		);
		boolean exceptionThrown=false;
		try{

			productService.deleteProductById(UUID.randomUUID());
		}catch(Exception e){
			exceptionThrown=true;
		}
		assertTrue(exceptionThrown,"No Product should be found");
	}
	@Test
	void testDeleteProductByIdEndPoint1() throws Exception{
		Product testProduct15=new Product();
		testProduct15.setId(UUID.randomUUID());
		testProduct15.setName("Test Product");
		testProduct15.setPrice(10.0);
		productRepository.products.add(testProduct15);
		mockMvc.perform(MockMvcRequestBuilders.delete("/product/delete/{id}", testProduct15.getId()))
				.andExpect(MockMvcResultMatchers.status().isOk())
				.andExpect(MockMvcResultMatchers.content().string("Product deleted successfully"));
	}

	// --------------------------------- Cart Tests -------------------------


	@Test
	void testAddCartRepository() throws Exception{
		User testUser19=new User();
		testUser19.setId(UUID.randomUUID());
		testUser19.setName("Test User19");
		userRepository.users.add(testUser19);
		assertTrue(
			Arrays.stream(cartRepository.getClass().getMethods())
				.anyMatch(method -> method.getName().equals("addCart")),
			"CartRepository should contain a method called addCart"
		);
		Cart cart = new Cart(UUID.randomUUID(), testUser19.getId(), new ArrayList<>());
		cartRepository.addCart(cart);
		assertTrue(cartRepository.carts.contains(cart),"Cart should be added correctly");
	}

	@Test
	void testAddCartService() throws Exception{
		User testUser20=new User();
		testUser20.setId(UUID.randomUUID());
		testUser20.setName("Test User20");
		userRepository.users.add(testUser20);
		assertTrue(
			Arrays.stream(cartService.getClass().getMethods())
				.anyMatch(method -> method.getName().equals("addCart")),
			"CartService should contain a method called addCart"
		);
		Cart cart = new Cart(UUID.randomUUID(), testUser20.getId(), new ArrayList<>());
		cartService.addCart(cart);
		assertTrue(cartRepository.carts.contains(cart),"Cart should be added correctly");
	}

	@Test
	void testAddCartEndPoint() throws Exception{
		User testUser21=new User();
		testUser21.setId(UUID.randomUUID());
		testUser21.setName("Test User21");
		userRepository.users.add(testUser21);
		mockMvc.perform(MockMvcRequestBuilders.post("/cart/")
				.contentType(MediaType.APPLICATION_JSON)
				.content(objectMapper.writeValueAsString(new Cart(UUID.randomUUID(), testUser21.getId(), new ArrayList<>())))
				)
				.andExpect(MockMvcResultMatchers.status().isOk());
		boolean found=false;
		for(Cart cart: cartRepository.carts){
			if(cart.getUserId().equals(testUser21.getId())){
				found=true;
				break;
			}
		}
		assertTrue(found,"Cart should be added correctly");
	}

	@Test
	void testGetCartsRepository(){
		assertTrue(
			Arrays.stream(cartRepository.getClass().getMethods())
				.anyMatch(method -> method.getName().equals("getCarts")),
			"CartRepository should contain a method called getCarts"
		);
		Cart cart = new Cart(UUID.randomUUID(), UUID.randomUUID(), new ArrayList<>());
		cartRepository.carts.add(cart);
		assertEquals(cartRepository.carts, cartRepository.getCarts(), "Carts should be returned correctly");
	}

	@Test
	void testGetCartsService(){
		assertTrue(
			Arrays.stream(cartService.getClass().getMethods())
				.anyMatch(method -> method.getName().equals("getCarts")),
			"CartService should contain a method called getCarts"
		);
		Cart cart = new Cart(UUID.randomUUID(), UUID.randomUUID(), new ArrayList<>());
		cartRepository.carts.add(cart);
		assertEquals(cartRepository.carts, cartService.getCarts(), "Carts should be returned correctly");
	}

	@Test
	void testGetCartsEndPoint() throws Exception{
		Cart cart = new Cart(UUID.randomUUID(), UUID.randomUUID(), new ArrayList<>());
		cartRepository.carts.add(cart);
		MvcResult result= mockMvc.perform(MockMvcRequestBuilders.get("/cart/")
				.contentType(MediaType.APPLICATION_JSON))
				.andExpect(MockMvcResultMatchers.status().isOk())
				.andReturn();
		String responseContent = result.getResponse().getContentAsString();
		List<Cart> responseCarts = objectMapper.readValue(responseContent, new TypeReference<List<Cart>>() {});
		assertEquals(cartRepository.carts.size(), responseCarts.size(), "Carts should be returned correctly From Endpoint");
	}

	@Test
	void testGetCartByIdRepository() throws Exception{
		assertTrue(
			Arrays.stream(cartRepository.getClass().getMethods())
				.anyMatch(method -> method.getName().equals("getCartById")),
			"CartRepository should contain a method called getCartById"
		);
		Cart cart = new Cart(UUID.randomUUID(), UUID.randomUUID(), new ArrayList<>());
		cartRepository.carts.add(cart);
		assertEquals(cart, cartRepository.getCartById(cart.getId()),"Cart should be returned correctly");
		assertEquals(null, cartRepository.getCartById(UUID.randomUUID()), "No Cart Should be found");
	}

	@Test
	void testGetCartByIdService() throws Exception{
		assertTrue(
			Arrays.stream(cartService.getClass().getMethods())
				.anyMatch(method -> method.getName().equals("getCartById")),
			"CartService should contain a method called getCartById"
		);
		Cart cart = new Cart(UUID.randomUUID(), UUID.randomUUID(), new ArrayList<>());
		cartRepository.carts.add(cart);
		assertEquals(cart, cartService.getCartById(cart.getId()),"Cart should be returned correctly");
		assertEquals(null, cartService.getCartById(UUID.randomUUID()), "No Cart Should be found");
	}

	@Test
	void testGetCartByIdEndPoint() throws Exception{
		Cart cart = new Cart(UUID.randomUUID(), UUID.randomUUID(), new ArrayList<>());
		cartRepository.carts.add(cart);
		mockMvc.perform(MockMvcRequestBuilders.get("/cart/{id}", cart.getId()))
				.andExpect(MockMvcResultMatchers.status().isOk())
				.andExpect(MockMvcResultMatchers.content().json(objectMapper.writeValueAsString(cart)));
	}

	@Test
	void testDeleteCartByIdRepository1(){
		assertTrue(
			Arrays.stream(cartRepository.getClass().getMethods())
				.anyMatch(method -> method.getName().equals("deleteCartById")),
			"CartRepository should contain a method called deleteCartById"
		);
		Cart cart = new Cart(UUID.randomUUID(), UUID.randomUUID(), new ArrayList<>());
		cartRepository.carts.add(cart);
		cartRepository.deleteCartById(cart.getId());
		assertFalse(cartRepository.carts.contains(cart),"Cart should be deleted correctly");
	}
	@Test
	void testDeleteCartByIdRepository2(){
		assertTrue(
			Arrays.stream(cartRepository.getClass().getMethods())
				.anyMatch(method -> method.getName().equals("deleteCartById")),
			"CartRepository should contain a method called deleteCartById"
		);
		boolean exceptionThrown=false;
		try{
			cartRepository.deleteCartById(UUID.randomUUID());
		}catch(Exception e){
			exceptionThrown=true;
		}
		assertTrue(exceptionThrown,"No Cart should be found");
	}

	@Test
	void testDeleteCartByIdService(){
		assertTrue(
			Arrays.stream(cartService.getClass().getMethods())
				.anyMatch(method -> method.getName().equals("deleteCartById")),
			"CartService should contain a method called deleteCartById"
		);
		Cart cart = new Cart(UUID.randomUUID(), UUID.randomUUID(), new ArrayList<>());
		cartRepository.carts.add(cart);
		cartService.deleteCartById(cart.getId());
		assertFalse(cartRepository.carts.contains(cart),"Cart should be deleted correctly");
	}

	@Test
	void testDeleteCartByIdEndPoint() throws Exception{
		Cart cart = new Cart(UUID.randomUUID(), UUID.randomUUID(), new ArrayList<>());
		cartRepository.carts.add(cart);
		mockMvc.perform(MockMvcRequestBuilders.delete("/cart/delete/{id}", cart.getId()))
				.andExpect(MockMvcResultMatchers.status().isOk())
				.andExpect(MockMvcResultMatchers.content().string("Cart deleted successfully"));
	}


	// --------------------------------- Order Tests -------------------------

	@Test
	void testAddOrderRepository(){

		assertTrue(
			Arrays.stream(orderRepository.getClass().getMethods())
				.anyMatch(method -> method.getName().equals("addOrder")),
			"OrderRepository should contain a method called addOrder"
		);
		Order order = new Order(UUID.randomUUID(), UUID.randomUUID(), 10.0, new ArrayList<>());
		orderRepository.addOrder(order);
		assertTrue(orderRepository.orders.contains(order),"Order should be added correctly");
	}
	@Test
	void testAddOrderService(){
		
		assertTrue(
			Arrays.stream(orderService.getClass().getMethods())
				.anyMatch(method -> method.getName().equals("addOrder")),
			"OrderService should contain a method called addOrder"
		);
		Order order = new Order(UUID.randomUUID(), UUID.randomUUID(), 10.0, new ArrayList<>());
		orderService.addOrder(order);
		assertTrue(orderRepository.orders.contains(order),"Order should be added correctly");
	}
	
	@Test
	void testAddOrderEndPoint() throws Exception{
		Order order = new Order(UUID.randomUUID(), UUID.randomUUID(), 10.0, new ArrayList<>());
		mockMvc.perform(MockMvcRequestBuilders.post("/order/")
				.contentType(MediaType.APPLICATION_JSON)
				.content(objectMapper.writeValueAsString(order)))
				.andExpect(MockMvcResultMatchers.status().isOk());
		boolean found=false;
		for(Order o: orderRepository.orders){
			if(o.getId().equals(order.getId())){
				found=true;
				break;
			}
		}
		assertTrue(found,"Order should be added correctly from Endpoint");
	}

	@Test
	void testGetOrdersRepository(){
		assertTrue(
			Arrays.stream(orderRepository.getClass().getMethods())
				.anyMatch(method -> method.getName().equals("getOrders")),
			"OrderRepository should contain a method called getOrders"
		);
		Order order = new Order(UUID.randomUUID(), UUID.randomUUID(), 10.0, new ArrayList<>());
		orderRepository.orders.add(order);
		assertEquals(orderRepository.orders, orderRepository.getOrders(), "Orders should be returned correctly");
	}

	@Test
	void testGetOrdersService(){
		assertTrue(
			Arrays.stream(orderService.getClass().getMethods())
				.anyMatch(method -> method.getName().equals("getOrders")),
			"OrderService should contain a method called getOrders"
		);
		Order order = new Order(UUID.randomUUID(), UUID.randomUUID(), 10.0, new ArrayList<>());
		orderRepository.orders.add(order);
		assertEquals(orderRepository.orders, orderService.getOrders(), "Orders should be returned correctly");
	}

	@Test
	void testGetOrdersEndPoint() throws Exception{

		Order order = new Order(UUID.randomUUID(), UUID.randomUUID(), 10.0, new ArrayList<>());
		orderRepository.orders.add(order);
		MvcResult result= mockMvc.perform(MockMvcRequestBuilders.get("/order/")
				.contentType(MediaType.APPLICATION_JSON))
				.andExpect(MockMvcResultMatchers.status().isOk())
				.andReturn();
		String responseContent = result.getResponse().getContentAsString();
		List<Order> responseOrders = objectMapper.readValue(responseContent, new TypeReference<List<Order>>() {});
		assertEquals(orderRepository.orders.size(), responseOrders.size(), "Orders should be returned correctly From Endpoint");
	}

	@Test
	void testGetOrderByIdRepository(){
		assertTrue(
			Arrays.stream(orderRepository.getClass().getMethods())
				.anyMatch(method -> method.getName().equals("getOrderById")),
			"OrderRepository should contain a method called getOrderById"
		);
		Order order = new Order(UUID.randomUUID(), UUID.randomUUID(), 10.0, new ArrayList<>());
		orderRepository.orders.add(order);
		assertEquals(order, orderRepository.getOrderById(order.getId()),"Order should be returned correctly");
		assertEquals(null, orderRepository.getOrderById(UUID.randomUUID()), "No Order Should be found");
	}

	@Test
	void testGetOrderByIdService(){
		assertTrue(
			Arrays.stream(orderService.getClass().getMethods())
				.anyMatch(method -> method.getName().equals("getOrderById")),
			"OrderService should contain a method called getOrderById"
		);
		Order order = new Order(UUID.randomUUID(), UUID.randomUUID(), 10.0, new ArrayList<>());
		orderRepository.orders.add(order);
		assertEquals(order, orderService.getOrderById(order.getId()),"Order should be returned correctly");
		assertEquals(null, orderService.getOrderById(UUID.randomUUID()), "No Order Should be found");
	}

	@Test
	void testGetOrderByIdEndPoint() throws Exception{
		Order order = new Order(UUID.randomUUID(), UUID.randomUUID(), 10.0, new ArrayList<>());
		orderRepository.orders.add(order);
		MvcResult result= mockMvc.perform(MockMvcRequestBuilders.get("/order/{id}", order.getId()))
				.andExpect(MockMvcResultMatchers.status().isOk())
				.andExpect(MockMvcResultMatchers.content().json(objectMapper.writeValueAsString(order)))
				.andReturn();
		String responseContent = result.getResponse().getContentAsString();
		Order responseOrder = objectMapper.readValue(responseContent, Order.class);
		assertEquals(order.getId(), responseOrder.getId(), "Order should be returned correctly From Endpoint");
	}

	@Test
	void testDeleteOrderByIdRepository(){
		assertTrue(
			Arrays.stream(orderRepository.getClass().getMethods())
				.anyMatch(method -> method.getName().equals("deleteOrderById")),
			"OrderRepository should contain a method called deleteOrderById"
		);
		Order order = new Order(UUID.randomUUID(), UUID.randomUUID(), 10.0, new ArrayList<>());
		orderRepository.orders.add(order);
		orderRepository.deleteOrderById(order.getId());
		assertFalse(orderRepository.orders.contains(order),"Order should be deleted correctly");
	}

	@Test
	void testDeleteOrderByIdService(){
		assertTrue(
			Arrays.stream(orderService.getClass().getMethods())
				.anyMatch(method -> method.getName().equals("deleteOrderById")),
			"OrderService should contain a method called deleteOrderById"
		);
		Order order = new Order(UUID.randomUUID(), UUID.randomUUID(), 10.0, new ArrayList<>());
		orderRepository.orders.add(order);
		orderService.deleteOrderById(order.getId());
		assertFalse(orderRepository.orders.contains(order),"Order should be deleted correctly");
	}

	@Test
	void testDeleteOrderByIdEndPoint() throws Exception{
		Order order = new Order(UUID.randomUUID(), UUID.randomUUID(), 10.0, new ArrayList<>());
		orderRepository.orders.add(order);
		mockMvc.perform(MockMvcRequestBuilders.delete("/order/delete/{id}", order.getId()))
				.andExpect(MockMvcResultMatchers.status().isOk())
				.andExpect(MockMvcResultMatchers.content().string("Order deleted successfully"));
	}

	@Test
	void testDeleteOrderByIdEndPoint2() throws Exception{
		
		mockMvc.perform(MockMvcRequestBuilders.delete("/order/delete/{id}", UUID.randomUUID()))
				.andExpect(MockMvcResultMatchers.status().isOk())
				.andExpect(MockMvcResultMatchers.content().string("Order not found"));
	}

	

	




	

   

}
