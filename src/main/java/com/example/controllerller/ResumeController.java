package com.example.controllerller;
import org.apache.poi.xwpf.usermodel.XWPFDocument;
import org.apache.poi.xwpf.usermodel.XWPFParagraph;
import org.apache.poi.xwpf.usermodel.XWPFRun;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.nio.charset.StandardCharsets;

@RestController
public class ResumeController {

    @GetMapping("/generateResume")
    public ResponseEntity<byte[]> generateResume(
            @RequestParam String fullName,
            @RequestParam String email,
            @RequestParam String phone,
            @RequestParam String address,
            @RequestParam String summary,
            @RequestParam String experience,
            @RequestParam String education,
            @RequestParam String skills,
            @RequestParam(required = false) String languages,
            @RequestParam(required = false) String portfolio) throws IOException {

        // Создаем новый документ Word
        XWPFDocument document = new XWPFDocument();

        // Добавляем параграфы с текстом
        addParagraph(document, "Full Name: " + fullName);
        addParagraph(document, "Email: " + email);
        addParagraph(document, "Phone: " + phone);
        addParagraph(document, "Address: " + address);
        addParagraph(document, "\nProfessional Summary:\n" + summary);
        addParagraph(document, "\nWork Experience:\n" + experience);
        addParagraph(document, "\nEducation:\n" + education);
        addParagraph(document, "\nSkills:\n" + skills);

        if (languages != null) {
            addParagraph(document, "\nLanguages: " + languages);
        }

        if (portfolio != null) {
            addParagraph(document, "\nPortfolio: " + portfolio);
        }

        // Конвертируем документ в байтовый массив для отправки
        ByteArrayOutputStream out = new ByteArrayOutputStream();
        document.write(out);
        byte[] docxContent = out.toByteArray();

        // Устанавливаем заголовки для скачивания .docx файла
        HttpHeaders headers = new HttpHeaders();
        headers.add("Content-Disposition", "attachment; filename=resume.docx");
        headers.add("Content-Type", "application/vnd.openxmlformats-officedocument.wordprocessingml.document");

        return new ResponseEntity<>(docxContent, headers, HttpStatus.OK);
    }

    // Метод для добавления параграфа в документ
    private void addParagraph(XWPFDocument document, String text) {
        XWPFParagraph paragraph = document.createParagraph();
        XWPFRun run = paragraph.createRun();
        run.setText(text);
    }
}
