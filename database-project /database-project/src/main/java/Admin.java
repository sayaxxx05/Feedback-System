package com.example.feedbacksystem;

public class Admin extends User {

    private FeedbackDAO feedbackDAO;

    public Admin(int id, String username, String password) {
        super(id, username, password);
        this.feedbackDAO = new FeedbackDAO();  // Инициализация DAO
    }

    @Override
    public String getRole() {
        return "Admin";
    }

    // Реализация удаления отзыва
    public void deleteFeedback(int feedbackId) {
        // Вызов метода из FeedbackDAO для удаления отзыва
        feedbackDAO.deleteFeedback(feedbackId);
    }
}
