package BusinessLogic;

import BusinessLogic.Validators.ClientAgeValidator;
import BusinessLogic.Validators.ClientEmailValidator;
import BusinessLogic.Validators.Validator;
import DataAccess.AbstractDAO;
import DataAccess.ClientDAO;
import Model.Client;
import java.util.ArrayList;
import java.util.List;
import java.util.NoSuchElementException;
/**
 * Business Logic Layer (BLL) class for managing Client entities.
 * This class provides methods to manage CRUD (Create, Read, Update, Delete) operations on Client entities,
 * along with validation for client data.
 */
public class ClientBLL{
    private static List<Validator<Client>> validators;

    /**
     * Constructs a ClientBLL object and initializes validators.
     */
    public ClientBLL() {
        validators = new ArrayList<Validator<Client>>();
        validators.add(new ClientEmailValidator());
        validators.add(new ClientAgeValidator());
    }

    /**
     * Finds a client by its ID.
     *
     * @param id The ID of the client to find.
     * @return The Client object if found.
     * @throws NoSuchElementException if the client with the specified ID is not found.
     */
    public Client findById(int id) {
        AbstractDAO<Client> clientAbstractDAO = new ClientDAO();
        Client st = clientAbstractDAO.findById(id,"client_id");
        if (st == null) {
            throw new NoSuchElementException("The student with id =" + id + " was not found!");
        }
        return st;
    }
    /**
     * Inserts a new client into the database, after checking the conditions.
     * @param client The Client object to insert.
     */
    public void insertClient(Client client) {
        AbstractDAO<Client> clientAbstractDAO = new ClientDAO();
        for (Validator<Client> v : validators) {
            v.validate(client);
        }
        clientAbstractDAO.insert(client);
    }
    /**
     * Deletes a client by its ID.
     *
     * @param id The ID of the client to delete.
     * @throws NoSuchElementException if the client with the specified ID does not exist.
     */
    public void deleteClientById(int id) {
        // Check if the client exists
        Client client = findById(id);
        if (client == null) {
            throw new NoSuchElementException("The client with id = " + id + " does not exist!");
        }
        // If the client exists, proceed with deletion
        AbstractDAO<Client> clientAbstractDAO = new ClientDAO();
        clientAbstractDAO.deleteById(id,"client_id");
    }
    /**
     * Edits a client's information by its ID.
     *
     * @param id     The ID of the client to edit.
     * @param client The updated Client object.
     * @throws NoSuchElementException if the client with the specified ID does not exist.
     */
    public void editClientById(int id, Client client) {
        // Check if the client exists
        Client originalClient = findById(id);
        if (originalClient == null) {
            throw new NoSuchElementException("The client with id = " + id + " does not exist!");
        }

        // If the client exists, proceed with deletion
        AbstractDAO<Client> clientAbstractDAO = new ClientDAO();
        clientAbstractDAO.editById(id,client,"client_id");
    }
    /**
     * Retrieves all clients from the database.
     *
     * @return A list of all Client objects.
     */
    public List<Client> findAll() {
        AbstractDAO<Client> clientAbstractDAO = new ClientDAO();
        return clientAbstractDAO.findAll();
    }
}
