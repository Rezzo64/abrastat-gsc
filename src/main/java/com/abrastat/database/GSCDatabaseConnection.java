package com.abrastat.database;

import com.abrastat.general.GameResult;
import com.abrastat.general.Pokemon;
import com.abrastat.general.Status;
import com.abrastat.gsc.GSCPokemon;
import org.apache.commons.lang3.ArrayUtils;
import org.jetbrains.annotations.NotNull;

import java.sql.*;

public class GSCDatabaseConnection {

    private final String url = "jdbc:postgresql://localhost/";
    private final String user = "postgres";
    private final String password = "root";
    Connection connection = null;

    public GSCDatabaseConnection(String dbName) {

        try {
            connection = DriverManager.getConnection(
                    url + dbName,
                    user,
                    password);

        } catch (SQLException e) {
            e.printStackTrace();
            System.err.println(e.getClass().getName() + ": " + e.getMessage());
        }
        System.out.println(dbName + " database successfully opened...");
    }

    public Connection connect(String dbName) throws SQLException {
        return DriverManager.getConnection(url + dbName, user, password);
    }

    public void addPokemon (@NotNull GSCPokemon pokemon) {
        String SQL = "INSERT INTO pokemon" +
                "(pokemon_id, species, nickname, gender, item, level, nonvolatilestatus," +
                " volatilestatus, startinghp, moves, movespp, behaviour, hiddenpower, stats, mods) " +
                "VALUES(DEFAULT,?,?,?,?,?,?,?,?,?,?,?,?,?,?)";

        try (PreparedStatement preparedStatement = connection.prepareStatement(SQL, Statement.RETURN_GENERATED_KEYS)) {
            preparedStatement.setString(2, pokemon.getSpecies());
            preparedStatement.setString(4, pokemon.getGender().toString());
            preparedStatement.setString(5, pokemon.getHeldItem().toString());
            preparedStatement.setInt(6, pokemon.getLevel());
            preparedStatement.setString(7, pokemon.getNonVolatileStatus().toString());
            preparedStatement.setArray(8, connection.createArrayOf("Status", pokemon.getVolatileStatus()));
            preparedStatement.setInt(9, pokemon.getStartingHP());
            preparedStatement.setArray(10, connection.createArrayOf("Moves", pokemon.getMoves()));
            preparedStatement.setArray(11, connection.createArrayOf("MovesPP", ArrayUtils.toObject(pokemon.getMovesPp())));
            preparedStatement.setString(12, pokemon.getActiveBehaviour().toString());
            preparedStatement.setString(13, pokemon.getGscHiddenPowerType().toString());
            int[] stats = new int[]{pokemon.getStatHP(), pokemon.getStatAtk(), pokemon.getStatDef(), pokemon.getStatSpA(), pokemon.getStatSpD(), pokemon.getStatSpe()};
            preparedStatement.setArray(14, connection.createArrayOf("Stats", ArrayUtils.toObject(stats)));
            int[] statMods = new int[]{pokemon.getAtkMod(), pokemon.getDefMod(), pokemon.getSpAMod(), pokemon.getSpDMod(), pokemon.getSpeMod(), pokemon.getAccMod(), pokemon.getEvaMod()};
            preparedStatement.setArray(15, connection.createArrayOf("Mods", ArrayUtils.toObject(statMods)));

            preparedStatement.executeUpdate();

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void addResult(GameResult result) {
        String SQL = "INSERT INTO results (" +
                "SET pokemon1 "
    }

    public void newDatabase(String dbName) {

    }

}
