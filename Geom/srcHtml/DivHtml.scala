/* Copyright 2018-26 Richard Oliver. Licensed under Apache Licence version 2.0. */
package ostrat; package pweb

/** HTML Div element.  */
trait DivHtml extends HtmlUnvoid
{ override def tagName: String = "div"
}

/** Companion object for the [[DivHtml]] DIV element class, contains various factory methods. */
object DivHtml extends HtmlElemFullCompanion[DivHtml]
{ /** Factory apply method for div HTML element. There is an apply overload that takes an [[RArr]] of [[XConInedit]] and an [[RArr]] of [[XAtt]], with a default
   * of no [[XAtt]]s. */
  def apply(input: XCon*): DivHtml = new DivHtmlGen(input.toRArr, RArr())

  /** Factory apply method for div HTML element. */
  override def apply(contents: RArr[XCon], attribs: RArr[XAtt] = RArr()): DivHtml = new DivHtmlGen(contents, attribs)

  override def fromStr(str: String, attribs: RArr[XAtt]): DivHtml = new DivHtmlGen(RArr(str), attribs)

  /** Factory apply method for creating HTML span element with a display attribute. */
  def display(contents: XConInedit*)(otherDisplay: CssDec*) = new DivHtmlGen(contents.toArr, RArr(StyleAtt(otherDisplay.toArr)))

  /** An implementation class for the general case of an HTML Div.  */
  class DivHtmlGen(val contents: RArr[XCon], val attribs: RArr[HAtt]) extends DivHtml, HtmlOwnLine
}

/** HTML Div that requires its own line in the editor. */
trait DivLine extends DivHtml, HtmlOwnLine

/** Class for creating HTML Div element with a Style attribute with a colour declaration. */
class DivColour(colour: Colour, val contents: RArr[XCon], val otherAttribs: RArr[XAtt]) extends DivLine
{ override def attribs: RArr[HAtt] = StyleAtt(ColourDec(colour)) %: otherAttribs
}

object DivColour
{/** Factory method for creating an HTML Div element with a Style attribute with a colour declaration. */
  def apply(colour: Colour, contents: XCon*): DivColour = new DivColour(colour, contents.toRArr, RArr())
}