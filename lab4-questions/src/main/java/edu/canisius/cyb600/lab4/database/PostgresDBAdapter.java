package edu.canisius.cyb600.lab4.database;

import edu.canisius.cyb600.lab4.dataobjects.Actor;
import edu.canisius.cyb600.lab4.dataobjects.Film;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

/**
 * Posgres Implementation of the db adapter.
 */
public abstract class PostgresDBAdapter extends AbstractDBAdapter {

    public PostgresDBAdapter(Connection conn) {
        super(conn);
    }
    @Override
    public List<Film> getFilmsInCategory(int categoryId) {
        try (PreparedStatement statement = conn.prepareStatement("SELECT * FROM Film WHERE category_id = ?")) {
            statement.setInt(1, categoryId);
            ResultSet results = statement.executeQuery();
            List<Film> films = new ArrayList<>();
            while (results.next()) {
                Film film = new Film();
                film.setFilmId(results.getInt("FILM_ID"));
                // Populate other film attributes similarly
                films.add(film);
            }
            return films;
        } catch (SQLException exception) {
            exception.printStackTrace();
        }
        return new ArrayList<>();
    }

    @Override
    public List<Actor> getActorsWithLastName(String lastName) {
        try (PreparedStatement statement = conn.prepareStatement("SELECT * FROM Actor WHERE last_name = ?")) {
            statement.setString(1, lastName);
            ResultSet results = statement.executeQuery();
            List<Actor> actors = new ArrayList<>();
            while (results.next()) {
                Actor actor = new Actor();
                actor.setActorId(results.getInt("actor_id"));
                actor.setFirstName(results.getString("first_name"));
                actor.setLastName(results.getString("last_name"));
                actor.setLastUpdate(results.getTimestamp("last_update"));
                actors.add(actor);
            }
            return actors;
        } catch (SQLException exception) {
            exception.printStackTrace();
        }
        return new ArrayList<>();
    }

    @Override
    public List<Actor> insertAllActorsWithAnOddNumberLastName(List<Actor> actors) {
        List<Actor> insertedActors = new ArrayList<>();
        String sql = "INSERT INTO Actor (first_name, last_name, last_update) VALUES (?, ?, ?) RETURNING actor_id, last_update";
        try (PreparedStatement statement = conn.prepareStatement(sql)) {
            for (Actor actor : actors) {
                if (actor.getLastName().length() % 2 != 0) { // Check if the last name length is odd
                    statement.setString(1, actor.getFirstName());
                    statement.setString(2, actor.getLastName());
                    statement.setTimestamp(3, new Timestamp(System.currentTimeMillis()));
                    ResultSet resultSet = statement.executeQuery();
                    if (resultSet.next()) {
                        Actor insertedActor = new Actor();
                        insertedActor.setActorId(resultSet.getInt("actor_id"));
                        insertedActor.setFirstName(actor.getFirstName());
                        insertedActor.setLastName(actor.getLastName());
                        insertedActor.setLastUpdate(resultSet.getTimestamp("last_update"));
                        insertedActors.add(insertedActor);
                    }
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return insertedActors;
    }

    @Override
    public List<Actor> getActorsFirstNameStartingWithX(char firstLetter) {
        try (PreparedStatement statement = conn.prepareStatement("SELECT * FROM Actor WHERE first_name LIKE ?")) {
            statement.setString(1, firstLetter + "%");
            ResultSet results = statement.executeQuery();
            List<Actor> actors = new ArrayList<>();
            while (results.next()) {
                Actor actor = new Actor();
                actor.setActorId(results.getInt("actor_id"));
                actor.setFirstName(results.getString("first_name"));
                actor.setLastName(results.getString("last_name"));
                actor.setLastUpdate(results.getTimestamp("last_update"));
                actors.add(actor);
            }
            return actors;
        } catch (SQLException exception) {
            exception.printStackTrace();
        }
        return new ArrayList<>();
    }

    @Override
    public List<Film> getFilmsWithALengthLongerThanX(int length) {
        try (Statement statement = conn.createStatement()) {
            String query = "SELECT * FROM Film WHERE length > " + length;
            ResultSet results = statement.executeQuery(query);
            List<Film> films = new ArrayList<>();
            while (results.next()) {
                Film film = new Film();
                // Populate film object similarly as before
                films.add(film);
            }
            return films;
        } catch (SQLException exception) {
            exception.printStackTrace();
        }
        return new ArrayList<>();
    }

    @Override
    public List<String> getAllDistinctCategoryNames() {
        try (Statement statement = conn.createStatement()) {
            ResultSet results = statement.executeQuery("SELECT DISTINCT name FROM Category");
            List<String> categories = new ArrayList<>();
            while (results.next()) {
                categories.add(results.getString("name"));
            }
            return categories;
        } catch (SQLException exception) {
            exception.printStackTrace();
        }
        return new ArrayList<>();
    }

}