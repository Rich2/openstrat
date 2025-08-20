/* Copyright 2018-25 Richard Oliver. Licensed under Apache Licence version 2.0. */
package ostrat

/** I'm just trying out a new package, not sure whether you will use pWeb. */
package object pWeb
{ /** The max line length for code is set at 160 characters. */
  inline val MaxLineLen: 160 = 160
  
  extension(thisString: String)
  { /** Implicit method to return an HTML bold element. */
    def htmlB: HtmlB = HtmlB(thisString)

    def xmlAsString: XmlAsString = XmlAsString(thisString)
    
    def enTag(tag: String): String = "<" + tag + ">" + thisString + "</" + tag + ">"
    
    /** Extension method to return an HTML gile / directory path code element. */
    def htmlPath: HtmlDirPath = HtmlDirPath(thisString)

    /** Extension method to return an HTML sbt plass code element. */
    def htmlSbt: HtmlSbtInline = HtmlSbtInline(thisString)

    /** Implicit method to return an HTML Scala element. */
    def htmlScala: HtmlScalaInline = HtmlScalaInline(thisString)

    /** Implicit method to return an HTML Bash element. */
    def htmlBash: HtmlBashInline = HtmlBashInline(thisString)
  }

  implicit class IntWebExtensions(thisInt: Int)
  { /** Extension method for CSS px units. Pixels (px) are relative to the viewing device. For low-dpi devices, 1px is one device pixel (dot) of the display.
     * For printers and high resolution screens 1px implies multiple device pixels. */
    def px: CssPx = CssPx(thisInt)

    /** Extension method for CSS em units. Relative to the font-size of the element (2em means 2 times the size of the current font) */
    def em: CssEm = CssEm(thisInt)

    /** Extension method for CSS vw units. Relative to 1% of the width of the viewport */
    def vw: CssVw = CssVw(thisInt)

    /** Extension method for CSS vh units. Relative to 1% of the height of the viewport */
    def vh: CssVh = CssVh(thisInt)
  }

  implicit class DoubleWebExtensions(thisDouble: Double)
  { /** Extension method for CSS px units. Pixels (px) are relative to the viewing device. For low-dpi devices, 1px is one device pixel (dot) of the display.
     * For printers and high resolution screens 1px implies multiple device pixels. */
    def px: CssPx = CssPx(thisDouble)

    /** Extension method for CSS em units. Relative to the font-size of the element (2em means 2 times the size of the current font) */
    def em: CssEm = CssEm(thisDouble)

    /** Extension method for CSS vw units. Relative to 1% of the width of the viewport. */
    def vw: CssVw = CssVw(thisDouble)

    /** Extension method for CSS vh units. Relative to 1% of the height of the viewport. */
    def vh: CssVh = CssVh(thisDouble)
  }
}