package cz.bald.student_tests.parser

import com.itextpdf.text.pdf.PdfReader
import com.itextpdf.text.pdf.parser.PdfTextExtractor

class PdfParser {
    fun extractPdfText(filename: String): String? {
        val reader = PdfReader(filename)
        val pages = reader.numberOfPages
        var pdfText = ""
        for (page in 1 until pages + 1) {
            pdfText += PdfTextExtractor.getTextFromPage(reader, page)
        }
        reader.close()
        return pdfText
    }
}