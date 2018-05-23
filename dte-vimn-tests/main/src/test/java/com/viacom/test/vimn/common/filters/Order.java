package com.viacom.test.vimn.common.filters;

/**
 * This class is used for the orderBy params in the filter and results class
 * 
 * @author pooned
 */
public class Order {

	public static final String ASC = "ASC";
	public static final String DESC = "DESC";

	/********************************************************************
	 * Order by fields for Episodes
	 *******************************************************************/
	public static final String episodeTitle = "title";
	public static final String episodeMGID = "id";
	public static final String episodeID = "id";
	public static final String episodeSeasonNumber = "seasonNumber";
	public static final String[] episodeDurationInMilliSeconds = { "duration", "milliseconds" };
	public static final String[] episodeDurationInTimeCode = { "duration", "timecode" };
	public static final String episodeNumber = "episodeNumber";
	public static final String[] episodeOriginalAirDateTimestamp = { "originalAirDate", "timestamp" };
	public static final String[] episodeOriginalPublishDateTimestamp = { "originalPublishDate", "timestamp" };
	
	/********************************************************************
	 * Order by fields for Series
	 *******************************************************************/
	public static final String seriesTitle = "title";
	public static final String seriesDetail = "description";
	public static final String seriesDescription = "description";
	public static final String seriesId = "id";
	public static final String seriesMGID = "id";
	
	/********************************************************************
	 * Order by fields for Clips
	 *******************************************************************/
	public static final String clipTitle = "title";
	public static final String clipDescription = "description";
	public static final String[] clipDurationInMilliSeconds = { "duration", "milliseconds" };
	public static final String[] clipdurationInTimeCode = { "duration", "timecode" };
	public static final String[] clipSubtitle = { "episode", "subTitle" };
	public static final String[] clipSeasonNumber = { "season", "seasonNumber" };
	public static final String[] clipEpisodeNumber = { "episode", "episodeNumber" };
	public static final String clipMGID = "mgid";

}
