package util;

import java.io.IOException;
import java.io.InputStream;
import java.net.URL;

import javax.swing.ImageIcon;

/**
 * Manages resources and handles errors when a resource cannot be found. If the
 * images won't load it's probably because eclipse's getting the images from the
 * "bin" directory instead of the "img" directory.
 */
public class ResManager {

	private static final String IMAGE_BASE_PATH = "img/";

	public static ImageIcon getImage(String imgname) {
		ImageIcon defaultImage = new ImageIcon("");
		URL url = ClassLoader.getSystemClassLoader().getResource(
				IMAGE_BASE_PATH + imgname);
		if (url == null)
			return defaultImage;
		ImageIcon result = new ImageIcon(url);
		if (result == null)
			return defaultImage;
		return result;
	}

	public static InputStream getStream(String path) {
		InputStream is = ResManager.class.getResourceAsStream(path);
		if (is == null) {
			return new InputStream() {
				@Override
				public int read() throws IOException {
					return -1;
				}
			};
		}
		return is;
	}

}
