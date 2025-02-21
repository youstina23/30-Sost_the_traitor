// package com.example.MiniProject1;

// import static org.junit.jupiter.api.Assertions.assertEquals;
// import static org.junit.jupiter.api.Assertions.assertNotNull;
// import static org.junit.jupiter.api.Assertions.assertNull;
// import static org.junit.jupiter.api.Assertions.assertTrue;

// import java.io.File;
// import java.io.IOException;
// import java.util.ArrayList;
// import java.util.Arrays;
// import java.util.HashMap;
// import java.util.List;
// import java.util.Map;
// import java.util.UUID;

// import org.springframework.http.MediaType;
// import org.junit.jupiter.api.BeforeEach;
// import org.junit.jupiter.api.Test;
// import org.springframework.beans.factory.annotation.Autowired;
// import org.springframework.beans.factory.annotation.Value;
// import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
// import org.springframework.context.annotation.ComponentScan;
// import org.springframework.test.web.servlet.MockMvc;
// import org.springframework.test.web.servlet.MvcResult;
// import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
// import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

// import com.example.model.Cart;
// import com.example.model.Order;
// import com.example.model.Product;
// import com.example.model.User;
// import com.example.repository.CartRepository;
// import com.example.repository.OrderRepository;
// import com.example.repository.ProductRepository;
// import com.example.repository.UserRepository;
// import com.example.service.CartService;
// import com.example.service.OrderService;
// import com.example.service.ProductService;
// import com.example.service.UserService;
// import com.fasterxml.jackson.core.JsonProcessingException;
// import com.fasterxml.jackson.core.type.TypeReference;
// import com.fasterxml.jackson.databind.ObjectMapper;
// @ComponentScan(basePackages = "com.example.*")
// @WebMvcTest
// class MiniProject1ApplicationTests {

// 	@Value("${spring.application.userDataPath}")
//     private String userDataPath;

//     @Value("${spring.application.productDataPath}")
//     private String productDataPath;

//     @Value("${spring.application.orderDataPath}")
//     private String orderDataPath;

//     @Value("${spring.application.cartDataPath}")
//     private String cartDataPath;

// 	@Autowired
// 	private MockMvc mockMvc;

// 	@Autowired
// 	private ObjectMapper objectMapper;

	

// 	@Autowired
// 	private UserService userService;

// 	@Autowired
// 	private CartService cartService;

// 	@Autowired
// 	private ProductService productService;

// 	@Autowired
// 	private OrderService orderService;
// 	@Autowired
// 	private UserRepository userRepository;

// 	@Autowired
// 	private CartRepository cartRepository;

// 	@Autowired
// 	private ProductRepository productRepository;

// 	@Autowired
// 	private OrderRepository orderRepository;

// 	public void overRideAll(){
//         try{
//             objectMapper.writeValue(new File(userDataPath), new ArrayList<User>());
//             objectMapper.writeValue(new File(productDataPath), new ArrayList<Product>());
//             objectMapper.writeValue(new File(orderDataPath), new ArrayList<Order>());
//             objectMapper.writeValue(new File(cartDataPath), new ArrayList<Cart>());
//         } catch (IOException e) {
//             throw new RuntimeException("Failed to write to JSON file", e);
//         }
//     }

//     public Object find(String typeString, Object toFind){
//         switch(typeString){
//             case "User":
//                 ArrayList<User> users = getUsers();
                
//                 for(User user: users){
//                     if(user.getId().equals(((User)toFind).getId())){
//                         return user;
//                     }
//                 }
//                 break;
//             case "Product":
//                 ArrayList<Product> products = getProducts();
//                 for(Product product: products){
//                     if(product.getId().equals(((Product)toFind).getId())){
//                         return product;
//                     }
//                 }
//                 break;
//             case "Order":
//                 ArrayList<Order> orders = getOrders();
//                 for(Order order: orders){
//                     if(order.getId().equals(((Order)toFind).getId())){
//                         return order;
//                     }
//                 }
//                 break;
//             case "Cart":
//                 ArrayList<Cart> carts = getCarts();
//                 for(Cart cart: carts){
//                     if(cart.getId().equals(((Cart)toFind).getId())){
//                         return cart;
//                     }
//                 }
//                 break;
//         }
//         return null;
//     }

//     public Product addProduct(Product product) {
//         try {
//            File file = new File(productDataPath);
//            ArrayList<Product> products;
//            if (!file.exists()) {
//                products = new ArrayList<>();
//            } 
//            else {
//                products = new ArrayList<>(Arrays.asList(objectMapper.readValue(file, Product[].class)));                
//            }
//            products.add(product);
//            objectMapper.writeValue(file, products);
//            return product;
//        } catch (IOException e) {
//            throw new RuntimeException("Failed to write to JSON file", e);
//        }
//    }
//    public ArrayList<Product> getProducts() {
//        try {
//            File file = new File(productDataPath);
//            if (!file.exists()) {
//                return new ArrayList<>();
//            }
//            return new ArrayList<Product>(Arrays.asList(objectMapper.readValue(file, Product[].class)));
//        } catch (IOException e) {
//            throw new RuntimeException("Failed to read from JSON file", e);
//        }
//    }

//    public User addUser(User user) {
//        try {
//            File file = new File(userDataPath);
//            ArrayList<User> users;
//            if (!file.exists()) {
//                users = new ArrayList<>();
//            } 
//            else {
//                users = new ArrayList<>(Arrays.asList(objectMapper.readValue(file, User[].class)));                
//            }
//            users.add(user);
//            objectMapper.writeValue(file, users);
//            return user;
//        } catch (IOException e) {
//            throw new RuntimeException("Failed to write to JSON file", e);
//        }
//    }
//     public ArrayList<User> getUsers() {
//          try {
//               File file = new File(userDataPath);
//               if (!file.exists()) {
//                 return new ArrayList<>();
//               }
//               return new ArrayList<User>(Arrays.asList(objectMapper.readValue(file, User[].class)));
//          } catch (IOException e) {
//               throw new RuntimeException("Failed to read from JSON file", e);
//          }
//     }
//     public Cart addCart(Cart cart){
//        try{
//               File file = new File(cartDataPath);
//               ArrayList<Cart> carts;
//               if (!file.exists()) {
//                 carts = new ArrayList<>();
//               } 
//               else {
//                 carts = new ArrayList<>(Arrays.asList(objectMapper.readValue(file, Cart[].class)));                
//               }
//               carts.add(cart);
//               objectMapper.writeValue(file, carts);
//               return cart;
//          } catch (IOException e) {
//               throw new RuntimeException("Failed to write to JSON file", e);
//        }
//     }
//     public ArrayList<Cart> getCarts() {
//          try {
//               File file = new File(cartDataPath);
//               if (!file.exists()) {
//                 return new ArrayList<>();
//               }
//               return new ArrayList<Cart>(Arrays.asList(objectMapper.readValue(file, Cart[].class)));
//          } catch (IOException e) {
//               throw new RuntimeException("Failed to read from JSON file", e);
//          }
//     }
//     public Order addOrder(Order order){
//          try{
//                   File file = new File(orderDataPath);
//                   ArrayList<Order> orders;
//                   if (!file.exists()) {
//                  orders = new ArrayList<>();
//                   } 
//                   else {
//                  orders = new ArrayList<>(Arrays.asList(objectMapper.readValue(file, Order[].class)));                
//                   }
//                   orders.add(order);
//                   objectMapper.writeValue(file, orders);
//                   return order;
//             } catch (IOException e) {
//                   throw new RuntimeException("Failed to write to JSON file", e);
//          }
//     }
//     public ArrayList<Order> getOrders() {
//          try {
//               File file = new File(orderDataPath);
//               if (!file.exists()) {
//                 return new ArrayList<>();
//               }
//               return new ArrayList<Order>(Arrays.asList(objectMapper.readValue(file, Order[].class)));
//          } catch (IOException e) {
//               throw new RuntimeException("Failed to read from JSON file", e);
//          }
//     }



// 	private UUID userId;
// 	private User testUser;
// 	@BeforeEach
// 	void setUp() {
// 		userId = UUID.randomUUID();
// 		testUser = new User();
// 		testUser.setId(userId);
// 		testUser.setName("Test User");
// 		overRideAll();
// 	}

// 	// ------------------------ User Tests -------------------------
	
// 	@Test
// 	void testAddUserRepository() {
// 		User testUser1 = new User();
// 		testUser1.setId(UUID.randomUUID());
// 		testUser1.setName("Test User1");
// 		assertTrue(
// 			Arrays.stream(userRepository.getClass().getMethods())
// 				.anyMatch(method -> method.getName().equals("addUser")),
// 			"UserRepository should contain a method called addUser"
// 		);
// 		userRepository.addUser(testUser1);
// 		assertNotNull(find("User",testUser1),"User should be added correctly");
// 	}
// 	@Test
// 	void testAddUserService() {
// 		User testUser2 = new User();
// 		testUser2.setId(UUID.randomUUID());
// 		testUser2.setName("Test User2");
// 		assertTrue(
// 			Arrays.stream(userService.getClass().getMethods())
// 				.anyMatch(method -> method.getName().equals("addUser")),
// 			"UserService should contain a method called addUser"
// 		);
// 		userService.addUser(testUser2);
// 		assertNotNull(find("User",testUser2),"User should be added correctly");
// 	}

// 	@Test
// 	void testAddUserEndPoint() throws Exception {
// 		User testUser3 = new User();
// 		testUser3.setId(UUID.randomUUID());
// 		testUser3.setName("Test User3");
		
		
// 		mockMvc.perform(MockMvcRequestBuilders.post("/user/")
// 				.contentType(MediaType.APPLICATION_JSON)
// 				.content(objectMapper.writeValueAsString(testUser3)))
// 				.andExpect(MockMvcResultMatchers.status().isOk());
// 		boolean found=false;

// 		for(User user: getUsers()){
// 			if(user.getId().equals(testUser3.getId()) && user.getName().equals(testUser3.getName())){
// 				found=true;
// 				break;
// 			}
// 		}
// 		assertTrue(found,"User should be added correctly");   
// 	}

// 	@Test void testGetUsersRepository() {
// 		User testUser4 = new User();
// 		testUser4.setId(UUID.randomUUID());
// 		testUser4.setName("Test User4");
// 		assertTrue(
// 			Arrays.stream(userRepository.getClass().getMethods())
// 				.anyMatch(method -> method.getName().equals("getUsers")),
// 			"UserRepository should contain a method called getUsers"
// 		);
// 		addUser(testUser4);
		
		
		
// 		assertEquals(getUsers().size(), userRepository.getUsers().size(), "Users should be returned correctly");
// 		assertNotNull(find("User", testUser4),"User should be returned correctly");
// 	}
	

// 	@Test void testGetUsersService() {
// 		User testUser5 = new User();
// 		testUser5.setId(UUID.randomUUID());
// 		testUser5.setName("Test User5");
// 		assertTrue(
// 			Arrays.stream(userService.getClass().getMethods())
// 				.anyMatch(method -> method.getName().equals("getUsers")),
// 			"UserService should contain a method called getUsers"
// 		);
// 		addUser(testUser5);
		
// 		assertEquals(getUsers().size(), userService.getUsers().size(), "Users should be returned correctly");
// 		assertNotNull(find("User", testUser5),"User should be returned correctly");	}

// 	@Test
// 	void testGetUsersEndPoint() throws Exception {
		
// 		addUser(testUser);
		

// 		MvcResult result= mockMvc.perform(MockMvcRequestBuilders.get("/user/")
// 				.contentType(MediaType.APPLICATION_JSON))
// 				.andExpect(MockMvcResultMatchers.status().isOk())
// 				.andReturn();
// 		String responseContent = result.getResponse().getContentAsString();
// 		List<User> responseUsers = objectMapper.readValue(responseContent, new TypeReference<List<User>>() {});
		
// 		assertEquals(responseUsers.size(), getUsers().size(), "Users should be returned correctly From Endpoint");
// 	}

// 	@Test
// 	void testGetUserByIdRepository() throws Exception {
		
		
// 		assertTrue(
// 			Arrays.stream(userRepository.getClass().getMethods())
// 				.anyMatch(method -> method.getName().equals("getUserById")),
// 			"UserRepository should contain a method called getUserById"
// 		);
// 		User testUser6=new User();
// 		testUser6.setId(UUID.randomUUID());
// 		testUser6.setName("Test User6");
// 		addUser(testUser6);
		
// 		assertEquals(testUser6.getId(),userRepository.getUserById(testUser6.getId()).getId(),"User should be returned correctly");
// 		assertEquals(null,userRepository.getUserById(UUID.randomUUID()), "No User Should be found if Id is not found");
// 	}

// 	@Test
// 	void testGetUserByIdService() throws Exception {
		
		
// 		assertTrue(
// 			Arrays.stream(userService.getClass().getMethods())
// 				.anyMatch(method -> method.getName().equals("getUserById")),
// 			"UserService should contain a method called getUserById"
// 		);
		
// 		User testUser7=new User();
// 		testUser7.setId(UUID.randomUUID());
// 		testUser7.setName("Test User7");
// 		addUser(testUser7);
// 		assertEquals(testUser7.getId(), userRepository.getUserById(testUser7.getId()).getId(),"User should be returned correctly");
// 		assertEquals(null, userRepository.getUserById(UUID.randomUUID()), "No User Should be found");
		
// 	}

// 	@Test
// 	void testGetUserByIdEndPoint() throws Exception {
// 		User testUser8=new User();
// 		testUser8.setId(UUID.randomUUID());
// 		testUser8.setName("Test User8");
// 		addUser(testUser8);
		
// 		mockMvc.perform(MockMvcRequestBuilders.get("/user/{userId}", testUser8.getId()))
// 				.andExpect(MockMvcResultMatchers.status().isOk())
// 				.andExpect(MockMvcResultMatchers.content().json(objectMapper.writeValueAsString(testUser8)));
// 	}

// 	@Test
// 	void testGetOrdersByUserIdRepository() throws Exception {
// 		User testUser9=new User();
// 		testUser9.setId(UUID.randomUUID());
// 		testUser9.setName("Test User9");
// 		List<Order> orders = List.of(new Order(UUID.randomUUID(), testUser9.getId(), 10.0, List.of(new Product(UUID.randomUUID(), "Test Product", 10.0))));
// 		testUser9.setOrders(orders);
// 		addUser(testUser9);
		
		
		
// 		assertTrue(
// 			Arrays.stream(userRepository.getClass().getMethods())
// 				.anyMatch(method -> method.getName().equals("getOrdersByUserId")),
// 			"UserRepository should contain a method called getOrdersByUserId"
// 		);
		
// 		assertEquals(orders.size(), userRepository.getOrdersByUserId(testUser9.getId()).size(),"Orders should be returned correctly");

		
// 	}

// 	@Test
// 	void testGetOrdersByUserIdService() throws Exception {
// 		User testUser10=new User();
// 		testUser10.setId(UUID.randomUUID());
// 		testUser10.setName("Test User10");
// 		List<Order> orders = List.of(new Order(UUID.randomUUID(), testUser10.getId(), 10.0, List.of(new Product(UUID.randomUUID(), "Test Product", 10.0))));
// 		testUser10.setOrders(orders);
// 		addUser(testUser10);
		
// 		assertTrue(
// 			Arrays.stream(userService.getClass().getMethods())
// 				.anyMatch(method -> method.getName().equals("getOrdersByUserId")),
// 			"UserService should contain a method called getOrdersByUserId"
// 		);
		
// 		assertEquals(orders.size(), userService.getOrdersByUserId(testUser10.getId()).size(),"Orders should be returned correctly");
// 	}

// 	@Test
// 	void testGetOrdersByUserIdEndPoint() throws Exception {
// 		User testUser10=new User();
// 		testUser10.setId(UUID.randomUUID());
// 		testUser10.setName("Test User10");
// 		List<Order> orders = List.of(new Order(UUID.randomUUID(), testUser10.getId(), 10.0, List.of(new Product(UUID.randomUUID(), "Test Product", 10.0))));
// 		testUser10.setOrders(orders);
// 		addUser(testUser10);		
// 		mockMvc.perform(MockMvcRequestBuilders.get("/user/{userId}/orders", testUser10.getId()))
// 				.andExpect(MockMvcResultMatchers.status().isOk())
// 				.andExpect(MockMvcResultMatchers.content().json(objectMapper.writeValueAsString(orders)));
// 	}

// 	@Test
// 	void testAddOrderToUserRepository() throws Exception {
// 		User testUser11=new User();
// 		testUser11.setId(UUID.randomUUID());
// 		testUser11.setName("Test User11");
// 		Cart cart=new Cart();
// 		cart.setId(UUID.randomUUID());
// 		cart.setUserId(testUser11.getId());
// 		Product tesProduct=new Product(UUID.randomUUID(), "Test Product", 10.0);
// 		cart.setProducts(List.of(tesProduct));
// 		addUser(testUser11);
// 		addCart(cart);
		
// 		assertTrue(
// 			Arrays.stream(userRepository.getClass().getMethods())
// 				.anyMatch(method -> method.getName().equals("addOrderToUser")),
// 			"UserRepository should contain a method called addOrderToUser"
// 		);
// 		Order order = new Order(UUID.randomUUID(), testUser11.getId(), 10.0, List.of(tesProduct));
// 		userRepository.addOrderToUser(testUser11.getId(), order);

// 		assertEquals(order.getId(), getUsers().getLast().getOrders().get(0).getId(),"Order should be added correctly");

		
// 	}

// 	@Test
// 	void testAddOrderToUserService() throws Exception {
// 		User testUser11=new User();
// 		testUser11.setId(UUID.randomUUID());
// 		testUser11.setName("Test User11");
// 		Cart cart=new Cart();
// 		cart.setId(UUID.randomUUID());
// 		cart.setUserId(testUser11.getId());
// 		Product tesProduct=new Product(UUID.randomUUID(), "Test Product", 10.0);
// 		cart.setProducts(List.of(tesProduct));
// 		addCart(cart);
// 		addUser(testUser11);
		
// 		assertTrue(
// 			Arrays.stream(userService.getClass().getMethods())
// 				.anyMatch(method -> method.getName().equals("addOrderToUser")),
// 			"UserService should contain a method called addOrderToUser"
// 		);

// 		int countBefore=testUser11.getOrders().size();
// 		userService.addOrderToUser(testUser11.getId());
// 		User afterUser=(User) find("User", testUser11);
// 		Cart afterCart=(Cart) find("Cart", cart);
// 		assertEquals(afterUser.getOrders().size(), countBefore+1,"Order should be added correctly");
// 		assertTrue(afterCart.getProducts().isEmpty(),"Cart should be emptied correctly");

	
// 	}

// 	@Test
// 	void testAddOrderToUserEndPoint() throws Exception {
// 		User testUser11=new User();
// 		testUser11.setId(UUID.randomUUID());
// 		testUser11.setName("Test User11");
// 		Cart cart=new Cart();
// 		cart.setId(UUID.randomUUID());
// 		cart.setUserId(testUser11.getId());
// 		Product tesProduct=new Product(UUID.randomUUID(), "Test Product", 10.0);
// 		cart.setProducts(List.of(tesProduct));
// 		addCart(cart);
// 		addUser(testUser11);
		
		
// 		mockMvc.perform(MockMvcRequestBuilders.post("/user/{userId}/checkout", testUser11.getId()))
// 				.andExpect(MockMvcResultMatchers.status().isOk())
// 				.andExpect(MockMvcResultMatchers.content().string("Order added successfully"));
// 	}

// 	@Test
// 	void testRemoveOrderOfUserRepository() throws Exception{
// 		User testUser12=new User();
// 		testUser12.setId(UUID.randomUUID());
// 		testUser12.setName("Test User12");
// 		Product product = new Product(UUID.randomUUID(), "Test Product", 100.0);
// 		Order order = new Order(UUID.randomUUID(), testUser12.getId(), 100.0, List.of(product));
// 		testUser12.getOrders().add(order);
// 		addUser(testUser12);
// 		addOrder(order);
		
// 		assertTrue(
// 			Arrays.stream(userRepository.getClass().getMethods())
// 				.anyMatch(method -> method.getName().equals("removeOrderFromUser")),
// 			"UserRepository should contain a method called removeOrderFromUser"
// 		);
// 		userRepository.removeOrderFromUser(testUser12.getId(), order.getId());
// 		assertEquals(0, getUsers().getLast().getOrders().size(),"Order should be deleted correctly");
// 	}
// 	@Test
// 	void testRemoveOrderOfUserService() throws Exception{
// 		User testUser12=new User();
// 		testUser12.setId(UUID.randomUUID());
// 		testUser12.setName("Test User12");
// 		Product product = new Product(UUID.randomUUID(), "Test Product", 100.0);
// 		Order order = new Order(UUID.randomUUID(), testUser12.getId(), 100.0, List.of(product));
// 		testUser12.getOrders().add(order);
// 		addUser(testUser12);
// 		addOrder(order);
		
// 		assertTrue(
// 			Arrays.stream(userService.getClass().getMethods())
// 				.anyMatch(method -> method.getName().equals("removeOrderFromUser")),
// 			"UserService should contain a method called removeOrderFromUser"
// 		);
// 		userService.removeOrderFromUser(testUser12.getId(), order.getId());
// 		assertEquals(0, getUsers().getLast().getOrders().size(),"Order should be deleted correctly");
// 	}
// 	@Test
// 	void testRemoveOrderOfUserEndPoint() throws Exception{
// 		User testUser12=new User();
// 		testUser12.setId(UUID.randomUUID());
// 		testUser12.setName("Test User12");
// 		Product product = new Product(UUID.randomUUID(), "Test Product", 100.0);
// 		Order order = new Order(UUID.randomUUID(), testUser12.getId(), 100.0, List.of(product));
// 		testUser12.getOrders().add(order);
// 		addUser(testUser12);
// 		addOrder(order);
		
// 		mockMvc.perform(MockMvcRequestBuilders.post("/user/{userId}/removeOrder", testUser12.getId()).param("orderId", order.getId().toString()))
// 				.andExpect(MockMvcResultMatchers.status().isOk())
// 				.andExpect(MockMvcResultMatchers.content().string("Order removed successfully"));
// 	}
// 	@Test
// 	void testEmptyCartService(){
// 		User testUser13=new User();
// 		testUser13.setId(UUID.randomUUID());
// 		testUser13.setName("Test User13");
// 		Product product = new Product(UUID.randomUUID(), "Test Product", 100.0);
// 		Cart cart = new Cart(UUID.randomUUID(), testUser13.getId(), new ArrayList<>(List.of(product)));
// 		addUser(testUser13);
// 		addCart(cart);
		
// 		assertTrue(
// 			Arrays.stream(userService.getClass().getMethods())
// 				.anyMatch(method -> method.getName().equals("emptyCart")),
// 			"UserService should contain a method called emptyCart"
// 		);
// 		userService.emptyCart(testUser13.getId());
// 		assertEquals(0, getCarts().getLast().getProducts().size(),"Cart should be emptied correctly");
// 	}

// 	@Test
// 	void testEmptyCartEndpoint() throws Exception{
// 		User testUser13=new User();
// 		testUser13.setId(UUID.randomUUID());
// 		testUser13.setName("Test User13");
// 		Product product = new Product(UUID.randomUUID(), "Test Product", 100.0);
// 		Cart cart = new Cart(UUID.randomUUID(), testUser13.getId(), new ArrayList<>(List.of(product)));
// 		addUser(testUser13);
// 		addCart(cart);
		
// 		mockMvc.perform(MockMvcRequestBuilders.delete("/user/{userId}/emptyCart", testUser13.getId()))
// 				.andExpect(MockMvcResultMatchers.status().isOk())
// 				.andExpect(MockMvcResultMatchers.content().string("Cart emptied successfully"));
// 	}
// 	@Test
// 	void testAddProductToCartRepository() throws Exception {
// 		User testUser12=new User();
// 		testUser12.setId(UUID.randomUUID());
// 		testUser12.setName("Test User12");
// 		Product product = new Product(UUID.randomUUID(), "Test Product", 100.0);
// 		Cart cart = new Cart(UUID.randomUUID(), testUser12.getId(), new ArrayList<>());
		
// 		addCart(cart);
// 		addUser(testUser12);
		
// 		assertTrue(
// 			Arrays.stream(cartRepository.getClass().getMethods())
// 				.anyMatch(method -> method.getName().equals("addProductToCart")),
// 			"CartRepository should contain a method called addProductToCart"
// 		);

// 		cartRepository.addProductToCart(cart.getId(), product);
// 		assertEquals(product.getId(), getCarts().getLast().getProducts().get(0).getId(),"Product should be added correctly");

// 	}

// 	@Test
// 	void testAddProductToCartService() throws Exception {
// 		User testUser13=new User();
// 		testUser13.setId(UUID.randomUUID());
// 		testUser13.setName("Test User13");
// 		Product product = new Product(UUID.randomUUID(), "Test Product", 100.0);
// 		Cart cart = new Cart(UUID.randomUUID(), testUser13.getId(), new ArrayList<>());
// 		addUser(testUser13);
// 		addCart(cart);
// 		assertTrue(
// 			Arrays.stream(cartService.getClass().getMethods())
// 				.anyMatch(method -> method.getName().equals("addProductToCart")),
// 			"UserService should contain a method called addProductToCart"
// 		);

// 		cartService.addProductToCart(cart.getId(), product);
// 		assertEquals(product.getId(), getCarts().getLast().getProducts().get(0).getId(),"Product should be added correctly");

		
// 	}

// 	@Test
// 	void testAddProductToCartEndPoint() throws Exception {
// 		User testUser14=new User();
// 		testUser14.setId(UUID.randomUUID());
// 		testUser14.setName("Test User14");
		
// 		Product testProduct=new Product(UUID.randomUUID(), "Test Product", 10.0);
// 		addUser(testUser14);
// 		addProduct(testProduct);
		
// 		mockMvc.perform(MockMvcRequestBuilders.put("/user/addProductToCart")
// 				.param("userId", testUser14.getId().toString())
// 				.param("productId", testProduct.getId().toString()))
// 				.andExpect(MockMvcResultMatchers.status().isOk())
// 				.andExpect(MockMvcResultMatchers.content().string("Product added to cart"));
// 		assertTrue(getCarts().getLast().getUserId().equals(testUser14.getId()),"New Cart Should be created for user");
// 		assertEquals(testProduct.getId(), getCarts().getLast().getProducts().get(0).getId(),"Product should be added correctly");
// 	}

// 	@Test
// 	void testDeleteProductFromCartRepository() throws Exception {
// 		User testUser15=new User();
// 		testUser15.setId(UUID.randomUUID());
// 		testUser15.setName("Test User15");
		
// 		Product testProduct=new Product(UUID.randomUUID(), "Test Product", 10.0);
// 		addUser(testUser15);
// 		addProduct(testProduct);
// 		Cart cart = new Cart(UUID.randomUUID(), testUser15.getId(), new ArrayList<>(List.of(testProduct)));
// 		addCart(cart);

// 		assertTrue(
// 			Arrays.stream(cartRepository.getClass().getMethods())
// 				.anyMatch(method -> method.getName().equals("deleteProductFromCart")),
// 			"CartRepository should contain a method called deleteProductFromCart"
// 		);
// 		System.out.println("before "+getCarts());
// 		cartRepository.deleteProductFromCart(cart.getId(), testProduct);
// 		System.out.println("after "+getCarts());
// 		assertEquals(0, getCarts().getLast().getProducts().size(),"Product should be deleted correctly");
		
		
// 	}

// 	@Test
// 	void testDeleteProductFromCartService() throws Exception {
// 		User testUser15=new User();
// 		testUser15.setId(UUID.randomUUID());
// 		testUser15.setName("Test User15");
		
// 		Product testProduct=new Product(UUID.randomUUID(), "Test Product", 10.0);
// 		addUser(testUser15);
// 		addProduct(testProduct);
// 		Cart cart = new Cart(UUID.randomUUID(), testUser15.getId(), new ArrayList<>(List.of(testProduct)));
// 		addCart(cart);
		
// 		assertTrue(
// 			Arrays.stream(cartService.getClass().getMethods())
// 				.anyMatch(method -> method.getName().equals("deleteProductFromCart")),
// 			"CartService should contain a method called deleteProductFromCart"
// 		);

// 		cartService.deleteProductFromCart(cart.getId(), testProduct);
// 		assertEquals(0, getCarts().getLast().getProducts().size(),"Product should be deleted correctly");
// 	}

// 	@Test
// 	void testDeleteProductFromCartEndPoint1() throws Exception {
// 		User testUser15=new User();
// 		testUser15.setId(UUID.randomUUID());
// 		testUser15.setName("Test User15");
		
// 		Product testProduct=new Product(UUID.randomUUID(), "Test Product", 10.0);
// 		addUser(testUser15);
// 		addProduct(testProduct);
// 		Cart cart = new Cart(UUID.randomUUID(), testUser15.getId(), new ArrayList<>(List.of(testProduct)));
// 		addCart(cart);
		
// 		mockMvc.perform(MockMvcRequestBuilders.put("/user/deleteProductFromCart")
// 				.param("userId", cart.getUserId().toString())
// 				.param("productId", testProduct.getId().toString()))
// 				.andExpect(MockMvcResultMatchers.status().isOk())
// 				.andExpect(MockMvcResultMatchers.content().string("Product deleted from cart"));
// 	}
// 	@Test
// 	void testDeleteProductFromCartEndPoint2() throws Exception {
// 		User testUser15=new User();
// 		testUser15.setId(UUID.randomUUID());
// 		testUser15.setName("Test User15");
		
// 		Product testProduct=new Product(UUID.randomUUID(), "Test Product", 10.0);
// 		addUser(testUser15);
// 		addProduct(testProduct);
// 		// Cart cart = new Cart(UUID.randomUUID(), testUser15.getId(), new ArrayList<>(List.of(testProduct)));
// 		// addCart(cart);
		
// 		mockMvc.perform(MockMvcRequestBuilders.put("/user/deleteProductFromCart")
// 				.param("userId", testUser15.getId().toString())
// 				.param("productId", testProduct.getId().toString()))
// 				.andExpect(MockMvcResultMatchers.status().isOk())
// 				.andExpect(MockMvcResultMatchers.content().string("Cart is empty"));
// 	}


// 	@Test
// 	void testDeleteUserByIdRepository() throws Exception {
// 		User testUser16=new User();
// 		testUser16.setId(UUID.randomUUID());
// 		testUser16.setName("Test User16");
// 		addUser(testUser16);
// 		assertTrue(
// 			Arrays.stream(userRepository.getClass().getMethods())
// 				.anyMatch(method -> method.getName().equals("deleteUserById")),
// 			"UserRepository should contain a method called deleteUserById"
// 		);
		
// 		userRepository.deleteUserById(testUser16.getId());
// 		assertNull(find("User", testUser16),"User should be deleted correctly");

		
// 	}

// 	@Test
// 	void testDeleteUserByIdService() throws Exception {
// 		User testUser17=new User();
// 		testUser17.setId(UUID.randomUUID());
// 		testUser17.setName("Test User17");
// 		addUser(testUser17);

// 		assertTrue(
// 			Arrays.stream(userService.getClass().getMethods())
// 				.anyMatch(method -> method.getName().equals("deleteUserById")),
// 			"UserService should contain a method called deleteUserById"
// 		);
		
// 		userService.deleteUserById(testUser17.getId());
// 		assertNull(find("User", testUser17),"User should be deleted correctly");

// 	}

// 	@Test
// 	void testDeleteUserByIdEndPoint1() throws Exception {
// 		User testUser18=new User();
// 		testUser18.setId(UUID.randomUUID());
// 		testUser18.setName("Test User18");
// 		addUser(testUser18);
		
// 		mockMvc.perform(MockMvcRequestBuilders.delete("/user/delete/{userId}", testUser18.getId()))
// 				.andExpect(MockMvcResultMatchers.status().isOk())
// 				.andExpect(MockMvcResultMatchers.content().string("User deleted successfully"));
// 	}
// 	@Test
// 	void testDeleteUserByIdEndPoint2() throws Exception {
// 		User testUser18=new User();
// 		testUser18.setId(UUID.randomUUID());
// 		testUser18.setName("Test User18");
// 		addUser(testUser18);
		
// 		mockMvc.perform(MockMvcRequestBuilders.delete("/user/delete/{userId}", UUID.randomUUID()))
// 				.andExpect(MockMvcResultMatchers.status().isOk())
// 				.andExpect(MockMvcResultMatchers.content().string("User not found"));
// 	}


// 	// ------------------------ Product Tests -------------------------

// 	@Test
// 	void testAddProductRepository() {

// 		assertTrue(
// 			Arrays.stream(productRepository.getClass().getMethods())
// 				.anyMatch(method -> method.getName().equals("addProduct")),
// 			"ProductRepository should contain a method called addProduct"
// 		);
// 		Product testProduct1=new Product();
// 		testProduct1.setId(UUID.randomUUID());
// 		testProduct1.setName("Test Product");
// 		testProduct1.setPrice(10.0);
// 		productRepository.addProduct(testProduct1);
// 		assertNotNull(find("Product",testProduct1),"Product should be added correctly");

// 	}
// 	@Test
// 	void testAddProductService() {

// 		assertTrue(
// 			Arrays.stream(productService.getClass().getMethods())
// 				.anyMatch(method -> method.getName().equals("addProduct")),
// 			"ProductService should contain a method called addProduct"
// 		);
// 		Product testProduct2=new Product();
// 		testProduct2.setId(UUID.randomUUID());
// 		testProduct2.setName("Test Product");
// 		testProduct2.setPrice(10.0);
// 		productService.addProduct(testProduct2);
// 		assertNotNull(find("Product",testProduct2),"Product should be added correctly");
// 	}
// 	@Test
// 	void testAddProductEndPoint() throws JsonProcessingException, Exception{

// 		Product testProduct3=new Product();
// 		testProduct3.setId(UUID.randomUUID());
// 		testProduct3.setName("Test Product");
// 		testProduct3.setPrice(10.0);

		
		
		
// 		mockMvc.perform(MockMvcRequestBuilders.post("/product/")
// 				.contentType(MediaType.APPLICATION_JSON)
// 				.content(objectMapper.writeValueAsString(testProduct3)))
// 				.andExpect(MockMvcResultMatchers.status().isOk());
		
// 		boolean found=false;

// 		for(Product product: getProducts()){
// 			if(product.getId().equals(testProduct3.getId()) && product.getName().equals(testProduct3.getName()) && product.getPrice()==testProduct3.getPrice()){
// 				found=true;
// 				break;
// 			}
// 		}
// 		assertTrue(found,"Product should be added correctly");   
// 	}

// 	@Test
// 	void testGetProductsRepository(){
		
// 		assertTrue(
// 			Arrays.stream(productRepository.getClass().getMethods())
// 				.anyMatch(method -> method.getName().equals("getProducts")),
// 			"ProductRepository should contain a method called getProducts"
// 		);
// 		Product testProduct4=new Product();
// 		testProduct4.setId(UUID.randomUUID());
// 		testProduct4.setName("Test Product");
// 		testProduct4.setPrice(10.0);
// 		addProduct(testProduct4);
		
// 		assertEquals(productRepository.getProducts().size(), getProducts().size(), "Products should be returned correctly");
// 	}

// 	@Test
// 	void testGetProductsService(){
		
// 		assertTrue(
// 			Arrays.stream(productService.getClass().getMethods())
// 				.anyMatch(method -> method.getName().equals("getProducts")),
// 			"ProductService should contain a method called getProducts"
// 		);
// 		Product testProduct5=new Product();
// 		testProduct5.setId(UUID.randomUUID());
// 		testProduct5.setName("Test Product");
// 		testProduct5.setPrice(10.0);
// 		addProduct(testProduct5);
		
// 		assertEquals(productService.getProducts().size(), getProducts().size(), "Products should be returned correctly");
// 	}

// 	@Test
// 	void testGetProductsEndPoint() throws Exception{
// 		Product testProduct6=new Product();
// 		testProduct6.setId(UUID.randomUUID());
// 		testProduct6.setName("Test Product");
// 		testProduct6.setPrice(10.0);
// 		addProduct(testProduct6);
		
// 		MvcResult result= mockMvc.perform(MockMvcRequestBuilders.get("/product/")
// 				.contentType(MediaType.APPLICATION_JSON))
// 				.andExpect(MockMvcResultMatchers.status().isOk())
// 				.andReturn();
// 		String responseContent = result.getResponse().getContentAsString();
// 		List<Product> responseProducts = objectMapper.readValue(responseContent, new TypeReference<List<Product>>() {});
		
// 		assertEquals(getProducts().size(), responseProducts.size(), "Products should be returned correctly From Endpoint");
// 	}

// 	@Test
// 	void testGetProductByIdRepository() throws Exception{
		
// 		assertTrue(
// 			Arrays.stream(productRepository.getClass().getMethods())
// 				.anyMatch(method -> method.getName().equals("getProductById")),
// 			"ProductRepository should contain a method called getProductById"
// 		);
// 		Product testProduct7=new Product();
// 		testProduct7.setId(UUID.randomUUID());
// 		testProduct7.setName("Test Product");
// 		testProduct7.setPrice(10.0);
// 		addProduct(testProduct7);
		
// 		assertEquals(testProduct7.getId(), productRepository.getProductById(testProduct7.getId()).getId(),"Product should be returned correctly");
// 		assertEquals(null, productRepository.getProductById(UUID.randomUUID()), "No Product Should be found");
// 	}

// 	@Test
// 	void testGetProductByIdService() throws Exception{
		
// 		assertTrue(
// 			Arrays.stream(productService.getClass().getMethods())
// 				.anyMatch(method -> method.getName().equals("getProductById")),
// 			"ProductService should contain a method called getProductById"
// 		);
// 		Product testProduct8=new Product();
// 		testProduct8.setId(UUID.randomUUID());
// 		testProduct8.setName("Test Product");
// 		testProduct8.setPrice(10.0);
// 		addProduct(testProduct8);
		
// 		assertEquals(testProduct8.getId(), productService.getProductById(testProduct8.getId()).getId(),"Product should be returned correctly");
// 		assertEquals(null, productService.getProductById(UUID.randomUUID()), "No Product Should be found");
// 	}
// 	@Test
// 	void testGetProductByIdEndPoint() throws Exception{
// 		Product testProduct9=new Product();
// 		testProduct9.setId(UUID.randomUUID());
// 		testProduct9.setName("Test Product");
// 		testProduct9.setPrice(10.0);
// 		addProduct(testProduct9);
		
// 		mockMvc.perform(MockMvcRequestBuilders.get("/product/{productId}", testProduct9.getId()))
// 				.andExpect(MockMvcResultMatchers.status().isOk())
// 				.andExpect(MockMvcResultMatchers.content().json(objectMapper.writeValueAsString(testProduct9)));
// 	}
// 	@Test
// 	void testUpdateProductRepository() throws Exception{
// 		Product testProduct10=new Product();
// 		testProduct10.setId(UUID.randomUUID());
// 		testProduct10.setName("Test Product");
// 		testProduct10.setPrice(10.0);
// 		addProduct(testProduct10);
// 		assertTrue(
// 			Arrays.stream(productRepository.getClass().getMethods())
// 				.anyMatch(method -> method.getName().equals("updateProduct")),
// 			"ProductRepository should contain a method called updateProduct"
// 		);
// 		Product updatedProduct = productRepository.updateProduct(testProduct10.getId(), "UpdatedName", 20.0);
// 		assertEquals(updatedProduct.getId(),testProduct10.getId(),"Product should be updated correctly");
// 		assertEquals(updatedProduct.getName(),"UpdatedName","Product should be updated correctly");
// 		assertEquals(updatedProduct.getPrice(),20.0,"Product should be updated correctly");
// 	}
// 	@Test
// 	void testUpdateProductService() throws Exception{
// 		Product testProduct11=new Product();
// 		testProduct11.setId(UUID.randomUUID());
// 		testProduct11.setName("Test Product");
// 		testProduct11.setPrice(10.0);
// 		addProduct(testProduct11);
// 		assertTrue(
// 			Arrays.stream(productService.getClass().getMethods())
// 				.anyMatch(method -> method.getName().equals("updateProduct")),
// 			"ProductService should contain a method called updateProduct"
// 		);
// 		Product updatedProduct = productService.updateProduct(testProduct11.getId(), "UpdatedName", 20.0);
// 		assertEquals(updatedProduct.getId(),testProduct11.getId(),"Product should be updated correctly");
// 		assertEquals(updatedProduct.getName(),"UpdatedName","Product name should be updated correctly");
// 		assertEquals(updatedProduct.getPrice(),20.0,"Product price should be updated correctly");
// 	}

// 	@Test
// 	void testUpdateProductEndPoint() throws Exception{
// 		Product testProduct12=new Product();
// 		testProduct12.setId(UUID.randomUUID());
// 		testProduct12.setName("Test Product");
// 		testProduct12.setPrice(10.0);
// 		addProduct(testProduct12);
// 		Map<String,Object> body=new HashMap<>();
// 		body.put("newName", "UpdatedName");
// 		body.put("newPrice", 20.0);
// 		MvcResult result= mockMvc.perform(MockMvcRequestBuilders.put("/product/update/{id}", testProduct12.getId())
// 				.contentType(MediaType.APPLICATION_JSON)
// 				.content(objectMapper.writeValueAsString(body)))
// 				.andExpect(MockMvcResultMatchers.status().isOk())
// 				.andReturn();
// 		String responseContent = result.getResponse().getContentAsString();
// 		Product updatedProduct = objectMapper.readValue(responseContent, Product.class);
// 		assertEquals(updatedProduct.getId(),testProduct12.getId(),"Product should be updated correctly");
// 		assertEquals(updatedProduct.getName(),"UpdatedName","Product name should be updated correctly");
// 		assertEquals(updatedProduct.getPrice(),20.0,"Product price should be updated correctly");
// 	}

// 	@Test
// 	void testDeleteProductByIdRepository1() throws Exception{
// 		Product testProduct13=new Product();
// 		testProduct13.setId(UUID.randomUUID());
// 		testProduct13.setName("Test Product");
// 		testProduct13.setPrice(10.0);
// 		addProduct(testProduct13);
// 		assertTrue(
// 			Arrays.stream(productRepository.getClass().getMethods())
// 				.anyMatch(method -> method.getName().equals("deleteProductById")),
// 			"ProductRepository should contain a method called deleteProductById"
// 		);
// 		productRepository.deleteProductById(testProduct13.getId());
// 		assertNull(find("Product", testProduct13),"Product should be deleted correctly");
// 	}
// 	@Test
// 	void testDeleteProductByIdRepository2() throws Exception{
		
// 		assertTrue(
// 			Arrays.stream(productRepository.getClass().getMethods())
// 				.anyMatch(method -> method.getName().equals("deleteProductById")),
// 			"ProductRepository should contain a method called deleteProductById"
// 		);
// 		boolean exceptionThrown=false;
// 		try{

// 			productRepository.deleteProductById(UUID.randomUUID());
// 		}catch(Exception e){
// 			exceptionThrown=true;
// 		}
// 		assertTrue(exceptionThrown,"No Product should be found");
// 	}
// 	@Test
// 	void testDeleteProductByIdService1() throws Exception{
// 		Product testProduct14=new Product();
// 		testProduct14.setId(UUID.randomUUID());
// 		testProduct14.setName("Test Product");
// 		testProduct14.setPrice(10.0);
// 		addProduct(testProduct14);
// 		assertTrue(
// 			Arrays.stream(productService.getClass().getMethods())
// 				.anyMatch(method -> method.getName().equals("deleteProductById")),
// 			"ProductService should contain a method called deleteProductById"
// 		);
// 		productService.deleteProductById(testProduct14.getId());
// 		assertNull(find("Product", testProduct14),"Product should be deleted correctly");
// 	}

// 	@Test
// 	void testDeleteProductByIdService2() throws Exception{
		
// 		assertTrue(
// 			Arrays.stream(productService.getClass().getMethods())
// 				.anyMatch(method -> method.getName().equals("deleteProductById")),
// 			"ProductService should contain a method called deleteProductById"
// 		);
// 		boolean exceptionThrown=false;
// 		try{

// 			productService.deleteProductById(UUID.randomUUID());
// 		}catch(Exception e){
// 			exceptionThrown=true;
// 		}
// 		assertTrue(exceptionThrown,"No Product should be found");
// 	}
// 	@Test
// 	void testDeleteProductByIdEndPoint1() throws Exception{
// 		Product testProduct15=new Product();
// 		testProduct15.setId(UUID.randomUUID());
// 		testProduct15.setName("Test Product");
// 		testProduct15.setPrice(10.0);
// 		addProduct(testProduct15);
// 		mockMvc.perform(MockMvcRequestBuilders.delete("/product/delete/{id}", testProduct15.getId()))
// 				.andExpect(MockMvcResultMatchers.status().isOk())
// 				.andExpect(MockMvcResultMatchers.content().string("Product deleted successfully"));
// 	}

// 	// --------------------------------- Cart Tests -------------------------


// 	@Test
// 	void testAddCartRepository() throws Exception{
// 		User testUser19=new User();
// 		testUser19.setId(UUID.randomUUID());
// 		testUser19.setName("Test User19");
// 		addUser(testUser19);
// 		assertTrue(
// 			Arrays.stream(cartRepository.getClass().getMethods())
// 				.anyMatch(method -> method.getName().equals("addCart")),
// 			"CartRepository should contain a method called addCart"
// 		);
// 		Cart cart = new Cart(UUID.randomUUID(), testUser19.getId(), new ArrayList<>());
// 		cartRepository.addCart(cart);
// 		assertNotNull(find("Cart",cart),"Cart should be added correctly");
// 	}

// 	@Test
// 	void testAddCartService() throws Exception{
// 		User testUser20=new User();
// 		testUser20.setId(UUID.randomUUID());
// 		testUser20.setName("Test User20");
// 		addUser(testUser20);
// 		assertTrue(
// 			Arrays.stream(cartService.getClass().getMethods())
// 				.anyMatch(method -> method.getName().equals("addCart")),
// 			"CartService should contain a method called addCart"
// 		);
// 		Cart cart = new Cart(UUID.randomUUID(), testUser20.getId(), new ArrayList<>());
// 		cartService.addCart(cart);
// 		assertNotNull(find("Cart",cart),"Cart should be added correctly");
// 	}

// 	@Test
// 	void testAddCartEndPoint() throws Exception{
// 		User testUser21=new User();
// 		testUser21.setId(UUID.randomUUID());
// 		testUser21.setName("Test User21");
// 		addUser(testUser21);
// 		mockMvc.perform(MockMvcRequestBuilders.post("/cart/")
// 				.contentType(MediaType.APPLICATION_JSON)
// 				.content(objectMapper.writeValueAsString(new Cart(UUID.randomUUID(), testUser21.getId(), new ArrayList<>())))
// 				)
// 				.andExpect(MockMvcResultMatchers.status().isOk());
// 		boolean found=false;
// 		for(Cart cart: getCarts()){
// 			if(cart.getUserId().equals(testUser21.getId())){
// 				found=true;
// 				break;
// 			}
// 		}
// 		assertTrue(found,"Cart should be added correctly");
// 	}

// 	@Test
// 	void testGetCartsRepository(){
// 		assertTrue(
// 			Arrays.stream(cartRepository.getClass().getMethods())
// 				.anyMatch(method -> method.getName().equals("getCarts")),
// 			"CartRepository should contain a method called getCarts"
// 		);
// 		Cart cart = new Cart(UUID.randomUUID(), UUID.randomUUID(), new ArrayList<>());
// 		addCart(cart);
// 		assertEquals(cartRepository.getCarts().size(), getCarts().size(), "Carts should be returned correctly");
// 	}

// 	@Test
// 	void testGetCartsService(){
// 		assertTrue(
// 			Arrays.stream(cartService.getClass().getMethods())
// 				.anyMatch(method -> method.getName().equals("getCarts")),
// 			"CartService should contain a method called getCarts"
// 		);
// 		Cart cart = new Cart(UUID.randomUUID(), UUID.randomUUID(), new ArrayList<>());
// 		addCart(cart);
// 		assertEquals(cartService.getCarts().size(), getCarts().size(), "Carts should be returned correctly");
// 	}

// 	@Test
// 	void testGetCartsEndPoint() throws Exception{
// 		Cart cart = new Cart(UUID.randomUUID(), UUID.randomUUID(), new ArrayList<>());
// 		addCart(cart);
// 		MvcResult result= mockMvc.perform(MockMvcRequestBuilders.get("/cart/")
// 				.contentType(MediaType.APPLICATION_JSON))
// 				.andExpect(MockMvcResultMatchers.status().isOk())
// 				.andReturn();
// 		String responseContent = result.getResponse().getContentAsString();
// 		List<Cart> responseCarts = objectMapper.readValue(responseContent, new TypeReference<List<Cart>>() {});
// 		assertEquals(getCarts().size(), responseCarts.size(), "Carts should be returned correctly From Endpoint");
// 	}

// 	@Test
// 	void testGetCartByIdRepository() throws Exception{
// 		assertTrue(
// 			Arrays.stream(cartRepository.getClass().getMethods())
// 				.anyMatch(method -> method.getName().equals("getCartById")),
// 			"CartRepository should contain a method called getCartById"
// 		);
// 		Cart cart = new Cart(UUID.randomUUID(), UUID.randomUUID(), new ArrayList<>());
// 		addCart(cart);
// 		assertEquals(cart.getId(), cartRepository.getCartById(cart.getId()).getId(),"Cart should be returned correctly");
// 		assertEquals(null, cartRepository.getCartById(UUID.randomUUID()), "No Cart Should be found");
// 	}

// 	@Test
// 	void testGetCartByIdService() throws Exception{
// 		assertTrue(
// 			Arrays.stream(cartService.getClass().getMethods())
// 				.anyMatch(method -> method.getName().equals("getCartById")),
// 			"CartService should contain a method called getCartById"
// 		);
// 		Cart cart = new Cart(UUID.randomUUID(), UUID.randomUUID(), new ArrayList<>());
// 		addCart(cart);
// 		assertEquals(cart.getId(), cartService.getCartById(cart.getId()).getId(),"Cart should be returned correctly");
// 		assertEquals(null, cartService.getCartById(UUID.randomUUID()), "No Cart Should be found");
// 	}

// 	@Test
// 	void testGetCartByIdEndPoint() throws Exception{
// 		Cart cart = new Cart(UUID.randomUUID(), UUID.randomUUID(), new ArrayList<>());
// 		addCart(cart);
// 		mockMvc.perform(MockMvcRequestBuilders.get("/cart/{id}", cart.getId()))
// 				.andExpect(MockMvcResultMatchers.status().isOk())
// 				.andExpect(MockMvcResultMatchers.content().json(objectMapper.writeValueAsString(cart)));
// 	}

// 	@Test
// 	void testDeleteCartByIdRepository1(){
// 		assertTrue(
// 			Arrays.stream(cartRepository.getClass().getMethods())
// 				.anyMatch(method -> method.getName().equals("deleteCartById")),
// 			"CartRepository should contain a method called deleteCartById"
// 		);
// 		Cart cart = new Cart(UUID.randomUUID(), UUID.randomUUID(), new ArrayList<>());
// 		addCart(cart);
// 		cartRepository.deleteCartById(cart.getId());
// 		assertNull(find("Cart", cart),"Cart should be deleted correctly");
// 	}
// 	@Test
// 	void testDeleteCartByIdRepository2(){
// 		assertTrue(
// 			Arrays.stream(cartRepository.getClass().getMethods())
// 				.anyMatch(method -> method.getName().equals("deleteCartById")),
// 			"CartRepository should contain a method called deleteCartById"
// 		);
// 		boolean exceptionThrown=false;
// 		try{
// 			cartRepository.deleteCartById(UUID.randomUUID());
// 		}catch(Exception e){
// 			exceptionThrown=true;
// 		}
// 		assertTrue(exceptionThrown,"No Cart should be found");
// 	}

// 	@Test
// 	void testDeleteCartByIdService(){
// 		assertTrue(
// 			Arrays.stream(cartService.getClass().getMethods())
// 				.anyMatch(method -> method.getName().equals("deleteCartById")),
// 			"CartService should contain a method called deleteCartById"
// 		);
// 		Cart cart = new Cart(UUID.randomUUID(), UUID.randomUUID(), new ArrayList<>());
// 		addCart(cart);
// 		cartService.deleteCartById(cart.getId());
// 		assertNull(find("Cart", cart),"Cart should be deleted correctly");
// 	}

// 	@Test
// 	void testDeleteCartByIdEndPoint() throws Exception{
// 		Cart cart = new Cart(UUID.randomUUID(), UUID.randomUUID(), new ArrayList<>());
// 		addCart(cart);
// 		mockMvc.perform(MockMvcRequestBuilders.delete("/cart/delete/{id}", cart.getId()))
// 				.andExpect(MockMvcResultMatchers.status().isOk())
// 				.andExpect(MockMvcResultMatchers.content().string("Cart deleted successfully"));
// 	}


// 	// --------------------------------- Order Tests -------------------------

// 	@Test
// 	void testAddOrderRepository(){

// 		assertTrue(
// 			Arrays.stream(orderRepository.getClass().getMethods())
// 				.anyMatch(method -> method.getName().equals("addOrder")),
// 			"OrderRepository should contain a method called addOrder"
// 		);
// 		Order order = new Order(UUID.randomUUID(), UUID.randomUUID(), 10.0, new ArrayList<>());
// 		orderRepository.addOrder(order);
// 		assertNotNull(find("Order", order),"Order should be added correctly");
// 	}
// 	@Test
// 	void testAddOrderService(){
		
// 		assertTrue(
// 			Arrays.stream(orderService.getClass().getMethods())
// 				.anyMatch(method -> method.getName().equals("addOrder")),
// 			"OrderService should contain a method called addOrder"
// 		);
// 		Order order = new Order(UUID.randomUUID(), UUID.randomUUID(), 10.0, new ArrayList<>());
// 		orderService.addOrder(order);
// 		assertNotNull(find("Order", order),"Order should be added correctly");
// 	}
	
// 	@Test
// 	void testAddOrderEndPoint() throws Exception{
// 		Order order = new Order(UUID.randomUUID(), UUID.randomUUID(), 10.0, new ArrayList<>());
// 		mockMvc.perform(MockMvcRequestBuilders.post("/order/")
// 				.contentType(MediaType.APPLICATION_JSON)
// 				.content(objectMapper.writeValueAsString(order)))
// 				.andExpect(MockMvcResultMatchers.status().isOk());
// 		boolean found=false;
// 		for(Order o: getOrders()){
// 			if(o.getId().equals(order.getId())){
// 				found=true;
// 				break;
// 			}
// 		}
// 		assertTrue(found,"Order should be added correctly from Endpoint");
// 	}

// 	@Test
// 	void testGetOrdersRepository(){
// 		assertTrue(
// 			Arrays.stream(orderRepository.getClass().getMethods())
// 				.anyMatch(method -> method.getName().equals("getOrders")),
// 			"OrderRepository should contain a method called getOrders"
// 		);
// 		Order order = new Order(UUID.randomUUID(), UUID.randomUUID(), 10.0, new ArrayList<>());
// 		addOrder(order);
// 		assertEquals(getOrders().size(), orderRepository.getOrders().size(), "Orders should be returned correctly");
// 	}

// 	@Test
// 	void testGetOrdersService(){
// 		assertTrue(
// 			Arrays.stream(orderService.getClass().getMethods())
// 				.anyMatch(method -> method.getName().equals("getOrders")),
// 			"OrderService should contain a method called getOrders"
// 		);
// 		Order order = new Order(UUID.randomUUID(), UUID.randomUUID(), 10.0, new ArrayList<>());
// 		addOrder(order);
// 		assertEquals(getOrders().size(), orderService.getOrders().size(), "Orders should be returned correctly");
// 	}

// 	@Test
// 	void testGetOrdersEndPoint() throws Exception{

// 		Order order = new Order(UUID.randomUUID(), UUID.randomUUID(), 10.0, new ArrayList<>());
// 		addOrder(order);
// 		MvcResult result= mockMvc.perform(MockMvcRequestBuilders.get("/order/")
// 				.contentType(MediaType.APPLICATION_JSON))
// 				.andExpect(MockMvcResultMatchers.status().isOk())
// 				.andReturn();
// 		String responseContent = result.getResponse().getContentAsString();
// 		List<Order> responseOrders = objectMapper.readValue(responseContent, new TypeReference<List<Order>>() {});
// 		assertEquals(getOrders().size(), responseOrders.size(), "Orders should be returned correctly From Endpoint");
// 	}

// 	@Test
// 	void testGetOrderByIdRepository(){
// 		assertTrue(
// 			Arrays.stream(orderRepository.getClass().getMethods())
// 				.anyMatch(method -> method.getName().equals("getOrderById")),
// 			"OrderRepository should contain a method called getOrderById"
// 		);
// 		Order order = new Order(UUID.randomUUID(), UUID.randomUUID(), 10.0, new ArrayList<>());
// 		addOrder(order);
// 		assertEquals(order.getId(), orderRepository.getOrderById(order.getId()).getId(),"Order should be returned correctly");
// 		assertEquals(null, orderRepository.getOrderById(UUID.randomUUID()), "No Order Should be found");
// 	}

// 	@Test
// 	void testGetOrderByIdService(){
// 		assertTrue(
// 			Arrays.stream(orderService.getClass().getMethods())
// 				.anyMatch(method -> method.getName().equals("getOrderById")),
// 			"OrderService should contain a method called getOrderById"
// 		);
// 		Order order = new Order(UUID.randomUUID(), UUID.randomUUID(), 10.0, new ArrayList<>());
// 		addOrder(order);
// 		assertEquals(order.getId(), orderService.getOrderById(order.getId()).getId(),"Order should be returned correctly");
// 		assertEquals(null, orderService.getOrderById(UUID.randomUUID()), "No Order Should be found");
// 	}

// 	@Test
// 	void testGetOrderByIdEndPoint() throws Exception{
// 		Order order = new Order(UUID.randomUUID(), UUID.randomUUID(), 10.0, new ArrayList<>());
// 		addOrder(order);
// 		mockMvc.perform(MockMvcRequestBuilders.get("/order/{id}", order.getId()))
// 				.andExpect(MockMvcResultMatchers.status().isOk())
// 				.andExpect(MockMvcResultMatchers.content().json(objectMapper.writeValueAsString(order)))
// 				.andReturn();
// 		// String responseContent = result.getResponse().getContentAsString();
// 		// Order responseOrder = objectMapper.readValue(responseContent, Order.class);
// 		// assertEquals(order.getId(), responseOrder.getId(), "Order should be returned correctly From Endpoint");
// 	}

// 	@Test
// 	void testDeleteOrderByIdRepository(){
// 		assertTrue(
// 			Arrays.stream(orderRepository.getClass().getMethods())
// 				.anyMatch(method -> method.getName().equals("deleteOrderById")),
// 			"OrderRepository should contain a method called deleteOrderById"
// 		);
// 		Order order = new Order(UUID.randomUUID(), UUID.randomUUID(), 10.0, new ArrayList<>());
// 		addOrder(order);
// 		orderRepository.deleteOrderById(order.getId());
// 		assertNull(find("Order", order),"Order should be deleted correctly");
// 	}

// 	@Test
// 	void testDeleteOrderByIdService(){
// 		assertTrue(
// 			Arrays.stream(orderService.getClass().getMethods())
// 				.anyMatch(method -> method.getName().equals("deleteOrderById")),
// 			"OrderService should contain a method called deleteOrderById"
// 		);
// 		Order order = new Order(UUID.randomUUID(), UUID.randomUUID(), 10.0, new ArrayList<>());
// 		addOrder(order);
// 		orderService.deleteOrderById(order.getId());
// 		assertNull(find("Order", order),"Order should be deleted correctly");
// 	}

// 	@Test
// 	void testDeleteOrderByIdEndPoint() throws Exception{
// 		Order order = new Order(UUID.randomUUID(), UUID.randomUUID(), 10.0, new ArrayList<>());
// 		addOrder(order);
// 		mockMvc.perform(MockMvcRequestBuilders.delete("/order/delete/{id}", order.getId()))
// 				.andExpect(MockMvcResultMatchers.status().isOk())
// 				.andExpect(MockMvcResultMatchers.content().string("Order deleted successfully"));
// 	}

// 	@Test
// 	void testDeleteOrderByIdEndPoint2() throws Exception{
		
// 		mockMvc.perform(MockMvcRequestBuilders.delete("/order/delete/{id}", UUID.randomUUID()))
// 				.andExpect(MockMvcResultMatchers.status().isOk())
// 				.andExpect(MockMvcResultMatchers.content().string("Order not found"));
// 	}

	

	




	

   

// }
