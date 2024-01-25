import java.awt.Color;

public class Editor4 {
    public static void main (String[] args) {
		String file = args[0];
		int n = Integer.parseInt(args[1]);
		Color[][] originImg = Runigram.read(file);
		Color[][] tempOriginImg = Runigram.read(file);
		Color[][] grayImg = Runigram.grayScaled(tempOriginImg);
		Runigram.setCanvas(originImg);
		Runigram.morph(originImg, grayImg, n);
	}
}
