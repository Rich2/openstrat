/* Copyright 2018-21 Richard Oliver. Licensed under Apache Licence version 2.0. */
package ostrat

/** I'm just trying out a new package, not sure whether will use pWeb. */
package object pWeb
{ def tagVoidStr(tagName: String, attribs: XmlAtt *): String = attribs.foldLeft("<" + tagName)(_ + " " + _.str) + " />"
  def tagVoidStr(tagName: String, attribs: RArr[XmlAtt]): String = attribs.foldLeft("<" + tagName)(_ + " " + _.str) + " />"

  implicit class StringExtension(thisString: String)
  { /** This implicit method allows Strings to be used as XML content. */
    def xCon: XConStr = XConStr(thisString)

    def xmlAsString: XmlAsString = XmlAsString(thisString)
    
    def enTag(tag: String): String = "<" + tag + ">" + thisString + "</" + tag + ">"
    
    def h1Str: String = thisString.enTag("h1")
  }
}