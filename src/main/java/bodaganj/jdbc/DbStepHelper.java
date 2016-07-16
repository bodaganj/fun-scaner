package bodaganj.jdbc;

import bodaganj.engine.ProjectLogger;
import org.fest.assertions.Fail;
import org.fest.assertions.api.Assertions;
import org.slf4j.Logger;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by bogdan on 21.05.16.
 * Test framework
 */
public final class DbStepHelper {

	private static final Logger LOG = ProjectLogger.getLogger(DbStepHelper.class.getSimpleName());
	private static final String ERROR_MESSAGE_MORE_THAN_ONE_ROW = "Query has got more than 1 row!";
	private static final String ERROR_MESSAGE_FOUND_NOTHING = "Query has got nothing!";

	private DbStepHelper() {
	}

	protected static Connection getDatabaseConnection() {
		return DbConnector.getInstance().getConnection();
	}

	public static int update(final String updateQuery) {
		int resultCode = -1;
		try (final PreparedStatement updateStatement = getDatabaseConnection().prepareStatement(updateQuery)) {
			resultCode = updateStatement.executeUpdate();
			LOG.info("Result code of 'update' request is: " + resultCode);
		} catch (final SQLException exc) {
			Fail.fail("SQL statement for update rows was not executed successfully", exc);
		}
		return resultCode;
	}

	/**
	 * Returns List of Rows. <br>
	 * Each Row is represented by Map which contains entries: Column-name - Value
	 */
	public static List<Map<String, String>> select(final String selectQuery) {
		LOG.info("Start to execute query: {}", selectQuery);
		List<Map<String, String>> rows = null;
		try (final Connection databaseConnection = getDatabaseConnection();
		     final PreparedStatement selectStatement = databaseConnection.prepareStatement(selectQuery);
		     final ResultSet resultSet = selectStatement.executeQuery()) {
			ResultSetMetaData metaData = resultSet.getMetaData();
			int columnCount = metaData.getColumnCount();
			List<String> columns = null;
			if (resultSet.next()) {
				columns = new ArrayList<>();
				for (int counter = 1; counter <= columnCount; counter++) {
					columns.add(metaData.getColumnLabel(counter));
				}
			} else {
				Assertions.fail("Query has found 0 rows");
			}
			rows = new ArrayList<>();
			if (!(columns == null)) {
				do {
					final Map<String, String> row = new LinkedHashMap<>();
					for (int columnIndex = 0; columnIndex < columnCount; columnIndex++) {
						row.put(columns.get(columnIndex), resultSet.getString(columnIndex + 1));
					}
					rows.add(row);
				} while (resultSet.next());
			} else {
				Assertions.fail("columns is null");
			}
		} catch (SQLException e) {
			Fail.fail("SQL statement was not executed successfully {}", e);
		}
		return rows;
	}

	/**
	 * Only columns specified in columnsToFilterResultsBy will be added to the final result table
	 * ResultTable's column names should be equalWithCase to columns in list
	 */
	public static List<Map<String, String>> select(final String selectQuery, final List<String>
			columnsToFilterResultsBy) {
		List<Map<String, String>> filteredResultTable = new ArrayList<>();
		List<Map<String, String>> resultTable = select(selectQuery);
		if (!(resultTable == null)) {
			for (int rowIndex = 0; rowIndex < resultTable.size(); rowIndex++) {
				LOG.info("-------- Row: {}", rowIndex);
				Map<String, String> row = resultTable.get(rowIndex);
				Map<String, String> newRow = new LinkedHashMap<>();
				for (final String field : columnsToFilterResultsBy) {
					if (row.containsKey(field)) {
						newRow.put(field, row.get(field));
						LOG.info("Field: {}}, Result: {}", field, row.get(field));
					}
				}
				filteredResultTable.add(newRow);
			}
		} else {
			Assertions.fail("Result table is null");
		}
		return filteredResultTable;
	}

	/**
	 * Returns value or null from 1st row and column by name.<BR>
	 * Will assert Fail if more than 1 row found by query!
	 */
	public static String selectByColumnName(final String request, final String columnName, final String...
			queryParams) {
		String formatRequest = String.format(request, queryParams);
		List<Map<String, String>> resultTable = select(formatRequest);
		assertFoundResults(resultTable);
		return resultTable.get(0).get(columnName);
	}

	/**
	 * Returns value or null from 1st row and 1st column of virtual table.<BR>
	 * Will assert Fail if more than 1 row or more than 1 column found by query!
	 */
	public static String selectSingleCell(final String request, final String... queryParams) {
		String formatRequest = String.format(request, queryParams);
		List<Map<String, String>> resultTable = select(formatRequest);
		assertFoundResults(resultTable);
		Map<String, String> firstRow = resultTable.get(0);
		if (firstRow.size() > 1) {
			Assertions.fail(String.format("There are %s columns have been found by query!", firstRow.size()));
		}
		return firstRow.entrySet().iterator().next().getValue();
	}

	private static void assertFoundResults(final List<Map<String, String>> resultTable) {
		Assertions.assertThat(resultTable).as("resultTable is NULL").isNotNull();
		if (resultTable.isEmpty()) {
			Assertions.fail(ERROR_MESSAGE_FOUND_NOTHING);
		}
		if (resultTable.size() > 1) {
			Assertions.fail(ERROR_MESSAGE_MORE_THAN_ONE_ROW);
		}
		Map<String, String> firstRow = resultTable.get(0);
		if (firstRow.isEmpty()) {
			Assertions.fail("No results in row!");
		}
	}

	/**
	 * Check that search result is empty
	 */
	public static void checkFoundResultIsNotEmpty(final String request, final boolean flag, final String...
			queryParams) {
		String formatRequest = String.format(request, queryParams);
		List<Map<String, String>> resultTable = select(formatRequest);
		Assertions.assertThat(resultTable == null).as("ResultTable is NOT as expected").isEqualTo(flag);
	}
}
