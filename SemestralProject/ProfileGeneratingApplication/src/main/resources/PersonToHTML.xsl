<?xml version="1.0"?>

<xsl:stylesheet xmlns:xsl="http://www.w3.org/1999/XSL/Transform" version="1.0">
    <xsl:output encoding="UTF-8" media-type="text/html" method="html" omit-xml-declaration="yes" indent="yes"/>

    <xsl:template match="/">
        <xsl:text disable-output-escaping='yes'>&lt;!DOCTYPE html&gt;</xsl:text>
        <html lang="en">
            <head>
                <title>Info osoba</title>
                <link rel="stylesheet" href="https://stackpath.bootstrapcdn.com/bootstrap/4.5.2/css/bootstrap.min.css" integrity="sha384-JcKb8q3iqJ61gNV9KGb8thSsNjpSL0n8PARn9HuZOnIxN0hoP+VmmDGMN5t9UJ0Z" crossorigin="anonymous"/>
            </head>
            <body style="background-color: #f5f5f5">

                <div class="container shadow p-3 my-5 bg-white rounded">
                    <div style="min-height: 75vh;">
                        <header class="header bg-primary py-1">
                            <h1 style="text-align: center;color: white;">Registered person info - <xsl:value-of select="//city"/></h1>
                            <p style="text-align: center;color: white;" class="mb-2">generated automaticaly by service</p>
                        </header>

                        <div class="row justify-content-around text-center my-2">
                            <div class="col-4">
                                <h4>First name:</h4>
                                <textarea style="background-color: white;" class="form-control" id="textName" readonly="readonly" rows="1"><xsl:value-of select="//firstName"/> </textarea>
                            </div>
                            <div class="col-4">
                                <h4>Last name:</h4>
                                <textarea style="background-color: white;" class="form-control" id="textLastName" readonly="readonly" rows="1"><xsl:value-of select="//lastName"/> </textarea>
                            </div>
                        </div>

                        <hr/>

                        <div class="row justify-content-around text-center ">
                            <h4>Address and city of person</h4>
                        </div>

                        <div class="row justify-content-around text-center my-2">
                            <div class="col-4">
                                <h5>Address and numb.:</h5>
                                <textarea style="background-color: white;" rows="1" readonly="readonly" id="textAddress" class="form-control"><xsl:value-of select="//address"/></textarea>
                            </div>
                            <div class="col-4">
                                <h5>City:</h5>
                                <textarea style="background-color: white;" rows="1" readonly="readonly" id="textCity" class="form-control"><xsl:value-of select="//city"/></textarea>
                            </div>
                        </div>

                        <hr/>

                        <div class="row justify-content-around text-center ">
                            <h4>Detatiled information</h4>
                        </div>

                        <div class="row text-center my-2">
                            <div class="col-12">
                                <h5>Persons occupation:</h5>
                                <textarea style="background-color: white;" rows="1" readonly="readonly" id="textAddress" class="form-control"><xsl:value-of select="//occupation"/></textarea>
                            </div>
                        </div>

                        <div class="row justify-content-around text-center">
                            <p>This person takes full responsibility on registration and giving the information
                                <br/>!Person Application output!</p>
                    </div>
                    </div>

                    <div class="footerInfo mt-auto py-3 text-center">
                        <p class="text-muted"> This document is a part of the semestral project of Advanced Java class
                            <br/>© Adam Lany</p>
                    </div>

                </div>

            </body>
        </html>
    </xsl:template>
</xsl:stylesheet>
