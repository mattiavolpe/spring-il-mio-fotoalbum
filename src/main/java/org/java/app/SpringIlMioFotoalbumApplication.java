package org.java.app;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import java.util.ListIterator;
import java.util.Random;

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
		
		Random random = new Random();
		
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
		
		List<User> users = new ArrayList<User>();
		
		users.add(superadmin);
		users.add(user);
		users.add(user2);
		
		System.out.println("----------\nUSERS SEEDED\n----------\n");
		
		Category landscape = new Category("Landscape");
		Category portrait = new Category("Portrait");
		Category night = new Category("Night");
		Category animal = new Category("Animal");
		Category food = new Category("Food");
		Category wedding = new Category("Wedding");
		Category city = new Category("City");
		Category shape = new Category("Shape");
		Category war = new Category("War");
		
		categoryService.saveCategory(landscape);
		categoryService.saveCategory(portrait);
		categoryService.saveCategory(night);
		categoryService.saveCategory(animal);
		categoryService.saveCategory(food);
		categoryService.saveCategory(wedding);
		categoryService.saveCategory(city);
		categoryService.saveCategory(shape);
		categoryService.saveCategory(war);
		
		System.out.println("----------\nCATEGORIES SEEDED\n----------\n");
		
		Photo photo1 = new Photo("Serenity at the Mountain's Edge", "Nestled by the calm waters of the lake, this idyllic scene captures a rustic wooden cottage at a picturesque lakeside port. The tranquil reflection of the surrounding mountains on the still water creates a mesmerizing tableau of nature's beauty and serenity.", "https://images.unsplash.com/photo-1470770841072-f978cf4d019e", true, false, users.get(random.nextInt(users.size())), landscape);
		Photo photo2 = new Photo("Mystic Earth Reverence", "This evocative photograph captures a middle-aged man, most likely of Indian descent, donning the distinctive attire of a monk. His serene countenance, adorned with earthy pigment on his forehead, pays homage to an age-old tradition that holds a profound reverence for the elements and the spiritual path.", "https://images.unsplash.com/photo-1514907728441-b33bec65e315", true, false, users.get(random.nextInt(users.size())), portrait);
		Photo photo3 = new Photo("Galactic Dreamscape", "Under the star-studded canvas of the night sky, the Milky Way weaves its cosmic tapestry above a tranquil forest. In the foreground, a shimmering lake reflects the celestial spectacle, creating a mesmerizing dreamscape that evokes the boundless wonders of the universe and the serene beauty of nature.", "https://images.unsplash.com/photo-1533683083439-1a776a5653cb", true, false, users.get(random.nextInt(users.size())), landscape, night);
		Photo photo4 = new Photo("Vibrant Camouflage", "Against a blurred backdrop of trees, a brilliantly colorful chameleon gracefully traverses a branch. Its vivid hues and delicate movements showcase the mesmerizing artistry of nature's camouflage, where vibrant life thrives in the heart of the jungle.", "https://images.unsplash.com/photo-1633008988882-b7193dc43a22", true, false, users.get(random.nextInt(users.size())), animal);
		Photo photo5 = new Photo("Savory Salmon Delight", "This delectable photo captures succulent salmon sushi rolls filled with creamy Philadelphia cheese, a touch of chili for a kick, and a side of soy sauce for dipping. The perfect combination of flavors and textures in a single bite, offering a culinary journey that's as pleasing to the eyes as it is to the palate.", "https://images.unsplash.com/photo-1579871494447-9811cf80d66c", true, false, users.get(random.nextInt(users.size())), food);
		Photo photo6 = new Photo("Love Beyond the Misty Peaks", "In this enchanting photograph, a newlywed couple clasps hands, standing together against a backdrop of mist-covered landscapes. Towering mountains loom in the distance, symbolizing the vast journey they're embarking upon as a team, their love unshaken by the fog of uncertainty.", "https://images.unsplash.com/photo-1498979237786-9c35706bd45e", true, false, users.get(random.nextInt(users.size())), wedding, landscape);
		Photo photo7 = new Photo("Prague's Nocturnal Elegance", "A mesmerizing night capture of Mala Strana, Prague's charming quarter. The iconic lanterns cast a warm glow along the cobbled streets, leading the way to the stunning silhouette of St. Nicholas Church, a masterpiece of baroque architecture. This image encapsulates the timeless beauty and romance of the city at night.", "https://images.unsplash.com/photo-1532631287453-22064af5316b", true, false, users.get(random.nextInt(users.size())), city, night);
		Photo photo8 = new Photo("Majestic White Tiger", "A breathtaking image of a white tiger, standing regal and powerful in the heart of the forest. Its striking presence amidst the lush greenery embodies both the enigmatic beauty of nature and the fierce grace of this rare and magnificent creature.", "https://images.unsplash.com/photo-1602491453631-e2a5ad90a131", true, false, users.get(random.nextInt(users.size())), animal);
		Photo photo9 = new Photo("Urban Nexus", "A striking aerial perspective of a cityscape reveals a sprawling metropolis with a colossal multi-tiered interchange at its core. This photograph captures the intricate web of pathways and the pulse of city life, where countless roads converge and diverge, symbolizing the bustling energy and complexity of urban existence.", "https://images.unsplash.com/photo-1465447142348-e9952c393450", true, false, users.get(random.nextInt(users.size())), city);
		Photo photo10 = new Photo("Banana Bliss Pancakes", "Indulge in the irresistible sight of golden, fluffy pancakes adorned with slices of ripe banana and drizzled with a generous pour of maple syrup. This mouthwatering image captures the perfect blend of textures and flavors, offering a delectable breakfast experience that's both comforting and enticing.", "https://images.unsplash.com/photo-1567620905732-2d1ec7ab7445", true, false, users.get(random.nextInt(users.size())), food);
		Photo photo11 = new Photo("Edinburgh's Sunset Ascent", "In the enchanting city of Edinburgh, a charming uphill road unfolds beneath the warm hues of the setting sun. On one side, rows of pastel-colored cottages stand in picturesque harmony, casting a dreamy glow. This photograph encapsulates the magic of the city as it bids farewell to the day, promising a journey of exploration and wonder.", "https://images.unsplash.com/photo-1594800083755-8fe31b2c99df", true, false, users.get(random.nextInt(users.size())), city, night);
		Photo photo12 = new Photo("Quaint English Haven", "This aerial view captures the essence of a delightful town, reminiscent of the cozy, attached cottages found in English and Scottish landscapes. The closely-knit houses create a charming and harmonious scene, evoking the idyllic charm of traditional British towns.", "https://images.unsplash.com/photo-1696802537968-6c5b792f40cf", true, false, users.get(random.nextInt(users.size())), city);
		Photo photo13 = new Photo("Impalas of the Arid Expanse", "This captivating image showcases a herd of impalas gracefully navigating the arid desert terrain. In a stark and unforgiving landscape, these resilient creatures epitomize the adaptability and grace of wildlife, finding their place in the wilderness.", "https://images.unsplash.com/photo-1682687220866-c856f566f1bd", true, false, users.get(random.nextInt(users.size())), animal);
		Photo photo14 = new Photo("Wanderlust Lens", "A wanderlust-driven traveler captures the world's essence through her lens. In a single click, she preserves the magic of her journey, revealing a story that speaks of adventure, discovery, and an enduring love for exploration.", "https://images.unsplash.com/photo-1697081544011-e472e6a19cc8", true, false, users.get(random.nextInt(users.size())), portrait);
		Photo photo15 = new Photo("Abstract Elegance", "This photograph showcases a collection of dark, almost black, indistinct and undulating forms set against a stark white background. It conveys a sense of mysterious sculpted shadows, inspiring viewers to ponder the enigmatic shapes and the interplay between light and darkness.", "https://images.unsplash.com/photo-1697143493170-8cf836596b34", true, false, users.get(random.nextInt(users.size())), shape);
		Photo photo16 = new Photo("Iridescent Arctic Dreams", "This stunning image captures the ethereal beauty of the Northern Lights, painting the night sky with shimmering shades of green and violet. Against a pristine snowy landscape, the aurora borealis adds a touch of otherworldly magic to the Arctic wilderness, creating a captivating scene that seems to belong to a dream.", "https://images.unsplash.com/photo-1531366936337-7c912a4589a7", true, false, users.get(random.nextInt(users.size())), landscape, night);
		Photo photo17 = new Photo("Hokan-ji Temple Serenity: Kyoto's Graceful Souls", "In the heart of Kyoto, two Japanese women gracefully traverse a traditional alleyway, the Hokan-ji Temple standing tall in the background. This photograph encapsulates the timeless essence of Kyoto, where heritage and culture harmoniously coexist, creating a serene and captivating tableau.", "https://images.unsplash.com/photo-1493976040374-85c8e12f0c0e", true, false, users.get(random.nextInt(users.size())), city, night);
		Photo photo18 = new Photo("Survivors of the Wreckage", "Amidst the devastation of war, a haunting image emerges. A father and his son walk down a desolate road, flanked by the ruins of their once-vibrant city. The stark contrast between the innocence of the child and the harsh reality surrounding them tells a poignant story of hope and resilience in the face of adversity.", "https://images.unsplash.com/photo-1643275590906-e44bbef1a543", true, false, users.get(random.nextInt(users.size())), war, city);
		
		photoService.savePhoto(photo1);
		photoService.savePhoto(photo2);
		photoService.savePhoto(photo3);
		photoService.savePhoto(photo4);
		photoService.savePhoto(photo5);
		photoService.savePhoto(photo6);
		photoService.savePhoto(photo7);
		photoService.savePhoto(photo8);
		photoService.savePhoto(photo9);
		photoService.savePhoto(photo10);
		photoService.savePhoto(photo11);
		photoService.savePhoto(photo12);
		photoService.savePhoto(photo13);
		photoService.savePhoto(photo14);
		photoService.savePhoto(photo15);
		photoService.savePhoto(photo16);
		photoService.savePhoto(photo17);
		photoService.savePhoto(photo18);
		
		List<Photo> photos = new ArrayList<Photo>();
		
		photos.add(photo1);
		photos.add(photo2);
		photos.add(photo3);
		photos.add(photo4);
		photos.add(photo5);
		photos.add(photo6);
		photos.add(photo7);
		photos.add(photo8);
		photos.add(photo9);
		photos.add(photo10);
		
		for (Photo photo : photos) {
			photo.setVisible(3 > random.nextInt(4));
			photo.setHiddenBySuperadmin(0 == random.nextInt(2));
			if (photo.getUser().getId() == 1 && !photo.getVisible())
				photo.setHiddenBySuperadmin(true);
			
			if (photo.getVisible())
				photo.setHiddenBySuperadmin(false);
			
			photoService.savePhoto(photo);
		}
		
		System.out.println("----------\nPHOTOS SEEDED\n----------\n");
	}
}
