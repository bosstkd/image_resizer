package resizer;

import com.lowagie.text.DocumentException;

import javax.imageio.ImageIO;
import java.awt.Dimension;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

public class ImageResizer {

    BufferedImage buf;

    public String convertAndGetFilesList(File[] fs, Dimension dms, String pictureName, String compressionType, String format)
            throws DocumentException, IOException {

        StringBuffer ce_trouve = new StringBuffer();
        for (File file : fs) {
            String fileAbsolutPath = file.getAbsolutePath();

            String fileFormat = getFileFormat(fileAbsolutPath);

            if (fileFormat.equalsIgnoreCase("pdf")) {
                PdfResizer pdfResizer = new PdfResizer();
                pdfResizer.reducePdfSize(new File(fileAbsolutPath));
            } else {
                String newFileName = fileAbsolutPath.substring(0, fileAbsolutPath.length() - 4) + "_RS" + format;
                new File(newFileName).delete();
                try {
                    buf = getBufferedImageFromFile(new File(fileAbsolutPath));
                } catch (IOException e) {
                    e.printStackTrace();
                }
                File f = getResizedImage(buf, compressionType, newFileName, dms.height, dms.width);
                ce_trouve.append(ce_trouve).append("\n").append(newFileName);
            }
        }
        return ce_trouve.toString();
    }

    private BufferedImage getBufferedImageFromFile(File file) throws IOException {
        return ImageIO.read(file);
    }

    private File getResizedImage(BufferedImage bufferedImageOrginal, String compressionType, String imageName,
                                int height, int width) {
        if (height < 1 || width < 1) {
            height = bufferedImageOrginal.getHeight();
            width = bufferedImageOrginal.getWidth();
        }

        BufferedImage bufferedImage = null;
        bufferedImage = new BufferedImage(width, height, BufferedImage.TYPE_INT_RGB);
        Graphics2D g = (Graphics2D) bufferedImage.getGraphics();
        g.setRenderingHint(RenderingHints.KEY_INTERPOLATION, RenderingHints.VALUE_INTERPOLATION_BILINEAR);
        g.drawImage(buf, 0, 0, width, height, null);
        g.dispose();

        File file = new File(imageName);
        try {
            ImageIO.write(bufferedImage, compressionType, file);
        } catch (IOException e) {
            e.printStackTrace();
        }

        return file;
    }

    private String getFileFormat(String fileName) {
        return fileName.substring(fileName.lastIndexOf(".") + 1);
    }
}
