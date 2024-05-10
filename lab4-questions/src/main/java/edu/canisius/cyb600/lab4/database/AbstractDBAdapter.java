package edu.canisius.cyb600.lab4.database;

import edu.canisius.cyb600.lab4.dataobjects.Actor;
import edu.canisius.cyb600.lab4.dataobjects.Category;
import edu.canisius.cyb600.lab4.dataobjects.Film;

import java.sql.Connection;
import java.util.List;

/**
 * Abstract DB Adapter
 */
public abstract class AbstractDBAdapter {
    Connection conn;

    public AbstractDBAdapter(Connection conn) {
        this.conn = conn;
    }

    public List<Film> getFilmsInCategory(Category category) {
        return null;
    }

    public List<Actor> addOddActors(List<Actor> actors) {
        return null;
    }

    public List<Actor> getActorsFirstNameStartingWithX(char firstLetter) {
        return null;
    }

    public List<String> getAllDistinctCategoryNames() {
        return null;
    }

    public List<Actor> insertAllActorsWithAnOddNumberLastName(List<Actor> actors) {
        return null;
    }

    public abstract List<Film> getFilmsInCategory();

    public abstract List<Film> getFilmsInCategory(int categoryId);

    public abstract List<Actor> getActorsWithLastName(String lastName);

    public abstract List<Film> insertAllActorsWithAnOddNumberLastName(Actor actor);

    public abstract List<Actor> addOddActors(String lastName);

    public abstract Actor getActorsFirstNameStartingWithX(Actor actor);

    public List<Film> getFilmsWithALengthLongerThanX(int length) {
        return null;
    }

    //SELECTS

    //INSERTS

    //JOIN



}