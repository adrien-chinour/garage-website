package frontend.api;

/**
 * Interface to implement for interact with a REST API resource.
 */
interface Api<T> {

    /**
     * Get all object
     * @return List object
     */
    T[] getAll();

    /**
     * Get an element by is id
     * @param id : id of the element
     * @return the element
     */
    T get(int id);

    /**
     * Create a new element
     * @param entity : element to create
     * @return the element
     */
    T create(T entity);

    /**
     * Edit an element
     * @param id : id of the element
     * @return the element
     */
    T edit(int id);

    /**
     * Delete an element
     * @param id : id of the element
     * @return the element
     */
    T delete(int id);
}
