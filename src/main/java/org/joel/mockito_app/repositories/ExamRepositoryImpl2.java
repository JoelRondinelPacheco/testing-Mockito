package org.joel.mockito_app.repositories;

import org.joel.mockito_app.models.Exam;

import java.util.List;
import java.util.concurrent.TimeUnit;

public class ExamRepositoryImpl2 implements ExamRepository{
    @Override
    public List<Exam> findAll() {
        try {
            System.out.println("Ingreso al metodo");
            TimeUnit.SECONDS.sleep(5);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        return null;
    }
}
