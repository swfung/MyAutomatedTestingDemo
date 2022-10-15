package utilities;

import java.awt.Image;
import java.awt.Toolkit;
import java.awt.image.BufferedImage;
import java.awt.image.PixelGrabber;

public class CompareImageUtil {

	public enum Result {
		Matched, SizeMismatch, PixelMismatch
	};

	public static Result compareImage(String baseFile, String actualFile) {
		Result compareResult = Result.PixelMismatch;

		Image baseImage = Toolkit.getDefaultToolkit().getImage(baseFile);
		Image actualImage = Toolkit.getDefaultToolkit().getImage(actualFile);

		try {
			PixelGrabber baseImageGrabber = new PixelGrabber(baseImage, 0, 0,
					-1, -1, false);
			PixelGrabber actualImageGrabber = new PixelGrabber(actualImage, 0,
					0, -1, -1, false);

			int[] baseImageData = null;
			int[] actualImageData = null;

			if (baseImageGrabber.grabPixels()) {
				int width = baseImageGrabber.getWidth();
				int height = baseImageGrabber.getHeight();
				baseImageData = new int[width * height];
				baseImageData = (int[]) baseImageGrabber.getPixels();
			}

			if (actualImageGrabber.grabPixels()) {
				int width = actualImageGrabber.getWidth();
				int height = actualImageGrabber.getHeight();
				actualImageData = new int[width * height];
				actualImageData = (int[]) actualImageGrabber.getPixels();
			}

			System.out.println(baseImageGrabber.getWidth() + " <> "
					+ actualImageGrabber.getWidth());
			System.out.println(baseImageGrabber.getHeight() + " <> "
					+ actualImageGrabber.getHeight());

			if (baseImageGrabber.getWidth() != actualImageGrabber.getWidth()
					|| baseImageGrabber.getHeight() != actualImageGrabber
							.getHeight()) {
				compareResult = Result.SizeMismatch;
			} else if (java.util.Arrays.equals(baseImageData, actualImageData)) {
				compareResult = Result.Matched;
			}

		} catch (Exception e) {
			e.printStackTrace();
		}

		return compareResult;
	}

	public static double getImageDifferencePercent(BufferedImage img1,
			BufferedImage img2) {
		int width = img1.getWidth();
		int height = img1.getHeight();
		int width2 = img2.getWidth();
		int height2 = img2.getHeight();
		if (width != width2 || height != height2) {
			throw new IllegalArgumentException(
					String.format(
							"Images must have the same dimensions: (%d,%d) vs. (%d,%d)",
							width, height, width2, height2));
		}

		long diff = 0;
		for (int y = 0; y < height; y++) {
			for (int x = 0; x < width; x++) {
				diff += pixelDiff(img1.getRGB(x, y), img2.getRGB(x, y));
			}
		}
		long maxDiff = 3L * 255 * width * height;

		return 100.0 * diff / maxDiff;
	}

	public static int pixelDiff(int rgb1, int rgb2) {
		int r1 = (rgb1 >> 16) & 0xff;
		int g1 = (rgb1 >> 8) & 0xff;
		int b1 = rgb1 & 0xff;
		int r2 = (rgb2 >> 16) & 0xff;
		int g2 = (rgb2 >> 8) & 0xff;
		int b2 = rgb2 & 0xff;
		return Math.abs(r1 - r2) + Math.abs(g1 - g2) + Math.abs(b1 - b2);
	}
}
