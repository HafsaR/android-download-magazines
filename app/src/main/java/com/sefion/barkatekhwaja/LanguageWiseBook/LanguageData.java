package com.sefion.barkatekhwaja.LanguageWiseBook;

import android.content.Context;

import com.sefion.barkatekhwaja.R;

import java.util.ArrayList;
import java.util.List;


public class LanguageData {
    String image, doc, subject, author, editor, translator, section, language,note, pageno, book_size;

    public LanguageData() {
    }


    public LanguageData(String image, String doc, String subject, String author, String editor, String translator, String section, String language, String note, String pageno, String book_size) {
        this.image = image;
        this.doc = doc;
        this.subject = subject;
        this.author = author;
        this.editor = editor;
        this.translator = translator;
        this.section = section;
        this.language = language;
        this.note = note;
        this.pageno = pageno;
        this.book_size = book_size;
    }

    public String getNote() {
        return note;
    }

    public void setNote(String note) {
        this.note = note;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public String getDoc() {
        return doc;
    }

    public void setDoc(String doc) {
        this.doc = doc;
    }

    public String getSubject() {
        return subject;
    }

    public void setSubject(String subject) {
        this.subject = subject;
    }

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public String getEditor() {
        return editor;
    }

    public void setEditor(String editor) {
        this.editor = editor;
    }

    public String getTranslator() {
        return translator;
    }

    public void setTranslator(String translator) {
        this.translator = translator;
    }

    public String getSection() {
        return section;
    }

    public void setSection(String section) {
        this.section = section;
    }

    public String getLanguage() {
        return language;
    }

    public void setLanguage(String language) {
        this.language = language;
    }

    public String getPageno() {
        return pageno;
    }

    public void setPageno(String pageno) {
        this.pageno = pageno;
    }

    public String getBook_size() {
        return book_size;
    }

    public void setBook_size(String book_size) {
        this.book_size = book_size;
    }
}
