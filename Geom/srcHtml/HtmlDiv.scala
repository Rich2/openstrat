/* Copyright 2018-25 Richard Oliver. Licensed under Apache Licence version 2.0. */
package ostrat; package pWeb

/** HTML Div element.  */
trait HtmlDiv extends HtmlUnvoid
{ override def tagName: String = "div"
}

/** Companion object for the [[HtmlDiv]] DIV element class, contains various factory methods. */
object HtmlDiv
{ /** Factory apply method for div HTML element. There is an apply overload that takes an [[RArr]] of [[XConInline]] and an [[RArr]] of [[XAtt]], with a default of no
 * [[XAtt]]s. */
  def apply(input: XCon*): HtmlDiv = new HtmlDivGen(input.toRArr, RArr())

  /** Factory apply method for div HTML element. */
  def apply(contents: RArr[XCon], attribs: RArr[XAtt] = RArr()): HtmlDiv = new HtmlDivGen(contents, attribs)

  /** Factory method to create Div element with an ID attribute. */
  def id(id: String, contents: XCon*): HtmlDiv = new HtmlDivGen(contents.toArr, RArr(IdAtt(id)))

  /** Factory method to create Div element with a class attribute. */
  def classAtt(id: String, contents: XCon*): HtmlDiv = new HtmlDivGen(contents.toArr, RArr(ClassAtt(id)))

  /** Factory apply method for creating HTML span element with a display attribute. */
  def display(contents: XConInline*)(otherDisplay: CssDec*) = new HtmlDivGen(contents.toArr, RArr(StyleAtt(otherDisplay.toArr)))

  /** Factory method for creating HTML Div element with a Style attribute with a colour declaration. */
  def colour(colour: Colour, contents: XConInline*): HtmlDiv = new HtmlDivGen(contents.toArr, RArr(StyleAtt(ColourDec(colour))))

  /** Creates a Div and registers the textContent with an HTML Text Input. */
  def inputText(input: InputUpdaterText)(f: String => String): HtmlDiv =
  { def newId = input.next1Id(f)
    new HtmlDivGen(RArr(f(input.valueStr)), RArr(newId))
  }

  /** Creates a Div and registers the textContent with an HTML number Input. */
  def inputNum(input: InputUpdaterNum)(f: Double => String): HtmlDiv =
  { val newId: IdAtt = input.next1Id(f)
    new HtmlDivGen(RArr(f(input.value)), RArr(newId))
  }

  /** An implementation class for the general case of an HTML Div.  */
  class HtmlDivGen(val contents: RArr[XCon], val attribs: RArr[XAtt]) extends HtmlDiv, HtmlOwnLine
}

trait HtmlDivLine extends HtmlDiv, HtmlOwnLine