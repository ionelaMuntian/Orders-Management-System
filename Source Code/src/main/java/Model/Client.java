package Model;

/**
 * Represents a Client entity with basic information.
 */
public class Client {
    private int client_id;
    private String client_name;
    private int client_age;
    private String client_email;
    /**
     * Default constructor for the Client class.
     */
    public Client() {
    }
    /**
     * Constructs a Client object with specified attributes.
     *
     * @param client_name  The name of the client.
     * @param client_age   The age of the client.
     * @param client_email The email of the client.
     */
    public Client(String client_name, int client_age, String client_email) {
        this.client_name = client_name;
        this.client_age = client_age;
        this.client_email = client_email;
    }
    public int getClient_id() {
        return client_id;
    }

    public void setClient_id(int client_id) {
        this.client_id = client_id;
    }

    public String getClient_name() {
        return client_name;
    }

    public void setClient_name(String client_name) {
        this.client_name = client_name;
    }

    public int getClient_age() {
        return client_age;
    }

    public void setClient_age(int client_age) {
        this.client_age = client_age;
    }

    public String getClient_email() {
        return client_email;
    }

    public void setClient_email(String client_email) {
        this.client_email = client_email;
    }
}
