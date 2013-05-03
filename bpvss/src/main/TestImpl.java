package main;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;

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
		String path = "C:/Users/Carlos/Pictures/";
		String image = "test.png";
//
//		BufferedImage bufferPlano = ImageIO.read(new File(path + "test.png"));
//
//		int height = bufferPlano.getHeight();
//		int width = bufferPlano.getWidth();
//
//		BufferedImage bufferResult = new BufferedImage(width, height,
//				BufferedImage.TYPE_INT_RGB);
//
//		for (int i = 0; i < width; i++) {
//			for (int j = 0; j < height; j++) {
//				int color = bufferPlano.getRGB(i, j);
//				int newcolor = 0;
//				R = (color) & 0xFF;
//				G = (color >> 8) & 0xFF;
//				B = (color >> 16) & 0xFF;
//				A = (color >> 24) & 0xFF;
//
//				// Procesado
//				R = 0;
//				B = 0;
//
//				newcolor = (A << 24) | (B << 16) | (G << 8) | R;
//				bufferResult.setRGB(i, j, newcolor);
//			}
//		}
//
//		Iterator<ImageWriter> writers = ImageIO
//				.getImageWritersByFormatName("png");
//		ImageWriter writer = (ImageWriter) writers.next();
//		if (writer == null) {
//			throw new RuntimeException("PNG not supported?!");
//		}
//
//		ImageOutputStream out = ImageIO.createImageOutputStream(new File(path
//				+ "Result.jpg"));
//		writer.setOutput(out);
//		writer.write(bufferResult);
//		out.close();

		BPVSS(path, image, 3);
	}

	
	public static void BPVSS(String path, String image, int n) {
		BufferedImage bufferPlano;
		try {
			bufferPlano = ImageIO.read(new File(path + image));
			List<BufferedImage> shares = new LinkedList<BufferedImage>();
			int height = bufferPlano.getHeight();
			int width = bufferPlano.getWidth();
			int split = width / n;
			int k;
			for (int i = 0; i < n; i++)
				shares.add(new BufferedImage(width, height,
						BufferedImage.TYPE_INT_RGB));

			for (int i = 0; i < width; i++) {
				for (int j = 0; j < height; j++) {
					k = (int) (Math.random() * 2);
					int color = bufferPlano.getRGB(i, j) & 0xFF;
					int[][] CMatrix;
					if (color == 0) {
						int m = i / split;
						CMatrix = createCMatrix(m + 1, n);
					} else {
						CMatrix = createCMatrix(0, n);
					}
					for (int l = 0; l < n; l++) {
						int newcolor = CMatrix[k][l] * 255;
						int rgb = (newcolor << 24) | (newcolor << 16)
								| (newcolor << 8) | newcolor;
						shares.get(l).setRGB(i, j, rgb);
					}
				}
			}

			Iterator<ImageWriter> writers = ImageIO
					.getImageWritersByFormatName("png");
			ImageWriter writer = (ImageWriter) writers.next();
			if (writer == null) {
				throw new RuntimeException("PNG not supported?!");
			}

			for (int i = 0; i < n; i++) {
				ImageOutputStream out = ImageIO
						.createImageOutputStream(new File(path + "share" + i
								+ ".png"));
				writer.setOutput(out);
				writer.write(shares.get(i));
				out.close();
			}

		} catch (IOException e) {
			System.err.println("Error al abrir la imagen de entrada.");
			e.printStackTrace();
		}

	}

	public static int[][] createCMatrix(int m, int n) {
		int[][] res = new int[2][n];

		for (int j = 0; j < n; j++) {
			if ((j + 1) == m) {
				res[0][j] = 1;
				res[1][j] = 0;
			} else {
				res[0][j] = 0;
				res[1][j] = 1;
			}

		}
		return res;

	}

}
