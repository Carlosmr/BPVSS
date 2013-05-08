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
	private String pathResult;

	public BPVSS(String path, String pathResult, int n) {
		this.path = path;
		this.n = n;
		this.pathResult = pathResult;
	}

	public void noiselikeShares(String image) {
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
					int[][] cMatrix;
					if (color == 0) {
						int m = i / split;
						cMatrix = createCMatrix(m + 1);
					} else {
						cMatrix = createCMatrix(0);
					}
					for (int l = 0; l < n; l++) {
						int newcolor = cMatrix[k][l] * 255;
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
						.createImageOutputStream(new File(pathResult + "share"
								+ i + ".png"));
				writer.setOutput(out);
				writer.write(shares.get(i));
				out.close();
			}

		} catch (IOException e) {
			System.err.println("Error al abrir la imagen de entrada.");
			e.printStackTrace();
		}

	}

	public void meaningfulShares(String secret, String cover) {
		BufferedImage secretimage, coverimage;
		try {
			secretimage = ImageIO.read(new File(path + secret));
			coverimage = ImageIO.read(new File(cover));
			List<BufferedImage> shares = new LinkedList<BufferedImage>();
			int height = secretimage.getHeight();
			int width = secretimage.getWidth();
			int split = width / n;
			int k;
			for (int i = 0; i < n; i++)
				shares.add(new BufferedImage(width, height,
						BufferedImage.TYPE_INT_RGB));

			for (int i = 0; i < width; i++) {
				for (int j = 0; j < height; j++) {
					k = (int) (Math.random() * 4);
					int secretcolor = secretimage.getRGB(i, j) & 0xFF;
					int covercolor = coverimage.getRGB(i, j) & 0xFF;
					int[][] mMatrix;
					int m = (i / split) + 1;
					if (secretcolor == 0) {
						if (covercolor == 0) {
							mMatrix = createMMatrix(m, 3);
						} else {
							mMatrix = createMMatrix(m, 1);
						}
					} else {
						if (covercolor == 0) {
							mMatrix = createMMatrix(m, 2);
						} else {
							mMatrix = createMMatrix(m, 0);
						}

					}
					for (int l = 0; l < n; l++) {
						int newcolor = mMatrix[k][l] * 255;
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
						.createImageOutputStream(new File(pathResult + "share"
								+ i + ".png"));
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

		for (int i = 0; i < n; i++) {
			if ((i + 1) == m) {
				res[0][i] = 0;
				res[1][i] = 1;
			} else {
				res[0][i] = 1;
				res[1][i] = 0;
			}
			if (m > n) {
				res[0][i] = 1;
				res[1][i] = 1;
			}
		}
		return res;

	}

	public int[][] createMMatrix(int m, int k) {
		int[][] res = new int[4][n];
		int[][] c1;
		int[][] c2;
		switch (k) {
		case 0:
			c1 = createCMatrix(m);
			c2 = createCMatrix(0);
			break;
		case 1:
			c1 = createCMatrix(m);
			c2 = createCMatrix(m);
			break;
		case 2:
			c1 = createCMatrix(n + 1);
			c2 = createCMatrix(0);
			break;
		default:
			c1 = createCMatrix(n + 1);
			c2 = createCMatrix(m);
			break;
		}
		for (int i = 0; i < n; i++) {
			res[0][i] = c1[0][i];
			res[1][i] = c1[1][i];
			res[2][i] = c2[0][i];
			res[3][i] = c2[1][i];

		}

		return res;

	}

	public void joinImages(String in1, String in2, String res) {
		try {
			BufferedImage image1 = ImageIO.read(new File(in1));
			BufferedImage image2 = ImageIO.read(new File(in2));
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
					pathResult+res));
			writer.setOutput(out);
			writer.write(imageOut);
			out.close();

		} catch (IOException e) {
			System.err.println("Error al leer los ficheros de entrada.");
			e.printStackTrace();
		}
	}

}
