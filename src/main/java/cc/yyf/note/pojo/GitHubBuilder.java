package cc.yyf.note.pojo;

/**
 * github信息的建造工厂
 */
public class GitHubBuilder {
    private static GitHub gitHub;

    public static GitHub build(String address, String token, String owner) {
        if (gitHub == null) {
            gitHub = new GitHub(address, token, owner);
            return gitHub;
        }
        gitHub.setGitHubAddress(address);
        gitHub.setGitHubToken(token);
        gitHub.setGitHubOwner(owner);
        return gitHub;
    }

    public static GitHub getInstance() {
        return gitHub;
    }
}
