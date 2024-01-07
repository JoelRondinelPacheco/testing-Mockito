package org.joel.mockito_app.examples.repositories;

import org.joel.mockito_app.examples.Data;
import org.joel.mockito_app.examples.models.Exam;

import java.util.List;
import java.util.concurrent.TimeUnit;

public class ExamRepositoryImpl implements ExamRepository{
    @Override
    public Exam save(Exam exam) {
        System.out.println("ExamRepositoryImpl.save");
        return Data.EXAM;
    }

    @Override
    public List<Exam> findAll() {
        System.out.println("ExamRepositoryImpl.findAll");
        try {
            System.out.println("Ingreso al metodo");
            TimeUnit.SECONDS.sleep(5);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        return Data.EXAMS;
    }
}
