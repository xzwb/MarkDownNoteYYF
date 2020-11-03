package cc.yyf.note.window;

import javax.swing.*;

public class OtherSettingWindow {
    private JTextField githubRepository;
    private JTextField githubToken;
    private JPanel commentPanel;

    public JTextField getGitHubOwner() {
        return gitHubOwner;
    }

    public void setGitHubOwner(JTextField gitHubOwner) {
        this.gitHubOwner = gitHubOwner;
    }

    private JTextField gitHubOwner;

    public JTextField getGithubRepository() {
        return githubRepository;
    }

    public void setGithubRepository(JTextField githubRepository) {
        this.githubRepository = githubRepository;
    }

    public JTextField getGithubToken() {
        return githubToken;
    }

    public void setGithubToken(JTextField githubToken) {
        this.githubToken = githubToken;
    }

    public JPanel getCommentPanel() {
        return commentPanel;
    }

    public void setCommentPanel(JPanel commentPanel) {
        this.commentPanel = commentPanel;
    }
}
