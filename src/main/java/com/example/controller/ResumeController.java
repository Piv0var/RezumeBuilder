package com.example.controller;

import com.itextpdf.text.Document;
import com.itextpdf.text.DocumentException;
import com.itextpdf.text.Element;
import com.itextpdf.text.Font;
import com.itextpdf.text.Paragraph;
import com.itextpdf.text.pdf.PdfWriter;

import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.io.ByteArrayOutputStream;
import java.io.IOException;

/**
 * Controller for generating PDF resumes.
 * <p>
 * Provides endpoints for creating and downloading resumes in PDF format based on user-provided details.
 */
@RestController
@RequestMapping("/resume")
public class ResumeController {

    /**
     * Generates a resume PDF based on the provided user details.
     *
     * @param fullName   the full name of the user
     * @param email      the email address of the user
     * @param phone      the phone number of the user
     * @param address    the address of the user
     * @param summary    the professional summary of the user
     * @param experience the work experience of the user
     * @param education  the education details of the user
     * @param skills     the skills of the user
     * @param languages  (optional) the languages known by the user
     * @param portfolio  (optional) the portfolio URL of the user
     * @return a ResponseEntity containing the generated PDF as a byte array and appropriate headers
     * @throws IOException         if an I/O error occurs while generating the PDF
     * @throws DocumentException   if an error occurs while manipulating the PDF document
     */
    @GetMapping("/generate")
    public ResponseEntity<byte[]> generateResume(
            @RequestParam(name = "fullName") String fullName,
            @RequestParam(name = "email") String email,
            @RequestParam(name = "phone") String phone,
            @RequestParam(name = "address") String address,
            @RequestParam(name = "summary") String summary,
            @RequestParam(name = "experience") String experience,
            @RequestParam(name = "education") String education,
            @RequestParam(name = "skills") String skills,
            @RequestParam(required = false, name = "languages") String languages,
            @RequestParam(required = false, name = "portfolio") String portfolio) throws IOException, DocumentException {

        // Create a new PDF document
        Document document = new Document();
        ByteArrayOutputStream out = new ByteArrayOutputStream();
        PdfWriter.getInstance(document, out);

        document.open();

        // Fonts for text
        Font titleFont = new Font(Font.FontFamily.HELVETICA, 16, Font.BOLD);
        Font normalFont = new Font(Font.FontFamily.HELVETICA, 12);

        // Add content to the PDF
        addParagraph(document, "Resume", titleFont, Element.ALIGN_CENTER);
        addParagraph(document, "\nFull Name: " + fullName, normalFont, Element.ALIGN_LEFT);
        addParagraph(document, "Email: " + email, normalFont, Element.ALIGN_LEFT);
        addParagraph(document, "Phone: " + phone, normalFont, Element.ALIGN_LEFT);
        addParagraph(document, "Address: " + address, normalFont, Element.ALIGN_LEFT);
        addParagraph(document, "\nProfessional Summary:\n" + summary, normalFont, Element.ALIGN_LEFT);
        addParagraph(document, "\nWork Experience:\n" + experience, normalFont, Element.ALIGN_LEFT);
        addParagraph(document, "\nEducation:\n" + education, normalFont, Element.ALIGN_LEFT);
        addParagraph(document, "\nSkills:\n" + skills, normalFont, Element.ALIGN_LEFT);

        if (languages != null) {
            addParagraph(document, "\nLanguages: " + languages, normalFont, Element.ALIGN_LEFT);
        }

        if (portfolio != null) {
            addParagraph(document, "\nPortfolio: " + portfolio, normalFont, Element.ALIGN_LEFT);
        }

        document.close();

        // Set headers for PDF download
        HttpHeaders headers = new HttpHeaders();
        headers.add("Content-Disposition", "attachment; filename=resume.pdf");
        headers.add("Content-Type", "application/pdf");

        return new ResponseEntity<>(out.toByteArray(), headers, HttpStatus.OK);
    }

    /**
     * Adds a paragraph to the provided PDF document.
     *
     * @param document  the PDF document to add the paragraph to
     * @param text      the text content of the paragraph
     * @param font      the font to be used for the paragraph text
     * @param alignment the alignment of the paragraph within the document
     * @throws DocumentException if an error occurs while adding the paragraph
     */
    private void addParagraph(Document document, String text, Font font, int alignment) throws DocumentException {
        Paragraph paragraph = new Paragraph(text, font);
        paragraph.setAlignment(alignment);
        document.add(paragraph);
    }
}
