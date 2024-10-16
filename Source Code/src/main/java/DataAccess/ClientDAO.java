package DataAccess;
import Model.Client;
import java.util.List;
import java.util.logging.Logger;
/**
 * The ClientDAO extends the AbstractDAO class, which implements all the CRUD operations.
 * The methods from ClientDAO class are called from the corresponding BLL class.
 */
public class ClientDAO extends AbstractDAO<Client> {
    private static final Logger LOGGER = Logger.getLogger(ClientDAO.class.getName());
    /**
     * Default constructor for ClientDAO.
     */
    public ClientDAO() {
        super();
    }
    /**
     * Find a client by its ID.
     *
     * @param client_id The ID of the client to find.
     * @return The Client object if found, otherwise null.
     */
    public Client findById(int client_id) {
        return super.findById(client_id, "client_id");
    }
    /**
     * Insert a new client into the database.
     *
     * @param client The Client object to insert.
     * @return The inserted Client object.
     */
    public Client insert(Client client) {
        return super.insert(client);
    }
    /**
     * Delete a client by its ID.
     *
     * @param client_id The ID of the client to delete.
     * @return True if the client is deleted successfully, otherwise false.
     */
    public boolean deleteById(int client_id) {
        return super.deleteById(client_id, "client_id");
    }

    /**
     * Edit a client's information by its ID.
     *
     * @param client_id The ID of the client to edit.
     * @param newClient The updated Client object.
     * @param field_name The name of the field to edit.
     */
    public void editById(int client_id, Client newClient, String field_name) {
        super.editById(client_id, newClient, field_name);
    }
    /**
     * Retrieve all clients from the database.
     *
     * @return A list of all Client objects.
     */
    public List<Client> findAll() {
        return super.findAll();
    }
}
