/*
    This file is part of the iText (R) project.
    Copyright (c) 1998-2017 iText Group NV
    Authors: iText Software.

    This program is free software; you can redistribute it and/or modify
    it under the terms of the GNU Affero General Public License version 3
    as published by the Free Software Foundation with the addition of the
    following permission added to Section 15 as permitted in Section 7(a):
    FOR ANY PART OF THE COVERED WORK IN WHICH THE COPYRIGHT IS OWNED BY
    ITEXT GROUP. ITEXT GROUP DISCLAIMS THE WARRANTY OF NON INFRINGEMENT
    OF THIRD PARTY RIGHTS

    This program is distributed in the hope that it will be useful, but
    WITHOUT ANY WARRANTY; without even the implied warranty of MERCHANTABILITY
    or FITNESS FOR A PARTICULAR PURPOSE.
    See the GNU Affero General Public License for more details.
    You should have received a copy of the GNU Affero General Public License
    along with this program; if not, see http://www.gnu.org/licenses or write to
    the Free Software Foundation, Inc., 51 Franklin Street, Fifth Floor,
    Boston, MA, 02110-1301 USA, or download the license from the following URL:
    http://itextpdf.com/terms-of-use/

    The interactive user interfaces in modified source and object code versions
    of this program must display Appropriate Legal Notices, as required under
    Section 5 of the GNU Affero General Public License.

    In accordance with Section 7(b) of the GNU Affero General Public License,
    a covered work must retain the producer line in every PDF that is created
    or manipulated using iText.

    You can be released from the requirements of the license by purchasing
    a commercial license. Buying such a license is mandatory as soon as you
    develop commercial activities involving the iText software without
    disclosing the source code of your own applications.
    These activities include: offering paid services to customers as an ASP,
    serving PDFs on the fly in a web application, shipping iText with a closed
    source product.

    For more information, please contact iText Software Corp. at this
    address: sales@itextpdf.com
 */
package com.itextpdf.layout;

import com.itextpdf.kernel.color.Color;
import com.itextpdf.kernel.pdf.PdfDocument;
import com.itextpdf.kernel.pdf.PdfWriter;
import com.itextpdf.kernel.utils.CompareTool;
import com.itextpdf.layout.element.Paragraph;
import com.itextpdf.layout.element.Text;
import com.itextpdf.layout.font.FontProvider;
import com.itextpdf.layout.property.Property;
import com.itextpdf.test.ExtendedITextTest;
import com.itextpdf.test.annotations.type.IntegrationTest;
import org.junit.Assert;
import org.junit.BeforeClass;
import org.junit.Test;
import org.junit.experimental.categories.Category;

import java.io.FileOutputStream;

@Category(IntegrationTest.class)
public class FontSelectorTest extends ExtendedITextTest {

    public static final String sourceFolder = "./src/test/resources/com/itextpdf/layout/FontSelectorTest/";
    public static final String destinationFolder = "./target/test/com/itextpdf/layout/FontSelectorTest/";
    public static final String fontsFolder = "./src/test/resources/com/itextpdf/layout/fonts/";

    @BeforeClass
    public static void beforeClass() {
        createDestinationFolder(destinationFolder);
    }

    @Test
    public void cyrillicAndLatinGroup() throws Exception {
        String outFileName = destinationFolder + "cyrillicAndLatinGroup.pdf";
        String cmpFileName = sourceFolder + "cmp_cyrillicAndLatinGroup.pdf";

        FontProvider sel = new FontProvider();
        sel.addFont(fontsFolder + "Puritan2.otf");
        sel.addFont(fontsFolder + "NotoSans-Regular.ttf");
        sel.addFont(fontsFolder + "FreeSans.ttf");


        String s = "Hello world! Здравствуй мир! Hello world! Здравствуй мир!";
        PdfDocument pdfDoc = new PdfDocument(new PdfWriter(new FileOutputStream(outFileName)));
        Document doc = new Document(pdfDoc);

        doc.setFontProvider(sel);
        doc.setProperty(Property.FONT, "Puritan");
        Text text = new Text(s).setBackgroundColor(Color.LIGHT_GRAY);
        Paragraph paragraph = new Paragraph(text);
        doc.add(paragraph);
        doc.close();

        Assert.assertNull(new CompareTool().compareByContent(outFileName, cmpFileName, destinationFolder, "diff"));
    }

    @Test
    public void cyrillicAndLatinGroup2() throws Exception {
        String outFileName = destinationFolder + "cyrillicAndLatinGroup2.pdf";
        String cmpFileName = sourceFolder + "cmp_cyrillicAndLatinGroup2.pdf";

        FontProvider sel = new FontProvider();
        sel.addFont(fontsFolder + "Puritan2.otf");
        sel.addFont(fontsFolder + "NotoSans-Regular.ttf");
        sel.addFont(fontsFolder + "FreeSans.ttf");


        String s = "Hello world! Здравствуй мир! Hello world! Здравствуй мир!";
        PdfDocument pdfDoc = new PdfDocument(new PdfWriter(new FileOutputStream(outFileName)));
        Document doc = new Document(pdfDoc);

        doc.setFontProvider(sel);
        doc.setProperty(Property.FONT, "'Puritan', \"FreeSans\"");
        Text text = new Text(s).setBackgroundColor(Color.LIGHT_GRAY);
        Paragraph paragraph = new Paragraph(text);
        doc.add(paragraph);
        doc.close();

        Assert.assertNull(new CompareTool().compareByContent(outFileName, cmpFileName, destinationFolder, "diff"));
    }

    @Test
    public void latinAndNotdefGroup() throws Exception {
        String outFileName = destinationFolder + "latinAndNotdefGroup.pdf";
        String cmpFileName = sourceFolder + "cmp_latinAndNotdefGroup.pdf";

        FontProvider sel = new FontProvider();
        sel.addFont(fontsFolder + "Puritan2.otf");

        String s = "Hello мир!";
        PdfDocument pdfDoc = new PdfDocument(new PdfWriter(new FileOutputStream(outFileName)));
        Document doc = new Document(pdfDoc);

        doc.setFontProvider(sel);
        doc.setProperty(Property.FONT, "Puritan");
        Text text = new Text(s).setBackgroundColor(Color.LIGHT_GRAY);
        Paragraph paragraph = new Paragraph(text);
        doc.add(paragraph);
        doc.close();

        Assert.assertNull(new CompareTool().compareByContent(outFileName, cmpFileName, destinationFolder, "diff"));
    }

    @Test
    public void standardPdfFonts() throws Exception {
        String outFileName = destinationFolder + "standardPdfFonts.pdf";
        String cmpFileName = sourceFolder + "cmp_standardPdfFonts.pdf";

        FontProvider sel = new FontProvider();
        sel.addStandardPdfFonts();

        String s = "Hello world!";
        PdfDocument pdfDoc = new PdfDocument(new PdfWriter(new FileOutputStream(outFileName)));
        Document doc = new Document(pdfDoc);
        doc.setFontProvider(sel);

        Paragraph paragraph = new Paragraph(s);
        paragraph.setProperty(Property.FONT, "Courier");
        doc.add(paragraph);
        paragraph = new Paragraph(s);
        paragraph.setProperty(Property.FONT, "Times-Roman");
        doc.add(paragraph);

        doc.close();

        Assert.assertNull(new CompareTool().compareByContent(outFileName, cmpFileName, destinationFolder, "diff"));
    }
}
