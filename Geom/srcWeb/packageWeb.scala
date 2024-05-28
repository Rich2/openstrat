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
  { /** Extensiom method for CSS px units. Pixels (px) are relative to the viewing device. For low-dpi devices, 1px is one device pixel (dot) of the
     * display. For printers and high resolution screens 1px implies multiple device pixels. */
    def px: CssPx = CssPx(thisInt)

    /** Extensiom method for CSS em units. Relative to the font-size of the element (2em means 2 times the size of the current font) */
    def em: CssEm = CssEm(thisInt)

    /** Extensiom method for CSS vh units. Relative to 1% of the height of the viewport */
    def vh: CssVh = CssVh(thisInt)
  }

  implicit class DoubleWebExtensions(thisDouble: Double)
  { /** Extensiom method for CSS px units. Pixels (px) are relative to the viewing device. For low-dpi devices, 1px is one device pixel (dot) of the
     * display. For printers and high resolution screens 1px implies multiple device pixels. */
    def px: CssPx = CssPx(thisDouble)

    /** Extensiom method for CSS em units. Relative to the font-size of the element (2em means 2 times the size of the current font) */
    def em: CssEm = CssEm(thisDouble)

    /** Extensiom method for CSS vh units. Relative to 1% of the height of the viewport. */
    def vh: CssVh = CssVh(thisDouble)
  }

  /** Css margin-lop and bottom declarations. */
  def CssMargTopBot(value: CssVal): RArr[CssDec] = RArr(DecMargTop(value), DecMargBottom(value))

  /** Css margin-left and margin-right declarations set to same value. */
  def DecMargLeftRight(value: CssVal): RArr[CssDec] = RArr(DecMargLeft(value), DecMargRight(value))

  /** Css margin-left and margin-right declarations set to auto. */
  def DecMargLeftRightAuto: RArr[CssDec] = RArr(DecMargLeft(CssAuto), DecMargRight(CssAuto))
}