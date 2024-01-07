package org.joel.mockito_app.services;

import org.joel.mockito_app.models.Exam;

import java.util.Arrays;
import java.util.List;

public class Data {
    public final static List<Exam> EXAMS = Arrays.asList(new Exam(5L, "Matemáticas"), new Exam(6L, "Lenguaje"), new Exam(7L, "Historia"));
    public final static List<Exam> EXAMS_ID_NULL = Arrays.asList(new Exam(null, "Matemáticas"), new Exam(null, "Lenguaje"), new Exam(null, "Historia"));
    public final static List<Exam> EXAMS_ID_NEGATIVE = Arrays.asList(new Exam(-5L, "Matemáticas"), new Exam(-7L, "Lenguaje"), new Exam(null, "Historia"));
    public final static List<String> QUESTIONS = Arrays.asList("arimética", "integrales", "derivadas", "trigonometría", "geometría");
    public final static Exam EXAM = new Exam(null, "Física");
}
