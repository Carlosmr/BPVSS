package main;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.Iterator;
import javax.imageio.ImageIO;
import javax.imageio.ImageWriter;
import javax.imageio.stream.ImageOutputStream;

public class TestImpl {

	/**
	 * @param args
	 * @throws IOException
	 */
	public static void main(String[] args) throws IOException {

		int R = 0;
		int G = 0;
		int B = 0;
		int A = 0;
		String path = "C:/Users/practica/Downloads/";

		BufferedImage bufferPlano = ImageIO.read(new File(path + "images.jpg"));

		int height = bufferPlano.getHeight();
		int width = bufferPlano.getWidth();

		BufferedImage bufferResult = new BufferedImage(width, height,
				BufferedImage.TYPE_INT_RGB);

		for (int i = 0; i < width; i++) {
			for (int j = 0; j < height; j++) {
				int color = bufferPlano.getRGB(i, j);
				int newcolor = 0;
				R = (color) & 0xFF;
				G = (color >> 8) & 0xFF;
				B = (color >> 16) & 0xFF;
				A = (color >> 24) & 0xFF;

				// Procesado
				R = 0;
				B = 0;

				newcolor = (A << 24) | (B << 16) | (G << 8) | R;
				bufferResult.setRGB(i, j, newcolor);
			}
		}

		Iterator<ImageWriter> writers = ImageIO
				.getImageWritersByFormatName("jpg");
		ImageWriter writer = (ImageWriter) writers.next();
		if (writer == null) {
			throw new RuntimeException("JPG not supported?!");
		}

		ImageOutputStream out = ImageIO.createImageOutputStream(new File(path
				+ "Result.jpg"));
		writer.setOutput(out);
		writer.write(bufferResult);
		out.close();

	}

}
