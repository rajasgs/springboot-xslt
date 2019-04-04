<?xml version="1.0" encoding="UTF-8"?>
<xsl:stylesheet version="2.0" xmlns:xsl="http://www.w3.org/1999/XSL/Transform" xmlns:cs="http://www.censhare.com/xml/3.0.0/xpath-functions" exclude-result-prefixes="cs">
    <!-- API V3 XSLT returning Products list for Brand API portal based on related permission group
          September 2018 - Bruno Schrappe, Gregory Fonkatz
     -->
    <!-- output -->
    <xsl:output method="xml" encoding="UTF-8" omit-xml-declaration="no" />
    <xsl:param name="page" select="0" />
    <!-- root match -->
    <xsl:template match="/">
        <!-- get values -->
        <xsl:variable name="assetsPerPage" select="20" />
        <xsl:variable name="startRow" select="$assetsPerPage * number($page)" />
        <xsl:variable name="query">
            <query count-rows="true" limit="{$assetsPerPage}" type="asset" offset="{format-number($startRow,'##')}">
                <and>
                    <condition name="censhare:asset.type" value="brand." />
                </and>
            </query>
        </xsl:variable>
        <xsl:variable name="result">
            <cs:command name="asset.query" return-slot="result" returning="result">
                <cs:param name="data" select="$query" />
            </cs:command>
        </xsl:variable>
        <!-- output report header -->
        <xsl:variable name="totalCount" select="$result/result/assets/@row-count" />
        <kwikeeApiV3>
            <xsl:if test="number($totalCount)=number($totalCount)">
                <xsl:variable name="pages" select="ceiling(number($result/result/assets/@row-count) div $assetsPerPage)" />
                <totalAssets>
                    <xsl:value-of select="$totalCount" />
                </totalAssets>
                <pageIndex>
                    <xsl:value-of select="$page" />
                </pageIndex>
                <totalPages>
                    <xsl:value-of select="format-number($pages,'##')" />
                </totalPages>
               
                  <xsl:for-each select="cs:asset($query)/cs:order-by()[@censhare:asset.name]">
                        
                    <xsl:variable name="brandName" select="@name" />
                    <xsl:variable name="displayName" select="asset_feature[@feature='kwikee:brand.displayName']/@value_string"/>
                     
                    <!-- Greg, I included a test for existence of brand display name (new attribute created in censhare)
                         If its XPath exists, we use it instead of the all-caps, horrendous-looking legacy attribute. ;-D
                         – Bruno. 
                    -->
                    
                    <xsl:variable name="brandId" select="@id"/>
                    <brands>
                      <id><xsl:value-of select="$brandId"/></id>
                      <name>
                        <xsl:choose>
                          <xsl:when test="$displayName">
                            <xsl:value-of select="$displayName" />
                          </xsl:when>
                          <xsl:otherwise>
                            <xsl:value-of select="$brandName" />
                          </xsl:otherwise>
                        </xsl:choose>
                      </name>
                      
                      <!-- Counts the number of current products linked to the brand -->
                      <products>
                        <xsl:variable name='prodCount' select = "cs:count-assets()[@censhare:asset.type='product.' and @kwikee:interface.product-is-current='true' and @kwikee:product.brand=$brandId]"/>
                        <xsl:value-of select="$prodCount"/>   
                      </products>
                    </brands>
                    </xsl:for-each>
          
            </xsl:if>
        </kwikeeApiV3>
    </xsl:template>
</xsl:stylesheet>
