package edu.pdx.cs410J.akanksha;

/**
 * Class for formatting messages on the server side.  This is mainly to enable
 * test methods that validate that the server returned expected strings.
 */
public class Messages
{
    public static String getMappingCount( int count )
    {
        return String.format( "Server contains %d owner/apptbook pairs", count );
    }

    public static String formatKeyValuePair( String owner, String apptbook )
    {
        return String.format("  %s -> %s", owner,apptbook);
    }

    public static String missingRequiredParameter( String parameterName )
    {
        return String.format("The required parameter \"%s\" is missing", parameterName);
    }

    public static String mappedKeyValue( String owner, String apptbook )
    {
        return String.format( "Mapped %s to %s", owner, apptbook );
    }

    /*public static String allMappingsDeleted() {
        return "All mappings have been deleted";
    }*/
}
