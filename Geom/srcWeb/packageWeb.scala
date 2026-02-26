/* Copyright 2018-26 Richard Oliver. Licensed under Apache Licence version 2.0. */
package ostrat

/** I'm just trying out a new package, not sure whether you will use pWeb. */
package object pWeb
{ /** The max line length for code is set at 160 characters. */
  inline val MaxLineLen: 160 = 160

  /** Returns the output string for an HTML link, plus a tailing string, useful for commas, fullstops etc. where a space is not wanted. */
  def linkOut(path: String, label: String, tailStr: String): String = HtmlA(path, label).out + tailStr

  /** Returns the output string for an HTML link where the url is used as the label,plus a tailing string, useful for commas, fullstops etc. where a space is
   * not wanted. */
  def linkRawOut(path: String, tailStr: String): String = HtmlA(path).out + tailStr

  extension (thisString: String) {/** Implicit method to return an HTML bold element. */
    def htmlB: HtmlB = HtmlB(thisString)

    def xmlAsString: XmlAsString = XmlAsString(thisString)

    /** Encloses the [[String]] with XML / HTML opening and closing tags. */
    def enTag(tag: String): String = "<" + tag + ">" + thisString + "</" + tag + ">"
  }

  implicit class IntWebExtensions(thisInt: Int) {
    /** Extension method for CSS px units. Pixels (px) are relative to the viewing device. For low-dpi devices, 1px is one device pixel (dot) of the display.
     * For printers and high resolution screens 1px implies multiple device pixels. */
      def px: PxCss = PxCss(thisInt)

    /** Extension method for CSS em units. Relative to the font-size of the element (2em means 2 times the size of the current font) */
    def em: EmCss = EmCss(thisInt)

    /** Extension method for CSS vw units. Relative to 1% of the width of the viewport */
    def vw: VwCss = VwCss(thisInt)

    /** Extension method for CSS vh units. Relative to 1% of the height of the viewport */
    def vh: VhCss = VhCss(thisInt)
  }

  implicit class DoubleWebExtensions(thisDouble: Double)
  { /** Extension method for CSS px units. Pixels (px) are relative to the viewing device. For low-dpi devices, 1px is one device pixel (dot) of the display.
     * For printers and high resolution screens 1px implies multiple device pixels. */
      def px: PxCss = PxCss(thisDouble)

    /** Extension method for CSS em units. Relative to the font-size of the element (2em means 2 times the size of the current font) */
    def em: EmCss = EmCss(thisDouble)

    /** Extension method for CSS vw units. Relative to 1% of the width of the viewport. */
    def vw: VwCss = VwCss(thisDouble)

    /** Extension method for CSS vh units. Relative to 1% of the height of the viewport. */
    def vh: VhCss = VhCss(thisDouble)
  }

  extension (thisArr: Arr[String])
  { def toDivLines: RArr[HtmlDiv] = thisArr.map{
      case "" => HtmlDiv(HtmlBr)
      case l => HtmlDiv(l)
    }

    def toSystemdDivs: RArr[HtmlDiv] = thisArr.map{
      case "" => HtmlDiv(HtmlBr)
      case line if line(0) == '[' => HtmlDiv.colour(Colour.LightGreen, line)
      case l => HtmlDiv(l)
    }
  }
}