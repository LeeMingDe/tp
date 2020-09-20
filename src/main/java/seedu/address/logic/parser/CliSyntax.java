package seedu.address.logic.parser;

/**
 * Contains Command Line Interface (CLI) syntax definitions common to multiple commands
 */
public class CliSyntax {

    /* Prefix definitions */
    public static final Prefix PREFIX_NAME = new Prefix("n/");
    public static final Prefix PREFIX_TEMP = new Prefix("t/");
    public static final Prefix PREFIX_PERIOD_OF_STAY = new Prefix("d/");
    public static final Prefix PREFIX_PHONE = new Prefix("p/");
    public static final Prefix PREFIX_AGE = new Prefix("a/");

    public static final Prefix PREFIX_COMMENTS = new Prefix("c/");

    //to be deleted VVV
    public static final Prefix PREFIX_EMAIL = new Prefix("e/");
    public static final Prefix PREFIX_TAG = new Prefix("t/");

}
