/* Copyright 2018-24 Richard Oliver. Licensed under Apache Licence version 2.0. */
package ostrat
import pWeb._, Colour._

object CssOpenstrat
{
  def apply(): String = body.out --- endStr

  def body: CssBody = CssBody(RArr(CssBGColour(Ivory), CssFontSize("18px")))

    def endStr: String =
  """

  h1
  { text-align: center;
    font-size: 44px;
  }

  ol {
    padding-left: 1em;
  }​

  ol li {
    margin-bottom 2em;
    margin-top 2em;
  }

  ul li {	margin: 0.25em; }

  ol > li
  { margin-top: 1em;
    margin-bottom: 1em;
  }

    .central
    { max-width: 68em;
      margin-left: auto;
      margin-right: auto;
    }

    .main
    { max-width: 68em;
      margin-left: auto;
      margin-right: auto;
    }

    .lexical { color: DarkBlue; }

  code { color: DarkRed; }
    .sbt { color: DarkGreen; }
    .folder { color: DarkBlue; white-space: nowrap; }
    .path { color: DarkBlue; white-space: nowrap; }
    .bash { color: DarkRed; white-space: nowrap; }
    .scala { color: Black; white-space: nowrap; font-size:10px; }

  @media (min-width:50em)
  {
    #topmenu li
    { display: inline-block;
      background-color: #dddddd;
      padding: 0.2em;
      border: 0.2em solid yellow;
    }

    #topmenu { text-align: center; max-width: 100em;}
    #bottommenu {display: none;}
  }

  td, th {padding-right: 2.0em;
  text-align: start;
}
  .centreBlock
  { display: block;
    margin-left: auto;
    margin-right: auto;
  }"""
}