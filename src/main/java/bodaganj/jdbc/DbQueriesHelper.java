package bodaganj.jdbc;

import bodaganj.engine.dataItems.items.FootballClubItem;
import bodaganj.utils.LeagueClubStatus;

/**
 * Created by bogdan on 04.06.16.
 * Test framework
 */
public final class DbQueriesHelper {

	private static String euroCupParticipantsDB = System.getProperty("db.euro.cup.participants");

	private DbQueriesHelper() {
	}

	public static String replaceRequestForEuroCupParticipants(final FootballClubItem championsLeagueTeam) {
		String replaceTemplate = "REPLACE INTO " + euroCupParticipantsDB + "(country, clubName, position, cupStatus," +
				" games) VALUES ('%s','%s',%s,'%s',%s);";
		return String.format(replaceTemplate, championsLeagueTeam.getCountry(), championsLeagueTeam
				.getClubName(), championsLeagueTeam.getLeaguePosition(), championsLeagueTeam.getLeagueClubStatus
				().getNavigationOption(), championsLeagueTeam.getPlayedGameNumber());
	}

	public static String selectEuroCupParticipants(final String country, final LeagueClubStatus leagueClubStatus) {
		return "SELECT DISTINCT clubName,MAX(games) FROM " + euroCupParticipantsDB + " WHERE country = '" + country +
				"' and cupStatus LIKE '" + leagueClubStatus.getNavigationOption() + "%' GROUP BY clubName;";
	}
}
