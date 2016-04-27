package bodaganj.utils;

/**
 * Created by bogdan on 27.04.16.
 * Test framework
 */
public enum LeagueClubStatus {

	CHAMPIONS_LEAGUE("Champions League"),
	CHAMPIONS_LEAGUE_QUALIFIERS("Champions League qualifiers"),
	EUROPA_LEAGUE("Europa League"),
	EUROPA_LEAGUE_QUALIFIERS("Europa League qualifiers"),
	RELEGATION("Relegation"),
	RELEGATION_QUALIFIERS("Relegation qualifiers"),
	MIDDLE("Middle");

	private String status;

	LeagueClubStatus(final String status) {
		this.status = status;
	}

	public String getNavigationOption() {
		return status;
	}
}
