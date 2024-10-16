package DataAccess;

import Connection.DatabaseConnection;

import java.beans.IntrospectionException;
import java.lang.reflect.*;
import java.sql.*;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.ArrayList;
import java.beans.PropertyDescriptor;
/**
 * The AbstractDAO class implements the CRUD operations. The methods receive as parameters the names of the columns in the table
 * from the subclasses. The class uses reflection techniques.
 * @param <T>
 */
public class AbstractDAO<T> {
    protected static final Logger LOGGER = Logger.getLogger(AbstractDAO.class.getName());
    private final Class<T> type;

    @SuppressWarnings("unchecked")
    public AbstractDAO() {
        this.type = (Class<T>) ((ParameterizedType) getClass().getGenericSuperclass()).getActualTypeArguments()[0];
    }
    /**
     * Creates a SELECT query string based on the given field.
     * @param field The field is the name of the column from the database that represents the ID of the object.
     * @return The SELECT query string.
     */
    private String createSelectQuery(String field) {
        StringBuilder sb = new StringBuilder();
        sb.append("SELECT ");
        sb.append(" * ");
        sb.append(" FROM ");
        sb.append(type.getSimpleName());
        sb.append(" WHERE " + field + " =?");
        return sb.toString();
    }
    /**
     * It finds the searched object based on its id.
     * @param id The ID filed of the searched object.
     * @param field_name The name of the column that represents the id. It is initialized in the subclasses.
     * @return
     */
    public T findById(int id, String field_name) {
        Connection connection = null;
        PreparedStatement statement = null;
        ResultSet resultSet = null;
        String query = createSelectQuery(field_name);
        try {
            connection = DatabaseConnection.getConnection();
            statement = connection.prepareStatement(query);
            statement.setInt(1, id);
            resultSet = statement.executeQuery();

            return createObjects(resultSet).get(0);
        } catch (SQLException e) {
            LOGGER.log(Level.WARNING, type.getName() + "DAO:findById " + e.getMessage());
        } finally {
            DatabaseConnection.close(resultSet);
            DatabaseConnection.close(statement);
            DatabaseConnection.close(connection);
        }
        return null;
    }

    /**
     * Creates objects from the given ResultSet.
     * @param resultSet The ResultSet containing data.
     * @return A list of objects created from the ResultSet.
     */
    protected List<T> createObjects(ResultSet resultSet) {
        List<T> list = new ArrayList<T>();
        Constructor[] ctors = type.getDeclaredConstructors();
        Constructor ctor = null;
        for (int i = 0; i < ctors.length; i++) {
            ctor = ctors[i];
            if (ctor.getGenericParameterTypes().length == 0)
                break;
        }
        try {
            while (resultSet.next()) {
                ctor.setAccessible(true);
                T instance = (T) ctor.newInstance();
                for (Field field : type.getDeclaredFields()) {
                    String fieldName = field.getName();
                    Object value = resultSet.getObject(fieldName);
                    PropertyDescriptor propertyDescriptor = new PropertyDescriptor(fieldName, type);
                    Method method = propertyDescriptor.getWriteMethod();
                    method.invoke(instance, value);
                }
                list.add(instance);
            }
        } catch (InstantiationException | IllegalAccessException | SecurityException |
                 IllegalArgumentException | InvocationTargetException | SQLException |
                 IntrospectionException e) {
            LOGGER.log(Level.WARNING, type.getName() + "DAO:createObjects " + e.getMessage());
        }
        return list;
    }

    /**
     * Inserts an object into the database.
     * @param t The object to insert.
     * @return The inserted object.
     */
    public T insert(T t) {
        Connection connection = null;
        PreparedStatement statement = null;
        ResultSet resultSet = null;
        try {
            connection = DatabaseConnection.getConnection();
            // Construct INSERT statement dynamically using reflection
            StringBuilder sb = new StringBuilder();
            sb.append("INSERT INTO ");
            sb.append(type.getSimpleName());
            sb.append(" (");
            // Get the list of declared fields excluding the "product_id"
            Field[] fields = type.getDeclaredFields();
            List<Field> nonIdFields = new ArrayList<>();

            boolean firstField=true;
            for (Field field : fields) {
                if(firstField){
                    if (!field.getName().equals("product_id") && !field.getName().equals("client_id") && !field.getName().equals("order_id")) {
                        nonIdFields.add(field);
                    }else{
                        firstField = false;
                    }
                }else{
                    nonIdFields.add(field);
                }
            }
            for (int i = 0; i < nonIdFields.size(); i++) {
                if (i > 0) {
                    sb.append(", ");
                }
                sb.append(nonIdFields.get(i).getName());
            }
            sb.append(") VALUES (");
            for (int i = 0; i < nonIdFields.size(); i++) {
                if (i > 0) {
                    sb.append(", ");
                }
                sb.append("?");
            }
            sb.append(")");
            // Prepare the statement
            statement = connection.prepareStatement(sb.toString(), Statement.RETURN_GENERATED_KEYS);

            // Set parameter values using reflection
            for (int i = 0; i < nonIdFields.size(); i++) {
                nonIdFields.get(i).setAccessible(true);
                statement.setObject(i + 1, nonIdFields.get(i).get(t));
            }

            // Execute the statement
            statement.executeUpdate();

            // Retrieve the generated keys
            resultSet = statement.getGeneratedKeys();
            if (resultSet.next()) {
                int insertedId = resultSet.getInt(1);
                // Set the generated ID to the object
                for (Field field : fields) {
                    if (field.getName().equals("product_id")) {
                        field.setAccessible(true);
                        field.set(t, insertedId);
                        break;
                    }
                }
                return t;
            }
        } catch (SQLException | IllegalAccessException e) {
            LOGGER.log(Level.WARNING, type.getName() + "DAO:insert " + e.getMessage());
        } finally {
            DatabaseConnection.close(resultSet);
            DatabaseConnection.close(statement);
            DatabaseConnection.close(connection);
        }
        return null;
    }

    /**
     * Deletes an object by its ID.
     * @param id The ID of the object to delete.
     * @param field_name The name of the column that represents the ID.
     * @return True if the object is deleted successfully, otherwise false.
     */
    public boolean deleteById(int id, String field_name) {
        Connection connection = null;
        PreparedStatement statement = null;
        boolean success = false;
        try {
            connection = DatabaseConnection.getConnection();
            // Construct DELETE statement dynamically using reflection
            StringBuilder sb = new StringBuilder();
            sb.append("DELETE FROM ");
            sb.append(type.getSimpleName());
            sb.append(" WHERE " + field_name + " =?");
            // Prepare the statement
            statement = connection.prepareStatement(sb.toString());
            statement.setInt(1, id);

            // Execute the statement
            int rowsAffected = statement.executeUpdate();

            // Check if any rows were affected
            if (rowsAffected > 0) {
                success = true;
            }
        } catch (SQLException e) {
            LOGGER.log(Level.WARNING, type.getName() + "DAO:deleteById " + e.getMessage());
        } finally {
            DatabaseConnection.close(statement);
            DatabaseConnection.close(connection);
        }
        return success;
    }

    /**
     * Edits an object's information by its ID.
     * @param id The ID of the object to edit.
     * @param newObject The updated object.
     * @param field_name The name of the column that represents the ID.
     */
    public void editById(int id, T newObject, String field_name) {
        Connection connection = null;
        PreparedStatement statement = null;
        connection = DatabaseConnection.getConnection();
        try {
            // Construct UPDATE statement dynamically using reflection
            StringBuilder sb = new StringBuilder();
            sb.append("UPDATE ");
            sb.append(type.getSimpleName());
            sb.append(" SET ");

            // Get the list of declared fields
            Field[] fields = type.getDeclaredFields();
            boolean firstField = true;
            for (Field field : fields) {
                if (!field.getName().equals("product_id") && !field.getName().equals("client_id") && !field.getName().equals("order_id")) {
                    if (!firstField) {
                        sb.append(", ");
                    }
                    sb.append(field.getName());
                    sb.append(" = ?");
                    firstField = false;
                }
            }

            sb.append(" WHERE " + field_name + " = ?");

            // Prepare the statement
            statement = connection.prepareStatement(sb.toString());

            // Set parameter values using reflection
            int paramIndex = 1;
            for (Field field : fields) {
                if (!field.getName().equals("product_id") && !field.getName().equals("client_id") && !field.getName().equals("order_id")) {
                    field.setAccessible(true);
                    statement.setObject(paramIndex++, field.get(newObject));
                }
            }
            statement.setInt(paramIndex, id);

            // Execute the statement
            statement.executeUpdate();
        } catch (SQLException | IllegalAccessException e) {
            LOGGER.log(Level.WARNING, type.getName() + "DAO:editById " + e.getMessage());
        } finally {
            DatabaseConnection.close(statement);
            DatabaseConnection.close(connection);
        }
    }
    /**
     * Retrieves all objects from the database.
     * @return A list of all objects.
     */
    public List<T> findAll() {
        List<T> objectList = new ArrayList<>();
        Connection connection = null;
        PreparedStatement statement = null;
        ResultSet resultSet = null;
        try {
            connection = DatabaseConnection.getConnection();
            String query = createSelectQuery();
            statement = connection.prepareStatement(query);
            resultSet = statement.executeQuery();
            if (resultSet != null) {
                objectList = createObjects(resultSet);
            }
        } catch (SQLException e) {
            LOGGER.log(Level.WARNING, type.getName() + "DAO:findAll " + e.getMessage());
        } finally {
            DatabaseConnection.close(resultSet);
            DatabaseConnection.close(statement);
            DatabaseConnection.close(connection);
        }
        return objectList;
    }
    /**
     * Creates a SELECT query string for retrieving all records.
     * @return The SELECT query string.
     */
    private String createSelectQuery() {
        StringBuilder sb = new StringBuilder();
        sb.append("SELECT * FROM ");
        sb.append(type.getSimpleName());
        return sb.toString();
    }
}
