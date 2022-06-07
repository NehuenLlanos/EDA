package space;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;

import javax.imageio.ImageIO;

public class Generate {

		public static void main1(String[] args) throws IOException {
			
			ArrayList<BufferedImage> cartoons= new ArrayList<BufferedImage>(100000);
			
			for(int rec= 0; rec < 100000; rec++ )
			{
				String fileName= String.format("/%02d.jpg", rec % 12);
				
				BufferedImage img = ImageIO.read(new File( Generate.class.getClass().getResource(fileName).getFile() ));
				cartoons.add(img);
				
				System.out.println(rec);
			}
		}
		
		
		public static void main(String[] args) throws IOException {
			
			ArrayList<BufferedImage> cartoons= new ArrayList<BufferedImage>(100000);
			
			for(int rec= 0; rec < 100000; rec++ )
			{
				String fileName= String.format("/%02d.jpg", rec % 12);
				
				InputStream is = Generate.class.getResourceAsStream(fileName);
				cartoons.add(ImageIO.read(is));
				
				System.out.println(rec);
			}
		}
		
		public static void main3(String[] args) throws IOException {
			
			ArrayList<BufferedImage> cartoons= new ArrayList<BufferedImage>(100000);
			
			for(int rec= 0; rec < 100000; rec++ )
			{
				String fileName= String.format("%02d.jpg", rec % 12);

				ClassLoader classLoader = Thread.currentThread().getContextClassLoader();
				InputStream is = classLoader.getResourceAsStream(fileName);
				cartoons.add(ImageIO.read(is));
				
				System.out.println(rec);

			}
		}
		

}
