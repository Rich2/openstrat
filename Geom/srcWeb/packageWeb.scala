/* Copyright 2018-24 Richard Oliver. Licensed under Apache Licence version 2.0. */
package ostrat

/** I'm just trying out a new package, not sure whether will use pWeb. */
package object pWeb
{ def tagVoidStr(tagName: String, attribs: XmlAtt *): String = attribs.foldLeft("<" + tagName)(_ + " " + _.str) + " />"
  def tagVoidStr(tagName: String, attribs: RArr[XmlAtt]): String = attribs.foldLeft("<" + tagName)(_ + " " + _.str) + " />"

  implicit class StringExtension(thisString: String)
  { /** This implicit method allows Strings to be used as XML content. */
    def xCon: XConText = XConText(thisString)

    def htmlB: HtmlB = HtmlB("thisString")

    def xmlAsString: XmlAsString = XmlAsString(thisString)
    
    def enTag(tag: String): String = "<" + tag + ">" + thisString + "</" + tag + ">"

    def htmlPath: String = "<code class='path'>" + thisString + "</code>"

    def htmlBash: String = "<code class='bash'>" + thisString + "</code>"
  }

  implicit class IntWebExtensions(thisInt: Int)
  { def px: CssPx = CssPx(thisInt)
    def em: CssEm = CssEm(thisInt)
  }

  implicit class DoubleWebExtensions(thisDouble: Double)
  { def px: CssPx = CssPx(thisDouble)
    def em: CssEm = CssEm(thisDouble)
  }

  /** Css margin-lop and bottom declarations. */
  def CssMargTopBot(value: CssVal): RArr[CssDec] = RArr(DecMargTop(value), DecMargBottom(value))

  /** Css margin-left and margin-right declarations set to same value. */
  def DecMargLeftRight(value: CssVal): RArr[CssDec] = RArr(DecMargLeft(value), DecMargRight(value))

  /** Css margin-left and margin-right declarations set to auto. */
  def DecMargLeftRightAuto: RArr[CssDec] = RArr(DecMargLeft(CssAuto), DecMargRight(CssAuto))
}