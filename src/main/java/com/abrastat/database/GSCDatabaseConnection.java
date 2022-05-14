package com.abrastat.database;

import com.abrastat.general.GameResult;
import com.abrastat.gsc.GSCPokemon;
import org.apache.commons.lang3.ArrayUtils;
import org.jetbrains.annotations.NotNull;

import java.sql.*;

public class GSCDatabaseConnection {

    private final String url = "jdbc:postgresql://localhost/";
    private final String user = "postgres";
    private final String password = "root";
    Connection connection = null;

    private ResultSet lastMonId;

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
                "(pokemon_id, species, nickname, gender, item, level, nonvolatilestatus, " +
                "volatilestatus, startinghp, moves, movespp, behaviour, hiddenpower, stats, mods) \n" +
                "VALUES (DEFAULT,?,?,?,?,?,?,?,?,?,?,?,?,?,?)\n" +
                "RETURNING pokemon_id";

        try (PreparedStatement ps = connection.prepareStatement(SQL, Statement.RETURN_GENERATED_KEYS)) {
            ps.setString(2, pokemon.getSpecies());
            ps.setString(4, pokemon.getGender().toString());
            ps.setString(5, pokemon.getHeldItem().toString());
            ps.setInt(6, pokemon.getLevel());
            ps.setString(7, pokemon.getNonVolatileStatus().toString());
            ps.setArray(8, connection.createArrayOf("VARCHAR", pokemon.getVolatileStatus()));
            ps.setInt(9, pokemon.getStartingHP());
            ps.setArray(10, connection.createArrayOf("VARCHAR", pokemon.getMoves()));
            ps.setArray(11, connection.createArrayOf("INTEGER", ArrayUtils.toObject(pokemon.getMovesPp())));
            ps.setString(12, pokemon.getActiveBehaviour().toString());
            ps.setString(13, pokemon.getGscHiddenPowerType().toString());
            int[] stats = new int[]{pokemon.getStatHP(), pokemon.getStatAtk(), pokemon.getStatDef(), pokemon.getStatSpA(), pokemon.getStatSpD(), pokemon.getStatSpe()};
            ps.setArray(14, connection.createArrayOf("INTEGER", ArrayUtils.toObject(stats)));
            int[] statMods = new int[]{pokemon.getAtkMod(), pokemon.getDefMod(), pokemon.getSpAMod(), pokemon.getSpDMod(), pokemon.getSpeMod(), pokemon.getAccMod(), pokemon.getEvaMod()};
            ps.setArray(15, connection.createArrayOf("INTEGER", ArrayUtils.toObject(statMods)));

            ps.executeUpdate();

            lastMonId = ps.getResultSet();

            if (lastMonId.next()) {
                int pokemonId = lastMonId.getInt(1);
                lastMonId.close();
                pokemon.setId(pokemonId);
            } else {
                throw new SQLException();
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void addResult(GameResult result) {
        String SQL = "INSERT INTO results (" +
                "pokemon1_id, pokemon1, pokemon2_id, pokemon2, winsp1, winsp2, draws, avgturns," +
                "avghp_p1, avgpp_p1, avghp_p2, avgpp_p2, struggle_p1, struggle_p2, boom_p1, boom_p2) \n" +
                "VALUES (?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?);";

        try (PreparedStatement ps = connection.prepareStatement(SQL, Statement.RETURN_GENERATED_KEYS)) {
            ps.setInt(1, result.pokemon1().getId());
            ps.setString(2, result.pokemon1().getSpecies());
            ps.setInt(3, result.pokemon2().getId());
            ps.setString(4, result.pokemon2().getSpecies());
            ps.setInt(5, result.winsP1());
            ps.setInt(6, result.winsP2());
            ps.setInt(7, result.draws());
            ps.setInt(8, result.avgTurns());
            ps.setInt(9, result.avgHpP1());
            ps.setArray(10, connection.createArrayOf("INTEGER", ArrayUtils.toObject(result.avgPpP1())));
            ps.setInt(11, result.avgHpP2());
            ps.setObject(12, connection.createArrayOf("INTEGER", ArrayUtils.toObject(result.avgPpP2())));
            ps.setInt(13, result.struggleP1());
            ps.setInt(14, result.struggleP2());
            ps.setInt(15, result.boomP1());
            ps.setInt(16, result.boomP2());

            ps.executeUpdate();

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void newDatabase(String dbName) {

        String create = "DO LANGUAGE plpgsql\n" +
                "$body$\n" +
                "DECLARE\n" +
                "   old_schema NAME = 'abrastat_template';\n" +
                "   new_schema NAME = '" + dbName + "';\n" +
                "   tbl TEXT;\n" +
                "   sql TEXT;\n" +
                "BEGIN\n" +
                "  EXECUTE format('CREATE SCHEMA IF NOT EXISTS %I', new_schema);\n" +
                "\n" +
                "  FOR tbl IN\n" +
                "    SELECT table_name\n" +
                "    FROM information_schema.tables\n" +
                "    WHERE table_schema=old_schema\n" +
                "  LOOP\n" +
                "    sql := format(\n" +
                "            'CREATE TABLE IF NOT EXISTS %I.%I '\n" +
                "            '(LIKE %I.%I INCLUDING INDEXES INCLUDING CONSTRAINTS)'\n" +
                "            , new_schema, tbl, old_schema, tbl);\n" +
                "\n" +
                "    EXECUTE sql;\n" +
                "\n" +
                "    sql := format(\n" +
                "            'INSERT INTO %I.%I '\n" +
                "            'SELECT * FROM %I.%I'\n" +
                "            , new_schema, tbl, old_schema, tbl);\n" +
                "\n" +
                "    EXECUTE sql;\n" +
                "  END LOOP;\n" +
                "END\n" +
                "$body$;";

        try {
            connection.createStatement().execute(create);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

}
