package com.example.feedbacksystem;

public class Client extends User {

    public Client(int id, String username, String password) {
        super(id, username, password);
    }

    @Override
    public String getRole() {
        return "Client";
    }

    public void createFeedback(Feedback feedback) {
        // Реализация создания отзыва (вызывается через FeedbackDAO)
    }
}
