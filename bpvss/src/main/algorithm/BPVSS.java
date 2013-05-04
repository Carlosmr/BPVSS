package main.algorithm;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;

import javax.imageio.ImageIO;
import javax.imageio.ImageWriter;
import javax.imageio.stream.ImageOutputStream;

public class BPVSS {
	private String path;
	private int n;

	public BPVSS(String path, int n) {
		this.path = path;
		this.n = n;
	}

	public void noiseLikeShares(String image) {
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
						CMatrix = createCMatrix(m + 1);
					} else {
						CMatrix = createCMatrix(0);
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

	public int[][] createCMatrix(int m) {
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
	
	public void joinImages(String in1, String in2,
			String res) {
		try {
			BufferedImage image1 = ImageIO.read(new File(path + in1));
			BufferedImage image2 = ImageIO.read(new File(path + in2));
			int height = image1.getHeight();
			int width = image1.getWidth();
			BufferedImage imageOut = new BufferedImage(width, height,
					BufferedImage.TYPE_INT_RGB);
			for (int i = 0; i < width; i++) {
				for (int j = 0; j < height; j++) {
					int color1 = image1.getRGB(i, j) & 0xFF;
					int color2 = image2.getRGB(i, j) & 0xFF;
					int color3 = color1 | color2;
					int rgb = (color3 << 24) | (color3 << 16) | (color3 << 8)
							| color3;
					imageOut.setRGB(i, j, rgb);
				}
			}
			Iterator<ImageWriter> writers = ImageIO
					.getImageWritersByFormatName("png");
			ImageWriter writer = (ImageWriter) writers.next();
			if (writer == null) {
				throw new RuntimeException("PNG not supported?!");
			}

			ImageOutputStream out = ImageIO.createImageOutputStream(new File(
					path + res));
			writer.setOutput(out);
			writer.write(imageOut);
			out.close();

		} catch (IOException e) {
			System.err.println("Error al leer los ficheros de entrada.");
			e.printStackTrace();
		}
	}

}
