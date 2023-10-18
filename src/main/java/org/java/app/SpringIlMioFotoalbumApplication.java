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
		
		userService.saveUser(superadmin);
		userService.saveUser(user);
		
		System.out.println("\n----------\nUSERS SEEDED\n----------\n");
		
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
		
		System.out.println("\n----------\nCATEGORIES SEEDED\n----------\n");
		
		Photo photo1 = new Photo("Mountain and lake photo", "A lake mirroring the mountains", "https://images.pexels.com/photos/147411/italy-mountains-dawn-daybreak-147411.jpeg", true, landscape);
		Photo photo2 = new Photo("Middle-age man", "A middle-age man with blue eyes, white hairs and a long white beard", "https://images.pexels.com/photos/4556737/pexels-photo-4556737.jpeg", true, portrait);
		Photo photo3 = new Photo("Milky Way through the trees", "A view of the Milky Way through the leafs of forest trees", "https://images.pexels.com/photos/1327786/pexels-photo-1327786.jpeg", true, landscape, night);
		Photo photo4 = new Photo("Colorful chameleon", "A colorful chameleon with a blurred background", "https://images.pexels.com/photos/567540/pexels-photo-567540.jpeg", true, animal);
		Photo photo5 = new Photo("Salmon sushi rolls", "A plate full of salmon sushi rolls", "https://images.pexels.com/photos/3620705/pexels-photo-3620705.jpeg", true, food);
		Photo photo6 = new Photo("Wedding bouquet", "Husband and wife hands holding a wedding bouquet", "https://images.pexels.com/photos/1730877/pexels-photo-1730877.jpeg", true, wedding);
		
		photoService.savePhoto(photo1);
		photoService.savePhoto(photo2);
		photoService.savePhoto(photo3);
		photoService.savePhoto(photo4);
		photoService.savePhoto(photo5);
		photoService.savePhoto(photo6);
		
		System.out.println("\n----------\nPHOTOS SEEDED\n----------\n");
	}
}
