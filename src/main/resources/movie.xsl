<?xml version="1.0" encoding="UTF-8"?>
<xsl:stylesheet xmlns:xsl="http://www.w3.org/1999/XSL/Transform"
    version="1.0">
    <xsl:template match="/">
        <html>
            <body>
                <h2>Movies</h2>
                <table border="1">
                    <tr bgcolor="#9acd32">
                        <th style="text-align:left">Title</th>
                        <th style="text-align:left">Artist</th>
                        <th style="text-align:left">Country</th>
                        <th style="text-align:left">Price</th>
                    </tr>
                    <xsl:for-each select="movies/movie">
                        <xsl:sort select="country"></xsl:sort>

                        <!-- IF CONDITION -->
                        <xsl:if test="country='USA'">
                            <tr>
                                <td>
                                    <xsl:value-of select="title" />
                                </td>
                                <td>
                                    <xsl:value-of select="artist" />
                                </td>
                                <td>
                                    <xsl:value-of select="country" />
                                </td>
                                <td>
                                    <xsl:value-of select="price" />
                                </td>
                            </tr>
                        </xsl:if>
                    </xsl:for-each>
                </table>
            </body>
        </html>
    </xsl:template>
</xsl:stylesheet>
