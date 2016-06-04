package bodaganj.jdbc;

import bodaganj.engine.dataItems.items.FootballClubItem;

/**
 * Created by bogdan on 04.06.16.
 * Test framework
 */
public final class DbQueriesHelper {

	private DbQueriesHelper() {
	}

	public static String replaceRequestForEuroCupParticipants(final FootballClubItem championsLeagueTeam) {
		String replaceTemplate = "REPLACE INTO " + System.getProperty("db.euro.cup.participants") + "(country, " +
				"clubName, position, cupStatus, games) VALUES ('%s','%s',%s,'%s',%s);";
		return String.format(replaceTemplate, championsLeagueTeam.getCountry(), championsLeagueTeam
				.getClubName(), championsLeagueTeam.getLeaguePosition(), championsLeagueTeam.getLeagueClubStatus
				().getNavigationOption(), championsLeagueTeam.getPlayedGameNumber());
	}
}
