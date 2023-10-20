package org.java.app;

import org.java.app.db.auth.pojo.Role;
import org.java.app.db.auth.pojo.User;
import org.java.app.db.auth.service.RoleService;
import org.java.app.db.auth.service.UserService;
import org.java.app.db.pojo.Category;
import org.java.app.db.pojo.Photo;
import org.java.app.db.service.CategoryService;
import org.java.app.db.service.PhotoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

@SpringBootApplication
public class SpringIlMioFotoalbumApplication implements CommandLineRunner {
	
	@Autowired
	private RoleService roleService;
	
	@Autowired
	private UserService userService;
	
	@Autowired
	private CategoryService categoryService;
	
	@Autowired
	private PhotoService photoService;

	public static void main(String[] args) {
		SpringApplication.run(SpringIlMioFotoalbumApplication.class, args);
	}

	@Override
	public void run(String... args) throws Exception {
		
		Role superadminRole = new Role("SUPERADMIN");
		Role userRole = new Role("USER");
		
		roleService.saveRole(superadminRole);
		roleService.saveRole(userRole);
		
		System.out.println("\n----------\nROLES SEEDED\n----------\n");
		
		User superadmin = new User("superadmin", new BCryptPasswordEncoder().encode("password"), superadminRole);
		User user = new User("user", new BCryptPasswordEncoder().encode("password"), userRole);
		User user2 = new User("user2", new BCryptPasswordEncoder().encode("password"), userRole);
		
		userService.saveUser(superadmin);
		userService.saveUser(user);
		userService.saveUser(user2);
		
		System.out.println("----------\nUSERS SEEDED\n----------\n");
		
		Category landscape = new Category("Landscape");
		Category portrait = new Category("Portrait");
		Category night = new Category("Night");
		Category animal = new Category("Animal");
		Category food = new Category("Food");
		Category wedding = new Category("Wedding");
		
		categoryService.saveCategory(landscape);
		categoryService.saveCategory(portrait);
		categoryService.saveCategory(night);
		categoryService.saveCategory(animal);
		categoryService.saveCategory(food);
		categoryService.saveCategory(wedding);
		
		System.out.println("----------\nCATEGORIES SEEDED\n----------\n");
		
		Photo photo1 = new Photo("Mountain and lake", "A lake mirroring the mountains", "https://images.unsplash.com/photo-1470770841072-f978cf4d019e", true, false, superadmin, landscape);
		Photo photo2 = new Photo("Old indian man", "An old indian man with a long beard and typical clothing", "https://images.unsplash.com/photo-1514907728441-b33bec65e315", true, false, superadmin, portrait);
		Photo photo3 = new Photo("Milky Way over the trees", "A view of the Milky Way over the forest trees", "https://images.unsplash.com/photo-1533683083439-1a776a5653cb", false, true, user, landscape, night);
		Photo photo4 = new Photo("Colorful chameleon", "A colorful chameleon on a blurred background", "https://images.unsplash.com/photo-1633008988882-b7193dc43a22", true, false, user, animal);
		Photo photo5 = new Photo("Salmon sushi rolls", "A plate full of salmon sushi rolls", "https://images.unsplash.com/photo-1579871494447-9811cf80d66c", false, true, superadmin, food);
		Photo photo6 = new Photo("Together towards infinity", "Husband and wife holding each other hands, standing in front of a infinite landscape", "https://images.unsplash.com/photo-1498979237786-9c35706bd45e", true, false, superadmin, wedding, landscape);
		
		photoService.savePhoto(photo1);
		photoService.savePhoto(photo2);
		photoService.savePhoto(photo3);
		photoService.savePhoto(photo4);
		photoService.savePhoto(photo5);
		photoService.savePhoto(photo6);
		
		System.out.println("----------\nPHOTOS SEEDED\n----------\n");
	}
}
