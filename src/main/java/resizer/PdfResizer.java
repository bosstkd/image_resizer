package resizer;

import com.lowagie.text.Document;
import com.lowagie.text.DocumentException;
import com.lowagie.text.pdf.PdfImportedPage;
import com.lowagie.text.pdf.PdfReader;
import com.lowagie.text.pdf.PdfSmartCopy;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;

public class PdfResizer {

    public void reducePdfSize(File file) throws IOException, DocumentException {
        PdfReader reader =
                new PdfReader(Files.readAllBytes(Path.of(file.getPath())));
        Document document = new Document(reader.getPageSizeWithRotation(1));
        String newFileName = file.getName().substring(0, file.getName().lastIndexOf(".")) + "_RS.pdf";
        String chemin = getFilePath(file.getPath()) + "\\" + newFileName;
        PdfSmartCopy writer = new PdfSmartCopy(document, new FileOutputStream(chemin));
        document.open();

        int total = reader.getNumberOfPages() + 1;
        for (int i = 1; i < total; i++) {
            PdfImportedPage page = writer.getImportedPage(reader, i);
            writer.addPage(page);
        }
        writer.setFullCompression();
        document.close();
        writer.close();
    }

    private String getFilePath(String fileName) {
        return fileName.substring(0, fileName.lastIndexOf("\\"));
    }
}
