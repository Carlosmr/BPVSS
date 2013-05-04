package test;

import main.algorithm.BPVSS;

public class TestImpl {

	/**
	 * @param args
	 * @throws IOException
	 */
	public static void main(String[] args) {

		String path = "C:/Users/Carlos/Pictures/";
		String image = "inicial.png";
		int n = 3;

		BPVSS bpvss = new BPVSS(path, n);

		bpvss.noiseLikeShares(image);
		bpvss.joinImages("share0.png", "share1.png", "join1.png");
		bpvss.joinImages("join1.png", "share2.png", "res.png");
	}

}
