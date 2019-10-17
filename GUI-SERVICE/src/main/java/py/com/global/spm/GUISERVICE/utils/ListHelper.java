package py.com.global.spm.GUISERVICE.utils;

import javax.validation.constraints.NotNull;
import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

/**
 * Created by cbenitez on 3/19/18.
 */
public class ListHelper {

    private ListHelper() {

        // No-op
    }

    /**
     * Genera un {@link ArrayList} a partir de una serie de elementos.
     * <p>
     * Retorna un ArrayList de items, es lo mismo que
     * {@link java.util.Arrays#asList(Object...)} solo que retorna un ArrayList,
     * al cual se le pueden agregar cosas, al contrario que asList que retorna
     * un AbstractList que no puede ser modificado.
     * </p>
     *
     * @param items
     *            array no nulo de elementos
     * @return {@link ArrayList} conteniendo los elementos no nulos pasados.
     */
    public static <T> List<T> getAsList(@NotNull T ... items) {

        List<T> aRet = new ArrayList<>(items.length);
        for (T item : items) {
            if (item != null) {
                aRet.add(item);
            }
        }
        return aRet;
    }

    /**
     * Este método convierte una colección de elementos en un vector.
     *
     * @param collection
     *            colección a convertir
     * @param clazz
     *            clase de la colección
     * @return vector de elementos
     */

    @SuppressWarnings("unchecked")
    public static <T> T[] asArray(Class<T> clazz, Collection<T> collection) {

        if (!hasElements(collection)) {
            return (T[]) Array.newInstance(clazz, 0);
        }
        T[] result = (T[]) Array.newInstance(clazz, collection.size());
        return collection.toArray(result);

    }

    /**
     * Este método convierte una colección de elementos en un vector.
     *
     * <p>
     * Se supone que todos los elementos son del tipo T, si no todos son del
     * mismo tipo, entonces utilizar el método
     * {@link #asArray(Class, Collection)}.
     * </p>
     * <p>
     * Este método infiere el tipo según cual sea su primer elemento.
     * </p>
     *
     * @param collection
     * @return vector de elementos
     */
    @SuppressWarnings("unchecked")
    public static <T> T[] asArray(Collection<T> collection) {

        Class<T> clazz = (Class<T>) collection.iterator().next().getClass();
        if (!hasElements(collection)) {
            return (T[]) Array.newInstance(clazz, 0);
        }
        T[] result = (T[]) Array.newInstance(clazz, collection.size());
        return collection.toArray(result);

    }

    /**
     * Define si una colección tiene o no elementos.
     * <p>
     * Este método es null-safe, es decir hasElements(null) retorna
     * <code>false</code>
     * </p>
     *
     * @param collection
     *            lista de elementos, puede ser <code>null</code>
     * @return <code>true</code> si la lista tiene elementos, <code>false</code>
     *         en otro caso
     */
    public static boolean hasElements(Collection<?> collection) {

        return !(collection == null || collection.isEmpty());
    }

    /**
     * Verifica si un array tiene un determinado elemento.
     *
     * <p>
     * Para ello realiza un recorrido lineal sobre el array y compara cada
     * elemento utilizadno {@link Object#equals(Object)}
     * </p>
     *
     * @param array
     *            vector de elementos
     * @param v
     *            elemento a verificar
     * @return <code>true</code> si lo contiene, <code>false</code> en caso
     *         contrario.
     */
    public static <T> boolean contains(final T[] array, final T v) {

        if (array != null && array.length > 0) {
            for (final T e : array) {
                if (e == v || v != null && v.equals(e)) {
                    return true;
                }
            }
        }
        return false;
    }
}
