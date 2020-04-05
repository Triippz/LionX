package edu.psu.lionx.utils;

public class Format
{
    public static String parseByteArray (byte array[] )
    {
        StringBuilder parsedVal = new StringBuilder();

        for (Byte anArray : array)
        {
            parsedVal.append ( Byte.toString ( anArray ) );
        }
        return parsedVal.toString();
    }

    public static String parseAmountString (String amount )
    {
        StringBuilder builder = new StringBuilder(amount);
        if ( amount.length() > 7 )
            builder.insert( amount.length() - 7, ".");
        else {

            while ( builder.toString().length() != 7 )
                builder.insert(0, "0");

            builder.insert(0, "0.");
        }
        return builder.toString();
    }

    public static String time (String time )
    {
        return new StringBuilder( time ).replace( 10, time.length(), "" ).toString();
    }

    public static String parseDollarAmount (String amount )
    {
        char[] amountArr = amount.toCharArray();
        StringBuilder builder = new StringBuilder();

        for ( int i = 0; i < amountArr.length; i++ )
        {
            if ( amountArr[i] == '.')
            {
                for (int j = 0; j <= i ; j++)
                    builder.append(amountArr[j]);

                builder.append( amountArr[i+1]).append( amountArr[i+2]);
                break;
            }
        }
        return builder.toString();
    }

    public static String sentPayment (String amount )
    {
        return new StringBuilder( amount ).insert(0, "-").toString();
    }

    public static String parseCursorUrl (String url, String cursor ) { return String.format ( url, cursor ); }

    public static String cleanAssetName (String assetName ) { return assetName.substring(0, assetName.indexOf(":")); }
}